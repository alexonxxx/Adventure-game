package game;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomLockedException extends RuntimeException {

        public RoomLockedException(int key) {
            super("Key :'" + key + "' is needed.");
        }
    }
