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
    private final PlayerRepository playerRepository;
    private PlayerUseCase playerUseCase;


    @Autowired
    PlayerRestController(RoomRepository roomRepository, PlayerRepository playerRepository) {
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;

        playerUseCase = new PlayerUseCase();
    }

    @PutMapping("moveup")
    public Room doMoveUp() {
        return doMove(Room.UP);
    }

    @PutMapping("movedown")
    public Room doMoveDown() {
        return doMove(Room.DOWN);
    }

    @PutMapping("moveright")
    public Room doMoveRight() {
        return doMove(Room.RIGHT);
    }

    @PutMapping("moveleft")
    public Room doMoveLeft() {
        return doMove(Room.LEFT);

    }



    private Room doMove(int direccio) {

        // Per fer el moviment recuperem el jugador y la seva habitació actual
        Player player = playerRepository.findAll().get(0);

        Room room = roomRepository.findByPosition(player.getPosition())
                .orElseThrow(
                        () -> new game.RoomNotFoundException(player.getPosition()));

        playerUseCase.move(player, room, direccio);

        room = roomRepository.findByPosition(player.getPosition())
                .orElseThrow(
                        () -> new game.RoomNotFoundException(player.getPosition()));

        playerRepository.save(player);

        return room;


    }


}
