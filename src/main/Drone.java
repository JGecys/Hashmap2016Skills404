package main;

import main.commands.IDroneCommand;

import java.util.ArrayList;
import java.util.List;

public class Drone {

    public Integer id;
    public Position position;
    public List<IDroneCommand> executedCommands = new ArrayList<>();
    public List<IDroneCommand> commands = new ArrayList<>();
    public List<Product> loadedProducts = new ArrayList<>();
    public Integer turn = 0;

    public Drone(Integer id, Position position) {
        this.id = id;
        this.position = position;
    }

    public void execute(){
        for(int i = executedCommands.size(); i < commands.size(); i++){
            commands.get(i).execute();
        }
    }

}
