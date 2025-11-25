import java.awt.Color;
import java.util.LinkedList;
import java.awt.Graphics;



// ====================== Enum Classes ====================== //
// different shape types (must add other shapes here if wanted and use a custom labyrinthGerenator method)
enum Type { 
    HEXAGON(6), 
    SQUARE(4), 
    TRIANGLE(3);

    private int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

// sets the color of different shapes depending on the Visited state
enum Explored { 
    NEVER(Color.decode("#1A2A4F")),
    PARTIALLY(Color.decode("#F7A5A5")),
    FULLY(Color.decode("#FFF2EF"));

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

    protected Explored explored = Explored.NEVER;

    protected LinkedList<Shape> neighbors = new LinkedList<>();
    protected boolean[] isConnected;

    protected Shape(int length, Type type, int i, int j) {
        this.length = length;
        this.type = type;
        this.i = i;
        this.j = j;
    }
    
    // ====================== Boolean Methods ====================== //
    // Checks if the node is not explored, partially explored or fully explored
    public boolean isFullyExplored() {
        return this.explored == Explored.FULLY;
    }
    
    public boolean isNeverExplored() {
        return this.explored == Explored.NEVER;
    }
    
    // ====================== Void Methods ====================== //
    public void setExplored(Explored explored) {
        this.explored = explored;
    }

    public void addNeighbor(Shape neighbor, int index) {
        if (index >= 0 && index < neighbors.size()) {
            neighbors.set(index, neighbor);
        }
    }
    
    public void connectNeighbor(Shape neighbor) {
        if (neighbor == null) return;
        int position = neighbors.indexOf(neighbor);
        isConnected[position] = true;
        int opposite = neighbor.neighbors.indexOf(this);
        neighbor.isConnected[opposite] = true;
    }

    // ====================== Get Methods ====================== //
    public Type getType() {
        // not used for this project
        return type;
    }

    public LinkedList<Shape> getNeighbors() {
        return neighbors;
    }

    // Every subclass must implement this method
    public abstract void draw(Graphics g);
}
