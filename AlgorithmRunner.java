import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class AlgorithmRunner implements Runnable {
    
    private static int width = 50;
    private static int height = 30;
    private static int shapeSize = 20;
    private static Type shapeType = Type.SQUARE; // 4: square, 6: hexagon, 3: triangle, (add to enum new Type accordingly)
    private static int time = 3;

    private LabyrinthAlgorithm labyrinthAlgorithm;
    private LabyrinthDrawer labyrinthDrawer;
    private Thread thread;
    private int dimension;

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
        
        for (int i = 0; i < args.length; i += 2) {
            try {
                String value = args[i + 1];
                switch (args[i].toLowerCase()) {
                    case "-width":
                        try {
                            width = Integer.parseInt(value);
                        } catch (Exception e) {
                            System.out.println("Error: value \"" + value + "\" not valid for propertie -width");
                        }
                        break;

                    case "-height":
                        try {
                            height = Integer.parseInt(value);
                        } catch (Exception e) {
                            System.out.println("Error: value \"" + value + "\" not valid for propertie -height");
                        }
                        break;

                    case "-shapesize":
                        try {
                            shapeSize = Integer.parseInt(value);
                        } catch (Exception e) {
                            System.out.println("Error: value \"" + value + "\" not valid for propertie -shapesize");
                        }
                        break;

                    case "-shapetype":
                        try {
                            switch (value.toLowerCase()) {
                                case "square" -> shapeType = Type.SQUARE;
                                case "hexagon" -> shapeType = Type.HEXAGON;
                                case "triangle" -> shapeType = Type.TRIANGLE;
                                default -> new Exception();
                            };
                        } catch (Exception e) {
                            System.out.println("Error: value \"" + value + "\" not valid for propertie -shapetype");
                        }
                        break;

                    case "-time":
                        try {
                            time = Integer.parseInt(value);
                        } catch (Exception e) {
                            System.out.println("Error: value \"" + value + "\" not valid for propertie -time");
                        }
                        break;
                
                    default:
                        System.out.println("Error: propertie \"" + args[i] + "\" not found");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: missing value");
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
