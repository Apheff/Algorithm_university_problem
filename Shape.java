import java.awt.Color;
import java.util.LinkedList;
import java.awt.Graphics;

abstract class Shape {
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

    protected int length;
    protected Type type;
    protected int i;
    protected int j;
    private boolean visited = false;
    protected boolean[] isConnected;
    private boolean hasConnection = false;
    protected LinkedList<Shape> neighbors;
    protected Color color = Color.GRAY;
    // unvisited -> gray
    // visited -> orange
    // fully explored -> green

    protected Shape(int length, Type type, int i, int j) {
        this.length = length;
        this.type = type;
        this.i = i;
        this.j = j;
        //this.randomInt = random.nextInt(type.getValue());
        isConnected = new boolean[type.getValue()];
        neighbors = new LinkedList<>();
        for (int ii = 0; ii < type.getValue(); ii++)
            neighbors.add(null);
    }

    public Type getType() {
        return type;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
        this.color = visited ? Color.WHITE : Color.GRAY;
    }

    public Color getColor() {
        return color;
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
        hasConnection = true;
        neighbor.hasConnection = true;
    }

    public boolean hasConnection() {
        return hasConnection;
    }

    // ogni sottoclasse implementa il proprio disegno
    public abstract void draw(Graphics g);
}
