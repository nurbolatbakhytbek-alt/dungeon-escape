package com.dungeondevs.game.world;

import com.badlogic.gdx.math.Rectangle;

public class Door {
    private Rectangle bounds;
    private boolean unlocked = false;

    public Door(float x, float y) {
        this.bounds = new Rectangle(x, y, 40, 50);
    }

    public boolean isPlayerNear(float px, float py) {
        return Math.abs(px - (bounds.x + 20)) < 35 &&
            Math.abs(py - (bounds.y + 25)) < 35;
    }

    public void unlock() { unlocked = true; }
    public boolean isUnlocked() { return unlocked; }

    public float getX() { return bounds.x; }
    public float getY() { return bounds.y; }
    public Rectangle getBounds() { return bounds; }
}
