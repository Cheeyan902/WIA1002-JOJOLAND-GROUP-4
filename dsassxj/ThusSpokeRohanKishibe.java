package dsassxj;

import java.util.*;

public class ThusSpokeRohanKishibe {
    static Scanner sc = new Scanner(System.in);
    static Graph map = new Graph();
    static List<String> locations;

    public ThusSpokeRohanKishibe(Graph map) {
        this.map = map;
        locations = this.map.getAllVertexObjects();
        ThusSpoke();
    }

    public static void ThusSpoke() {
        System.out.println("List of locations: ");
        int i = 0;
        for (String location : locations) {
            System.out.printf("[%s] %s\n", i, location);
            i++;
        }
        System.out.println();

        // Get input from the user
        System.out.print("Enter the locations: ");
        String input = sc.nextLine();
        String[] locationNames = input.split(", ");

        // Check if all the locations are available
        boolean allLocationsAvailable = true;
        for (String locationName : locationNames) {
            if (!map.hasVertex(locationName)) {
                System.out.println("Location '" + locationName + "' is not found");
                allLocationsAvailable = false;
            }
        }

        if (allLocationsAvailable) {
            System.out.println("======================================================================");
            List<String> shortestPath = Dijkstra(locationNames);
            int pathLength = calculatePathLength(shortestPath);
            printPath(shortestPath, pathLength);
        } else {
            // Ask if the user wants to try again
            System.out.print("\nDo you want to try again? [y/n]: ");
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("y")) {
                System.out.println("======================================================================");
                ThusSpoke();
            } else if (s.equalsIgnoreCase("n")) {
                System.out.println("Returning back.....\n");
                System.out.println("======================================================================");
            }
        }
    }

    public static int calculatePathLength(List<String> path) {
        int length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String source = path.get(i);
            String destination = path.get(i + 1);
            Comparable weight = map.getEdgeWeight(source, destination);
            if (weight != null && weight instanceof Integer) {
                length += (int) weight;
            }
        }
        return length;
    }

    public static List<String> Dijkstra(String[] locations) {
        String source = "Morioh Grand Hotel";

        // Reset the visited and shortest paths for all vertices
        map.resetVertices();

        List<Vertex<String, Integer>> destinationVertices = new ArrayList<>();
        for (String loc : locations) {
            Vertex<String, Integer> destinationVertex = map.getVertex(loc);
            destinationVertices.add(destinationVertex);
        }

        Vertex<String, Integer> sourceVertex = map.getVertex(source);
        sourceVertex.setDistance(0);

        PriorityQueue<Vertex<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        queue.add(sourceVertex);

        while (!queue.isEmpty()) {
            Vertex<String, Integer> vertex = queue.poll();
            vertex.setVisited(true);

            boolean allDestinationsVisited = true;
            for (Vertex<String, Integer> destinationVertex : destinationVertices) {
                if (!destinationVertex.hasVisited()) {
                    allDestinationsVisited = false;
                    break;
                }
            }

            if (allDestinationsVisited) {
                // If all destination locations have been visited, break the loop
                break;
            }

            Edge<String, Integer> edge = vertex.firstEdge;
            while (edge != null) {
                Vertex<String, Integer> neighbor = edge.toVertex;

                if (!neighbor.hasVisited()) {
                    int distance = vertex.getDistance() + edge.weight;

                    if (distance < neighbor.getDistance()) {
                        neighbor.setDistance(distance);
                        List<String> newPath = new ArrayList<>(vertex.getShortestPaths());
                        newPath.add(vertex.vertexInfo);
                        neighbor.setShortestPaths(newPath);
                        queue.add(neighbor);
                    }
                }
                edge = edge.nextEdge;
            }
        }

        // Find the destination location with the shortest path
        String destination = "";
        int shortestPathLength = Integer.MAX_VALUE;
        for (Vertex<String, Integer> destinationVertex : destinationVertices) {
            List<String> shortestPath = destinationVertex.getShortestPaths();
            if (shortestPath.size() == locations.length && destinationVertex.getDistance() < shortestPathLength) {
                destination = destinationVertex.vertexInfo;
                shortestPathLength = destinationVertex.getDistance();
            }
        }

        // Retrieve the shortest path to the destination location
        List<String> shortestPath = new ArrayList<>();
        Vertex<String, Integer> destinationVertex = map.getVertex(destination);
        shortestPath.addAll(destinationVertex.getShortestPaths());
        shortestPath.add(destination);

        return shortestPath;
    }

    public static void printPath(List<String> path, int weight) {
        System.out.println("Shortest Path:");
        for (int i = 0; i < path.size(); i++) {
            if (i == path.size() - 1) {
                System.out.printf("%s", path.get(i));
            } else {
                System.out.printf("%s -> ", path.get(i));
            }
        }
        System.out.printf(" (%dkm)\n", weight);
        System.out.println("======================================================================");

        // Ask if the user wants to try again
        System.out.print("Do you want to try again? [y/n]: ");
        String s = sc.nextLine();
        if (s.equalsIgnoreCase("y")) {
            ThusSpoke();
        } else if (s.equalsIgnoreCase("n")) {
            System.out.println("Returning back.....");
            System.out.println("======================================================================");
        }
    }
}