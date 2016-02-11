package main.commands;

import main.Drone;
import main.Product;
import main.Warehouse;

public class LoadCommand implements IDroneCommand {

    public Warehouse warehouse;
    public Product product;
    public Drone drone;

    public LoadCommand(Warehouse warehouse, Product product, Drone drone) {
        this.warehouse = warehouse;
        this.product = product;
        this.drone = drone;
    }

    @Override
    public String getCommand() {
        return drone.id + " L " + warehouse.id + " " + product.type + " " + product.count;
    }

    @Override
    public void execute() {
        drone.turn += ((int) Math.ceil(warehouse.location.distanceTo(drone.position))) + 1;
        drone.position = warehouse.location;
        drone.loadedProducts.add(product);
    }
}