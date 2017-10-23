package game.useCases;

import game.PlayerAlreadyExistException;
import game.domain.Player;
import game.domain.Room;
import game.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class PlayerUseCase {

    private final PlayerRepository playerRepository;

    public PlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void movePlayerToRoom(Player player, Room room) {

        player.x = room.getX();
        player.y = room.getY();
        playerRepository.save(player);
    }


    public void move(Player player, Room room, int direccio) {

        // Comprovem el tipus de sortida de l'habitació en la direcció del moviment
        // -1 cerrada, 0 abierta, > 0 llave

        int v = room.salidas[direccio];

        if (v < 0) {
            throw  new game.ExitRoomNotFoundException();

        } else if (v == 0) {

            int dx = 0 , dy = 0;
            switch (direccio) {
                case Room.UP: dx=0; dy=1; break; //NORD
                case Room.DOWN: dx=0; dy=-1; break; //SUD
                case Room.RIGHT: dx=1; dy=0; break; //EST
                case Room.LEFT: dx=-1; dy=0; break; //OEST
            }

            player.x += dx;
            player.y += dy;

        } else if (v > 0) {
/*
            if (jugador->codigo_objeto_llave >= 0 && lobjetos.objetos[jugador->codigo_objeto_llave].atributo == v) {

                habitacion.salidas[salida]= 0;
                jugador->codigo_objeto_llave= -1;
                mhabitaciones[jugador->i][jugador->j]= habitacion;
                OpcionMover(lobjetos, mhabitaciones, jugador, di, dj, salida);
            } else {
                //mensaje("La salida esta cerrada y no llevas la llave.");
            }
*/
        }

        playerRepository.save(player);


    }

    public Player getFirst() {
        return playerRepository.findAll().get(0);
    }


    public Player save(Player player) throws PlayerAlreadyExistException {

        if (playerRepository.findByUsername(player.getUsername()).isPresent() )
            throw new PlayerAlreadyExistException(player.getUsername());

        return playerRepository.save(player);
    }

}
