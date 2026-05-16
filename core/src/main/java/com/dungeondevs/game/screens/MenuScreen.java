package com.dungeondevs.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dungeondevs.game.DungeonEscape;

public class MenuScreen implements Screen {

    private final DungeonEscape game;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public MenuScreen(DungeonEscape game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font.draw(game.batch, "DUNGEON ESCAPE", 260, 400);
        font.draw(game.batch, "Click anywhere to START", 270, 300);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
