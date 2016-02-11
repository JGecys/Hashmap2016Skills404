package main;

import main.commands.DeliverCommand;
import main.commands.IDroneCommand;
import main.commands.LoadCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        new Main();
    }

    public static Integer cnt = 0;
    public Integer currentTurn = 0;
    public Integer rows;
    public Integer columns;
    public Integer turns;
    public List<Drone> drones = new ArrayList<>();
    public List<Warehouse> warehouses = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();
    public String FILE_NAME = "mother_of_all_warehouses.in";

    public Main() {
        init();
    }

    private void init() {
        readFile();
        execution();
        try {
            PrintWriter writer = new PrintWriter("result.txt");
            writer.write(cnt + "\r\n");
            for (Drone drone : drones) {
                for (IDroneCommand executedCommand : drone.executedCommands) {
                    writer.write(executedCommand.getCommand() + "\r\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> ignore = new ArrayList<>();

    public void execution() {
        while (incompleteOrders() && currentTurn < turns) {
            for (Order order : getTodoOrders()) {
                int treshhold = 0;
                Drone closestAvailableDrone = getClosestAvailableDrone(order.destination);
                System.out.println("executing order: " + order.id);
                for (Product product : order.orders) {
                    if (product.count > 0) {
                        Warehouse warehouse = getWarehouse(order.destination, product);
                        Integer weight = ProductWeights.getInstance().getWeight(product.type);
                        Integer limit = ProductWeights.getInstance().getWeightLimit();
                        Integer availableWeight = limit - closestAvailableDrone.getWeight();
                        Integer count = availableWeight / weight;
                        int min = Math.min(count, warehouse.products.get(product.type).count);
                        if(min != 0) {
                            closestAvailableDrone.commands
                                    .add(new LoadCommand(warehouse,
                                            new Product(product.type, min),
                                            closestAvailableDrone));
                            closestAvailableDrone.execute();
                        }
                    }
                }
                for (Product product : closestAvailableDrone.loadedProducts) {
                    closestAvailableDrone.commands.add(new DeliverCommand(order, product, closestAvailableDrone));
                }
                closestAvailableDrone.execute();
            }
            currentTurn++;
        }
    }

    public boolean incompleteOrders() {
        for (Order order : orders) {
            if(ignore.indexOf(order.id) < 0){
                for (Product product : order.orders) {
                    if (product.count > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Warehouse getWarehouse(Position pos, Product product) {
        List<Warehouse> sortedWarehouses = getSortedWarehouses(pos, product);
        if (sortedWarehouses.size() > 0) {
            return sortedWarehouses.get(0);
        }

        return null;
    }

    private List<Warehouse> getSortedWarehouses(Position pos, Product product) {
        List<Warehouse> hasProduct = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.products.get(product.type).count >= product.count) {
                hasProduct.add(warehouse);
            }
        }
        if (hasProduct.size() > 0) {
            hasProduct.sort((o1, o2) -> o1.location.distanceTo(pos).compareTo(o2.location.distanceTo(pos)));
            return hasProduct;
        }
        for (Warehouse warehouse : warehouses) {
            if (warehouse.products.get(product.type).count > 0) {
                hasProduct.add(warehouse);
            }
        }
        if(hasProduct.size() > 0){
            hasProduct.sort((o1, o2) -> o1.products.get(product.type).count.compareTo(o2.products.get(product.type).count));
            return hasProduct;
        }
        for (Warehouse warehouse : warehouses) {
            hasProduct.add(warehouse);
        }

        hasProduct.sort((o1, o2) -> o1.products.get(product.type).count.compareTo(o2.products.get(product.type).count) * -1);
        return hasProduct;

    }

    public Order getBestOrder() {
        List<Order> todoOrders = getTodoOrders();
        Position averageDronePosition = getDronesAvaragePosition();
        todoOrders.sort((o1, o2) -> o1.destination.distanceTo(averageDronePosition)
                .compareTo(o2.destination.distanceTo(averageDronePosition)));
        return todoOrders.get(0);
    }

    public List<Order> getTodoOrders() {
        List<Order> todo = new ArrayList<>();
        for (Order order : orders) {
            if (!order.isCompleted) {
                todo.add(order);
            }
        }
        return todo;
    }

    public List<Drone> getAvailableDrones() {
        List<Drone> availableDrones = new ArrayList<>();
        while (currentTurn < turns) {
            for (Drone drone : drones) {
                if (drone.turn <= currentTurn) {
                    availableDrones.add(drone);
                }
            }
            if (availableDrones.size() > 0) {
                return availableDrones;
            }
            currentTurn++;
        }
        return null;
    }

    public Position getDronesAvaragePosition() {
        int x = 0, y = 0;
        List<Drone> availableDrones = getAvailableDrones();
        for (Drone drone : availableDrones) {
            x += drone.position.coordX;
            y += drone.position.coordY;
        }
        return new Position(x / availableDrones.size(), y / availableDrones.size());
    }

    public Drone getClosestAvailableDrone(Position pos) {
        while (currentTurn < turns) {
            List<Drone> availableDrones = new ArrayList<>();
            for (Drone drone : drones) {
                if (drone.turn <= currentTurn) {
                    availableDrones.add(drone);
                }
            }
            if (availableDrones.size() > 0) {
                availableDrones.sort((o1, o2) -> o1.position.distanceTo(pos).compareTo(o2.position.distanceTo(pos)));
                return availableDrones.get(0);
            }
            currentTurn++;
        }
        return null;
    }


    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

            Scanner scanner = new Scanner(br);

            this.rows = scanner.nextInt();
            this.columns = scanner.nextInt();
            int droneCount = scanner.nextInt();
            this.turns = scanner.nextInt();

            ProductWeights.getInstance().setWeightLimit(scanner.nextInt());
            scanner.nextLine();
            int productTypesCount = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < productTypesCount; i++)
                ProductWeights.getInstance().addWeight(scanner.nextInt());

            scanner.nextLine();
            int warehousesCount = scanner.nextInt();
            for (int i = 0; i < warehousesCount; i++) {
                scanner.nextLine();
                int x, y;
                x = scanner.nextInt();
                y = scanner.nextInt();
                if (i == 0) {
                    for (int j = 0; j < droneCount; j++) {
                        drones.add(new Drone(j, new Position(x, y)));
                    }
                }
                scanner.nextLine();
                Warehouse warehouse = new Warehouse(i, new Position(x, y));
                for (int j = 0; j < productTypesCount; j++) {
                    warehouse.products.add(new Product(j, scanner.nextInt()));
                }
                warehouses.add(warehouse);
            }
            scanner.nextLine();
            int ordersCount = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < ordersCount; i++) {
                int x, y;
                x = scanner.nextInt();
                y = scanner.nextInt();
                scanner.nextLine();
                int orderProductCount = scanner.nextInt();
                scanner.nextLine();
                Order temp = new Order(i, new Position(x, y));
                for (int j = 0; j < orderProductCount; j++) {
                    int type = scanner.nextInt();
                    boolean found = false;
                    for (Product order : temp.orders) {
                        if(order.type.equals(type)){
                            order.count++;
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        temp.orders.add(new Product(type, 1));
                    }
                }
                orders.add(temp);
                scanner.nextLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
