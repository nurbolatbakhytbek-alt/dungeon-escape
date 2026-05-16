package com.dungeondevs.game.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class GameEventManager {
    private List<GameEventListener> listeners = new ArrayList<>();

    public void subscribe(GameEventListener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void unsubscribe(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void notify(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
