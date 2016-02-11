package tests

import junit.framework.TestCase
import main.*
import main.commands.IDroneCommand
import main.commands.LoadCommand
import org.junit.Test

import java.util.function.Consumer

class DroneCommandsTest extends TestCase {

    @Test
    public void testDroneCommand(){
        Drone drone = new Drone(0, new Position(0, 0));
        Product product = new Product(0, 5);
        Warehouse warehouse = new Warehouse(0, new Position(0, 1));
        Order order = new Order(0, new Position(7, 5));
        order.orders.add(product);
        warehouse.products.add(product);
        drone.commands.add(new LoadCommand(warehouse, product, drone));
        drone.commands.forEach(new Consumer<IDroneCommand>() {
            @Override
            void accept(IDroneCommand iDroneCommand) {
                println iDroneCommand.getCommand();
                iDroneCommand.execute();
            }
        })
        assertEquals(12, drone.turn);
    }

}
