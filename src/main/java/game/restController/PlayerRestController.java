package game.restController;

import game.domain.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerRestController {

    private Player player;

    public PlayerRestController() {
        this.player = new Player("pepe");
    }

    @GetMapping("/playerStatus/")
    public String getStatus() {

        return "{" +
                    "\"life\": " + this.player.getLife() + ", " +
                    "\"shield\": " + this.player.getShield() + ", " +
                    "\"weapon\": " + this.player.getWeapon() + ", " +
                    "\"key\": " + this.player.getKey() + ", " +
                    "\"room\":" +
                        "{ " +
                            "\"x\": " + this.player.getPosX() + ", " +
                            "\"y\": " + this.player.getPosY() + "" +
                        "} " +
                "}";

    }
}
