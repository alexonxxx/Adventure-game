package game;

import game.domain.Player;
import game.domain.Room;
import game.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AutoConfigurationPackage
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(
            PlayerRepository playerRepository
    ) {

        return (evt) -> {

            // Inicialitzem un jugador
            playerRepository.save(new Player("Pepe"));

        };


    }

}