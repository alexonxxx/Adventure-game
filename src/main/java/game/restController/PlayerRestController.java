package game.restController;

import game.domain.Player;
import game.repositories.PlayerRepository;
import game.useCases.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerRestController {

    private final PlayerUseCase playerUseCase;

    @Autowired
    public PlayerRestController(PlayerUseCase playerUseCase) {
        this.playerUseCase = playerUseCase;
    }

    @GetMapping("/getStatus")
    public Player getStatus() {

        return this.playerUseCase.getFirst();

    }
}
