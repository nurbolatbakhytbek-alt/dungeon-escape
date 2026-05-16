package com.dungeondevs.game.patterns.abstractfactory;

import com.dungeondevs.game.entities.heroes.Hero;

public class Trap {
    private float x, y;
    private int damage;
    private String name;

    public Trap(String name, float x, float y, int damage) {
        this.name = name; this.x = x; this.y = y; this.damage = damage;
    }

    public void activate(Hero hero) {
        if (Math.abs(hero.getPosition().x - x) < 32 &&
            Math.abs(hero.getPosition().y - y) < 32) {
            hero.takeDamage(damage);
        }
    }

    public String getName() { return name; }
    public float getX() { return x; }
    public float getY() { return y; }
}
