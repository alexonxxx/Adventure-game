package game;

import game.domain.Monster;
import game.domain.Player;
import game.domain.Room;
import game.repositories.MonsterRepository;
import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
import game.useCases.PlayerUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static game.domain.Room.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PlayerRestControllerTest {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerRestControllerTest.class);

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private  Room[][] mapa;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerUseCase playerUseCase;

    @Autowired
    private MonsterRepository monsterRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();



        // Inicialitzem les habitacions
        /* norte, sur, este, oeste
                 +---+
                 | 0 |
                 |0 0|
                 | 1 |
                 +---+
            +---++---++---+
            | 0 || 1 || 0 |
            |0 1||1 1||1 0|
            | 0 || 1 || 0 |
            +---++---++---+
                 +---+
                 | 1 |
                 |0 0|
                 | 0 |
                 +---+
                 [1,2]
            [0,1][1,1][2,1]
                 [1,0]
         */

        mapa = new Room[3][3];
        mapa[1][2] = new Room(1,2,"Adalt", TANCADA, TANCADA, TANCADA, OBERTA, -1, -1);
        mapa[0][1] = new Room(0,1,"Esquerra", TANCADA, TANCADA, OBERTA, TANCADA, -1, -1);
        mapa[1][1] = new Room(1,1,"Centre", OBERTA, OBERTA, OBERTA, OBERTA, -1, -1);
        mapa[2][1] = new Room(2,1,"Dreta", OBERTA, TANCADA, TANCADA, TANCADA, -1, -1);
        mapa[1][0] = new Room(1,0,"Abaix", TANCADA, OBERTA, TANCADA, TANCADA, -1, -1);

        this.roomRepository.deleteAllInBatch();

        for (int i = 0; i < mapa.length ; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] != null)
                    roomRepository.save(mapa[i][j]);
            }
        }

        this.playerRepository.deleteAllInBatch();
        Player player = new Player("usuari");
        playerUseCase.save(player);

        //Coloquem al player a la posicio inicial per fer les proves
        playerUseCase.movePlayerToRoom(player, mapa[1][1]);

        monsterRepository.save(new Monster("monster1",2,4,-1));

    }

    @Test
    public void addPlayer() throws Exception {

        Player player = new Player("nou usuari");

        this.mockMvc.perform(post("/player")
                .contentType(contentType)
                .content(json(player)))
                .andExpect(status().isCreated())
        ;
    }
    //variar


    @Test
    public void addPlayerAlreadyExists() throws Exception {

        Player player = new Player("usuari");

        this.mockMvc.perform(post("/player")
                .contentType(contentType)
                .content(json(player)))
                .andExpect(status().isConflict())
        ;
    }
    @Test
    public void attackMonster() throws Exception {

        Room actualRoom = mapa[1][1];
        actualRoom.setMonsterCode(1);
        roomRepository.save(actualRoom);
        this.mockMvc.perform(get("/player/attackMonster")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message").value("player loses"))

        ;
    }


    @Test
    public void moveright() throws Exception {

        Room origen = mapa[1][1]; // Centre
        Room destino = mapa[2][1]; // Dreta

        Player player =  playerUseCase.getFirst();

        playerUseCase.movePlayerToRoom(player, origen);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.room[0].x").value(destino.getX()))
                .andExpect(jsonPath("$.room[1].y").value(destino.getY()))
        ;
    }

    @Test
    public void moveleft() throws Exception {

        Room origen = mapa[1][1]; // Centre
        Room destino = mapa[0][1]; // Esquerra

        Player player =  playerUseCase.getFirst();

        this.mockMvc.perform(put("/player/moveleft")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.room[0].x").value(destino.getX()))
                .andExpect(jsonPath("$.room[1].y").value(destino.getY()))
        ;
    }


    @Test
    public void moveup() throws Exception {

        Room destino = mapa[1][2]; // Adalt

        Player player =  playerUseCase.getFirst();

        this.mockMvc.perform(put("/player/moveup")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.room[0].x").value(destino.getX()))
                .andExpect(jsonPath("$.room[1].y").value(destino.getY()))
        ;
    }
    @Test
    public void movedown() throws Exception {

        Room destino = mapa[1][0]; // Abaix

        Player player =  playerUseCase.getFirst();

        this.mockMvc.perform(put("/player/movedown")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.room[0].x").value(destino.getX()))
                .andExpect(jsonPath("$.room[1].y").value(destino.getY()))
        ;
    }



    @Test
    public void nomove() throws Exception {

        this.mockMvc.perform(put("/player/nomove")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isNotFound());
    }


    @Test
    public void noWayOut() throws Exception {

        Room origen = mapa[1][1]; // Centre

        origen.salidas[NORD] = TANCADA;
        origen.salidas[SUD] = TANCADA;
        origen.salidas[EST] = TANCADA;
        origen.salidas[OEST] = TANCADA;
        roomRepository.save(origen);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void moveRightDoor() throws Exception {

        Room origen = mapa[1][1]; // Centre

        origen.salidas[EST] = KEY_2;
        roomRepository.save(origen);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isNotFound())
        ;
    }





    @Test
    public void moveRightDoorWithKey() throws Exception {

        Room origen = mapa[1][1]; // Centre
        Room destino = mapa[2][1]; // Dreta

        origen.salidas[EST] = KEY_2;
        roomRepository.save(origen);

        Player player =  playerUseCase.getFirst();
        player.key = KEY_2;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.room[0].x").value(destino.getX()))
                .andExpect(jsonPath("$.room[1].y").value(destino.getY()))
        ;
    }

    @Test
    public void moveRightDoorWithWrongKey() throws Exception {

        Room origen = mapa[1][1]; // Centre
        Room destino = mapa[2][1]; // Dreta

        origen.salidas[EST] = KEY_2;
        roomRepository.save(origen);

        Player player =  playerUseCase.getFirst();
        player.key = KEY_3;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isNotFound())
        ;
    }



    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Test
    public void showPlayerStatus() throws Exception {

        Player player = this.playerUseCase.getFirst();

        this.mockMvc.perform(get("/player/getStatus")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.life").value(player.getLife()))
                .andExpect(jsonPath("$.username").value(player.getUsername()))
                .andExpect(jsonPath("$.weapon").value(player.getWeapon()))
                .andExpect(jsonPath("$.shield").value(player.getShield()))
                .andExpect(jsonPath("$.position").value(player.getPosition()))
                .andExpect(jsonPath("$.key").value(player.getKey()))
        ;
    }

}