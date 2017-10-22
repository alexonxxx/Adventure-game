package game.restControllers;


public class Coord {

    public Position[] room;

    Coord(int x, int y) {
        this.room = new Position[2];
        this.room[0] = new PosX(x);
        this.room[1] = new PosY(y);
    }

    abstract class Position {};

    class PosX extends Position {
        public int x;
        PosX(int x) {
            this.x = x;
        }
    }

    class PosY extends Position {
        public int y;
        PosY(int y) {
            this.y = y;
        }
    }

}


