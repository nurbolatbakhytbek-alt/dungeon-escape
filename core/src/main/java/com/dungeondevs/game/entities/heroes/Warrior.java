package com.dungeondevs.game.entities.heroes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.patterns.strategy.MeleeAttack;
import com.dungeondevs.game.patterns.decorator.BasicWeapon;

public class Warrior extends Hero {
    public Warrior(float x, float y) {
        super(x, y, 150, "Warrior");
        this.speed = 180f;
        this.attackStrategy = new MeleeAttack();
        this.weapon = new BasicWeapon("Sword", 30);
    }

    @Override public void render(SpriteBatch batch) {}
}
