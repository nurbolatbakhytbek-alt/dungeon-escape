package com.dungeondevs.game.entities.heroes;

import com.badlogic.gdx.math.Vector2;
import com.dungeondevs.game.entities.Entity;
import com.dungeondevs.game.patterns.strategy.AttackStrategy;
import com.dungeondevs.game.patterns.state.PlayerState;
import com.dungeondevs.game.patterns.state.IdleState;
import com.dungeondevs.game.patterns.decorator.WeaponDecorator;
import com.dungeondevs.game.world.Wall;
import java.util.List;

public abstract class Hero extends Entity {
    protected AttackStrategy attackStrategy;
    protected PlayerState currentState;
    protected WeaponDecorator weapon;
    protected String name;
    protected float speed;
    protected float damageInvulnerability = 0;
    protected Vector2 prevPosition = new Vector2();  // ✅ ЖАҢА

    public Hero(float x, float y, int hp, String name) {
        super(x, y, hp);
        this.name = name;
        this.currentState = new IdleState();
        this.prevPosition.set(x, y);
    }

    public void setState(PlayerState newState) {
        if (currentState != null) currentState.exit(this);
        this.currentState = newState;
        newState.enter(this);
    }

    public void handleInput() {
        if (currentState != null) currentState.handleInput(this);
    }

    /** ✅ Қозғалмастан бұрын прежние координаталарды сақтау */
    public void savePosition() {
        prevPosition.set(position);
    }

    /** ✅ Қабырғаға тиді ме — артқа қайтару */
    public void checkWallCollision(List<Wall> walls) {
        for (Wall w : walls) {
            if (w.collidesWith(position.x, position.y, 14)) {
                position.set(prevPosition);
                return;
            }
        }
    }

    @Override
    protected void updateBehavior(float delta) {
        if (currentState != null) currentState.update(this, delta);
        if (damageInvulnerability > 0) damageInvulnerability -= delta;
    }

    @Override
    public void takeDamage(int dmg) {
        if (damageInvulnerability > 0) return;
        super.takeDamage(dmg);
        damageInvulnerability = 0.5f;
    }

    @Override
    protected void updateAnimation(float delta) {}

    @Override
    protected void checkBounds() {
        if (position.x < 45) position.x = 45;
        if (position.y < 45) position.y = 45;
        if (position.x > 1155) position.x = 1155;
        if (position.y > 555) position.y = 555;
    }

    public void attack(Entity target) {
        if (attackStrategy != null) attackStrategy.attack(this, target);
    }

    public boolean isInvulnerable() { return damageInvulnerability > 0; }

    public AttackStrategy getAttackStrategy() { return attackStrategy; }
    public void setAttackStrategy(AttackStrategy s) { this.attackStrategy = s; }
    public WeaponDecorator getWeapon() { return weapon; }
    public void setWeapon(WeaponDecorator w) { this.weapon = w; }
    public String getName() { return name; }
    public float getSpeed() { return speed; }
    public PlayerState getCurrentState() { return currentState; }
}
