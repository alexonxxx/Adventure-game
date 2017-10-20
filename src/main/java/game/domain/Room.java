package game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Room {

    public static int NORD = 0;
    public static int SUD = 1;
    public static int EST = 2;
    public static int OEST = 3;

    public String description;

    public int salidas[]; // -1 cerrada, 0 abierta, > 0 llave
    public int codigo_objeto; // -1 no hay
    public int codigo_monstruo; // -1 no hay

    @Id
    @GeneratedValue
    private Long id;


    public String position;

    Room() { // jpa only
    }

    public Room(String position, String description, int norte, int sur, int este, int oeste) {

        this.position = position;
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

    public String getDescription() {
        return description;
    }

}
