package com.dungeondevs.game.entities.heroes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.patterns.strategy.RangedAttack;
import com.dungeondevs.game.patterns.decorator.BasicWeapon;

public class Archer extends Hero {
    public Archer(float x, float y) {
        super(x, y, 120, "Archer");
        this.speed = 220f;
        this.attackStrategy = new RangedAttack();
        this.weapon = new BasicWeapon("Bow", 20);
    }

    @Override public void render(SpriteBatch batch) {}
}
