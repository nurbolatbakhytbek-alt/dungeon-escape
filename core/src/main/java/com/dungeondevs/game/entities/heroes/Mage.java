package com.dungeondevs.game.entities.heroes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.patterns.strategy.MagicAttack;
import com.dungeondevs.game.patterns.decorator.BasicWeapon;

public class Mage extends Hero {
    public Mage(float x, float y) {
        super(x, y, 100, "Mage");
        this.speed = 150f;
        this.attackStrategy = new MagicAttack();
        this.weapon = new BasicWeapon("Staff", 35);
    }

    @Override public void render(SpriteBatch batch) {}
}
