package com.dungeondevs.game.patterns.abstractfactory;

import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.patterns.factory.BossFactory;
import com.dungeondevs.game.patterns.factory.GoblinFactory;
import com.dungeondevs.game.patterns.factory.SkeletonFactory;
import com.dungeondevs.game.world.Door;
import com.dungeondevs.game.world.MazeBuilder;
import com.dungeondevs.game.world.Wall;
import java.util.ArrayList;
import java.util.List;

public class HardLevelFactory extends LevelFactory {

    @Override
    public List<Enemy> createEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        GoblinFactory gf = new GoblinFactory();
        SkeletonFactory sf = new SkeletonFactory();
        BossFactory bf = new BossFactory();

        enemies.add(gf.createEnemy(220, 200));
        enemies.add(gf.createEnemy(380, 400));
        enemies.add(gf.createEnemy(550, 200));
        enemies.add(sf.createEnemy(680, 450));
        enemies.add(sf.createEnemy(820, 300));
        enemies.add(bf.createEnemy(1050, 450));  // Boss есіктің жанында!

        return enemies;
    }

    @Override
    public List<Trap> createTraps() {
        List<Trap> traps = new ArrayList<>();
        traps.add(new Trap("Fire Trap", 350, 300, 20));
        traps.add(new Trap("Poison Trap", 550, 350, 15));
        traps.add(new Trap("Spike", 800, 200, 10));
        traps.add(new Trap("Fire Trap", 950, 200, 20));
        return traps;
    }

    @Override
    public List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (int x = 0; x < 30; x++)
            for (int y = 0; y < 15; y++)
                tiles.add(new Tile(Tile.Type.FLOOR, x * 40, y * 40));
        return tiles;
    }

    public List<Wall> createWalls() { return MazeBuilder.buildHard(); }
    public Door createDoor() { return new Door(1080, 480); }
    public float[] getPlayerSpawn() { return new float[]{70, 70}; }

    @Override public String getDifficulty() { return "Hard"; }
}
