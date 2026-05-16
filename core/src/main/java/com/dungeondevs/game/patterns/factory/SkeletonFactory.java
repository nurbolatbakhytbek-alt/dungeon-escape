package com.dungeondevs.game.patterns.factory;

import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.entities.enemies.Skeleton;

public class SkeletonFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new Skeleton(x, y);
    }
}
