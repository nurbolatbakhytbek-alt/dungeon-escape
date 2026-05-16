package com.dungeondevs.game.patterns.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dungeondevs.game.entities.heroes.Hero;

public class IdleState implements PlayerState {
    @Override public void enter(Hero hero) { System.out.println(hero.getName() + " is idle"); }
    @Override public void update(Hero hero, float delta) {}

    @Override
    public void handleInput(Hero hero) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.setState(new MovingState());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            hero.setState(new AttackingState());
        }
    }

    @Override public void exit(Hero hero) {}
    @Override public String getName() { return "Idle"; }
}
