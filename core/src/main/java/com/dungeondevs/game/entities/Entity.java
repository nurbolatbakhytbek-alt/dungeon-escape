package com.dungeondevs.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected int hp;
    protected int maxHp;
    protected boolean alive;
    protected boolean deathProcessed = false;  // Бір рет қана өңдеу үшін

    public Entity(float x, float y, int hp) {
        this.position = new Vector2(x, y);
        this.hp = hp;
        this.maxHp = hp;
        this.alive = true;  // ✅ Анық белгілеу
    }

    public final void update(float delta) {
        if (!alive) return;
        updateBehavior(delta);
        updateAnimation(delta);
        checkBounds();
    }

    protected abstract void updateBehavior(float delta);
    protected abstract void updateAnimation(float delta);
    protected abstract void checkBounds();

    public abstract void render(SpriteBatch batch);

    public void takeDamage(int dmg) {
        if (!alive) return;
        hp -= dmg;
        if (hp <= 0) {
            hp = 0;
            alive = false;
            onDeath();
        }
    }

    protected void onDeath() {}

    public float distanceTo(Entity other) {
        return position.dst(other.position);
    }

    public boolean isDeathProcessed() { return deathProcessed; }
    public void setDeathProcessed(boolean v) { this.deathProcessed = v; }

    public Vector2 getPosition() { return position; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return alive; }
}
