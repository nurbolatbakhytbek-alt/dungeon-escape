package com.dungeondevs.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondevs.game.entities.heroes.Hero;

public class Goblin extends Enemy {
    public Goblin(float x, float y) {
        super(x, y, 40, 8, 90f);
        this.scoreValue = 50;
        this.attackInterval = 1.0f;  // Жылдам, әлсіз
    }

    @Override
    public void attackPlayer(Hero player) {
        if (canAttack() && distanceTo(player) < 45f) {
            player.takeDamage(damage);
            resetAttackCooldown();
        }
    }

    @Override public void render(SpriteBatch batch) {}  // ShapeRenderer қолданамыз
}
