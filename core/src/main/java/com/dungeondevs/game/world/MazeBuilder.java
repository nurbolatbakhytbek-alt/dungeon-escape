package com.dungeondevs.game.world;

import java.util.ArrayList;
import java.util.List;

public class MazeBuilder {

    /** Easy лабиринт - қарапайым */
    public static List<Wall> buildEasy() {
        List<Wall> walls = new ArrayList<>();
        // Сыртқы периметр (рамка қалың емес)
        walls.add(new Wall(0, 0, 1200, 30));         // bottom
        walls.add(new Wall(0, 570, 1200, 30));       // top
        walls.add(new Wall(0, 0, 30, 600));          // left
        walls.add(new Wall(1170, 0, 30, 600));       // right

        // Ішкі лабиринт қабырғалары
        walls.add(new Wall(200, 100, 30, 200));      // вертикалды
        walls.add(new Wall(200, 100, 150, 30));      // горизонталды

        walls.add(new Wall(400, 250, 200, 30));      // ортасында
        walls.add(new Wall(400, 280, 30, 200));

        walls.add(new Wall(700, 100, 30, 250));      // оң жақ
        walls.add(new Wall(700, 100, 200, 30));

        walls.add(new Wall(550, 400, 250, 30));      // төменгі ортасы

        walls.add(new Wall(900, 350, 30, 200));      // оң блок

        return walls;
    }

    /** Hard лабиринт - күрделірек */
    public static List<Wall> buildHard() {
        List<Wall> walls = new ArrayList<>();
        // Сыртқы периметр
        walls.add(new Wall(0, 0, 1200, 30));
        walls.add(new Wall(0, 570, 1200, 30));
        walls.add(new Wall(0, 0, 30, 600));
        walls.add(new Wall(1170, 0, 30, 600));

        // Күрделі лабиринт - көп бұрылыс
        walls.add(new Wall(150, 100, 30, 250));
        walls.add(new Wall(150, 100, 150, 30));
        walls.add(new Wall(150, 450, 30, 120));
        walls.add(new Wall(150, 450, 200, 30));

        walls.add(new Wall(300, 200, 30, 150));
        walls.add(new Wall(300, 200, 100, 30));

        walls.add(new Wall(450, 100, 30, 200));
        walls.add(new Wall(450, 350, 30, 150));
        walls.add(new Wall(450, 480, 150, 30));

        walls.add(new Wall(600, 100, 150, 30));
        walls.add(new Wall(600, 250, 30, 150));
        walls.add(new Wall(600, 380, 100, 30));

        walls.add(new Wall(750, 200, 30, 200));
        walls.add(new Wall(750, 480, 200, 30));

        walls.add(new Wall(850, 100, 30, 250));
        walls.add(new Wall(850, 100, 200, 30));

        walls.add(new Wall(950, 350, 30, 150));

        return walls;
    }
}
