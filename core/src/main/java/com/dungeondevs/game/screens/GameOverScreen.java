package com.dungeondevs.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dungeondevs.game.DungeonEscape;
import com.dungeondevs.game.managers.AssetManager;
import com.dungeondevs.game.managers.ScoreManager;

public class GameOverScreen implements Screen {
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private float time = 0;

    public GameOverScreen(DungeonEscape game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.sr = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 600);
    }

    @Override public void show() {}

    @Override
    public void render(float delta) {
        time += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.08f, 0.02f, 0.02f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 600; i++) {
            float a = i / 600f * 0.4f;
            sr.setColor(0.5f, 0.05f, 0.05f, a);
            sr.rect(0, i, 1200, 1);
        }
        sr.setColor(0, 0, 0, 0.8f);
        sr.rect(350, 130, 500, 340);
        sr.setColor(0.15f, 0.05f, 0.05f, 1);
        sr.rect(400, 280, 400, 60);
        sr.end();

        Gdx.gl.glLineWidth(3);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0.7f, 0.1f, 0.1f, 1);
        sr.rect(350, 130, 500, 340);
        sr.setColor(0.9f, 0.7f, 0.2f, 1);
        sr.rect(400, 280, 400, 60);
        sr.end();
        Gdx.gl.glLineWidth(1);

        float blink = (float)(Math.sin(time * 4) * 0.5f + 0.5f);

        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(3.5f);
        AssetManager.getInstance().getFont().setColor(0.95f, 0.15f, 0.15f, 1);
        AssetManager.getInstance().getFont().draw(batch, "GAME OVER", 425, 445);

        AssetManager.getInstance().getFont().getData().setScale(1.1f);
        AssetManager.getInstance().getFont().setColor(0.7f, 0.7f, 0.7f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "The dungeon has claimed another soul...", 425, 385);

        AssetManager.getInstance().getFont().getData().setScale(1.6f);
        AssetManager.getInstance().getFont().setColor(1f, 0.85f, 0.3f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "FINAL SCORE: " + ScoreManager.getInstance().getCurrentScore(), 450, 320);

        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(0.8f, 0.8f, 0.9f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "Best Score: " + ScoreManager.getInstance().getHighScore(), 540, 250);

        AssetManager.getInstance().getFont().getData().setScale(1.2f);
        AssetManager.getInstance().getFont().setColor(1f, 1f, 1f, 0.4f + blink * 0.6f);
        AssetManager.getInstance().getFont().draw(batch, "[R]  RESTART", 460, 195);
        AssetManager.getInstance().getFont().setColor(0.9f, 0.9f, 0.9f, 0.4f + blink * 0.6f);
        AssetManager.getInstance().getFont().draw(batch, "[ESC]  MAIN MENU", 630, 195);

        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            ScoreManager.getInstance().reset();
            game.setScreen(new GameScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScoreManager.getInstance().reset();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
