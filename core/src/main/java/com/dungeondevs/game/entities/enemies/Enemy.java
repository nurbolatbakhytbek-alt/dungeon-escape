package com.dungeondevs.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.dungeondevs.game.entities.Entity;
import com.dungeondevs.game.entities.heroes.Hero;
import com.dungeondevs.game.world.Wall;
import java.util.List;

public abstract class Enemy extends Entity {
    protected int damage;
    protected float speed;
    protected int scoreValue;
    protected float attackCooldown = 0;
    protected float attackInterval = 1.2f;
    protected float aiTimer = 0;
    protected Vector2 wanderTarget;
    protected Vector2 prevPos = new Vector2();

    public Enemy(float x, float y, int hp, int damage, float speed) {
        super(x, y, hp);
        this.damage = damage;
        this.speed = speed;
        this.wanderTarget = new Vector2(x, y);
        this.prevPos.set(x, y);
    }

    public abstract void attackPlayer(Hero player);

    /** ✅ Қабырғаны ескере отырып AI */
    public void updateAI(float delta, Hero player, List<Wall> walls) {
        if (!alive) return;

        attackCooldown -= delta;
        aiTimer += delta;
        prevPos.set(position);

        float dist = position.dst(player.getPosition());

        if (dist < 250f && dist > 35f) {
            Vector2 dir = new Vector2(player.getPosition()).sub(position).nor();
            position.x += dir.x * speed * delta;
            position.y += dir.y * speed * delta;
        } else if (dist >= 250f) {
            if (aiTimer > 2f) {
                wanderTarget.set(
                    position.x + (float)(Math.random() * 200 - 100),
                    position.y + (float)(Math.random() * 200 - 100)
                );
                aiTimer = 0;
            }
            Vector2 dir = new Vector2(wanderTarget).sub(position);
            if (dir.len() > 5) {
                dir.nor();
                position.x += dir.x * speed * 0.5f * delta;
                position.y += dir.y * speed * 0.5f * delta;
            }
        }

        // Қабырғадан өтсе - артқа қайтару
        for (Wall w : walls) {
            if (w.collidesWith(position.x, position.y, 12)) {
                position.set(prevPos);
                // Кездейсоқ басқа бағытқа айналу
                wanderTarget.set(
                    position.x + (float)(Math.random() * 200 - 100),
                    position.y + (float)(Math.random() * 200 - 100)
                );
                break;
            }
        }
    }

    @Override protected void updateBehavior(float delta) {}
    @Override protected void updateAnimation(float delta) {}

    @Override
    protected void checkBounds() {
        if (position.x < 45) position.x = 45;
        if (position.y < 45) position.y = 45;
        if (position.x > 1155) position.x = 1155;
        if (position.y > 555) position.y = 555;
    }

    public boolean canAttack() { return attackCooldown <= 0; }
    public void resetAttackCooldown() { attackCooldown = attackInterval; }

    public int getDamage() { return damage; }
    public int getScoreValue() { return scoreValue; }
}
