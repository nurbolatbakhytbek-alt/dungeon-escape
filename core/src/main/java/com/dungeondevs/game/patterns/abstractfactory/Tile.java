package com.dungeondevs.game.patterns.abstractfactory;

public class Tile {
    public enum Type { FLOOR, WALL, EXIT }
    private Type type;
    private float x, y;

    public Tile(Type type, float x, float y) {
        this.type = type; this.x = x; this.y = y;
    }

    public Type getType() { return type; }
    public float getX() { return x; }
    public float getY() { return y; }
}
