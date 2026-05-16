package com.dungeondevs.game.patterns.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dungeondevs.game.entities.heroes.Hero;

public class MovingState implements PlayerState {
    @Override public void enter(Hero hero) {}
    @Override public void update(Hero hero, float delta) {}

    @Override
    public void handleInput(Hero hero) {
        boolean moving = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D);
        if (!moving) hero.setState(new IdleState());
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) hero.setState(new AttackingState());
    }

    @Override public void exit(Hero hero) {}
    @Override public String getName() { return "Moving"; }
}
