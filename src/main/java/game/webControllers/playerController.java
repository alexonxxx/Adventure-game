package game.webControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import game.domini.*;


@RestController
public class playerController {


    Room room;
    playerController(){
        room = new Room() ;
    }

    @PutMapping("moveup")
    public String doMoveUp() {
        room.y++;
        return "{\n" +
                "\"room\": [{\"x\":"+ room.x +"},{\"y\":\""+ room.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("movedown")
    public String doMoveDown() {
        room.y--;
        return "{\n" +
                "\"room\": [{\"x\":"+ room.x +"},{\"y\":\""+ room.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("moveright")
    public String doMoveRight() {
        room.x++;
        return "{\n" +
                "\"room\": [{\"x\":"+ room.x +"},{\"y\":\""+ room.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }

    @PutMapping("moveleft")
    public String doMoveLeft() {
        room.x--;
        return "{\n" +
                "\"room\": [{\"x\":"+ room.x +"},{\"y\":\""+ room.y +"\"}],\n" +
                "\"error\": \"\",\n" +
                "}";
    }


}
