package game.domain;

public class Item {

    private int x;
    private String name;
    private final String type;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
