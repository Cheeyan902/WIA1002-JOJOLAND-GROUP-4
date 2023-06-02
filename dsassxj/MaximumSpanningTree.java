package dsassxj;

import java.util.*;

class MaximumSpanningTree<T extends Comparable<T>, N extends Number & Comparable<N>> {

    public Graph<T, N> graph;
    public Graph<T, N> mst;
    public Graph<T,N> directed;
    public int totalLength;

    public MaximumSpanningTree(Graph<T, N> graph) {
        this.graph = graph;
        this.mst = new Graph<>(); //Initialize MST graph
        this.directed = new Graph<>(); //Initialize directed graph
        this.totalLength = 0;
    }

    public void findMaximumSpanningTree() {
        
        //Initialize an empty graph for MST
        List<Edge<T, N>> allEdges = graph.getAllEdges();

        // Sort all edges based on weight in descending order
        Collections.sort(allEdges, Collections.reverseOrder(Comparator.comparing(Edge::getWeight)));

        // Create a map to track visited vertices
        Map<T, Boolean> visited = new HashMap<>();
        for (Vertex<T, N> vertex : graph.getAllVertices()) {
            visited.put(vertex.vertexInfo, false);
        }

        // Start with the first vertex
        Vertex<T, N> startVertex = graph.getAllVertices().get(0);
        visited.put(startVertex.vertexInfo, true);

        // Create a priority queue to store the edges
        PriorityQueue<Edge<T, N>> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        priorityQueue.addAll(startVertex.getAllEdges());

        while (!priorityQueue.isEmpty()) {
            // Get the edge with maximum weight
            Edge<T, N> edge = priorityQueue.remove();

            Vertex<T, N> toVertex = edge.toVertex;

            if (!visited.get(toVertex.vertexInfo)) {
                // Add the edge to the maximum spanning tree
                mst.addEdge2(edge.fromVertex.vertexInfo, toVertex.vertexInfo, edge.weight);
                visited.put(toVertex.vertexInfo, true);

                // Add the weight of the edge to the total length
                totalLength += edge.weight.intValue();

                // Add the edges of the new vertex to the priority queue
                priorityQueue.addAll(toVertex.getAllEdges());
            }
        }
        /*
        //Convert undirected graph to directed graph
        for (Edge<T, N> edgee : allEdges) {
            T fromVertex = edgee.fromVertex.vertexInfo;
            T toVertex = edgee.toVertex.vertexInfo;
            if (graph.hasEdge(fromVertex, toVertex) && graph.hasEdge(toVertex, fromVertex)) {
               graph.removeEdge(toVertex, fromVertex);
            }
            graph.addEdge2(edgee.fromVertex.vertexInfo, toVertex, edgee.weight);
        }
        
        for (Edge<T,N> edge : graph.getAllEdges2()) {
            System.out.println(edge.fromVertex.vertexInfo + " --- " + edge.toVertex.vertexInfo + " (" + edge.weight + " km)");
        }*/
     
        System.out.println("Unnecessary Water Connections to be Removed:");
        int index = 1;
        for (Edge<T,N> edge : mst.getAllEdges2()) {
            System.out.println(index + ". " + edge.fromVertex.vertexInfo + " --- " + edge.toVertex.vertexInfo + " (" + edge.weight + " km)");
            index++;
        }
        System.out.println("Total Length: " + totalLength + " km");
        System.out.println("======================================================================");
    }
}