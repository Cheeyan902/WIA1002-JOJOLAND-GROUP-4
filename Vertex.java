package dsassxj;

class Vertex<T extends Comparable<T>, N extends Comparable <N>> {
    T vertexInfo;
    int deg;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;
    
    public Vertex(){
        vertexInfo=null;
        deg=0;
        nextVertex = null;
        firstEdge = null;
    }
	
    public Vertex(T vInfo, Vertex<T,N> next){
        vertexInfo = vInfo;
        deg=0;
        nextVertex = next;
        firstEdge = null;
    }	
}
