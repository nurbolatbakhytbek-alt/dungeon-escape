package com.dungeondevs.game.patterns.decorator;

public class IceEnchantment extends WeaponDecorator {
    private WeaponDecorator weapon;

    public IceEnchantment(WeaponDecorator weapon) { this.weapon = weapon; }

    @Override public int getDamage() { return weapon.getDamage() + 10; }
    @Override public String getDescription() { return weapon.getDescription() + " + Ice ❄"; }
}
