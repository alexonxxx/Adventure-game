package game.restControllers;

import game.PlayerAlreadyExistException;
import game.repositories.RoomRepository;
import game.useCases.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import game.domain.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/player")
public class PlayerRestController {

    private final RoomRepository roomRepository;

    private final PlayerUseCase playerUseCase;


    @Autowired
    PlayerRestController(RoomRepository roomRepository,  PlayerUseCase playerUseCase) {
        this.roomRepository = roomRepository;
        this.playerUseCase = playerUseCase;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addPlayer(@RequestBody Player input) { //@PathVariable String userId,
//        this.validateUser(userId);

        try {
            Player result = playerUseCase.save(new Player(input.getUsername()));

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(result.getId()).toUri();

            return ResponseEntity.created(location).build();

        } catch (PlayerAlreadyExistException e) {
            // log excpetion first, then return Conflict (409)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }



    @PutMapping("moveup")
    public Coord doMoveUp() {
        return doMove(Room.UP);
    }

    @PutMapping("movedown")
    public Coord doMoveDown() {
        return doMove(Room.DOWN);
    }

    @PutMapping("moveright")
    public Coord doMoveRight() {
        return doMove(Room.RIGHT);
    }

    @PutMapping("moveleft")
    public Coord doMoveLeft() {
        return doMove(Room.LEFT);

    }



    private Coord doMove(int direccio) {

        // Per fer el moviment recuperem el jugador y la seva habitació actual
        Player player = playerUseCase.getFirst();

        Room room = roomRepository.findByPosition(player.getPosition())
                .orElseThrow(
                        () -> new game.RoomNotFoundException(player.getPosition()));

        playerUseCase.move(player, room, direccio);

        return new Coord(player.x, player.y);

    }

    @GetMapping("/getStatus")
    public Player getStatus() {

        return this.playerUseCase.getFirst();

    }
    @GetMapping("/attackMonster")
    public StringResponse attackMonster(){
        StringResponse response= new StringResponse("there isn't any monster");
        Player player = playerUseCase.getFirst();
        Room room = roomRepository.findByPosition(player.getPosition())
                .orElseThrow(
                        () -> new game.RoomNotFoundException(player.getPosition()));

        if(room.getMonsterCode()==-1){

            return response;

        }
        else{
        boolean result= this.playerUseCase.resultAttackMonster(player,room);
        //depenent del resultat escriura una cosa o una altra

        if(result){
            response.setMessage("monster killed, an item has spawned");

        }
        else{
            response.setMessage("player loses");

        }
        }

        return response;
    }


}
