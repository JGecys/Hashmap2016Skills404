package main.commands;

import main.Drone;
import main.Order;
import main.Product;

public class DeliverCommand implements IDroneCommand {

    public Order order;
    public Product product;
    public Drone drone;

    public DeliverCommand(Order order, Product product, Drone drone) {
        this.order = order;
        this.product = product;
        this.drone = drone;
    }

    @Override
    public String getCommand() {
        return drone.id + " D " + order.id + " " + product.type + " " + product.count;
    }

    @Override
    public void execute() {
        drone.executedCommands.add(this);
        drone.turn += ((int) Math.ceil(order.destination.distanceTo(drone.position))) + 1;
        drone.position = order.destination;
        drone.loadedProducts.remove(product);
        order.orders.get(product.type).count -= product.count;
        for (Product product : order.orders) {
            if(product.count != 0){
                order.isCompleted = false;
                return;
            }
        }
        order.isCompleted = true;
    }
}
