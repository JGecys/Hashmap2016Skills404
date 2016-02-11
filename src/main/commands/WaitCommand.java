package main.commands;

import main.Drone;

public class WaitCommand implements IDroneCommand {

    public Drone drone;

    public Integer wait;

    public WaitCommand(Drone drone, Integer wait) {
        this.drone = drone;
        this.wait = wait;
    }

    @Override
    public String getCommand() {
        return drone.id + " W " + wait;
    }

    @Override
    public void execute() {
        drone.turn += wait;
    }
}
