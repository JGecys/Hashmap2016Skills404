package main;

public class Position {

    public int coordX;
    public int coordY;

    public Position() {
        this(0, 0);
    }

    public Position(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Double distanceTo(Position pos){
        return Math.sqrt(Math.pow(coordX - pos.coordX, 2) + Math.pow(coordY - pos.coordY, 2));
    }

}
