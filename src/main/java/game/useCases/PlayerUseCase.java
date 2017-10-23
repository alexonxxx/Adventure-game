package game.useCases;

import game.domain.Player;
import game.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerUseCase {

    private PlayerRepository playerRepository;

    public PlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getFirst() {
        return this.playerRepository.findAll().get(0);
    }

}
