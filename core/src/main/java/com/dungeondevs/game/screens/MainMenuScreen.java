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

public class MainMenuScreen implements Screen {
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private float time = 0;

    public MainMenuScreen(DungeonEscape game) {
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

        Gdx.gl.glClearColor(0.03f, 0.02f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        // Жоғарғы градиент
        for (int i = 0; i < 250; i++) {
            float a = (250 - i) / 250f * 0.3f;
            sr.setColor(0.4f, 0.1f, 0.2f, a);
            sr.rect(0, 600 - i, 1200, 1);
        }
        // Төменгі градиент
        for (int i = 0; i < 180; i++) {
            float a = (180 - i) / 180f * 0.25f;
            sr.setColor(0.2f, 0, 0.3f, a);
            sr.rect(0, i, 1200, 1);
        }

        // Жұлдыздар
        for (int i = 0; i < 60; i++) {
            float sx = (i * 73 + 27) % 1200;
            float sy = (i * 131 + 87) % 600;
            float tw = (float)(Math.sin(time * 2 + i) * 0.3f + 0.7f);
            sr.setColor(0.9f, 0.9f, 1f, tw * 0.4f);
            sr.circle(sx, sy, 1);
        }

        // Алау - сол жақ
        float fy1 = 380 + (float)Math.sin(time * 4) * 8;
        sr.setColor(1f, 0.3f, 0.05f, 0.7f); sr.circle(150, fy1, 35);
        sr.setColor(1f, 0.6f, 0.1f, 0.9f); sr.circle(150, fy1 + 5, 22);
        sr.setColor(1f, 0.9f, 0.3f, 1f); sr.circle(150, fy1 + 10, 10);

        // Алау - оң жақ
        float fy2 = 380 + (float)Math.sin(time * 4 + 2) * 8;
        sr.setColor(1f, 0.3f, 0.05f, 0.7f); sr.circle(1050, fy2, 35);
        sr.setColor(1f, 0.6f, 0.1f, 0.9f); sr.circle(1050, fy2 + 5, 22);
        sr.setColor(1f, 0.9f, 0.3f, 1f); sr.circle(1050, fy2 + 10, 10);

        // Тақырып фоны
        sr.setColor(0, 0, 0, 0.6f);
        sr.rect(300, 420, 600, 120);

        // Меню түймелер фоны
        float pulse = (float)(Math.sin(time * 3) * 0.15f + 0.4f);
        sr.setColor(0.6f, 0.2f, 0.1f, pulse);
        sr.rect(450, 280, 300, 55);
        sr.setColor(0.3f, 0.1f, 0.3f, 0.7f);
        sr.rect(450, 200, 300, 55);
        sr.end();

        // Жиектемелер
        Gdx.gl.glLineWidth(3);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0.9f, 0.7f, 0.3f, 1);
        sr.rect(300, 420, 600, 120);
        sr.setColor(1f, 0.7f, 0.3f, 1);
        sr.rect(450, 280, 300, 55);
        sr.setColor(0.6f, 0.4f, 0.6f, 1);
        sr.rect(450, 200, 300, 55);
        sr.end();
        Gdx.gl.glLineWidth(1);

        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(3f);
        AssetManager.getInstance().getFont().setColor(0.95f, 0.75f, 0.3f, 1);
        AssetManager.getInstance().getFont().draw(batch, "DUNGEON ESCAPE", 360, 510);

        AssetManager.getInstance().getFont().getData().setScale(1.1f);
        AssetManager.getInstance().getFont().setColor(0.7f, 0.7f, 0.8f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "Escape the cursed dungeon...", 470, 450);

        AssetManager.getInstance().getFont().getData().setScale(1.4f);
        AssetManager.getInstance().getFont().setColor(1, 1, 1, 1);
        AssetManager.getInstance().getFont().draw(batch, "[ENTER]  START GAME", 480, 315);
        AssetManager.getInstance().getFont().setColor(0.9f, 0.9f, 0.9f, 1);
        AssetManager.getInstance().getFont().draw(batch, "[ESC]  QUIT", 540, 235);


        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            game.setScreen(new HeroSelectScreen(game));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
