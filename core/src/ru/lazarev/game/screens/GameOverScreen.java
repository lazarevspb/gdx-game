package ru.lazarev.game.screens;

import static ru.lazarev.game.utils.GfxUtils.getCenterY;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.fonts.SpaceFont;
import ru.lazarev.game.utils.GfxUtils;

public class GameOverScreen implements Screen, InputProcessor {

  public static final String GAME_OVER = "GAME OVER";
  public static final String NEW_GAME = "new game?";
  public static final String EXIT = "exit";
  private final SpriteBatch batch;
  private final Game game;
  private final BitmapFont newGame;
  private final BitmapFont exit;
  private final SpaceFont gameOver;
  private final Vector2 newGamePosition;
  private final Vector2 sizeText;
  private final Vector2 exitPosition;

  public GameOverScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    if (Gdx.graphics.getHeight() > 800) {
      gameOver = new SpaceFont(200);

    } else {
      gameOver = new SpaceFont(70);

    }
    gameOver.setColor(Color.YELLOW);
    newGame = new BitmapFont();
    newGame.setColor(Color.YELLOW);
    newGame.getData().setScale(6, 4);

    exit = new BitmapFont();
    exit.setColor(Color.YELLOW);
    exit.getData().setScale(6, 4);

    newGamePosition = new Vector2(880, 370);
    exitPosition = new Vector2(880, 160);
    sizeText = new Vector2(460, 200);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.FIREBRICK);
    batch.begin();

    if (Gdx.graphics.getHeight() > 800) {
      gameOver.draw(batch, GAME_OVER, (Gdx.graphics.getWidth() - gameOver.getWidth()) / 4,
          getCenterY() / 20 + getCenterY() / 2);
      newGame.draw(batch, NEW_GAME,
          getCenterScreen().x - ((newGame.getRegion().getRegionWidth() >> 1) + 136),
          getCenterScreen().y - 100);
      exit.draw(batch, EXIT,
          getCenterScreen().x - ((newGame.getRegion().getRegionWidth() >> 1) + 136),
          getCenterScreen().y - 200);


    } else {
      gameOver.draw(batch, GAME_OVER, GfxUtils.getCenterX() - 256, GfxUtils.getCenterY());
      newGame.draw(batch, NEW_GAME, GfxUtils.getCenterX() + 20, GfxUtils.getCenterY() - 156);
      exit.draw(batch, EXIT, GfxUtils.getCenterX() + 20, GfxUtils.getCenterY() - 186);
    }
    batch.end();
//    GfxUtils.getRectangle(newGamePosition, sizeText);
//    GfxUtils.getRectangle(exitPosition, sizeText);

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
    newGame.dispose();
    exit.dispose();
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

    if (getNewGameRectangle().contains(cursor)) {
      dispose();
      game.setScreen(new MainScreen(game));
      return true;
    } else if (getExitRectangle().contains(cursor)) {
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
    Rectangle newGame = getNewGameRectangle();
    Rectangle exit = getExitRectangle();

    Vector2 cursor = GfxUtils.getCursorPosition();

    if (newGame.contains(cursor)) {
      this.newGame.setColor(Color.BLUE);
      return true;
    } else if (exit.contains(cursor)) {
      this.exit.setColor(Color.BLUE);
      return true;
    } else {
      this.newGame.setColor(Color.YELLOW);
      this.exit.setColor(Color.YELLOW);
    }
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  private Rectangle getNewGameRectangle() {

    return new Rectangle(newGamePosition.x, newGamePosition.y, sizeText.x, sizeText.y);
  }

  private Rectangle getExitRectangle() {
    return new Rectangle(exitPosition.x, exitPosition.y, sizeText.x, sizeText.y);
  }

  private Vector2 getCenterScreen() {
    return new Vector2(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1);
  }
}
