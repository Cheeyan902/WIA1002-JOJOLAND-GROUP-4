package dsassxj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.util.Stack;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class StartInterface {
    
    static Scanner sc = new Scanner(System.in);
    static Graph map = new Graph();
    static String mapLocation;
    static int dayNum = 1;   //initialize day 1
    static String currentLocation = "Town Hall";    //initialize current location 
    static Stack<String> history = new Stack<>();
    static Stack<String> forward = new Stack<>();
    static ArrayList<String> list;
    
    //method to start the game
    public static void startGame() throws ParseException, IOException{
        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit\n");
                
        switch(getInput()){
            case 1:
                chooseMap();
                displayDay();
                getMission();
                break;
                
            case 2:
                System.out.print("Enter the path of your save file: ");
                loadGame();
                break;
            
            case 3:
                endGame();
                break;
                
            default:
                System.out.println("Invalid input. Please choose again.");
                startGame();
                break;
        }
    }
    
    //method to get an input for selection
    public static int getInput(){
        System.out.print("Select [integer only]: ");
        int input = sc.nextInt();
        sc.nextLine();
        System.out.println("======================================================================");
        return input;
    }
    
    //method to choose map
    public static void chooseMap(){
        System.out.println("Select a map: ");
        System.out.println("[1] Default Map");
        System.out.println("[2] Parallel Map");
        System.out.println("[3] Alternate Map\n");
        int num = getInput();
        switch (num){
            case 1:
                mapLocation = "DefaultMap.txt";
                break;
            case 2:
                mapLocation = "ParallelMap.txt";
                break;
            case 3:
                mapLocation = "AlternateMap.txt";
                break;
            default:
                System.out.println("Invalid input. Please choose again");
                chooseMap();
        }
        createMap(mapLocation);
    }
    
    //method to create map
    public static void createMap(String loc){
        try{
            Scanner scan = new Scanner(new FileInputStream(loc));
            while(scan.hasNextLine()){
                String s = scan.nextLine();
                String[] edge = s.split(",");
                map.addEdge(edge[0], edge[1], Integer.valueOf(edge[2]));
            }
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
    
    //method to choose the day
    public static String getDay(int dayNum){
        String[] day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int num;
        if(dayNum>7){
            num = (dayNum%7)-1;
        }else{
            num = dayNum-1;
        }
        return day[num];
    }
    
    //method to display the day
    public static void displayDay(){
        System.out.printf("It's Day %d (%s) of our journey in JOJOLands!\n", dayNum, getDay(dayNum));
    }
    
    //method to choose mission for each location
    public static void getMission(){
        while(true){
            System.out.println("Current Location: " + currentLocation);
            switch(currentLocation){
                case "Town Hall":
                    MissionOne();
                    break;
                case "Morioh Grand Hotel":
                    MissionTwo();
                    break;
                case "Trattoria Trussardi", "Jade Garden", "Cafe Deux Magots", "Libeccio", "Savage Garden":
                    MissionThree();
                    break;
                case "Polnareff Land":
                    MissionFour();
                    break;
                case "Joestar Mansion":
                    MissionFive();
                    break;
                case "Angelo Rock":
                    MissionSix();
                    break;
                case "San Giorgio Maggiore":
                    MissionSeven();
                    break;
                case "Green Dolphin Street Prison":
                    MissionEight();
                    break;
                default:
                    DefaultMission();
                    break;
            }
        }
    }
    
    //Town Hall mission
    public static void MissionOne(){
        int i = 2;
        System.out.println("[1] Move to New Location");
        choices();
        if(!history.empty()){
            System.out.printf("[%d] Back to (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek()); 
            i++;
        }
        System.out.printf("[%d] Advance to Next Day\n", i);
        i++;
        System.out.printf("[%d] Save Game\n", i);
        i++;
        System.out.printf("[%d] Exit\n\n", i);
        switch(getInput()){
            case 1:                            
                moveLocation();
                break;
            case 2:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    advance();
                break;
            case 3: 
                if(history.empty() && forward.empty())
                    saveGame();
                else if((!history.empty() && forward.empty()) || history.empty() && !forward.empty())
                    advance();
                else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 4:
                if(history.empty() && forward.empty())
                    endGame();
                else if((!history.empty() && forward.empty()) ||(!history.empty() && forward.empty()) )
                    saveGame();
                else if(!history.empty() && !forward.empty())
                    advance();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    saveGame();
                else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty()))
                    endGame();
                else if(history.empty() && forward.empty())
                    System.out.println("Invalid input. Please choose again\n");
                    MissionOne();
                break;
            case 6:
                if(!history.empty() && !forward.empty())
                    endGame();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionOne();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionOne();
                break;
        }
    }
    
    //Morioh Grand Hotel mission
    public static void MissionTwo(){
        int i = 5;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] The Hand");
        System.out.println("[4] Thus Spoke Rohan Kishibe");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                break;
            case 3: 
                //jump to Super Fly (basic feature 7);
                TheHand();
                break;
            case 4: 
                //jump to Super Fly (basic feature 7);
                ThusSpokeRohanKishibe();
                break;
            case 5:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 6:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionTwo();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 7:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionTwo();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionTwo();
                break;
        }
    }
    
    //Trattoria Trussardi, Jade Garden, Cafe Deux Magots, Libeccio, Savage Garden mission
    public static void MissionThree(){
        int i = 6;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Waiting List and Order Processing List");
        System.out.println("[3] View Menu");
        System.out.println("[4] View Sales Information");
        System.out.println("[5] Milagro Man");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        
        // Create an instance of the PearlJam class with the selected restaurant name
        PearlJam pearlJam = new PearlJam(currentLocation);
        Menu menu = new Menu(currentLocation, dayNum);
        
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Pearl Jam (basic feature 3)
                Customer[] customers = new Customer[6];
                customers[0] = new Customer(1, "Jonathan Joestar", 20, "Male", 10, currentLocation, "Braised Chicken in Black Bean Sauce");
                customers[1] = new Customer(2, "Joseph Joestar", 18, "Male", 15, currentLocation, "Scrambled Egg White with Milk");
                customers[2] = new Customer(3, "Jotaro Kujo", 17, "Male", 12, currentLocation, "Braised Goose Web with Vermicelli");
                customers[3] = new Customer(4, "Josuke Higashikata", 16, "Male", 18, currentLocation, "Poached Tofu with Dried Shrimps");
                customers[4] = new Customer(5, "Giorno Giovanna", 15, "Male", 18, currentLocation, "Deep-fried Hiroshima Oysters");
                customers[5] = new Customer(6, "Jolyne Cujoh", 19, "Female", 18, currentLocation, "Braised Goose Web with Vermicelli");
                System.out.println("======================================================================");
                pearlJam.serveCustomers(customers);
                pearlJam.addToWaitingList(customers);
                pearlJam.sortWaitingList();
                pearlJam.setProcessOrders();
                pearlJam.displayWaitingList();
                pearlJam.displayOrderProcessingList();
                break;
            case 3: 
                //jump to Pearl Jam (basic feature 3)
                System.out.println("======================================================================");
                //pearlJam.displayMenu(currentLocation);
                menu.displayMenu();
                break;
            case 4:
                //jump to Moody Blue (basic feature 5)
                MoodyBlues moodyblue = new MoodyBlues();
                System.out.println("======================================================================");
                moodyblue.printMoodyBlues();
                break;
            case 5:
                //jump to Milagro Man (basic feature 6)
                break;
            case 6:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 7:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 8:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionThree();
                break;
        }
    }
    
    //Polnareff Land mission
    public static void MissionFour(){
        int i = 3;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                break;
            case 3:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 4:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionFour();
                break;
        }
    }
    
    //Joestar Mansion mission
    public static void MissionFive(){
        int i = 4;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] The Golden Spirit");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to basic feature 4
                break;
            case 3:
                //jump to extra feature 7
                TheGoldenSpirit();
                break;
            case 4:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 5:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 6:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionFive();
                break;
        }
    }
    
    //Angelo Rock mission
    public static void MissionSix(){
        int i = 5;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] View Resident Information");
        System.out.println("[3] Red Hot Chili Pepper");
        System.out.println("[4] Another One Bites the Dust");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to Heaven's Door (basic feature 2)
                break;
            case 3: 
                //jump to Super Fly (basic feature 7);
                redHotChiliPepper();
                break;
            case 4: 
                //jump to extra feature 1;
                AnotherOneBites();
                break;
            case 5:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 6:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 7:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionSix();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionSix();
                break;
        }
    }
    
    //Joestar Mansion mission
    public static void MissionSeven(){
        int i = 3;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] Stay the Hell Away from Me!");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek()); 
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to extra feature 7
                StayTheHell();
                break;
            case 3:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 4:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFive();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionSeven();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionSeven();
                break;
        }
    }
    
    //Polnareff Land mission
    public static void MissionEight(){
        int i = 3;
        System.out.println("[1] Move to New Location");
        choices();
        System.out.println("[2] Dirty Deeds Done Dirt Cheap");
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                //jump to extra feature 4
                DirtyDeedsDone();
                break;
            case 3:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 4:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    MissionFour();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 5:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    MissionEight();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                MissionEight();
                break;
        }
    }
    
    //Default mission
    public static void DefaultMission(){
        int i = 2;
        System.out.println("Sorry, no mission available at " + currentLocation);
        System.out.println("[1] Move to New Location");
        choices();
        if(!history.empty()){
            System.out.printf("[%d] Back (%s)\n", i, history.peek());
            i++;
        }
        if(!forward.empty()){
            System.out.printf("[%d] Forward (%s)\n", i, forward.peek());
            i++;
        }
        System.out.printf("[%d] Back to Town Hall\n\n", i);
        switch(getInput()){
            case 1:
                moveLocation();
                break;
            case 2:
                if(!history.empty())
                    moveBackward();
                else if(history.empty() && !forward.empty())
                    moveForward();
                else if(history.empty() && forward.empty())
                    TownHall();
                break;
            case 3:
                if(history.empty() && forward.empty()){
                    System.out.println("Invalid input. Please choose again\n");
                    DefaultMission();
                }else if((!history.empty() && forward.empty()) || (history.empty() && !forward.empty())){
                    TownHall();
                }else if(!history.empty() && !forward.empty())
                    moveForward();
                break;
            case 4:
                if(!history.empty() && !forward.empty())
                    TownHall();
                else{
                    System.out.println("Invalid input. Please choose again\n");
                    DefaultMission();
                }
                break;
            default:
                System.out.println("Invalid input. Please choose again\n");
                DefaultMission();
                break;
        }
    }
    
    //method to move to new location
    public static void moveLocation(){
        history.push(currentLocation);       
        System.out.print("Enter the destination [alphabet only]: ");
        String destinationOption = sc.nextLine().toUpperCase();
        char optionChar = destinationOption.charAt(0);
        int index = optionChar - 'A';
        if(index >= 0 && index < list.size()){
            currentLocation = (String) list.get(index);
            System.out.println("Moving to " + currentLocation);
            System.out.println("======================================================================");
        }else{
            System.out.println("Invalid input. Please choose again\n");
            history.pop();
            moveLocation();
        }
        
        //there's forward history, the record will be cleared
        if(!forward.empty())
            forward.clear();
    }
    
    public static void choices(){
        list = map.getNeighbours(currentLocation);
        if(!history.empty())        //removing locations that will contrast with moveForward and moveBackward
            list.remove(history.peek());
        if(!forward.empty())
            list.remove(forward.peek());
        if(list.isEmpty()){
            System.out.print("    No new location\n");
        }else{
            for(int i=0; i<list.size(); i++){
                if(i!=0 && i%2==0)
                    System.out.println("");
                String option = String.valueOf((char)('A' + i));
                System.out.printf("    [%s] %-25s", option, list.get(i));
            }
            System.out.println("");
        }
    }
    
    //method to move backward
    public static void moveBackward(){
        if(!forward.empty())
            forward.clear();
        String reverseLocation = history.pop();
        forward.push(currentLocation);
        currentLocation = reverseLocation;
    }
    
    //method to move forward
    public static void moveForward(){
        history.push(currentLocation);
        currentLocation = forward.pop();
        if(!forward.empty())
            forward.clear();
    }   
    
    //method to return to Town Hall directly
    public static void TownHall(){
        //check if Town Hall is the backward/forward adjacent location
        if(!history.empty() && history.peek().equals("Town Hall")){
            history.pop();
            forward.push(currentLocation);
        }else if(!forward.empty() && forward.peek().equals("Town Hall")){
            history.push(currentLocation);
            forward.clear();
        }else{
            history.push(currentLocation);
            if(!forward.empty())
                forward.clear();
        }
        currentLocation = "Town Hall";
    }
    
    //method to advance to next day
    public static void advance(){
        dayNum++;
        forward.clear();
        history.clear();
        displayDay();
    }
    
    //method to save the game
    public static void saveGame(){
        System.out.println("Saving game.....");
        
        JSONObject json = new JSONObject();
        
        //add chosen map
        json.put("mapLocation", mapLocation);
        
        //add dayNum
        json.put("dayNum", dayNum);
                
        //add currentLocation
        json.put("currentLocation", currentLocation);
        
        //add previous location
        if(!history.empty())
            json.put("history", history.pop());  //to fix
        
        //add forward location
        if(!forward.empty())
            json.put("forward", forward.pop());  //to fix
        
        //add any relevant information from other features
        
        try {
            PrintWriter output = new PrintWriter(new FileWriter("savedGame.json"));
            output.write(json.toString());
            output.close();
        } catch (IOException e) {
            System.out.println("Problem occurs while saving the game...");
        }
        
        System.out.println("Game saved at savedGame.json");
        System.out.println("======================================================================\n");
        endGame();
        
    }
    
    //method to load the game
    public static void loadGame() throws FileNotFoundException, ParseException, IOException{
        String savedPath = sc.nextLine();
        System.out.println("======================================================================");
        
        JSONObject j  = (JSONObject) new JSONParser().parse(new FileReader("savedGame.json"));
        
        //read chosen map
        mapLocation = (String) j.get("mapLocation");
        
        //read dayNum
        dayNum = (int)(long)j.get("dayNum");
        
        //read currentLocation
        currentLocation = (String) j.get("currentLocation");
        
        //read previous location
        if(j.containsKey("history"))
            history.push((String) j.get("history"));    //to fix
        
        //read forward location
        if(j.containsKey("forward"))
            forward.push((String) j.get("forward"));    //to fix
        
        //read other features
        
        //then resume the game
        createMap(mapLocation);
        displayDay();
        getMission();
        
    }
    
    //method to exit the game
    public static void endGame(){
        System.out.println("THE END");
        System.out.println("Thank you for playing!");
        System.out.println("=".repeat(70));
        System.exit(0);
    }
    
    //method for red hot chili pepper
    public static void redHotChiliPepper(){
        // Create an instance of MinimumSpanningTree
        RedHotChiliPepper<String,Integer> mst = new RedHotChiliPepper<>(map);
        
        // Find the minimum spanning tree
        mst.findMinimumSpanningTree();
    }
    
    //method for red hot chili pepper
    public static void TheHand() {
         // Create an instance of MinimumSpanningTree
        TheHand<String,Integer> mst = new TheHand<>(map);
        
        // Find the maximum spanning tree
        mst.findMaximumSpanningTree();
    }
    
    //method for Stay the Hell Away from Me!
    public static void StayTheHell(){
        StayTheHellAway s = new StayTheHellAway(map);
    }
    
    //method for The Golden Spirit
    public static void TheGoldenSpirit(){
        
       // LowestCommonAncestor spirit = new LowestCommonAncestor();
    }
    
    //method for Another One Bites the Dust
    public static void AnotherOneBites() {
        BiteTheDust btd = new BiteTheDust();
        btd.run();
    }
    
    //method for Dirty Deeds Done Dirt Cheap
    public static void DirtyDeedsDone(){
        DirtyDeedsDoneDirtCheap d = new DirtyDeedsDoneDirtCheap(map);
    }
    
    //method for Thus Spoke Rohan Kishibe
    public static void ThusSpokeRohanKishibe(){
        ThusSpokeRohanKishibe t = new ThusSpokeRohanKishibe(map);
    }
}
