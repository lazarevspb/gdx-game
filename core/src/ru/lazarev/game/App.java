package ru.lazarev.game;

import com.badlogic.gdx.Game;
import ru.lazarev.game.screens.MainScreen;

public class App extends Game {
    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}