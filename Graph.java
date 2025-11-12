import java.util.HashMap;
import java.util.LinkedList;

class Graph<T> {
    /* Generic graph structure */
    private HashMap <Shape, LinkedList<Shape>> graphMap;
    
    public Graph(){
        this.graphMap = new HashMap<>();
    }

    public void addVertex(Shape vertex) {
        this.graphMap.putIfAbsent(vertex, new LinkedList<Shape>());
    }

    public void addEdge(Shape source, Shape destination) {
        addVertex(source);
        addVertex(destination);
        if (!hasEdge(source, destination)) {
            this.graphMap.get(source).add(destination);
            this.graphMap.get(destination).add(source);
        }
    }

    public LinkedList<Shape> getAdjacentList(Shape vertex) {
        return this.graphMap.get(vertex);
    }

    public Shape[] getVertices() {
        return (Shape[]) this.graphMap.keySet().toArray(new Shape[0]);
    }
    
    public boolean hasEdge(Shape source, Shape destination){
        return this.graphMap.get(source).contains(destination) && this.graphMap.get(destination).contains(source);
    }
}