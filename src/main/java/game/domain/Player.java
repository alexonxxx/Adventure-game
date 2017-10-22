package game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Player(String name) {
        this.name = name;
    }


    public String getPosition() {
        return x +"-"+ y;
    }



}