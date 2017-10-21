package game;

import game.domain.Player;
import game.domain.Room;
import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import sun.security.ssl.Debug;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static game.domain.Room.TANCADA;
import static game.domain.Room.oberta;

import static org.hamcrest.Matchers.*;

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

        this.roomRepository.deleteAllInBatch();

        mapa = new Room[3][3];

        // Inicialitzem les habitacions

        mapa[0][1] = new Room(0,1,"Esquerra", TANCADA,TANCADA, oberta,TANCADA );
        mapa[1][1] = new Room(1,1,"Centre", oberta,oberta, oberta,oberta );
        mapa[2][1] = new Room(2,1,"Dreta", TANCADA,TANCADA, TANCADA,oberta );
        mapa[1][2] = new Room(1,2,"Adalt", TANCADA,oberta, TANCADA,TANCADA );
        mapa[1][0] = new Room(1,0,"Abaix", oberta,TANCADA, TANCADA,TANCADA );

        for (int j = 0; j < mapa.length; j++) {
            for (int i = 0; i < mapa[j].length ; i++) {
                if (mapa[i][j] != null)
                    roomRepository.save(mapa[i][j]);
            }
        }

    }

    @Test
    public void moveright() throws Exception {

        Room origen = mapa[1][1];
        Room destino = mapa[2][1];

        Player player =  playerRepository.findById(1L).get();
        player.x = 1;
        player.y = 1;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/moveright")
                .contentType(contentType)
                .content(json(origen)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(destino.getId().longValue()))
        ;
    }

    @Test
    public void moveleft() throws Exception {

        Room origen = mapa[1][1];
        Room destino = mapa[0][1];

        Player player =  playerRepository.findById(1L).get();
        player.x = 1;
        player.y = 1;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/moveleft")
                .contentType(contentType)
                .content(json(origen)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(destino.getId().longValue()))
        ;
    }


    @Test
    public void moveup() throws Exception {

        Room origen = mapa[1][1];
        Room destino = mapa[1][2];

        Player player =  playerRepository.findById(1L).get();
        player.x = 1;
        player.y = 1;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/moveup")
                .contentType(contentType)
                .content(json(origen)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(destino.getId().longValue()))
        ;
    }
    @Test
    public void movedown() throws Exception {

        Room origen = mapa[1][1];
        Room destino = mapa[1][0];

        Player player =  playerRepository.findById(1L).get();
        player.x = 1;
        player.y = 1;
        playerRepository.save(player);

        this.mockMvc.perform(put("/player/movedown")
                .contentType(contentType)
                .content(json(origen)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(destino.getId().longValue()))
        ;
    }



    @Test
    public void nomove() throws Exception {

        this.mockMvc.perform(put("/player/nomove")
                .contentType(contentType)
                .content(""))
                .andExpect(status().isNotFound());
    }




    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
