package main;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public Integer id;
    public Position destination;
    public List<Product> orders = new ArrayList<>();
    public boolean isCompleted = false;

    public Order(Integer id, Position destination) {
        this.id = id;
        this.destination = destination;
    }
}
