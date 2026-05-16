package com.dungeondevs.game.managers;

import com.dungeondevs.game.patterns.observer.GameEvent;
import com.dungeondevs.game.patterns.observer.GameEventListener;

public class ScoreManager implements GameEventListener {
    private static ScoreManager instance;
    private int currentScore = 0;
    private int highScore = 0;

    private ScoreManager() {}

    public static synchronized ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ENEMY_KILLED: currentScore += 100; break;
            case TRAP_DISARMED: currentScore += 50; break;
            case LEVEL_COMPLETED: currentScore += 500; break;
            case BOSS_DEFEATED: currentScore += 1000; break;
        }
    }

    public void saveScore(int score) {
        if (score > highScore) highScore = score;
    }

    public int getCurrentScore() { return currentScore; }
    public int getHighScore() { return highScore; }
    public void reset() { currentScore = 0; }
}
