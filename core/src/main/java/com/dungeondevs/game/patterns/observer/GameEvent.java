package com.dungeondevs.game.patterns.observer;

public class GameEvent {
    public enum Type {
        ENEMY_KILLED, TRAP_DISARMED, LEVEL_COMPLETED,
        BOSS_DEFEATED, PLAYER_DIED, ITEM_PICKED_UP
    }

    private final Type type;
    private final Object data;

    public GameEvent(Type type) { this(type, null); }
    public GameEvent(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() { return type; }
    public Object getData() { return data; }
}
