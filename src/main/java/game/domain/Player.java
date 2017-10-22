package game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int life;
    private String name;
    private int weapon;
    private int shield;
    private int posX;
    private int posY;

    private int key;

    public Player(String name) {
        this.posX = 0;
        this.posY = 0;
        this.life = 20;
        this.name = name;
        this.weapon = 0;
        this.shield = 0;
        this.key = 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getKey() {
        return (this.key == 0) ? null : this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
