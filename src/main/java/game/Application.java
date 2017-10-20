package game;

import game.domain.Room;
import game.repositories.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {


    int MAX_ROOMS = 3;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(RoomRepository roomRepository) {

        return (evt) -> {

            Room room[][] = new Room[MAX_ROOMS][MAX_ROOMS];

            for (int i = 0; i < MAX_ROOMS; i++) {
                for (int j = 0; j < MAX_ROOMS ; j++) {
                    room[i][j] = roomRepository.save(new Room(i+"-"+j,i+" "+j, 0,0,0,0 ));
                }
            }



        };
    }
}

