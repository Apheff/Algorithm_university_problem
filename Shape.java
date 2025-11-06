import java.awt.Color;

public class Shape {
    enum Type {
        HEXAGON(6),
        SQUARE(4),
        TRIANGLE(3);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private int length = 1;
    private Type type;
    private boolean visited = false;
    private Color color = Color.GRAY;
    // unvisited -> gray
    // visited -> orange
    // fully explored -> green

    public Shape(Type type) {
        this.type = type;
    }
    public Shape(int length, Type type) {
        this.length = length;
        this.type = type;
    }

    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
