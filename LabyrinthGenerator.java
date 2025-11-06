public class LabyrinthGenerator {
    private Shape[][] labyrinth;
    private Graph<Shape> graph;

    public LabyrinthGenerator(int dimension, Shape.Type shapeType) {
        this.labyrinth = new Shape[dimension][dimension];
        this.graph = new Graph<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.labyrinth[i][j] = new Shape(shapeType);
            }
        }

        switch (shapeType) {
            case SQUARE -> generateSquareLabyrinth();
            case HEXAGON -> generateHexagonalLabyrinth();
            case TRIANGLE -> generateTriangularLabyrinth();
        }
    }

    public void generateSquareLabyrinth() {
        graph.addVertex(labyrinth[0][0]); // Add starting point
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    graph.addEdge(currentShape, leftNeighbor);
                }

                // Connect to top neighbor
                if (i > 0) {
                    Shape topNeighbor = labyrinth[i - 1][j];
                    graph.addEdge(currentShape, topNeighbor);
                }
            }
        }
    }

    public void generateHexagonalLabyrinth() {
        graph.addVertex(labyrinth[0][0]); // Add starting point
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth.length; j++) {
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    graph.addEdge(currentShape, leftNeighbor);
                }
                
                // Connect to top-centre neighbor
                if (i > 0) {
                Shape topCenterNeighbor = labyrinth[i - 1][j];
                graph.addEdge(currentShape, topCenterNeighbor);
                }
                
                // Connect to top-side neighbor
                int side = (i % 2 == 0) ? -1 : 1;
                if (i > 0 && j + side >= 0 && j + side < labyrinth.length) {
                    Shape otherTopNeighbor = labyrinth[i - 1][j + side];
                    graph.addEdge(currentShape, otherTopNeighbor);
                }
            }
        }
    }

    public void generateTriangularLabyrinth() {
        graph.addVertex(labyrinth[0][0]); // Add starting point
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth.length; j++) {
                Shape currentShape = labyrinth[i][j];

                // Connect to left neighbor
                if (j > 0) {
                    Shape leftNeighbor = labyrinth[i][j - 1];
                    graph.addEdge(currentShape, leftNeighbor);
                }

                // Connect to top neighbor
                if (i > 0 && (i + j) % 2 == 0) {
                    Shape topNeighbor = labyrinth[i - 1][j];
                    graph.addEdge(currentShape, topNeighbor);
                }
            }
        }
    }

    public Shape[][] getLabyrinth() {
        return this.labyrinth;
    }
    public Graph<Shape> getGraph() {
        return this.graph;
    }
}
