package com.dungeondevs.game.patterns.abstractfactory;

import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.world.Door;
import com.dungeondevs.game.world.Wall;
import java.util.List;

public abstract class LevelFactory {
    public abstract List<Enemy> createEnemies();
    public abstract List<Trap> createTraps();
    public abstract List<Tile> createTiles();
    public abstract String getDifficulty();

    // ✅ ЖАҢА абстрактты методтар
    public abstract List<Wall> createWalls();
    public abstract Door createDoor();
    public abstract float[] getPlayerSpawn();
}
