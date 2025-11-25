import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class AlgorithmRunner implements Runnable {
    
    private static int width = 50;
    private static int height = 30;
    private static int shapeSize = 20;
    private static Type shapeType = Type.SQUARE; // 4: square, 6: hexagon, 3: triangle, (add to enum new Type accordingly)
    private static int time = 3;
    private static boolean test;

    private LabyrinthAlgorithm labyrinthAlgorithm;
    private LabyrinthDrawer labyrinthDrawer;
    private Thread thread;
    private int dimension;

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            String value = "?";
            switch (args[i].toLowerCase()) {
                case "-test":
                    i--;
                    test = true;
                    break;

                case "-width":
                    try {
                        value = args[i + 1];
                        width = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.out.println("Error: value \"" + value + "\" not valid for propertie -width");
                    }
                    break;

                case "-height":
                    try {
                        value = args[i + 1];
                        height = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.out.println("Error: value \"" + value + "\" not valid for propertie -height");
                    }
                    break;

                case "-shapesize":
                    try {
                        value = args[i + 1];
                        shapeSize = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.out.println("Error: value \"" + value + "\" not valid for propertie -shapesize");
                    }
                    break;

                case "-shapetype":
                    try {
                        value = args[i + 1];
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
                        value = args[i + 1];
                        time = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.out.println("Error: value \"" + value + "\" not valid for propertie -time");
                    }
                    break;
            
                default:
                    System.out.println("Error: propertie \"" + args[i] + "\" not found");
                    break;
            }
        }
        if (test)
            time = 0;

        SwingUtilities.invokeLater(() -> new AlgorithmRunner());
    }

    public AlgorithmRunner() {
        this.dimension = width * height;
        BufferedImage buffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);

        LabyrinthGenerator generator = new LabyrinthGenerator(width, height, shapeSize, shapeType, buffer);
        Shape[][] labyrinth = generator.getLabyrinth();
        System.out.println(width + "x" + height + " labyrinth generated.");

        this.labyrinthAlgorithm = new LabyrinthAlgorithm(labyrinth, buffer);
        if (!test)
            this.labyrinthDrawer = new LabyrinthDrawer(buffer);

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        while (labyrinthAlgorithm.running()) {
            labyrinthAlgorithm.update(test);
            if (!test)
                labyrinthDrawer.repaint();
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Number of shapes: " + this.dimension + ", completed in " + (endTime - startTime) / 1000_000.0 + " ms.");
    }
}
