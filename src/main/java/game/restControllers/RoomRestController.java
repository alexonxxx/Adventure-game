package game.restControllers;

import game.PlayerAlreadyExistException;
import game.domain.Room;
import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addRoom(@RequestBody Room input) { //@PathVariable String userId,
//        this.validateUser(userId);

        Room result = roomRepository.save(new Room(input.getX(), input.getY(), input.description, input.salidas[3], input.salidas[0], input.salidas[2], input.salidas[1], -1, -1));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path="/{Id}",method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long Id) {
        try {
            roomRepository.delete(Id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{roomId}")
    Room readRoom( @PathVariable Long roomId) { //@PathVariable String userId,
        //this.validateUser(userId);
        return this.roomRepository.findOne(roomId);
    }


}
