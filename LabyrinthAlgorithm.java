import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;


public class LabyrinthAlgorithm {
    private LinkedList<Shape> partialQueue = new LinkedList<>();
    private LinkedList<Shape> connectionQueue = new LinkedList<>();
    private Random random = new Random();
    private BufferedImage buffer;
    private int sides;

    public LabyrinthAlgorithm(Shape[][] labyrinth, BufferedImage buffer) {
        this.buffer = buffer;

        /*
            here is where all the magic begins, this is the setup for <== THE REAL ALGORITHM ==>
            we randomically select the starting shape and add it to the partialQueue 
        */
        int[] startingPoint = { random.nextInt(labyrinth.length), random.nextInt(labyrinth[0].length) };
        Shape startingShape = labyrinth[startingPoint[0]][startingPoint[1]];
        sides = startingShape.getType().getValue();
        partialQueue.add(startingShape);
    }

    /*
        <== THE REAL ALGORITHM ==>
          complexity -> O(V * E)
    */ 
    // this method iterates until there are no more shapes to visit -> O(V)
    public void update(boolean test) {
        // here we generate a random index to choose the next shape to be visited from the partialQueue
        int randomIndex = random.nextInt(Math.min(partialQueue.size(), sides));

        Shape currentShape = partialQueue.remove(randomIndex);
        currentShape.setExplored(Explored.FULLY);
        
        // gathering information about the neighbors to the currentShape to choose the right way to proceed
        for (Shape neighbor : currentShape.getNeighbors()) {
            if (neighbor == null) continue;

            // there are two cases:
            // 1. if the neighbor is fully visited we add it to the currentShape's connectionQueue (a list of possible shapes to connect)
            //    the visited neighbor is then choosed randomly below.. 
            // 
            // 2. if the neighbor is never explored we add it to the parialQueue and set it to partially visited
            if (neighbor.isFullyExplored()) {
                connectionQueue.add(neighbor);
            } else if (neighbor.isNeverExplored()) {
                partialQueue.addFirst(neighbor);
                neighbor.setExplored(Explored.PARTIALLY);
            }
        }
        // finally we select a neighbor blindly to generate entropy
        Shape connectedNeighbor = null;
        if (connectionQueue.size() > 1) {
            connectedNeighbor = connectionQueue.get(randomIndex % connectionQueue.size());
            currentShape.connectNeighbor(connectedNeighbor);
            connectionQueue.clear();
        } else if (connectionQueue.size() == 1) {
            connectedNeighbor = connectionQueue.remove();
            currentShape.connectNeighbor(connectedNeighbor);
        }

        if (test) return;
        // draw to the buffer the currentShape and his neighbors
        currentShape.draw(buffer.getGraphics());
        for (Shape neighbor : currentShape.getNeighbors())
            if (neighbor != null)
                neighbor.draw(buffer.getGraphics());
        /*
                                 _                       
                                 \`*-.                   
                                  )  _`-.                
                                 .  : `. .               
                                 : _   '  \              
                                 ; *` _.   `*-._         
                                 `-.-'          `-.      
                                   ;       `       `.    
                                   :.       .        \   
                                   . \  .   :   .-'   .  
                                   '  `+.;  ;  '      :  
                                   :  '  |    ;       ;-.
                                   ; '   : :`-:     _.`* ;
            [nothing to see]    .*' /  .*' ; .*`- +'  `*'  
                                `*-*   `*-*  `*-*'    
        */
    }

    public boolean running() {
        return !partialQueue.isEmpty();
    }
}
