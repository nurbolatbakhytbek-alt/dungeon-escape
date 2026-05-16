package com.dungeondevs.game.patterns.factory;

import com.dungeondevs.game.entities.enemies.Enemy;

public abstract class EnemyFactory {
    public abstract Enemy createEnemy(float x, float y);
}
