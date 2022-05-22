package ru.lazarev.game;

import com.badlogic.gdx.Game;
import ru.lazarev.game.screens.StageFour;

public class App extends Game {
    @Override
    public void create() {
//        this.setScreen(new MainScreen(this));
//        this.setScreen(new StageTwo(this));
//        this.setScreen(new StageThree(this));
        this.setScreen(new StageFour());
    }

    @Override
    public void render() {
        super.render();
    }
}