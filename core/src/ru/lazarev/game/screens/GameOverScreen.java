package ru.lazarev.game.screens;

import static ru.lazarev.game.utils.GfxUtils.getCenterY;

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

public class GameOverScreen implements Screen, InputProcessor {

  public static final String GAME_OVER = "GAME OVER";
  public static final String NEW_GAME = "New Game?";
  public static final String EXIT = "Exit";
  private final SpriteBatch batch;
  private final Game game;
  private final TitleFont gameOver;
  private final MainFont newGameText;
  private final MainFont exitText;


  public GameOverScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    if (Gdx.graphics.getHeight() > 800) {
      gameOver = new TitleFont(200);
      newGameText = new MainFont(NEW_GAME, new Vector2(880, 370), 6, 4);
      exitText = new MainFont(EXIT, new Vector2(880, 160), 6, 4);
    } else {
      gameOver = new TitleFont(70, new Vector2(GfxUtils.getCenterX() - 200, GfxUtils.getCenterY()));
      newGameText = new MainFont(NEW_GAME, new Vector2(240, 200), 2, 1);
      exitText = new MainFont(EXIT, new Vector2(240, 150), 2, 1);
    }
    gameOver.setColor(Color.YELLOW);
  }

  @Override
  public void show() {
  }

  @SuppressWarnings("IntegerDivisionInFloatingPointContext")
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.FIREBRICK);
    batch.begin();

    if (Gdx.graphics.getHeight() > 800) {
      gameOver.draw(batch, GAME_OVER,
          new Vector2((Gdx.graphics.getWidth() - gameOver.getWidth()) >> 2,
              getCenterY() / 20 + (getCenterY() >> 1)));
    } else {
      gameOver.draw(batch, GAME_OVER);
    }
    newGameText.draw(batch);
    exitText.draw(batch);
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
    newGameText.dispose();
    exitText.dispose();
    batch.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Input.Keys.ENTER) {
      game.setScreen(new MainScreen((game)));
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
    Gdx.app.log("App:", "x: " + screenX + ", y: " + screenY);
    Gdx.app.log("App:",
        "graphicsX: " + Gdx.graphics.getWidth() + ", graphicsY: " + Gdx.graphics.getHeight());
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    Vector2 cursor = GfxUtils.getCursorPosition();

    if (newGameText.getBoundingRectangle().contains(cursor)) {
      dispose();
      game.setScreen(new MainScreen(game));
      return true;
    } else if (exitText.getBoundingRectangle().contains(cursor)) {
      dispose();
      Gdx.app.exit();
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
    if (newGameText.getBoundingRectangle().contains(cursor)) {
      this.newGameText.setColor(Color.BLUE);
      return true;
    } else if (exitText.getBoundingRectangle().contains(cursor)) {
      this.exitText.setColor(Color.BLUE);
      return true;
    } else {
      this.newGameText.setColor(Color.YELLOW);
      this.exitText.setColor(Color.YELLOW);
    }
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
