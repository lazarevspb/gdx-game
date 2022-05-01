package ru.lazarev.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.fonts.MainFont;
import ru.lazarev.game.fonts.TitleFont;
import ru.lazarev.game.utils.GfxUtils;

public class MainScreen implements Screen, InputProcessor {

    public static final String START_GAME = "Start Game";
    public static final String NAME_GAME = "STAR WARS";
    private final Game game;
    private final SpriteBatch batch;
    private final MainFont startGameText;
    private final TitleFont titleFont;

    public MainScreen(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        startGameText = new MainFont(START_GAME, new Vector2(58, 100), 2, 1);

        if (Gdx.graphics.getWidth() > 800) {
            titleFont = new TitleFont(150);
        } else {
            titleFont = new TitleFont(70);
        }
        titleFont.setColor(Color.YELLOW);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);
        batch.begin();
        startGameText.draw(batch);
        titleFont.draw(batch, NAME_GAME, new Vector2(58, 250));
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        startGameText.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("app", "keyDown: " + keycode);

        if (keycode == Input.Keys.ENTER) {
            game.setScreen(new GameScreen(game));
        } else if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 cursor = GfxUtils.getCursorPosition();
        if (startGameText.getBoundingRectangle().contains(cursor)) {
            dispose();
            game.setScreen(new GameScreen(game));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 cursor = GfxUtils.getCursorPosition();
        if (startGameText.getBoundingRectangle().contains(cursor)) {
            startGameText.setColor(Color.RED);
            return true;
        } else {
            startGameText.setColor(Color.YELLOW);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
