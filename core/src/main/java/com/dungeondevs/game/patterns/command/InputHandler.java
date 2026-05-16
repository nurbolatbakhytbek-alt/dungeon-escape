package com.dungeondevs.game.patterns.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dungeondevs.game.entities.heroes.Hero;

public class InputHandler {
    private Hero hero;
    private CommandInvoker invoker;

    public InputHandler(Hero hero, CommandInvoker invoker) {
        this.hero = hero;
        this.invoker = invoker;
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            invoker.execute(new MoveCommand(hero, MoveCommand.Direction.UP, delta));
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            invoker.execute(new MoveCommand(hero, MoveCommand.Direction.DOWN, delta));
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            invoker.execute(new MoveCommand(hero, MoveCommand.Direction.LEFT, delta));
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            invoker.execute(new MoveCommand(hero, MoveCommand.Direction.RIGHT, delta));
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
            invoker.undoLast();
    }
}
