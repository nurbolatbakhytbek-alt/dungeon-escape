package com.dungeondevs.game.patterns.decorator;

public class BasicWeapon extends WeaponDecorator {
    private String name;
    private int damage;

    public BasicWeapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override public int getDamage() { return damage; }
    @Override public String getDescription() { return name; }
}
