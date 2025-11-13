import java.awt.Color;
import java.util.LinkedList;
import java.awt.Graphics;

enum Type { HEXAGON, SQUARE, TRIANGLE }
enum Explored { 
    NONE(Color.BLACK), 
    PARTIALLY(Color.ORANGE), 
    FULL(Color.GREEN);

    private Color color;

    Explored(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}

abstract class Shape {
    protected int length;
    protected Type type;
    protected int i, j;

    protected Explored explored = Explored.NONE;

    protected LinkedList<Shape> neighbors = new LinkedList<>();
    protected boolean[] isConnected;

    protected Shape(int length, Type type, int i, int j) {
        this.length = length;
        this.type = type;
        this.i = i;
        this.j = j;
    }

    public Type getType() {
        return type;
    }

    public boolean isExplored() {
        return this.explored == Explored.FULL;
    }

    public boolean isPartiallyExplored() {
        return this.explored == Explored.PARTIALLY;
    }

    public void setExplored(Explored explored) {
        this.explored = explored;
    }

    public void addNeighbor(Shape neighbor, int index) {
        if (index >= 0 && index < neighbors.size()) {
            neighbors.set(index, neighbor);
        }
    }

    public LinkedList<Shape> getNeighbors() {
        return neighbors;
    }

    public void connectNeighbor(Shape neighbor) {
        if (neighbor == null) return;
        int position = neighbors.indexOf(neighbor);
        isConnected[position] = true;
        int opposite = neighbor.neighbors.indexOf(this);
        neighbor.isConnected[opposite] = true;
    }

    // ogni sottoclasse implementa il proprio disegno
    public abstract void draw(Graphics g);
}
