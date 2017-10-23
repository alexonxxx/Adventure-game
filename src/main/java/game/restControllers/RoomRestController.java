package game.restControllers;

import game.domain.Room;
import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    RoomRestController(RoomRepository roomRepository, PlayerRepository playerRepository) {
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping(path = "/",
            produces = APPLICATION_JSON_VALUE)
    Iterable<Room> getAllRooms() {
        //this.validateUser(userId);
        return this.roomRepository.findAll();
    }

    @GetMapping(path="/room/{x}")
    Room getRoom(@PathVariable int x) {

        return new Room();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Room input) { //@PathVariable String userId,
//        this.validateUser(userId);


        Room result = roomRepository.save(new Room(input.getX(), input.getY(), input.description, input.salidas[0], input.salidas[1], input.salidas[2], input.salidas[3]));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();

    }




    @RequestMapping(method = RequestMethod.GET, value = "/{roomId}")
    Room readRoom( @PathVariable Long roomId) { //@PathVariable String userId,
        //this.validateUser(userId);
        return this.roomRepository.findOne(roomId);
    }


}
