package com.dungeondevs.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.entities.heroes.Hero;

public class Boss extends Enemy {
    public Boss(float x, float y) {
        super(x, y, 300, 25, 60f);
        this.scoreValue = 500;
        this.attackInterval = 2.0f;  // Баяу, бірақ қатты
    }

    @Override
    public void attackPlayer(Hero player) {
        if (canAttack() && distanceTo(player) < 75f) {
            player.takeDamage(damage);
            resetAttackCooldown();
        }
    }

    @Override public void render(SpriteBatch batch) {}
}
