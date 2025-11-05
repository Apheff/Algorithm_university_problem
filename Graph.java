import java.util.HashMap;
import java.util.LinkedList;

class Graph<T> {
    /* Generic graph structure */
    private HashMap <T, LinkedList<T>> graphMap;
    
    public Graph(){
        this.graphMap = new HashMap<>();
    }

    public void addEdge(T source, T destination) {
        this.graphMap.putIfAbsent(source, new LinkedList<T>());
        this.graphMap.putIfAbsent(destination, new LinkedList<T>());
        this.graphMap.get(source).append(destination);
        this.graphMap.get(destination).append(source);
    }

    public LinkedList<T> getAdjacentList(T vertex) {
        return this.graphMap.get(vertex);
    }

    public T getVertices() {
        return HashMap.keySet();
    }
    
    public boolean hasEdge(T source, T destination){
        return this.graphMap.get(source).contains(destination) && this.graphMap.get(destination).contains(source);
    }
}