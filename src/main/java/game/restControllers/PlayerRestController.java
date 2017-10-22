package game.restControllers;

import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
import game.useCases.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import game.domain.*;


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


}
