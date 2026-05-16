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

public class DifficultySelectScreen implements Screen {
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private int selectedIndex = 0;
    private float time = 0;

    private String[] difficulties = {"EASY", "HARD"};
    private String[] descriptions = {
        "4 Goblins + 1 Skeleton.  Perfect for warming up.",
        "6 Enemies + Skeletons + BOSS!  Only true heroes survive..."
    };

    public DifficultySelectScreen(DungeonEscape game) {
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

        Gdx.gl.glClearColor(0.04f, 0.03f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        // Фон градиент
        for (int i = 0; i < 600; i++) {
            float a = i / 600f * 0.2f;
            sr.setColor(0.3f, 0.1f, 0.3f, a);
            sr.rect(0, i, 1200, 1);
        }

        // 2 үлкен карта
        float cardW = 400;
        float cardH = 320;
        float gap = 80;
        float totalW = 2 * cardW + gap;
        float startX = (1200 - totalW) / 2;
        float cardY = 150;

        for (int i = 0; i < 2; i++) {
            float cx = startX + i * (cardW + gap);
            boolean sel = (i == selectedIndex);

            // Көлеңке
            sr.setColor(0, 0, 0, 0.5f);
            sr.rect(cx + 8, cardY - 8, cardW, cardH);

            // Фон
            if (i == 0) {  // Easy - жасылырақ
                if (sel) {
                    float p = (float)(Math.sin(time * 4) * 0.1f + 0.85f);
                    sr.setColor(0.15f * p, 0.3f * p, 0.15f * p, 1);
                } else {
                    sr.setColor(0.08f, 0.15f, 0.08f, 1);
                }
            } else {  // Hard - қызылырақ
                if (sel) {
                    float p = (float)(Math.sin(time * 4) * 0.1f + 0.85f);
                    sr.setColor(0.35f * p, 0.08f * p, 0.08f * p, 1);
                } else {
                    sr.setColor(0.15f, 0.05f, 0.05f, 1);
                }
            }
            sr.rect(cx, cardY, cardW, cardH);
        }

        // Easy иконкасы - жасыл щит
        float iconY = cardY + 200;
        sr.setColor(0.2f, 0.6f, 0.25f, 1);
        sr.rect(startX + cardW/2 - 35, iconY - 30, 70, 70);
        sr.setColor(0.4f, 0.8f, 0.4f, 1);
        sr.rect(startX + cardW/2 - 25, iconY - 20, 50, 50);
        sr.setColor(0.95f, 0.95f, 0.3f, 1);
        // Жұлдыз орталықта
        float sx = startX + cardW/2;
        float sy = iconY + 5;
        sr.triangle(sx, sy + 15, sx - 5, sy - 5, sx + 5, sy - 5);
        sr.triangle(sx - 13, sy + 5, sx + 13, sy + 5, sx, sy - 10);

        // Hard иконкасы - бас сүйек + от
        float hx = startX + cardW + gap + cardW/2;
        float hy = iconY;
        // Алау артында
        float fp = (float)(Math.sin(time * 5) * 0.2f + 0.8f);
        sr.setColor(1f * fp, 0.4f * fp, 0.05f, 0.7f);
        sr.circle(hx, hy - 5, 45);
        sr.setColor(1f, 0.7f, 0.1f, 0.9f);
        sr.circle(hx, hy - 5, 32);
        sr.setColor(1f, 0.95f, 0.4f, 1);
        sr.circle(hx, hy - 5, 18);
        // Бас сүйек
        sr.setColor(0.95f, 0.92f, 0.85f, 1);
        sr.circle(hx, hy + 5, 22);
        sr.rect(hx - 14, hy - 10, 28, 8);
        // Көз шұңқырлары
        sr.setColor(0.05f, 0.05f, 0.08f, 1);
        sr.circle(hx - 8, hy + 8, 5);
        sr.circle(hx + 8, hy + 8, 5);
        // Жанып тұрған көз
        sr.setColor(1f, 0.3f, 0.1f, 1);
        sr.circle(hx - 8, hy + 8, 2);
        sr.circle(hx + 8, hy + 8, 2);
        // Тістер
        sr.setColor(0.05f, 0.05f, 0.08f, 1);
        sr.rect(hx - 10, hy - 5, 20, 4);
        sr.setColor(0.95f, 0.92f, 0.85f, 1);
        for (int t = 0; t < 4; t++) {
            sr.rect(hx - 9 + t * 5, hy - 5, 2, 4);
        }

        sr.end();

        // Жиектемелер
        Gdx.gl.glLineWidth(4);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < 2; i++) {
            float cx = startX + i * (cardW + gap);
            if (i == selectedIndex) {
                if (i == 0) sr.setColor(0.4f, 1f, 0.4f, 1);
                else sr.setColor(1f, 0.3f, 0.2f, 1);
            } else {
                sr.setColor(0.4f, 0.3f, 0.5f, 1);
            }
            sr.rect(cx, cardY, cardW, cardH);
        }
        sr.end();
        Gdx.gl.glLineWidth(1);

        // Тексттер
        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(2.2f);
        AssetManager.getInstance().getFont().setColor(0.95f, 0.75f, 0.3f, 1);
        AssetManager.getInstance().getFont().draw(batch, "SELECT DIFFICULTY", 410, 540);

        // Карта тақырыптары
        AssetManager.getInstance().getFont().getData().setScale(2.5f);
        // Easy
        if (selectedIndex == 0) AssetManager.getInstance().getFont().setColor(0.4f, 1f, 0.4f, 1);
        else AssetManager.getInstance().getFont().setColor(0.5f, 0.7f, 0.5f, 1);
        AssetManager.getInstance().getFont().draw(batch, "EASY", startX + 145, cardY + 90);
        // Hard
        if (selectedIndex == 1) AssetManager.getInstance().getFont().setColor(1f, 0.3f, 0.2f, 1);
        else AssetManager.getInstance().getFont().setColor(0.7f, 0.5f, 0.5f, 1);
        AssetManager.getInstance().getFont().draw(batch, "HARD",
            startX + cardW + gap + 145, cardY + 90);

        // Сипаттама
        AssetManager.getInstance().getFont().getData().setScale(1.1f);
        AssetManager.getInstance().getFont().setColor(0.85f, 0.85f, 1f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            descriptions[selectedIndex], 220, 100);

        // Басқару
        AssetManager.getInstance().getFont().getData().setScale(0.9f);
        AssetManager.getInstance().getFont().setColor(0.7f, 0.7f, 0.7f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            "← →  Choose      [ENTER]  Confirm      [ESC]  Back", 380, 50);

        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();

        // Input
        if (Gdx.input.isKeyJustPressed(Input.Keys.A))
            selectedIndex = (selectedIndex - 1 + 2) % 2;
        if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            selectedIndex = (selectedIndex + 1) % 2;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setDifficulty(difficulties[selectedIndex]);
            game.resume();
            game.setScreen(new GameScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new HeroSelectScreen(game));
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
