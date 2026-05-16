package com.dungeondevs.game.patterns.strategy;

import com.dungeondevs.game.entities.Entity;

public class RangedAttack implements AttackStrategy {
    private static final int DAMAGE = 20;
    private static final float RANGE = 120f;

    @Override
    public void attack(Entity attacker, Entity target) {
        float distance = attacker.distanceTo(target);
        if (distance < RANGE) {
            target.takeDamage(DAMAGE);
            System.out.println("Arrow shot: -" + DAMAGE + " HP");
        }
    }

    @Override public String getName() { return "Bow Shot"; }
    @Override public int getBaseDamage() { return DAMAGE; }
}
