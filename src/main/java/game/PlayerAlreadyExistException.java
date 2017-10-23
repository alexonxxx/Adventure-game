package game;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlayerAlreadyExistException extends RuntimeException {

    public PlayerAlreadyExistException(String username) {

        super("Player username already exists:'" + username + "'.");
    }
}
