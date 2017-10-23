package game.domain;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public Player() { // jpa only
    }

    public int x,y;

    @Column(name = "username", nullable = false, unique=true)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Player(String username) {
        this.username = username;
    }


    public String getPosition() {
        return x +"-"+ y;
    }



}