package com.dungeondevs.game.patterns.command;

public interface Command {
    void execute();
    void undo();
}
