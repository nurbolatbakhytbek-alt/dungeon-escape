package com.dungeondevs.game.patterns.strategy;

import com.dungeondevs.game.entities.Entity;

public class MeleeAttack implements AttackStrategy {
    private static final int DAMAGE = 30;

    @Override
    public void attack(Entity attacker, Entity target) {
        float distance = attacker.distanceTo(target);
        if (distance < 50f) {
            target.takeDamage(DAMAGE);
            System.out.println("Melee attack: -" + DAMAGE + " HP");
        }
    }

    @Override public String getName() { return "Sword Strike"; }
    @Override public int getBaseDamage() { return DAMAGE; }
}
