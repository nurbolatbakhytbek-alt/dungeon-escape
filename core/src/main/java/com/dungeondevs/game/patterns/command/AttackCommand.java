package com.dungeondevs.game.patterns.command;

import com.dungeondevs.game.entities.Entity;
import com.dungeondevs.game.entities.heroes.Hero;

public class AttackCommand implements Command {
    private Hero hero;
    private Entity target;

    public AttackCommand(Hero hero, Entity target) {
        this.hero = hero;
        this.target = target;
    }

    @Override
    public void execute() { hero.attack(target); }

    @Override
    public void undo() { /* attack is irreversible */ }
}
