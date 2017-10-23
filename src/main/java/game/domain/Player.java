package game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {


    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private int life;
    private String name;
    private int weapon;
    private int shield;
    private int posX;
    private int posY;

    private int key;

    public Player() {

    }

    public Player(String name) {
        this.posX = 0;
        this.posY = 0;
        this.life = 20;
        this.name = name;
        this.weapon = 0;
        this.shield = 0;
        this.key = -99;
    }

    public Long getId() {
        return id;
    }

    public int getLife() {
        return life;
    }

    public String getName() {
        return name;
    }

    public int getWeapon() {
        return weapon;
    }

    public int getShield() {
        return shield;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getKey() {
        return key;
    }
}
