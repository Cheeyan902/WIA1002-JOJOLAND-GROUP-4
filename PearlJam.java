package dsassxj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;

public class PearlJam {
    
    private List<Customer> waitingList;
    private List<Customer> orderProcessingList;
    private List<Customer> orders;
    private int dayNum;
    private String selectedRestaurant;
    private String menures;
    private Map<String,Double> menu;

    public PearlJam(String selectedRestaurant) {
        this.waitingList = new ArrayList<>();
        this.orderProcessingList = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.selectedRestaurant = selectedRestaurant;
        this.menu = new HashMap<>();
        setProcessOrders();
    }
    
    public void addMenu(String food, double price){
        menu.put(food, price);
    }
    
    public double getPrice(String food){
        return menu.getOrDefault(food, 0.0);
    }
    
    public void displayMenu(String menures){
        System.out.printf("Menu for restaurant : %s\n",menures);
        switch(menures){
            case "Jade Garden" :
                addMenu("Braised Chicken in Black Bean Sauce", 15);
                addMenu("Braised Goose Web with Vermicelli", 21);
                addMenu("Deep-fried Hiroshima Oysters", 17);
                addMenu("Poached Tofu with Dried Shrimps", 12);
                addMenu("Scrambled Egg White with Milk", 10);
                break;
             case "Cafe Deux Magots":
                addMenu("Sampling Matured Cheese Platter", 23);
                addMenu("Spring Lobster Salad", 35);
                addMenu("Spring Organic Omelette", 23);
                addMenu("Truffle-flavoured Poultry Supreme", 34);
                addMenu("White Asparagus", 26);
                break;
            case "Trattoria Trussardi":
                addMenu("Caprese Salad", 10);
                addMenu("Creme caramel", 6.5);
                addMenu("Lamb Chops with Apple Sauce", 25);
                addMenu("Spaghetti alla Puttanesca", 15);
                break;
            case "Libeccio":
                addMenu("Formaggio", 12.5);
                addMenu("Ghiaccio", 1.01);
                addMenu("Melone", 5.2);
                addMenu("Prosciutto and Pesci", 20.23);
                addMenu("Risotto", 13.14);
                addMenu("Zucchero and Sale", 0.6);
                break;
            case "Savage Garden":
                addMenu("Abbacchio's Tea", 1);
                addMenu("DIO's Bread", 36.14);
                addMenu("Giorno's Donuts", 6.66);
                addMenu("Joseph's Tequila", 35);
                addMenu("Kakyoin's Cherry", 3.5);
                addMenu("Kakyoin's Porridge", 4.44);
                break;
            default:
                System.out.println("Menu not available for the specified restaurant.");
                break;
        }
                System.out.println("-+----+----------------------------------------+-------------+-");
                System.out.println(" | No | Food                                   | Price       | ");
                System.out.println("-+----+----------------------------------------+-------------+-");
                int num = 1;
                for(String menus : menu.keySet()){
                    System.out.printf(" | %-2d | %-38s | $%-10.2f |\n",num,menus, menu.get(menus));
                    num++;
                }
                System.out.println("-+----+----------------------------------------+-------------+-");
                System.out.println();
                System.out.println("======================================================================");
    }
    
    public void serveCustomers(Customer[] customers) {
        
        // Check if it's within the allowed serving time (13 PM to 14)
        LocalTime currentTime = LocalTime.now();
        LocalTime servingStartTime = LocalTime.of(13, 0);
        LocalTime servingEndTime = LocalTime.of(14, 0);

        if (currentTime.isBefore(servingStartTime) || currentTime.isAfter(servingEndTime)) {
            System.out.println("Sorry, the restaurant is currently not serving customers.");
            return;
        } else {
            System.out.println("Welcome!");
        }
            // Serve each customer
        for (Customer customer : customers) {
            // Check if the customer has already dined in today
            boolean hasDinedIn = false;
            for (Customer servedCustomer : orders) {
                if (servedCustomer.getName().equals(customer.getName())) {
                    hasDinedIn = true;
                    break;
                }
            }

            if (hasDinedIn) {
                System.out.println("Customer " + customer.getName() + " has already dined in today. Cannot serve again.");
            } 
        }
    }

    // Add a customer to the waiting list
    public void addToWaitingList(Customer[] customers) {
        for(Customer customer : customers){
            waitingList.add(customer);
        }
        
    }

    // Sort the waiting list by arrival time in ascending order
    public void sortWaitingList() {
        Collections.sort(waitingList, Comparator.comparing(Customer::getArrivalTime));
    }

    // Process the orders based on the restaurant's rule
    public void setProcessOrders() {
        orderProcessingList.clear();
        orders.clear();

        switch (selectedRestaurant) {
            case "Jade Garden":
                processJadeGardenOrders();
                break;
            case "Cafe Deux Magots":
                processCafeDeuxMagotsOrders();
                break;
            case "Trattoria Trussardi":
                processTrattoriaTrussardiOrders();
                break;
            case "Libeccio":
                processLibeccioOrders();
                break;
            case "Savage Garden":
                processSavageGardenOrders();
                break;
            default:
                break;
        }
    }

    // Process orders for Jade Garden restaurant
    private void processJadeGardenOrders() {
        int left = 0;
        int right = waitingList.size() - 1;

        while (left <= right) {
            orderProcessingList.add(waitingList.get(left));
            if (left != right) {
                orderProcessingList.add(waitingList.get(right));
            }
            left++;
            right--;
        }
    }

