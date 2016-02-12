package main.commands;

import main.Drone;
import main.Main;
import main.Order;
import main.Product;

public class DeliverCommand implements IDroneCommand {

    private final int count;
    public Order order;
    public Product product;
    public Drone drone;

    public DeliverCommand(Order order, Product product, Drone drone) {
        this.count = product.count;
        this.order = order;
        this.product = product;
        this.drone = drone;
    }

    @Override
    public String getCommand() {
        return drone.id + " D " + order.id + " " + product.type + " " + count;
    }

    @Override
    public void execute() {
        drone.executedCommands.add(this);
        drone.turn += ((int) Math.ceil(order.destination.distanceTo(drone.position))) + 1;
        drone.position = order.destination;
        drone.loadedProducts.remove(product);
        for (Product order1 : order.orders) {
            if(order1.type == product.type){
                order1.count -= product.count;
            }
        }
        for (Product product : order.orders) {
            if(product.count > 0){
                order.isCompleted = false;
                return;
            }
        }
        order.isCompleted = true;
        Main.cnt++;
    }
}
