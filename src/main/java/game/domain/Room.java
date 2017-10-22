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


    public static final int oberta = 0;
    public static final int TANCADA = -1;


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

    public Room(int x, int y, String description, int norte, int sur, int este, int oeste) {

        this.x = x;
        this.y = y;

        this.position = x+"-"+y;
        this.description = description;
        this.salidas = new int[4];

        this.salidas[NORD]= norte;
        this.salidas[SUD]= sur;
        this.salidas[EST]= este;
        this.salidas[OEST]= oeste;

        this.codigo_objeto = -1;
        this.codigo_monstruo = -1;

        // TODO: afegir objecte
        // TODO: afegir monstre

    }



}
