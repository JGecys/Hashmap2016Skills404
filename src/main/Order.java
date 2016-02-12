package main;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public int id;
    public Position destination;
    public List<Product> orders = new ArrayList<>();
    public boolean isCompleted = false;

    public Order(Integer id, Position destination) {
        this.id = id;
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        return !(destination != null ? !destination.equals(order.destination) : order.destination != null);

    }
}
