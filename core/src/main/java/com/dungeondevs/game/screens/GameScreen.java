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
import com.dungeondevs.game.entities.enemies.Enemy;
import com.dungeondevs.game.managers.AssetManager;
import com.dungeondevs.game.managers.ScoreManager;
import com.dungeondevs.game.patterns.abstractfactory.*;
import com.dungeondevs.game.patterns.command.CommandInvoker;
import com.dungeondevs.game.patterns.command.InputHandler;
import com.dungeondevs.game.patterns.observer.GameEvent;
import com.dungeondevs.game.entities.heroes.*;
import com.dungeondevs.game.world.Door;
import com.dungeondevs.game.world.Wall;
import java.util.List;

public class GameScreen implements Screen {
    private DungeonEscape game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;

    private Hero hero;
    private LevelFactory levelFactory;
    private List<Enemy> enemies;
    private List<Trap> traps;
    private List<Wall> walls;
    private Door door;

    private InputHandler inputHandler;
    private CommandInvoker invoker;

    private float gameOverTimer = 0;
    private float winTimer = 0;
    private boolean gameEnded = false;
    private boolean gameWon = false;
    private float time = 0;

    public GameScreen(DungeonEscape game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.sr = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 600);

        ScoreManager.getInstance().reset();

        if (game.getDifficulty().equalsIgnoreCase("Hard")) {
            levelFactory = new HardLevelFactory();
        } else {
            levelFactory = new EasyLevelFactory();
        }

        enemies = levelFactory.createEnemies();
        traps = levelFactory.createTraps();
        walls = levelFactory.createWalls();
        door = levelFactory.createDoor();
        float[] spawn = levelFactory.getPlayerSpawn();

        switch (game.getSelectedHero()) {
            case "Mage":   hero = new Mage(spawn[0], spawn[1]); break;
            case "Archer": hero = new Archer(spawn[0], spawn[1]); break;
            default:       hero = new Warrior(spawn[0], spawn[1]); break;
        }

