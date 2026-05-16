package com.dungeondevs.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.entities.heroes.Hero;

public class Skeleton extends Enemy {
    public Skeleton(float x, float y) {
        super(x, y, 60, 12, 75f);
        this.scoreValue = 75;
        this.attackInterval = 1.5f;
    }

    @Override
    public void attackPlayer(Hero player) {
        if (canAttack() && distanceTo(player) < 50f) {
            player.takeDamage(damage);
            resetAttackCooldown();
        }
    }

    @Override public void render(SpriteBatch batch) {}
}
