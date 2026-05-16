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

public class WinScreen implements Screen {
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private float time = 0;

    // Конфетти бөлшектері
    private float[] confettiX = new float[80];
    private float[] confettiY = new float[80];
    private float[] confettiSpeed = new float[80];
    private float[] confettiColor = new float[80];

    public WinScreen(DungeonEscape game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.sr = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 600);

        // Конфетти инициализациясы
        for (int i = 0; i < 80; i++) {
            confettiX[i] = (float)(Math.random() * 1200);
            confettiY[i] = 600 + (float)(Math.random() * 300);
            confettiSpeed[i] = 50 + (float)(Math.random() * 100);
            confettiColor[i] = (float)(Math.random() * 6);
        }
    }

    @Override public void show() {}

    @Override
    public void render(float delta) {
        time += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.03f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Алтын градиент (жоғарыдан)
        for (int i = 0; i < 600; i++) {
            float a = i / 600f * 0.25f;
            sr.setColor(0.9f, 0.7f, 0.2f, a);
            sr.rect(0, 600 - i, 1200, 1);
        }

        // Жұлдыздар - қуанышты
        for (int i = 0; i < 80; i++) {
            float sx = (i * 73 + 27) % 1200;
            float sy = (i * 131 + 87) % 600;
            float tw = (float)(Math.sin(time * 3 + i) * 0.4f + 0.6f);
            sr.setColor(1f, 0.95f, 0.5f, tw * 0.7f);
            sr.circle(sx, sy, 2);
        }

        // Конфетти жаңарту және салу
        for (int i = 0; i < 80; i++) {
            confettiY[i] -= confettiSpeed[i] * delta;
            if (confettiY[i] < -20) {
                confettiY[i] = 620;
                confettiX[i] = (float)(Math.random() * 1200);
            }
            // Жанға қозғалу (тербеліс)
            float xOff = (float)Math.sin((time + i) * 2) * 3;

            // Түс
            int c = (int) confettiColor[i];
            switch (c) {
                case 0: sr.setColor(1f, 0.3f, 0.3f, 1); break;
                case 1: sr.setColor(0.3f, 0.9f, 0.4f, 1); break;
                case 2: sr.setColor(0.3f, 0.6f, 1f, 1); break;
                case 3: sr.setColor(1f, 0.9f, 0.3f, 1); break;
                case 4: sr.setColor(0.9f, 0.4f, 0.9f, 1); break;
                default: sr.setColor(1f, 0.6f, 0.2f, 1); break;
            }
            sr.rect(confettiX[i] + xOff, confettiY[i], 6, 10);
        }

        // Орталық панель
        sr.setColor(0, 0, 0, 0.7f);
        sr.rect(300, 130, 600, 340);

        // Алтын score қорабы
        sr.setColor(0.2f, 0.15f, 0.05f, 1);
        sr.rect(380, 270, 440, 70);
        sr.end();

        // Жиектемелер - қос алтын
        Gdx.gl.glLineWidth(4);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(1f, 0.85f, 0.3f, 1);
        sr.rect(300, 130, 600, 340);
        sr.setColor(0.95f, 0.75f, 0.2f, 1);
        sr.rect(290, 120, 620, 360);
        sr.setColor(1f, 0.85f, 0.3f, 1);
        sr.rect(380, 270, 440, 70);
        sr.end();
        Gdx.gl.glLineWidth(1);

        // Сәуле эффектісі
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 12; i++) {
            float angle = (time * 30 + i * 30) * (float)Math.PI / 180f;
            float x1 = 600 + (float)Math.cos(angle) * 30;
            float y1 = 300 + (float)Math.sin(angle) * 30;
            float x2 = 600 + (float)Math.cos(angle) * 280;
            float y2 = 300 + (float)Math.sin(angle) * 280;
            float a = (float)(Math.sin(time * 2 + i) * 0.05f + 0.08f);
            sr.setColor(1f, 0.9f, 0.4f, a);
            sr.rectLine(x1, y1, x2, y2, 8);
        }
        sr.end();

        float blink = (float)(Math.sin(time * 4) * 0.5f + 0.5f);

        batch.begin();
        // VICTORY! - үлкен алтын
        AssetManager.getInstance().getFont().getData().setScale(4.5f);
        // Көлеңке эффект үшін екі рет рендер
        AssetManager.getInstance().getFont().setColor(0.4f, 0.25f, 0.05f, 1);
        AssetManager.getInstance().getFont().draw(batch, "VICTORY!", 433, 443);
        AssetManager.getInstance().getFont().setColor(1f, 0.85f, 0.2f, 1);
        AssetManager.getInstance().getFont().draw(batch, "VICTORY!", 430, 445);

        // Subtitle
        AssetManager.getInstance().getFont().getData().setScale(1.3f);
        AssetManager.getInstance().getFont().setColor(0.95f, 0.95f, 0.7f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "You escaped the dungeon!", 460, 380);

        // Score
        AssetManager.getInstance().getFont().getData().setScale(1.7f);
        AssetManager.getInstance().getFont().setColor(1f, 0.9f, 0.3f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "SCORE: " + ScoreManager.getInstance().getCurrentScore(), 460, 320);

        // High Score
        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(0.85f, 0.85f, 0.95f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "Best: " + ScoreManager.getInstance().getHighScore() +
                "   |   Hero: " + game.getSelectedHero() +
                "   |   Mode: " + game.getDifficulty(),
            420, 245);

        // Промпт
        AssetManager.getInstance().getFont().getData().setScale(1.2f);
        AssetManager.getInstance().getFont().setColor(1f, 1f, 1f, 0.4f + blink * 0.6f);
        AssetManager.getInstance().getFont().draw(batch, "[ENTER]  MAIN MENU", 430, 190);
        AssetManager.getInstance().getFont().setColor(0.9f, 0.9f, 0.9f, 0.4f + blink * 0.6f);
        AssetManager.getInstance().getFont().draw(batch, "[R]  PLAY AGAIN", 670, 190);

        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            ScoreManager.getInstance().reset();
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            ScoreManager.getInstance().reset();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
