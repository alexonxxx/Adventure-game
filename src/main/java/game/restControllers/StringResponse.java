package game.restControllers;

/**
 * Created by Àlex on 28/10/2017.
 */
public class StringResponse{

    private String message;


    public StringResponse(String response) {
        this.message = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
