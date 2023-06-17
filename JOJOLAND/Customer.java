package JOJOLAND;

public class Customer {
    private String name;
    private String restaurantName;
    private String order;

    public Customer(String name, String restaurantName, String order) {
        this.name = name;
        this.restaurantName = restaurantName;
        this.order = order;
    }

    public String getName() {
        return name;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public String getOrder() {
        return order;
    }
    
}