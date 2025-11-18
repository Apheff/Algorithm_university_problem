import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class AlgorithmRunner implements Runnable {

    private LabyrinthAlgorithm labyrinthAlgorithm;
    private LabyrinthDrawer labyrinthDrawer;
    private Thread thread;
    private int dimension;
    static int width = 50;
    static int height = 30;
    static int shapeSize = 20;
    static Type shapeType = Type.SQUARE; // 4: square, 6: hexagon, 3: triangle, (add to enum new Type accordingly)

    public static void main(String[] args) {
        /*
        AlgorithmRunner runner[] = new AlgorithmRunner[10];
        for (int i = 1; i <= 10; i++)
        runner[i - 1] = new AlgorithmRunner(i * 100, i * 100, 10, Type.SQUARE);
        
        runner[0].startThread();
        for (int i = 1; i <= 9; i++) {
            while (runner[i - 1].thread.isAlive()) {}
            runner[i].startThread();
            }
        */
        
        if (args.length > 0) {
            try {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
                shapeSize = Integer.parseInt(args[2]);
                shapeType = switch (args[3].toLowerCase()) {
                    case "square" -> Type.SQUARE;
                    case "hexagon" -> Type.HEXAGON;
                    case "triangle" -> Type.TRIANGLE;
                    default -> Type.SQUARE;
                };
            } catch (NumberFormatException e) {
                System.out.println("Using default width value: " + width);
                System.out.println("Using default height value: " + height);
                System.out.println("Using default shape size value: " + shapeSize);
                System.out.println("Using default shape type value: square");
            }
        } 
    
        SwingUtilities.invokeLater(() -> {
            AlgorithmRunner runner = new AlgorithmRunner(width, height, shapeSize, shapeType);
            runner.startThread();
        });
    }

    public AlgorithmRunner(int dimensionX, int dimensionY, int shapeDimension, Type shapeType) {
        this.dimension = dimensionX * dimensionY;
        BufferedImage buffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);

        LabyrinthGenerator generator = new LabyrinthGenerator(dimensionX, dimensionY, shapeDimension, shapeType, buffer);
        Shape[][] labyrinth = generator.getLabyrinth();
        System.out.println(this.dimension + " shapes labyrinth generated.");

        this.labyrinthAlgorithm = new LabyrinthAlgorithm(labyrinth, buffer);
        this.labyrinthDrawer = new LabyrinthDrawer(buffer);

        this.thread = new Thread(this);
        //this.thread.start();
    }

    private void startThread() {
        this.thread.start();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (labyrinthAlgorithm.running()) {
            labyrinthAlgorithm.update();
            labyrinthDrawer.repaint();
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Number of shapes: " + this.dimension + ", completed in " + (endTime - startTime) + " ms.");
    }
}
