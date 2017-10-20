package game;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

        public RoomNotFoundException(int x, int y) {
            super("could not find room x:'" + x + "'  y:'\" + y + \"'.");
        }
    }
