package dsassxj;

import java.util.*;

class DijkstraAlgorithm<T extends Comparable<T>, N extends Comparable<N>>{
    
    private Graph<T, N> graph;

    public DijkstraAlgorithm(Graph<T, N> graph) {
        this.graph = graph;
    }
    
    public List<T> findShortestPath(T source, T destination, List<T> identifiedLocations) {
        
        // Create a copy of the graph
        Graph<T, N> graphCopy = new Graph<>();
        
        // Convert undirected to directed graph
        List<Edge<T, N>> allEdges = graph.getAllEdges();

        // Create a map to track visited vertices
        Map<T, Boolean> visited = new HashMap<>();
        for (Vertex<T, N> vertex : graph.getAllVertices()) {
            visited.put(vertex.vertexInfo, false);
        }

        // Start with the first vertex
        Vertex<T, N> startVertex = graph.getAllVertices().get(0);
        visited.put(startVertex.vertexInfo, true);

        // Create a priority queue to store the edges
        PriorityQueue<Edge<T, N>> priorityQueue = new PriorityQueue<>();
        priorityQueue.addAll(startVertex.getAllEdges());

        while (!priorityQueue.isEmpty()) {
            // Get the edge with minimum weight
            Edge<T, N> edge = priorityQueue.remove();

            Vertex<T, N> toVertex = edge.toVertex;

            if (!visited.get(toVertex.vertexInfo)) {
                // Add the edge to the minimum spanning tree
                graphCopy.addEdge2(edge.fromVertex.vertexInfo, toVertex.vertexInfo, edge.weight);
                // So now graphCopy is a directed graph
                visited.put(toVertex.vertexInfo, true);

                // Add the edges of the new vertex to the priority queue
                priorityQueue.addAll(toVertex.getAllEdges());
            }
        }

        // Remove identified locations from the copied graph
        for (T location : identifiedLocations) {
            graphCopy.removeVertex(location);
        }

        // Dijkstra's algorithm on the copied graph
        Map<T, N> distances = new HashMap<>();
        Map<T, T> previousVertices = new HashMap<>();
        //PriorityQueue<Vertex<T, N>> minHeap = new PriorityQueue<>(Comparator.comparing(distances::get));
        PriorityQueue<Vertex<T, N>> minHeap = new PriorityQueue<>((v1, v2) -> {
            N dist1 = distances.get(v1.getName());
            N dist2 = distances.get(v2.getName());
            if (dist1 == null) {
                return -1;
            }
            if (dist2 == null) {
                return 1;
            }
            return dist1.compareTo(dist2);
        });

        
        for (Vertex<T, N> vertex : graphCopy.getAllVertices()) {
            N initialDistance = vertex.getName().equals(source) ? null : getMaxDistance();
            distances.put(vertex.getName(), initialDistance);
            previousVertices.put(vertex.getName(), null);
            minHeap.add(vertex);
        }

        while (!minHeap.isEmpty()) {
            Vertex<T, N> currentVertex = minHeap.poll();
            T currentVertexName = currentVertex.getName();

            if (currentVertexName.equals(destination)) {
                break;
            }

            for (Edge<T, N> edge : currentVertex.getAllEdges()) {
                Vertex<T, N> neighborVertex = edge.toVertex;
                T neighborVertexName = neighborVertex.getName();

                if (minHeap.contains(neighborVertex)) {

                    N currentDistance = distances.get(neighborVertexName);
                    N newDistance = add(distances.get(currentVertexName), edge.getWeight(), currentDistance);

                    if (currentDistance == null || newDistance == null || newDistance.compareTo(currentDistance) < 0) {
                        distances.put(neighborVertexName, newDistance);
                        previousVertices.put(neighborVertexName, currentVertexName);

                        // Update the priority of the neighbor vertex in the min heap
                        minHeap.remove(neighborVertex);
                        minHeap.add(neighborVertex);
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<T> shortestPath = new ArrayList<>();
        T currentVertex = destination;
        while (currentVertex != null) {
            shortestPath.add(0, currentVertex);
            currentVertex = previousVertices.get(currentVertex);
        }

        return shortestPath;
    }    
    
    private N getMaxDistance() {
        return (N) Integer.valueOf(Integer.MAX_VALUE);
    }
    
    private N add(N num1, N num2, N currentDistance) {
        if (num1 == null || num2 == null) {
            return null;
        }
        // Assuming N is a numeric type that supports addition
        int num1Value = num1 instanceof Integer ? (Integer) num1 : Integer.parseInt(num1.toString());
        int num2Value = num2 instanceof Integer ? (Integer) num2 : Integer.parseInt(num2.toString());
        int currentDistanceValue = currentDistance instanceof Integer ? (Integer) currentDistance : Integer.parseInt(currentDistance.toString());
        int sum = num1Value + num2Value;
        if (currentDistanceValue == Integer.MAX_VALUE || sum > Integer.MAX_VALUE) {
            return (N) Integer.valueOf(Integer.MAX_VALUE);
        }
        return (N) Integer.valueOf(sum);
    }
}





