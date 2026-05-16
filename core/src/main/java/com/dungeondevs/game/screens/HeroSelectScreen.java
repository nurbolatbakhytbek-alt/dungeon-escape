package com.dungeondevs.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dungeondevs.game.DungeonEscape;
import com.dungeondevs.game.managers.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class HeroSelectScreen implements Screen {
    private OrthographicCamera camera;
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private String[] heroes = {"Warrior", "Mage", "Archer"};
    private String[] descriptions = {
        "Strong melee fighter with high HP and heavy armor",
        "Master of arcane magic, deals high damage from afar",
        "Swift hunter with deadly accuracy and long-range bow"
    };
    private int[] hp = {150, 100, 120};
    private int[] dmg = {30, 35, 20};
    private float[] speed = {180, 150, 220};
    private int selectedIndex = 0;
    private float time = 0;

    public HeroSelectScreen(DungeonEscape game) {
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 600);
        this.game = game;
        this.batch = new SpriteBatch();
        this.sr = new ShapeRenderer();
    }

    @Override public void show() {}

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);
        time += delta;

        Gdx.gl.glClearColor(0.04f, 0.03f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 600; i++) {
            float a = i / 600f * 0.15f;
            sr.setColor(0.3f, 0.1f, 0.4f, a);
            sr.rect(0, i, 1200, 1);
        }

        // 3 карта
        float cardW = 280;
        float cardH = 340;
        float gap = 30;
        float totalW = 3 * cardW + 2 * gap;
        float startX = (1200 - totalW) / 2;
        float cardY = 140;

        for (int i = 0; i < 3; i++) {
            float cx = startX + i * (cardW + gap);
            boolean sel = (i == selectedIndex);

            sr.setColor(0, 0, 0, 0.5f);
            sr.rect(cx + 6, cardY - 6, cardW, cardH);

            if (sel) {
                float p = (float)(Math.sin(time * 4) * 0.1f + 0.85f);
                sr.setColor(0.25f * p, 0.15f * p, 0.3f * p, 1);
            } else {
                sr.setColor(0.12f, 0.08f, 0.15f, 1);
            }
            sr.rect(cx, cardY, cardW, cardH);

            // Кейіпкер иконкасын картаға салу (үлкен)
            float ix = cx + cardW / 2;
            float iy = cardY + 230;
            sr.end();
            drawHeroIcon(i, ix, iy);
            sr.begin(ShapeRenderer.ShapeType.Filled);

            // Stat барлар
            float barX = cx + 30;
            float barW = cardW - 60;

            // HP
            sr.setColor(0.1f, 0.05f, 0.05f, 1);
            sr.rect(barX, cardY + 130, barW, 12);
            sr.setColor(0.9f, 0.2f, 0.2f, 1);
            sr.rect(barX, cardY + 130, barW * (hp[i] / 200f), 12);
            // DMG
            sr.setColor(0.1f, 0.05f, 0.05f, 1);
            sr.rect(barX, cardY + 95, barW, 12);
            sr.setColor(0.9f, 0.6f, 0.1f, 1);
            sr.rect(barX, cardY + 95, barW * (dmg[i] / 50f), 12);
            // SPD
            sr.setColor(0.1f, 0.05f, 0.05f, 1);
            sr.rect(barX, cardY + 60, barW, 12);
            sr.setColor(0.3f, 0.8f, 0.9f, 1);
            sr.rect(barX, cardY + 60, barW * (speed[i] / 250f), 12);
        }
        sr.end();

        // Жиектемелер
        Gdx.gl.glLineWidth(3);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < 3; i++) {
            float cx = startX + i * (cardW + gap);
            if (i == selectedIndex) sr.setColor(1f, 0.85f, 0.3f, 1);
            else sr.setColor(0.4f, 0.3f, 0.5f, 1);
            sr.rect(cx, cardY, cardW, cardH);
        }
        sr.end();
        Gdx.gl.glLineWidth(1);

        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(2.2f);
        AssetManager.getInstance().getFont().setColor(0.95f, 0.75f, 0.3f, 1);
        AssetManager.getInstance().getFont().draw(batch, "CHOOSE YOUR HERO", 410, 555);

        AssetManager.getInstance().getFont().getData().setScale(1.4f);
        for (int i = 0; i < 3; i++) {
            float cx = startX + i * (cardW + gap);
            if (i == selectedIndex) AssetManager.getInstance().getFont().setColor(1f, 0.9f, 0.4f, 1);
            else AssetManager.getInstance().getFont().setColor(0.7f, 0.7f, 0.8f, 1);
            AssetManager.getInstance().getFont().draw(batch, heroes[i], cx + 30, cardY + 330);
        }

        AssetManager.getInstance().getFont().getData().setScale(0.9f);
        for (int i = 0; i < 3; i++) {
            float cx = startX + i * (cardW + gap);
            AssetManager.getInstance().getFont().setColor(0.9f, 0.2f, 0.2f, 1);
            AssetManager.getInstance().getFont().draw(batch, "HP    " + hp[i], cx + 30, cardY + 155);
            AssetManager.getInstance().getFont().setColor(0.9f, 0.6f, 0.1f, 1);
            AssetManager.getInstance().getFont().draw(batch, "DMG   " + dmg[i], cx + 30, cardY + 120);
            AssetManager.getInstance().getFont().setColor(0.3f, 0.8f, 0.9f, 1);
            AssetManager.getInstance().getFont().draw(batch, "SPD   " + (int)speed[i], cx + 30, cardY + 85);
        }

        AssetManager.getInstance().getFont().getData().setScale(1.1f);
        AssetManager.getInstance().getFont().setColor(0.85f, 0.85f, 1f, 1);
        AssetManager.getInstance().getFont().draw(batch, descriptions[selectedIndex], 280, 95);

        AssetManager.getInstance().getFont().getData().setScale(0.9f);
        AssetManager.getInstance().getFont().setColor(0.7f, 0.7f, 0.7f, 1);
        AssetManager.getInstance().getFont().draw(batch,
            " A / D Choose      [ENTER]  Confirm      [ESC]  Back", 380, 50);

        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.A))
            selectedIndex = (selectedIndex - 1 + 3) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            selectedIndex = (selectedIndex + 1) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setSelectedHero(heroes[selectedIndex]);
            game.setScreen(new DifficultySelectScreen(game));  // ✅ Difficulty-ге өту
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(new MainMenuScreen(game));
    }

    /** Карта ішіндегі үлкейтілген hero иконкасы */
    private void drawHeroIcon(int type, float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        // Scale up - 2 есе үлкенірек
        if (type == 0) {  // Warrior
            sr.setColor(0.25f, 0.15f, 0.1f, 1);
            sr.rect(x - 14, y - 36, 10, 20);
            sr.rect(x + 4, y - 36, 10, 20);
            sr.setColor(0.65f, 0.18f, 0.18f, 1);
            sr.rect(x - 20, y - 16, 40, 36);
            sr.setColor(0.9f, 0.75f, 0.2f, 1);
            sr.rect(x - 20, y - 8, 40, 6);
            sr.setColor(0.5f, 0.35f, 0.15f, 1);
            sr.rect(x - 32, y - 10, 10, 24);
            sr.setColor(0.85f, 0.7f, 0.3f, 1);
            sr.circle(x - 27, y + 2, 4);
            sr.setColor(0.55f, 0.35f, 0.2f, 1);
            sr.rect(x + 22, y - 6, 8, 16);
            sr.setColor(0.9f, 0.9f, 0.95f, 1);
            sr.rect(x + 30, y - 4, 6, 32);
            sr.setColor(0.95f, 0.78f, 0.6f, 1);
            sr.circle(x, y + 28, 14);
            sr.setColor(0.6f, 0.15f, 0.15f, 1);
            sr.rect(x - 14, y + 32, 28, 10);
            sr.setColor(0.1f, 0.1f, 0.15f, 1);
            sr.circle(x - 6, y + 26, 2.5f);
            sr.circle(x + 6, y + 26, 2.5f);
        } else if (type == 1) {  // Mage
            sr.setColor(0.3f, 0.2f, 0.6f, 1);
            sr.triangle(x - 28, y - 36, x + 28, y - 36, x + 20, y + 4);
            sr.triangle(x - 28, y - 36, x + 20, y + 4, x - 20, y + 4);
            sr.setColor(0.35f, 0.25f, 0.7f, 1);
            sr.rect(x - 20, y - 4, 40, 24);
            sr.setColor(0.9f, 0.75f, 0.2f, 1);
            sr.rect(x - 20, y + 4, 40, 4);
            sr.setColor(0.85f, 0.85f, 0.9f, 1);
            sr.triangle(x - 8, y + 16, x + 8, y + 16, x, y + 26);
            sr.setColor(0.95f, 0.78f, 0.6f, 1);
            sr.circle(x, y + 28, 12);
            sr.setColor(0.1f, 0.3f, 0.5f, 1);
            sr.circle(x - 5, y + 28, 2);
            sr.circle(x + 5, y + 28, 2);
            sr.setColor(0.25f, 0.15f, 0.55f, 1);
            sr.triangle(x - 18, y + 38, x + 18, y + 38, x, y + 68);
            sr.setColor(0.95f, 0.85f, 0.3f, 1);
            sr.circle(x, y + 56, 3);
            sr.setColor(0.45f, 0.3f, 0.15f, 1);
            sr.rect(x + 24, y - 20, 6, 56);
            float glow = (float)(Math.sin(time * 4) * 0.3f + 0.7f);
            sr.setColor(0.3f * glow, 0.8f * glow, 1f, 1);
            sr.circle(x + 27, y + 40, 8);
            sr.setColor(0.7f, 1f, 1f, 1);
            sr.circle(x + 27, y + 40, 4);
        } else {  // Archer
            sr.setColor(0.25f, 0.18f, 0.1f, 1);
            sr.rect(x - 14, y - 36, 10, 20);
            sr.rect(x + 4, y - 36, 10, 20);
            sr.setColor(0.2f, 0.5f, 0.25f, 1);
            sr.rect(x - 18, y - 16, 36, 32);
            sr.setColor(0.45f, 0.3f, 0.15f, 1);
            sr.rect(x - 18, y - 4, 36, 8);
            sr.setColor(0.95f, 0.78f, 0.6f, 1);
            sr.rect(x - 32, y - 4, 10, 8);
            sr.setColor(0.95f, 0.78f, 0.6f, 1);
            sr.circle(x, y + 24, 12);
            sr.setColor(0.95f, 0.95f, 0.3f, 1);
            sr.circle(x - 5, y + 24, 2);
            sr.circle(x + 5, y + 24, 2);
            sr.setColor(0.15f, 0.45f, 0.2f, 1);
            sr.triangle(x - 18, y + 24, x + 18, y + 24, x - 18, y + 44);
            sr.triangle(x + 18, y + 24, x - 18, y + 44, x + 18, y + 44);
            sr.triangle(x - 18, y + 44, x + 18, y + 44, x, y + 52);
            sr.end();
            sr.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl.glLineWidth(4);
            sr.setColor(0.5f, 0.3f, 0.15f, 1);
            sr.arc(x - 44, y, 24, 270, 180);
            sr.end();
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(0.9f, 0.9f, 0.85f, 1);
            sr.rectLine(x - 32, y - 24, x - 32, y + 24, 1.5f);
            Gdx.gl.glLineWidth(1);
        }
        sr.end();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
