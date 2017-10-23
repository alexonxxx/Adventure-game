package game;

import game.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class RoomTest {


    @Test
    public void showRoomExits() {

        Room room = new Room(0,0,"", 0, 0, 1, 0, 5, -1);

        System.out.println(room.toString());



    }
}
