import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

class Graph<T> {
    /* Generic graph structure */
    private HashMap <T, LinkedList<T>> graphMap;
    
    public Graph(){
        this.graphMap = new HashMap<>();
    }

    public void addVertex(T vertex) {
        this.graphMap.putIfAbsent(vertex, new LinkedList<T>());
    }

    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);
        if (!hasEdge(source, destination)) {
            this.graphMap.get(source).add(destination);
            this.graphMap.get(destination).add(source);
        }
    }

    public LinkedList<T> getAdjacentList(T vertex) {
        return this.graphMap.get(vertex);
    }

    public Set<T> getVertices() {
        return graphMap.keySet();
    }
    
    public boolean hasEdge(T source, T destination){
        return this.graphMap.get(source).contains(destination) && this.graphMap.get(destination).contains(source);
    }
}