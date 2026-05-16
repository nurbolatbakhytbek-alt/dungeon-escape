package com.dungeondevs.game.patterns.strategy;

import com.dungeondevs.game.entities.Entity;

public class MagicAttack implements AttackStrategy {
    private static final int DAMAGE = 35;

    @Override
    public void attack(Entity attacker, Entity target) {
        float distance = attacker.distanceTo(target);
        if (distance < 200f) {
            target.takeDamage(DAMAGE);
            System.out.println("Magic blast: -" + DAMAGE + " HP");
        }
    }

    @Override public String getName() { return "Fireball"; }
    @Override public int getBaseDamage() { return DAMAGE; }
}
