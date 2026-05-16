package com.dungeondevs.game.patterns.state;

import com.dungeondevs.game.entities.heroes.Hero;

public class AttackingState implements PlayerState {
    private float attackTimer = 0.5f;

    @Override public void enter(Hero hero) { System.out.println(hero.getName() + " attacks!"); }

    @Override
    public void update(Hero hero, float delta) {
        attackTimer -= delta;
        if (attackTimer <= 0) hero.setState(new IdleState());
    }

    @Override public void handleInput(Hero hero) {}
    @Override public void exit(Hero hero) {}
    @Override public String getName() { return "Attacking"; }
}
