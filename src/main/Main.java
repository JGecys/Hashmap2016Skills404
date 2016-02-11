package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        new Main();
    }

    public Integer rows;
    public Integer columns;
    public Integer turns;
    public List<Drone> drones = new ArrayList<>();
    public List<Warehouse> warehouses = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();
    public String FILE_NAME = "busy_day.in";

    public Main() {
        init();
    }

    private void init() {
        readFile();
        System.out.println("");
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
                    temp.orders.add(new Product(j, scanner.nextInt()));
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
