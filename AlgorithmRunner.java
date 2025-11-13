import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class AlgorithmRunner implements Runnable {

    private LabyrinthAlgorithm labyrinthAlgorithm;
    private LabyrinthDrawer labyrinthDrawer;
    private Thread thread;
    private int dimension = 10000;

    public static void main(String[] args) {
        /*
        AlgorithmRunner runner[] = new AlgorithmRunner[10];
        for (int i = 11; i <= 13; i++)
        runner[i - 11] = new AlgorithmRunner(i * 200, i * 200, 10, Type.SQUARE);
        
        runner[0].startThread();
        for (int i = 1; i <= 2; i++) {
            while (runner[i - 1].thread.isAlive()) {}
            runner[i].startThread();
            }
            */
            
        SwingUtilities.invokeLater(() -> {
            AlgorithmRunner runner = new AlgorithmRunner(50, 30, 20, Type.HEXAGON);
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
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Number of shapes: " + this.dimension + ", completed in " + (endTime - startTime) + " ms.");
    }
}
