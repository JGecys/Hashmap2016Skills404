package main;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    public Integer id;
    public Position location;
    public List<Product> products = new ArrayList<>();

    public Warehouse(Integer id, Position location) {
        this.id = id;
        this.location = location;
    }

}
