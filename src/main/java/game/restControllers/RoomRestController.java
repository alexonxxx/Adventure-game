package game.restControllers;

import game.domain.Room;
import game.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/")
public class RoomRestController {

    private final RoomRepository roomRepository;

    @Autowired
    RoomRestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Iterable<Room> getAllRooms() {
        //this.validateUser(userId);
        return this.roomRepository.findAll();
    }

/*

    @RequestMapping(method = RequestMethod.GET)
    Collection<Room> getByRoomId(@PathVariable String roomId) {
        //this.validateUser(userId);
        return this.roomRepository.findById(roomId);
    }
*/



}
