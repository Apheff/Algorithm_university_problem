import java.awt.image.BufferedImage;

public class LabyrinthGenerator {
    private Shape[][] labyrinth;
    private BufferedImage buffer;

    public LabyrinthGenerator(int dimensionX, int dimensionY, int shapeDimension, Type shapeType, BufferedImage buffer) {
        this.labyrinth = new Shape[dimensionY][dimensionX];
        this.buffer = buffer;

        switch (shapeType) {
            case SQUARE -> generateSquareLabyrinth(shapeDimension);
            case HEXAGON -> generateHexagonalLabyrinth(shapeDimension);
            case TRIANGLE -> generateTriangularLabyrinth(shapeDimension);
        }
    }

    public void generateSquareLabyrinth(int shapeDimension) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = new Square(shapeDimension, i, j);
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    currentShape.addNeighbor(leftNeighbor, 0);
                    leftNeighbor.addNeighbor(currentShape, 2);
                }

                // Connect to top neighbor
                if (i > 0) {
                    Shape topNeighbor = labyrinth[i - 1][j];
                    currentShape.addNeighbor(topNeighbor, 1);
                    topNeighbor.addNeighbor(currentShape, 3);
                }
                currentShape.draw(buffer.getGraphics());
            }
        }
    }

    public void generateHexagonalLabyrinth(int shapeDimension) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = new Hexagon(shapeDimension, i, j);
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    currentShape.addNeighbor(leftNeighbor, 0);
                    leftNeighbor.addNeighbor(currentShape, 3);
                }
                
                // Connect to top-centre neighbor
                int side = (i % 2 == 0) ? -1 : 1;
                if (i > 0) {
                    Shape topCenterNeighbor = labyrinth[i - 1][j];
                    currentShape.addNeighbor(topCenterNeighbor, 2 - i % 2);
                    topCenterNeighbor.addNeighbor(currentShape, 5 - i % 2);
                }
                
                // Connect to top-side neighbor
                if (i > 0 && j + side >= 0 && j + side < labyrinth[i].length) {
                    Shape otherTopNeighbor = labyrinth[i - 1][j + side];
                    currentShape.addNeighbor(otherTopNeighbor, 1 + i % 2);
                    otherTopNeighbor.addNeighbor(currentShape, 4 + i % 2);
                }
                currentShape.draw(buffer.getGraphics());
            }
        }
    }

    public void generateTriangularLabyrinth(int shapeDimension) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = new Triangle(shapeDimension, i, j);
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    currentShape.addNeighbor(leftNeighbor, 0);
                    leftNeighbor.addNeighbor(currentShape, 1 + (i + j) % 2);
                }

                // Connect to top neighbor
                if (i > 0 && (i + j) % 2 == 0) {
                    Shape topNeighbor = labyrinth[i - 1][j];
                    currentShape.addNeighbor(topNeighbor, 1);
                    topNeighbor.addNeighbor(currentShape, 2);
                }
                currentShape.draw(buffer.getGraphics());
            }
        }
    }

    public Shape[][] getLabyrinth() {
        return this.labyrinth;
    }
}
