package com.dungeondevs.game.world;

import com.badlogic.gdx.math.Rectangle;

public class Wall {
    private Rectangle bounds;

    public Wall(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public boolean collidesWith(float x, float y, float radius) {
        // Hero шеңбер тәрізді коллизия тексеру
        float closestX = Math.max(bounds.x, Math.min(x, bounds.x + bounds.width));
        float closestY = Math.max(bounds.y, Math.min(y, bounds.y + bounds.height));
        float dx = x - closestX;
        float dy = y - closestY;
        return (dx * dx + dy * dy) < (radius * radius);
    }

    public Rectangle getBounds() { return bounds; }
    public float getX() { return bounds.x; }
    public float getY() { return bounds.y; }
    public float getWidth() { return bounds.width; }
    public float getHeight() { return bounds.height; }
}
