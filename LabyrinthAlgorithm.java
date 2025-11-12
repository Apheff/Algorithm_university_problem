import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;


public class LabyrinthAlgorithm {
    private LinkedList<Shape> queue = new LinkedList<>();
    private LinkedList<Shape> connectionQueue = new LinkedList<>();
    private Random random = new Random();
    private BufferedImage buffer;

    public LabyrinthAlgorithm(Shape[][] labyrinth, BufferedImage buffer) {
        this.buffer = buffer;

        int[] startingPoint = { random.nextInt(labyrinth.length), random.nextInt(labyrinth[0].length) };
        Shape startingShape = labyrinth[startingPoint[0]][startingPoint[1]];
        queue.add(startingShape);
    }

    public void update() {
        int randomIndex = random.nextInt(queue.size());
        //int randomIndex = 0;
        Shape currentShape = queue.remove(randomIndex);
        currentShape.setVisited(true);
        
        for (Shape neighbor : currentShape.getNeighbors()) {
            if (neighbor == null) continue;
            if (neighbor.isVisited()) {
                connectionQueue.add(neighbor);
            } else if (!queue.contains(neighbor)) {
                queue.add(neighbor);
            }
        }
        Shape connectedNeighbor = null;
        if (connectionQueue.size() > 1) {
            connectedNeighbor = connectionQueue.get(randomIndex % connectionQueue.size());
            currentShape.connectNeighbor(connectedNeighbor);
            connectionQueue.clear();
        } else if (connectionQueue.size() == 1) {
            connectedNeighbor = connectionQueue.remove();
            currentShape.connectNeighbor(connectedNeighbor);
        }
        currentShape.draw(buffer.getGraphics());
        if (connectedNeighbor != null) {
            connectedNeighbor.draw(buffer.getGraphics());
        }
    }

    public boolean running() {
        return !queue.isEmpty();
    }
}
