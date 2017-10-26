package game;

import game.domain.Player;
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
            RoomRepository roomRepository, PlayerRepository playerRepository
    ) {

        return (evt) -> {

<<<<<<< HEAD
            // Inicialitzem un jugador
            playerRepository.save(new Player("Pepe"));
=======
            // Inicialitzem les habitacions
            roomRepository.save(new Room(0,0,"Entrada", TANCADA, TANCADA, OBERTA, TANCADA, -1, -1));
            roomRepository.save(new Room(1,0,"Passadis", OBERTA, TANCADA, OBERTA, TANCADA, -1, -1));
            roomRepository.save(new Room(2,0,"Tresor", OBERTA, TANCADA, TANCADA, TANCADA, -1, -1));

            // Inicialitzem un jugador
            playerRepository.save(new Player("One"));
>>>>>>> b78c00e129583fde8734923b5d3e5de09e5ca52d

        };


    }
<<<<<<< HEAD

}
=======
}

>>>>>>> b78c00e129583fde8734923b5d3e5de09e5ca52d
