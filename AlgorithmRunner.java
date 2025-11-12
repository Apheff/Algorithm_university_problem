import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

public class AlgorithmRunner implements Runnable {

    private LabyrinthAlgorithm labyrinthAlgorithm;
    private LabyrinthDrawer labyrinthDrawer;
    private Thread thread;
    private BufferedImage buffer;
    private static int dimension = 10000;

    public static void main(String[] args) {
        /*
        AlgorithmRunner runner[] = new AlgorithmRunner[15];
        for (int i = 1; i <= 15; i++)
            runner[i - 1] = new AlgorithmRunner(i * 100, i * 100, 10, Shape.Type.SQUARE);

        runner[0].startThread();
        for (int i = 1; i <= 14; i++) {
            while (runner[i - 1].isRunning()) {}
            dimension = (int) Math.pow(i + 1, 2) * 10000;
            runner[i].startThread();
        }
        */
        SwingUtilities.invokeLater(() -> {
            AlgorithmRunner runner = new AlgorithmRunner(25, 25, 20, Shape.Type.SQUARE);
            runner.startThread();
        });
    }

    public AlgorithmRunner(int dimensionX, int dimensionY, int shapeDimension, Shape.Type shapeType) {
        buffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);

        LabyrinthGenerator generator = new LabyrinthGenerator(
            dimensionX, 
            dimensionY, 
            shapeDimension, 
            shapeType,
            buffer
        );
        Shape[][] labyrinth = generator.getLabyrinth();
        System.out.println("Labyrinth generated.");

        this.labyrinthAlgorithm = new LabyrinthAlgorithm(labyrinth, buffer);
        this.labyrinthDrawer = new LabyrinthDrawer(buffer);

        this.thread = new Thread(this);
        //this.thread.start();
    }

    private void startThread() {
        this.thread.start();
    }

    private boolean isRunning() {
        return this.thread.isAlive();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (labyrinthAlgorithm.running()) {
            labyrinthAlgorithm.update();
            labyrinthDrawer.repaint();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Number of shapes: " + dimension + ", completed in " + (endTime - startTime) + " ms.");
    }
}
