package game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
public class Player {

    // INITIAL VALUES
    @JsonIgnore
    private final int INITIA_LIFE = 20;
    @JsonIgnore
    private final int INITIA_SHIELD = 0;
    @JsonIgnore
    private final int INITIA_WEAPON = 0;
    @JsonIgnore
    private final int INITIA_KEY = -1;
    @JsonIgnore
    private final int INITIA_POSX = 0;
    @JsonIgnore
    private final int INITIA_POSY = 0;
    // END INITIAL VALUES

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private int life;
    private int weapon;
    private int shield;
    public int x,y;

    private int key;

    @Column(name = "username", nullable = false, unique=true)
    private String username;

    public Player() {

    }

    public Player(String name) {
        this.x = this.INITIA_POSX;
        this.y = this.INITIA_POSY;
        this.life = this.INITIA_LIFE;
        this.username = name;
        this.weapon = this.INITIA_WEAPON;
        this.shield = this.INITIA_WEAPON;
        this.key = this.INITIA_KEY;
    }

    public Long getId() {
        return id;
    }

    public int getLife() {
        return life;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWeapon() {
        return weapon;
    }

    public int getShield() {
        return shield;
    }

    public String getPosition() {
        return x +"-"+ y;
    }

    public int getKey() {
        return key;
    }

    @JsonIgnore
    public int getINITIA_LIFE() {
        return INITIA_LIFE;
    }
    @JsonIgnore
    public int getINITIA_SHIELD() {
        return INITIA_SHIELD;
    }
    @JsonIgnore
    public int getINITIA_WEAPON() {
        return INITIA_WEAPON;
    }
    @JsonIgnore
    public int getINITIA_KEY() {
        return INITIA_KEY;
    }
    @JsonIgnore
    public int getINITIA_POSX() {
        return INITIA_POSX;
    }
    @JsonIgnore
    public int getINITIA_POSY() {
        return INITIA_POSY;
    }
}
