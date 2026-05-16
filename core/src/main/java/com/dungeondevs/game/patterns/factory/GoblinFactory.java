package com.dungeondevs.game.patterns.factory;

import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.entities.enemies.Goblin;

public class GoblinFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new Goblin(x, y);
    }
}
