package com.dungeondevs.game.patterns.command;

import com.dungeondevs.game.entities.heroes.Hero;

public class MoveCommand implements Command {
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private Hero hero;
    private Direction direction;
    private float deltaTime;
    private float prevX, prevY;

    public MoveCommand(Hero hero, Direction direction, float deltaTime) {
        this.hero = hero;
        this.direction = direction;
        this.deltaTime = deltaTime;
    }

    @Override
    public void execute() {
        prevX = hero.getPosition().x;
        prevY = hero.getPosition().y;
        float speed = hero.getSpeed() * deltaTime;
        switch (direction) {
            case UP:    hero.getPosition().y += speed; break;
            case DOWN:  hero.getPosition().y -= speed; break;
            case LEFT:  hero.getPosition().x -= speed; break;
            case RIGHT: hero.getPosition().x += speed; break;
        }
    }

    @Override
    public void undo() {
        hero.getPosition().x = prevX;
        hero.getPosition().y = prevY;
    }
}
