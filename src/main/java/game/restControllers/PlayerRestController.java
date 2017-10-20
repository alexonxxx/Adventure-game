package game.restControllers;

import game.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import game.domain.*;


@RestController
@RequestMapping("/player/")
public class PlayerRestController {

    private RoomRepository roomRepository;
    Player player;

    @Autowired
    PlayerRestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        player = new Player();
    }


    private void doMove(int dx, int dy) {

        Room room = roomRepository.findByPosition(player.x +"-"+player.y)
                .orElseThrow(
                        () -> new game.RoomNotFoundException(player.x,player.y));

        int v = room.salidas[Room.NORD];

        if (v < 0) {
            //mensaje("La salida no existe.\n");
        } else if (v == 0) {
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

    }

    @PutMapping("moveup")
    public String doMoveUp() {

        doMove(0,1);

        return "{\n" +
                "\"room\": [{\"x\":"+ player.x +"},{\"y\":\""+ player.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("movedown")
    public String doMoveDown() {
        player.y--;
        return "{\n" +
                "\"room\": [{\"x\":"+ player.x +"},{\"y\":\""+ player.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("moveright")
    public String doMoveRight() {
        player.x++;
        return "{\n" +
                "\"room\": [{\"x\":"+ player.x +"},{\"y\":\""+ player.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("moveleft")
    public String doMoveLeft() {
        player.x--;
        return "{\n" +
                "\"room\": [{\"x\":"+ player.x +"},{\"y\":\""+ player.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }


}
