package com.dungeondevs.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.HashMap;

public class AssetManager {
    private static AssetManager instance;
    private HashMap<String, Texture> textures = new HashMap<>();
    private BitmapFont font;

    private AssetManager() {}

    public static synchronized AssetManager getInstance() {
        if (instance == null) instance = new AssetManager();
        return instance;
    }

    public void loadAll() {
        font = new BitmapFont();
        // Текстураларды жүктеу
        loadTexture("player", "player.png");
        loadTexture("warrior", "warrior.png");
        loadTexture("mage", "mage.png");
        loadTexture("archer", "archer.png");
        loadTexture("goblin", "goblin.png");
        loadTexture("skeleton", "skeleton.png");
        loadTexture("boss", "boss.png");
        loadTexture("wall", "wall.png");
        loadTexture("floor", "floor.png");
        loadTexture("trap", "trap.png");
    }

    private void loadTexture(String key, String path) {
        if (Gdx.files.internal(path).exists()) {
            textures.put(key, new Texture(path));
        }
    }

    public Texture getTexture(String key) { return textures.get(key); }
    public BitmapFont getFont() { return font; }

    public void dispose() {
        for (Texture t : textures.values()) t.dispose();
        if (font != null) font.dispose();
    }
}
