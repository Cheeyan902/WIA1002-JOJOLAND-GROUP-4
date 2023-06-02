package dsassxj;

import java.util.*;

class Vertex<T extends Comparable<T>, N extends Comparable <N>> {
    
    T vertexInfo;
    int deg;
    boolean visited;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;
    
    public Vertex(T vInfo, Vertex<T,N> next){
        vertexInfo = vInfo;
        deg=0;
        visited = false;
        nextVertex = next;
        firstEdge = null;
    }
    
    public T getName(){
        return this.vertexInfo;
    }
    
    @Override
    public String toString(){
        return vertexInfo + " " + deg;
    }
    
    public List<Edge<T, N>> getAllEdges() {
        List<Edge<T, N>> allEdges = new ArrayList<>();
        Edge<T, N> currentEdge = firstEdge;
        while (currentEdge != null) {
            allEdges.add(currentEdge);
            currentEdge = currentEdge.nextEdge;
        }
        return allEdges;
    }
    
    //check if the vertex has been visited previously
    public boolean hasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
