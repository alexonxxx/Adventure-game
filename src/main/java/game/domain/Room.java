package game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public static final int NORD = 0;
    public static final int SUD = 1;
    public static final int OEST = 2;
    public static final int EST = 3;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;


    public static final int OBERTA = 0;
    public static final int TANCADA = -1;
    public static final int KEY_1 = 1;
    public static final int KEY_2 = 2;
    public static final int KEY_3 = 3;


    public String description;

    public int salidas[]; // -1 cerrada, 0 abierta, > 0 llave
    public int codigo_objeto; // -1 no hay
    public int codigo_monstruo; // -1 no hay



    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String position;


    public Room() { // jpa only
    }

    public Room(int x, int y, String description, int west, int north, int east, int south, int object, int monster) {

        this.x = x;
        this.y = y;

        this.position = x+"-"+y;
        this.description = description;
        this.salidas = new int[4];

        this.salidas[NORD]= north;
        this.salidas[SUD]= south;
        this.salidas[EST]= east;
        this.salidas[OEST]= west;

        this.codigo_objeto = object;
        this.codigo_monstruo = monster;

        // TODO: afegir objecte
        // TODO: afegir monstre

    }

    public int getMonsterCode() {
        return codigo_monstruo;
    }

    public void setMonsterCode(int codigo_monstruo) {
        this.codigo_monstruo = codigo_monstruo;
    }

    /*
                    0
                0	-	0
                    0
             */
    public String toString() {

        String s = "";

        s += " "+"\t"+ this.salidas[NORD] +"\t"+" \n";
        s += this.salidas[OEST] +"\t"+ this.codigo_objeto +"\t"+ this.salidas[EST] +"\n";
        s += " "+"\t"+ this.salidas[SUD] +"\t"+" \n";

        return s;
    }


}
