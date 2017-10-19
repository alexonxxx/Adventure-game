package game.domain;

public class Player {

    private int life;
    private Item shield;
    private Item weapon;
    private Item key;

    public Player() {

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Item getShield() {
        return shield;
    }

    public void setShield(Item shield) {
        this.shield = shield;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getKey() {
        return key;
    }

    public void setKey(Item key) {
        this.key = key;
    }
}
