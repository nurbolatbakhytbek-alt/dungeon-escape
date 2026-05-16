package com.dungeondevs.game.patterns.decorator;

public class PoisonEnchantment extends WeaponDecorator {
    private WeaponDecorator weapon;

    public PoisonEnchantment(WeaponDecorator weapon) { this.weapon = weapon; }

    @Override public int getDamage() { return weapon.getDamage() + 8; }
    @Override public String getDescription() { return weapon.getDescription() + " + Poison"; }
}
