package com.dungeondevs.game.patterns.state;

import com.dungeondevs.game.entities.heroes.Hero;

public interface PlayerState {
    void enter(Hero hero);
    void update(Hero hero, float delta);
    void handleInput(Hero hero);
    void exit(Hero hero);
    String getName();
}