    // Process orders for Cafe Deux Magots restaurant
    private void processCafeDeuxMagotsOrders() {
        List<Customer> orderedList = new ArrayList<>(waitingList);
        Collections.sort(orderedList, Comparator.comparing(Customer::getAge).reversed());

        int left = 0;
        int right = orderedList.size() - 1;

        while (left <= right) {
            orderProcessingList.add(orderedList.get(left));
            if (left != right) {
                orderProcessingList.add(orderedList.get(right));
            }
            left++;
            right--;
        }
    }

    // Process orders for Trattoria Trussardi restaurant
    private void processTrattoriaTrussardiOrders() {
        List<Customer> males = new ArrayList<>();
        List<Customer> females = new ArrayList<>();
        List<Customer> unspecified = new ArrayList<>();

        // Categorize customers based on gender and age
        for (Customer customer : waitingList) {
            if (customer.getGender().equalsIgnoreCase("Male")) {
                males.add(customer);
            } else if (customer.getGender().equalsIgnoreCase("Female")) {
                females.add(customer);
            } else {
                unspecified.add(customer);
            }
        }

        // Process orders based on the rule
        while (!males.isEmpty() || !females.isEmpty()) {
            if (!males.isEmpty()) {
                Customer youngestMale = Collections.min(males, Comparator.comparing(Customer::getAge));
                Customer oldestMale = Collections.max(males, Comparator.comparing(Customer::getAge));

                orderProcessingList.add(youngestMale);
                orderProcessingList.add(oldestMale);

                males.remove(youngestMale);
                males.remove(oldestMale);
            }

            if (!females.isEmpty()) {
                Customer oldestFemale = Collections.max(females, Comparator.comparing(Customer::getAge));
                Customer youngestFemale = Collections.min(females, Comparator.comparing(Customer::getAge));

                orderProcessingList.add(oldestFemale);
                orderProcessingList.add(youngestFemale);

                females.remove(oldestFemale);
                females.remove(youngestFemale);
            }
        }

        // Add remaining unspecified customers to the order processing list
        orderProcessingList.addAll(unspecified);
    }

    // Process orders for Libeccio restaurant
    private void processLibeccioOrders() {
        int index = dayNum - 1;

        while (!waitingList.isEmpty()) {
            int customerIndex = index % waitingList.size();
            Customer customer = waitingList.remove(customerIndex);
            orderProcessingList.add(customer);
        }
    }

    // Process orders for Savage Garden restaurant
    private void processSavageGardenOrders() {
        int index = dayNum - 1;

        while (!waitingList.isEmpty()) {
            int customerIndex = index % waitingList.size();
            Customer customer = waitingList.remove(customerIndex);
            orderProcessingList.add(customer);
            index++;
        }
    }

    // Display the waiting list of the selected restaurant
    public void displayWaitingList() {
        System.out.println("Waiting List for " + selectedRestaurant + ":");
        boolean found = false;
        for (Customer customer : waitingList) {
            if (customer.getRestaurantName().equals(selectedRestaurant)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("+----+--------------------+-----+--------+-");
            System.out.println("| No | Name               | Age | Gender |");
            System.out.println("+----+--------------------+-----+--------+-");
            int count = 1;
            for (Customer customer : waitingList) {
                if (customer.getRestaurantName().equals(selectedRestaurant)) {
                    System.out.printf("| %-2d | %-18s | %-3d | %-6s |\n", count, customer.getName(), customer.getAge(), customer.getGender());
                    count++;
                }
            }
            System.out.println("+----+--------------------+-----+--------+-");
            System.out.println("-+----------------------------------------+");
            System.out.println(" | Order                                  |");
            System.out.println("-+----------------------------------------+");
            for (Customer customer : waitingList) {
                System.out.printf(" | %-38s |\n", customer.getOrder());
            }
            System.out.println("-+----------------------------------------+");
        } else {
            System.out.println("No customers in the waiting list for " + selectedRestaurant);
        }
    }

    // Display the order processing list of the selected restaurant
    public void displayOrderProcessingList() {
        System.out.println();
        System.out.println("Order Processing List for " + selectedRestaurant + ":");
        boolean found = false;
        for (Customer customer : orderProcessingList) {
            if (customer.getRestaurantName().equals(selectedRestaurant)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("+----+--------------------+-----+--------+-");
            System.out.println("| No | Name               | Age | Gender |");
            System.out.println("+----+--------------------+-----+--------+-");
            int count = 1;
            for (Customer customer : orderProcessingList) {
                if (customer.getRestaurantName().equals(selectedRestaurant)) {
                    System.out.printf("| %-2d | %-18s | %-3d | %-6s |\n", count, customer.getName(), customer.getAge(), customer.getGender());
                    count++;
                }
            }
            System.out.println("+----+--------------------+-----+--------+-");
            System.out.println("-+----------------------------------------+");
            System.out.println(" | Order                                  |");
            System.out.println("-+----------------------------------------+");
            for (Customer customer : orderProcessingList) {
                System.out.printf(" | %-38s |\n", customer.getOrder());
            }
            System.out.println("-+----------------------------------------+");
        } else {
            System.out.println("No customers in the order processing list for " + selectedRestaurant);
        }
        System.out.println("======================================================================");
    }

}
