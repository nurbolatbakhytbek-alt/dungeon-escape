package com.dungeondevs.game.patterns.abstractfactory;

import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.patterns.factory.GoblinFactory;
import com.dungeondevs.game.patterns.factory.SkeletonFactory;
import com.dungeondevs.game.world.Door;
import com.dungeondevs.game.world.MazeBuilder;
import com.dungeondevs.game.world.Wall;
import java.util.ArrayList;
import java.util.List;

public class EasyLevelFactory extends LevelFactory {

    @Override
    public List<Enemy> createEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        GoblinFactory gf = new GoblinFactory();
        SkeletonFactory sf = new SkeletonFactory();
        // Лабиринт ішіндегі жаулар
        enemies.add(gf.createEnemy(280, 200));
        enemies.add(gf.createEnemy(500, 150));
        enemies.add(gf.createEnemy(820, 200));
        enemies.add(sf.createEnemy(650, 480));
        return enemies;
    }

    @Override
    public List<Trap> createTraps() {
        List<Trap> traps = new ArrayList<>();
        traps.add(new Trap("Spike", 380, 350, 10));
        traps.add(new Trap("Spike", 620, 320, 10));
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

    // ✅ ЖАҢА методтар
    public List<Wall> createWalls() { return MazeBuilder.buildEasy(); }
    public Door createDoor() { return new Door(1080, 480); }  // жоғарғы оң бұрыш
    public float[] getPlayerSpawn() { return new float[]{80, 80}; }  // төменгі сол бұрыш

    @Override public String getDifficulty() { return "Easy"; }
}
