package game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExitRoomNotFoundException extends RuntimeException {
    public ExitRoomNotFoundException() {
        super("La salida no existe.\n");
    }

}
