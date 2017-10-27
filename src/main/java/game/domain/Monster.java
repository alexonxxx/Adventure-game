package game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Àlex on 19/10/2017.
 */
@Entity
public class Monster {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int weapon;
    private int shield;
    private int item_code;

    public Monster(){//jpa

    }
    public Monster(String name, int shield, int weapon, int object_code){
        this.shield=shield;
        this.weapon=weapon;
        this.name=name;
        this.item_code=object_code;

    }

    public Long getId() {

        return this.id;

    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return shield;
    }

    public int getAttack() {
        return weapon;
    }
    public int getItem() { return item_code;}

}
