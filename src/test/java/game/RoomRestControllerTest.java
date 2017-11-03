package game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


import game.repositories.RoomRepository;
import game.domain.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class RoomRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Room room;

    private List<Room> roomList = new ArrayList<>();

    @Autowired
    private RoomRepository roomRepository;

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

        this.roomList.add(roomRepository.save(new Room(0,0,"", 0, 0, 0, 0, -1, -1)));
        this.roomList.add(roomRepository.save(new Room(1,1,"", 0, 0, 0, 0, -1, -1)));
    }
    @Test
    public void showRoom() throws Exception {
        mockMvc.perform(get("/room/1"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.id", is(this.roomList.get(0).getId().intValue())))
//                .andExpect(jsonPath("$.description", is(this.roomList.get(0).description)))
        ;
    }

    @Test
    public void addRoom() throws Exception {

        Room room = new Room(0,0,"Nova habitació", 0, 0, 0, 0, -1, -1);

        this.mockMvc.perform(post("/room")
                .contentType(contentType)
                .content(json(room)))
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void deleteRoom() throws Exception {

        this.mockMvc.perform(delete("/room/"
                + this.roomList.get(0).getId()))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void deleteNonExistingRoom() throws Exception {

        this.mockMvc.perform(delete("/room/1000"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void readSingleRoom() throws Exception {
        mockMvc.perform(get("/room/"
                + this.roomList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.roomList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.description", is(this.roomList.get(0).description)))
                ;
    }

    @Test
    public void readRooms() throws Exception {
        mockMvc.perform(get("/room/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.roomList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].description", is(this.roomList.get(0).description)))
                .andExpect(jsonPath("$[1].id", is(this.roomList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].description", is(this.roomList.get(1).description)))
                ;
    }

    @Test
    public void createRoom() throws Exception {
        String roomJson = json(new Room(1,0, "", 0, 0, 0, 0, -1, -1));

        this.mockMvc.perform(post("/room")
                .contentType(contentType)
                .content(roomJson))
                .andExpect(status().isCreated());
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
