package dsassxj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

//class to create map
public class Map extends Graph{
    
    public static Graph<String, Integer> map;
    public Scanner sc = new Scanner(System.in);
    
    public Map(){
        map = new Graph();
    }
    
    public void chooseMap(int num){
        switch (num) {
            case 1:
                {
                    //creating default map
                    String[] location = {"Town Hall", "Cafe Deux Magots", "Polnareff Land",
                        "Savage Garden", "Vineyard", "Joestar Mansion", "Jade Garden",
                        "Morioh Grand Hotel", "San Giorgio Maggiore", "Trattoria Trussardi",
                        "Libeccio", "DIO's Mansion", "Angelo Rock", "Green Dolphin Street Prison"};
                    
                    //adding vertices into the map
                    for(String i : location)
                        map.addVertex(i);
                    
                    //adding edges between the vertices
                    try{
                        Scanner sc = new Scanner(new FileInputStream("DefaultMap.txt"));
                        while(sc.hasNextLine()){
                            String s = sc.nextLine();
                            String[] edge = s.split(",");
                            map.addEdge(edge[0], edge[1], Integer.valueOf(edge[2]));
                        }
                    }catch(FileNotFoundException e){
                        System.out.println("File Not Found");
                    }       break;
                }
                
            case 2:
                {
                    //creating parallel map
                    String[] location = {"Town Hall", "Cafe Deux Magots", "Polnareff Land",
                        "Savage Garden", "Vineyard", "Joestar Mansion", "Jade Garden",
                        "Morioh Grand Hotel", "San Giorgio Maggiore", "Trattoria Trussardi",
                        "Libeccio", "DIO's Mansion", "Angelo Rock", "Green Dolphin Street Prison"};
                    
                    //adding vertices into the map
                    for(String i : location)
                        map.addVertex(i);
                    
                    //adding edges between the vertices
                    try{
                        Scanner sc = new Scanner(new FileInputStream("ParallelMap.txt"));
                        while(sc.hasNextLine()){
                            String s = sc.nextLine();
                            String[] edge = s.split(",");
                            map.addEdge(edge[0], edge[1], Integer.valueOf(edge[2]));
                        }
                    }catch(FileNotFoundException e){
                        System.out.println("File not found");
                    }       break;
                }
                
            case 3:
                {
                    //creating alternate map
                    String[] location = {"Town Hall", "Cafe Deux Magots", "Polnareff Land",
                        "Savage Garden", "Vineyard", "Joestar Mansion", "Jade Garden",
                        "Morioh Grand Hotel", "San Giorgio Maggiore", "Trattoria Trussardi",
                        "Passione Restaurant", "DIO's Mansion", "Angelo Rock", "Green Dolphin Street Prison"};
                    
                    //adding vertices into the map
                    for(String i : location)
                        map.addVertex(i);
                    
                    //adding edges between the vertices
                    try{
                        Scanner sc = new Scanner(new FileInputStream("AlternateMap.txt"));
                        while(sc.hasNextLine()){
                            String s = sc.nextLine();
                            String[] edge = s.split(",");
                            map.addEdge(edge[0], edge[1], Integer.valueOf(edge[2]));
                        }
                    }catch(FileNotFoundException e){
                        System.out.println("File not found");
                    }       break;
                }
            default:
                break;
        }
    }
    
     //method to display neighbour vertexs
     public void choices(){
        for(int i=0; i<map.getDeg(start.currentLocation); i++){
            String option = String.valueOf((char)('A' + i));
            System.out.print("     [" + option + "] " + map.getNeighbours(start.currentLocation).get(i) + " ".repeat(5));
        }
        System.out.println(" ");
    }
    
    //method to move to adjacent location
    public void move(){
        start.history.add(start.currentLocation);
        System.out.print("Enter the destination: ");
              String destinationOption = sc.nextLine().toUpperCase(); 
              char optionChar = destinationOption.charAt(0);
              int index = optionChar - 'A';
              if (index >= 0 && index < map.getNeighbours(start.currentLocation).size()) {
                    start.currentLocation = map.getNeighbours(start.currentLocation).get(index);
                    System.out.println("Moving to " + start.currentLocation + ".");
                 } else {
                  System.out.println("Invalid destination.");
                }
        System.out.println("======================================================================");
              
        //to check if forward location is same as the location before backtrack
        if(!start.forward.empty() && !start.forward.pop().equals(start.currentLocation)){
            start.history.pop();
            start.history.pop();
            start.forward.clear();
        }else if(!start.forward.empty() && start.forward.pop().equals(start.currentLocation)){
            start.history.pop();
            start.forward.clear();
        }
    }
}