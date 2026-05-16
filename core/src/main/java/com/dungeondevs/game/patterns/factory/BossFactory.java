package com.dungeondevs.game.patterns.factory;

import com.dungeondevs.game.entities.enemies.Boss;
import com.dungeondevs.game.entities.enemies.Enemy;

public class BossFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new Boss(x, y);
    }
}