        game.getEvents().subscribe(ScoreManager.getInstance());
        invoker = new CommandInvoker();
        inputHandler = new InputHandler(hero, invoker);
    }

    @Override public void show() {}

    @Override
    public void render(float delta) {
        time += delta;

        if (hero.isAlive() && !gameEnded) {
            hero.savePosition();
            inputHandler.handleInput(delta);
            hero.handleInput();
            hero.update(delta);
            hero.checkWallCollision(walls);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                for (Enemy e : enemies) {
                    if (e.isAlive()) hero.attack(e);
                }
            }

            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    e.updateAI(delta, hero, walls);
                    e.update(delta);
                    e.attackPlayer(hero);
                } else if (!e.isDeathProcessed()) {
                    if (e.getClass().getSimpleName().equals("Boss")) {
                        game.getEvents().notify(new GameEvent(GameEvent.Type.BOSS_DEFEATED));
                    } else {
                        game.getEvents().notify(new GameEvent(GameEvent.Type.ENEMY_KILLED));
                    }
                    e.setDeathProcessed(true);
                }
            }
            for (Trap t : traps) t.activate(hero);

            boolean allDead = true;
            for (Enemy e : enemies) {
                if (e.isAlive()) { allDead = false; break; }
            }
            if (allDead && !door.isUnlocked()) door.unlock();

            if (door.isUnlocked() && door.isPlayerNear(hero.getPosition().x, hero.getPosition().y)) {
                gameWon = true;
                gameEnded = true;
                game.getEvents().notify(new GameEvent(GameEvent.Type.LEVEL_COMPLETED));
                winTimer = 0;
            }
        }

        if (!hero.isAlive() && !gameEnded) {
            gameEnded = true;
            gameOverTimer = 0;
        }

        if (gameEnded) {
            if (gameWon) {
                winTimer += delta;
                if (winTimer > 1.5f) {
                    game.gameWon(ScoreManager.getInstance().getCurrentScore());
                    game.setScreen(new WinScreen(game));
                    return;
                }
            } else {
                gameOverTimer += delta;
                if (gameOverTimer > 1.8f) {
                    game.gameOver(ScoreManager.getInstance().getCurrentScore());
                    game.setScreen(new GameOverScreen(game));
                    return;
                }
            }
        }

        Gdx.gl.glClearColor(0.08f, 0.06f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        drawDungeonFloor();
        drawWalls();
        drawTraps();
        drawDoor();

        for (Enemy e : enemies) if (e.isAlive()) drawEnemy(e);
        drawHero();
        drawHealthBars();
        drawHUD();

        if (gameEnded) {
            if (gameWon) drawVictoryOverlay();
            else drawDeathOverlay();
        }
    }

    private void drawDungeonFloor() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 15; y++) {
                if ((x + y) % 2 == 0) sr.setColor(0.18f, 0.16f, 0.22f, 1);
                else sr.setColor(0.15f, 0.13f, 0.19f, 1);
                sr.rect(x * 40, y * 40, 40, 40);
            }
        }
        sr.setColor(0.08f, 0.07f, 0.11f, 1);
        for (int x = 0; x <= 30; x++) sr.rect(x * 40 - 0.5f, 0, 1, 600);
        for (int y = 0; y <= 15; y++) sr.rect(0, y * 40 - 0.5f, 1200, 1);
        sr.end();
    }

    private void drawWalls() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Wall w : walls) {
            sr.setColor(0, 0, 0, 0.6f);
            sr.rect(w.getX() + 3, w.getY() - 3, w.getWidth(), w.getHeight());
            sr.setColor(0.32f, 0.26f, 0.34f, 1);
            sr.rect(w.getX(), w.getY(), w.getWidth(), w.getHeight());
            sr.setColor(0.45f, 0.38f, 0.48f, 1);
            sr.rect(w.getX(), w.getY() + w.getHeight() - 3, w.getWidth(), 3);
            sr.setColor(0.2f, 0.16f, 0.22f, 1);
            for (float bx = w.getX(); bx < w.getX() + w.getWidth(); bx += 20)
                sr.rect(bx, w.getY(), 1, w.getHeight());
            for (float by = w.getY(); by < w.getY() + w.getHeight(); by += 20)
                sr.rect(w.getX(), by, w.getWidth(), 1);
        }
        sr.end();
    }

    private void drawDoor() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float dx = door.getX();
        float dy = door.getY();
        if (door.isUnlocked()) {
            float glow = (float)(Math.sin(time * 4) * 0.2f + 0.8f);
            sr.setColor(1f * glow, 0.85f, 0.3f, 0.3f);
            sr.circle(dx + 20, dy + 25, 50);
            sr.setColor(1f, 0.95f, 0.4f, 0.5f);
            sr.circle(dx + 20, dy + 25, 35);
            sr.setColor(0.55f, 0.35f, 0.15f, 1);
            sr.rect(dx - 3, dy - 3, 46, 56);
            sr.setColor(0.9f * glow, 0.75f, 0.2f, 1);
            sr.rect(dx, dy, 40, 50);
            sr.setColor(1f, 0.95f, 0.6f, 0.9f);
            sr.rect(dx + 6, dy + 6, 28, 38);
        } else {
            sr.setColor(0.4f, 0.25f, 0.1f, 1);
            sr.rect(dx - 3, dy - 3, 46, 56);
            sr.setColor(0.3f, 0.18f, 0.08f, 1);
            sr.rect(dx, dy, 40, 50);
            sr.setColor(0.2f, 0.1f, 0.05f, 1);
            sr.rect(dx, dy + 15, 40, 1);
            sr.rect(dx, dy + 30, 40, 1);
            sr.setColor(0.5f, 0.45f, 0.4f, 1);
            sr.circle(dx + 8, dy + 8, 2);
            sr.circle(dx + 32, dy + 8, 2);
            sr.circle(dx + 8, dy + 42, 2);
            sr.circle(dx + 32, dy + 42, 2);
            sr.setColor(0.6f, 0.55f, 0.5f, 1);
            sr.rect(dx + 16, dy + 22, 8, 8);
            sr.setColor(0.2f, 0.15f, 0.1f, 1);
            sr.rect(dx + 19, dy + 24, 2, 4);
        }
        sr.end();
    }

    private void drawTraps() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float pulse = (float)(Math.sin(time * 5) * 0.2f + 0.8f);
        for (Trap t : traps) {
            float cx = t.getX() + 20;
            float cy = t.getY() + 20;
            sr.setColor(0.15f, 0.05f, 0.05f, 1);
            sr.circle(cx, cy, 18);
            sr.setColor(0.6f * pulse, 0.1f, 0.1f, 1);
            sr.circle(cx, cy, 14);
            sr.setColor(0.85f, 0.85f, 0.9f, 1);
            sr.triangle(cx - 8, cy - 4, cx, cy + 8, cx + 8, cy - 4);
            sr.triangle(cx - 8, cy + 4, cx, cy - 8, cx + 8, cy + 4);
        }
        sr.end();
    }

    private void drawHero() {
        float x = hero.getPosition().x;
        float y = hero.getPosition().y;
        boolean blink = hero.isInvulnerable() && (int)(time * 20) % 2 == 0;
        if (blink) return;
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 0, 0, 0.4f);
        sr.ellipse(x - 14, y - 20, 28, 8);
        sr.end();
        String type = hero.getName();
        if (type.equals("Warrior")) drawWarrior(x, y);
        else if (type.equals("Mage")) drawMage(x, y);
        else if (type.equals("Archer")) drawArcher(x, y);
    }

    private void drawWarrior(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.25f, 0.15f, 0.1f, 1); sr.rect(x-7,y-18,5,10); sr.rect(x+2,y-18,5,10);
        sr.setColor(0.65f, 0.18f, 0.18f, 1); sr.rect(x-10,y-8,20,18);
        sr.setColor(0.85f, 0.25f, 0.25f, 1); sr.rect(x-10,y+5,20,3);
        sr.setColor(0.9f, 0.75f, 0.2f, 1); sr.rect(x-10,y-4,20,3);
        sr.setColor(0.5f, 0.35f, 0.15f, 1); sr.rect(x-16,y-5,5,12);
        sr.setColor(0.85f, 0.7f, 0.3f, 1); sr.circle(x-13.5f,y+1,2);
        sr.setColor(0.55f, 0.35f, 0.2f, 1); sr.rect(x+11,y-3,4,8);
        sr.setColor(0.9f, 0.9f, 0.95f, 1); sr.rect(x+15,y-2,3,16);
        sr.setColor(0.95f, 0.78f, 0.6f, 1); sr.circle(x,y+14,7);
        sr.setColor(0.6f, 0.15f, 0.15f, 1); sr.rect(x-7,y+16,14,5);
        sr.setColor(0.1f, 0.1f, 0.15f, 1); sr.circle(x-3,y+13,1.2f); sr.circle(x+3,y+13,1.2f);
        sr.end();
    }

    private void drawMage(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.3f,0.2f,0.6f,1); sr.triangle(x-14,y-18,x+14,y-18,x+10,y+2); sr.triangle(x-14,y-18,x+10,y+2,x-10,y+2);
        sr.setColor(0.35f,0.25f,0.7f,1); sr.rect(x-10,y-2,20,12);
        sr.setColor(0.9f,0.75f,0.2f,1); sr.rect(x-10,y+2,20,2);
        sr.setColor(0.95f,0.78f,0.6f,1); sr.circle(x,y+14,6);
        sr.setColor(0.25f,0.15f,0.55f,1); sr.triangle(x-9,y+19,x+9,y+19,x,y+34);
        sr.setColor(0.45f,0.3f,0.15f,1); sr.rect(x+12,y-10,3,28);
        float glow=(float)(Math.sin(time*4)*0.3f+0.7f);
        sr.setColor(0.3f*glow,0.8f*glow,1f,1); sr.circle(x+13.5f,y+20,4);
        sr.setColor(0.7f,1f,1f,1); sr.circle(x+13.5f,y+20,2);
        sr.end();
    }

    private void drawArcher(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.25f,0.18f,0.1f,1); sr.rect(x-7,y-18,5,10); sr.rect(x+2,y-18,5,10);
        sr.setColor(0.2f,0.5f,0.25f,1); sr.rect(x-9,y-8,18,16);
        sr.setColor(0.95f,0.78f,0.6f,1); sr.circle(x,y+12,6);
        sr.setColor(0.15f,0.45f,0.2f,1); sr.triangle(x-9,y+12,x+9,y+12,x-9,y+22);
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(3);
        sr.setColor(0.5f,0.3f,0.15f,1); sr.arc(x-22,y,12,270,180);
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.9f,0.9f,0.85f,1); sr.rectLine(x-16,y-12,x-16,y+12,1);
        sr.end();
        Gdx.gl.glLineWidth(1);
    }

    private void drawEnemy(Enemy e) {
        float x = e.getPosition().x;
        float y = e.getPosition().y;
        String type = e.getClass().getSimpleName();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0,0,0,0.4f); sr.ellipse(x-12,y-18,24,7);
        sr.end();
        if (type.equals("Goblin")) drawGoblin(x,y);
        else if (type.equals("Skeleton")) drawSkeleton(x,y);
        else if (type.equals("Boss")) drawBoss(x,y);
    }

    private void drawGoblin(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.35f,0.6f,0.2f,1); sr.rect(x-8,y-8,16,12);
        sr.setColor(0.4f,0.65f,0.25f,1); sr.circle(x,y+8,7);
        sr.triangle(x-7,y+8,x-11,y+14,x-6,y+12);
        sr.triangle(x+7,y+8,x+11,y+14,x+6,y+12);
        sr.setColor(0.95f,0.15f,0.1f,1); sr.circle(x-2.5f,y+9,1.3f); sr.circle(x+2.5f,y+9,1.3f);
        sr.end();
    }

    private void drawSkeleton(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.88f,0.85f,0.75f,1);
        sr.rect(x-6,y-18,3,10); sr.rect(x+3,y-18,3,10);
        sr.rect(x-8,y-9,16,4);
        sr.rect(x-1,y-5,2,12);
        sr.setColor(0.92f,0.9f,0.82f,1); sr.circle(x,y+13,8);
        sr.setColor(0.05f,0.05f,0.08f,1); sr.circle(x-3,y+14,2); sr.circle(x+3,y+14,2);
        float glow=(float)(Math.sin(time*6)*0.3f+0.7f);
        sr.setColor(0.3f*glow,0.6f*glow,1f,1); sr.circle(x-3,y+14,1); sr.circle(x+3,y+14,1);
        sr.end();
    }

    private void drawBoss(float x, float y) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.35f,0.05f,0.08f,1); sr.rect(x-18,y-10,36,22);
        sr.setColor(0.4f,0.06f,0.08f,1); sr.rect(x-22,y+8,44,4);
        sr.setColor(0.4f,0.05f,0.08f,1); sr.circle(x,y+22,12);
        float glow=(float)(Math.sin(time*8)*0.2f+0.8f);
        sr.setColor(1f*glow,0.7f*glow,0.1f,1); sr.rect(x-6,y+20,3,3); sr.rect(x+3,y+20,3,3);
        sr.end();
    }

    private void drawHealthBars() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                float p = (float) e.getHp() / e.getMaxHp();
                String type = e.getClass().getSimpleName();
                float barW = type.equals("Boss") ? 50 : 30;
                float yOff = type.equals("Boss") ? 50 : 25;
                float bx = e.getPosition().x - barW/2;
                float by = e.getPosition().y + yOff;
                sr.setColor(0,0,0,0.7f); sr.rect(bx-1,by-1,barW+2,6);
                sr.setColor(0.2f,0,0,1); sr.rect(bx,by,barW,4);
                if (type.equals("Boss")) sr.setColor(1f,0.5f,0.1f,1);
                else sr.setColor(0.9f,0.15f,0.15f,1);
                sr.rect(bx,by,barW*p,4);
            }
        }
        sr.end();
    }

    private void drawHUD() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0,0,0,0.7f); sr.rect(0,540,1200,60);
        sr.end();
        float hpP = (float) hero.getHp() / hero.getMaxHp();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.15f,0.05f,0.05f,1); sr.rect(20,565,250,22);
        if (hpP > 0.5f) sr.setColor(0.2f,0.85f,0.3f,1);
        else if (hpP > 0.25f) sr.setColor(0.95f,0.7f,0.1f,1);
        else sr.setColor(0.95f,0.15f,0.15f,1);
        sr.rect(20,565,250*hpP,22);
        sr.end();
        int aliveCount = 0;
        for (Enemy e : enemies) if (e.isAlive()) aliveCount++;
        batch.begin();
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        AssetManager.getInstance().getFont().draw(batch, "HP "+hero.getHp()+" / "+hero.getMaxHp(), 100, 581);
        AssetManager.getInstance().getFont().setColor(1f,0.85f,0.3f,1);
        AssetManager.getInstance().getFont().draw(batch, "SCORE: "+ScoreManager.getInstance().getCurrentScore(), 320, 583);
        AssetManager.getInstance().getFont().setColor(0.7f,0.9f,1f,1);
        AssetManager.getInstance().getFont().draw(batch, hero.getName()+" | "+hero.getCurrentState().getName(), 510, 583);
        if (aliveCount > 0) {
            AssetManager.getInstance().getFont().setColor(1f,0.5f,0.3f,1);
            AssetManager.getInstance().getFont().draw(batch, "Kill enemies: "+aliveCount+" left", 760, 583);
        } else {
            float blink=(float)(Math.sin(time*5)*0.5f+0.5f);
            AssetManager.getInstance().getFont().setColor(1f,0.9f,0.3f,0.5f+blink*0.5f);
            AssetManager.getInstance().getFont().draw(batch, ">>> GO TO THE DOOR! <<<", 760, 583);
        }
        AssetManager.getInstance().getFont().setColor(0.6f,0.6f,0.7f,1);
        AssetManager.getInstance().getFont().draw(batch, "WASD: Move    SPACE: Attack", 510, 563);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();
    }

    private void drawDeathOverlay() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float a = Math.min(gameOverTimer/1.8f, 0.7f);
        sr.setColor(0.5f,0,0,a); sr.rect(0,0,1200,600);
        sr.end();
        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(3f);
        AssetManager.getInstance().getFont().setColor(1,0.9f,0.9f,a+0.3f);
        AssetManager.getInstance().getFont().draw(batch, "YOU DIED...", 480, 320);
        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();
    }

    private void drawVictoryOverlay() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float a = Math.min(winTimer/1.5f, 0.6f);
        sr.setColor(1f,0.85f,0.2f,a); sr.rect(0,0,1200,600);
        sr.end();
        batch.begin();
        AssetManager.getInstance().getFont().getData().setScale(3f);
        AssetManager.getInstance().getFont().setColor(0.3f,0.2f,0,a+0.3f);
        AssetManager.getInstance().getFont().draw(batch, "ESCAPED!", 503, 318);
        AssetManager.getInstance().getFont().setColor(1,1,1,a+0.3f);
        AssetManager.getInstance().getFont().draw(batch, "ESCAPED!", 500, 320);
        AssetManager.getInstance().getFont().getData().setScale(1f);
        AssetManager.getInstance().getFont().setColor(Color.WHITE);
        batch.end();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); sr.dispose(); }
}
