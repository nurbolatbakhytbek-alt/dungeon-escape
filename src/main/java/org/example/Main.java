package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Main extends org.example.ApplicationAdapter {

    org.example.ShapeRenderer shape;

    float playerX = 400, playerY = 300;
    float speed = 200;

    @Override
    public void create() {
        shape = new ShapeRenderer();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Қозғалыс
        if (Gdx.input.isKeyPressed(Input.Keys.W)) playerY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) playerY -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) playerX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) playerX += speed * delta;

        // Экранды тазалау
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Сурет салу
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Еден (жасыл)
        shape.setColor(Color.DARK_GRAY);
        shape.rect(0, 0, 800, 600);

        // Қабырғалар (сұр)
        shape.setColor(Color.GRAY);
        shape.rect(0, 0, 800, 20);
        shape.rect(0, 580, 800, 20);
        shape.rect(0, 0, 20, 600);
        shape.rect(780, 0, 20, 600);

        // Ойыншы (көк шаршы)
        shape.setColor(Color.CYAN);
        shape.rect(playerX - 15, playerY - 15, 30, 30);

        // Шығу есігі (жасыл)
        shape.setColor(Color.GREEN);
        shape.rect(740, 270, 30, 60);

        shape.end();
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
}
