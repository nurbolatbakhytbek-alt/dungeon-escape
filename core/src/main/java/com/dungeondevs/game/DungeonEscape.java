package com.dungeondevs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.managers.AssetManager;
import com.dungeondevs.game.managers.ScoreManager;
import com.dungeondevs.game.patterns.observer.GameEventManager;
import com.dungeondevs.game.screens.MenuScreen;

public class DungeonEscape extends Game {

    public SpriteBatch batch;

    private static DungeonEscape instance;
    private String selectedHero = "Warrior";
    private String difficulty = "Easy";
    private GameEventManager eventManager;

    public static DungeonEscape getInstance() { return instance; }

    public void create() {
        instance = this;
        batch = new SpriteBatch();
        eventManager = new GameEventManager();
        AssetManager.getInstance().loadAll();
        ScoreManager.getInstance();
        setScreen(new MenuScreen(this));
    }

    public String getSelectedHero() { return selectedHero; }
    public void setSelectedHero(String hero) { this.selectedHero = hero; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public GameEventManager getEvents() { return eventManager; }

    public void gameOver(int score) { ScoreManager.getInstance().saveScore(score); }
    public void gameWon(int score) { ScoreManager.getInstance().saveScore(score); }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
