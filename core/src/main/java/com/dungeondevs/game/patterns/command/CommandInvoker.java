package com.dungeondevs.game.patterns.command;

import java.util.Stack;

public class CommandInvoker {
    private Stack<Command> history = new Stack<>();

    public void execute(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undoLast() {
        if (!history.isEmpty()) history.pop().undo();
    }
}
