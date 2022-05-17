package ru.lazarev.game;

import com.badlogic.gdx.Game;
import ru.lazarev.game.screens.MainScreen;
import ru.lazarev.game.screens.StageTwo;

public class App extends Game {
    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
//        this.setScreen(new StageTwo(this));
    }

    @Override
    public void render() {
        super.render();
    }
}