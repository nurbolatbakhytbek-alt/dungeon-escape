package com.dungeondevs.game.patterns.strategy;

import com.dungeondevs.game.entities.Entity;

public interface AttackStrategy {
    void attack(Entity attacker, Entity target);
    String getName();
    int getBaseDamage();
}
