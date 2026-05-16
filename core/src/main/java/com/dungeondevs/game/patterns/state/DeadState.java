package com.dungeondevs.game.patterns.state;

import com.dungeondevs.game.entities.heroes.Hero;

public class DeadState implements PlayerState {
    @Override public void enter(Hero hero) { System.out.println(hero.getName() + " died"); }
    @Override public void update(Hero hero, float delta) {}
    @Override public void handleInput(Hero hero) {}
    @Override public void exit(Hero hero) {}
    @Override public String getName() { return "Dead"; }
}
