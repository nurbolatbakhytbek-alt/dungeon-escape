package com.dungeondevs.game.patterns.decorator;

public class FireEnchantment extends WeaponDecorator {
    private WeaponDecorator weapon;

    public FireEnchantment(WeaponDecorator weapon) { this.weapon = weapon; }

    @Override public int getDamage() { return weapon.getDamage() + 15; }
    @Override public String getDescription() { return weapon.getDescription() + " + Fire "; }
}
