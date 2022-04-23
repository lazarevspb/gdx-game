package ru.lazarev.game.screens;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.fonts.SpaceFont;

public class GameOverScreen implements Screen, InputProcessor {

  private final SpriteBatch batch;
  private final Game game;
  private final BitmapFont newGame;
  private final BitmapFont exit;
  private final SpaceFont gameOver;

  public GameOverScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    gameOver = new SpaceFont(70);
    gameOver.setColor(Color.YELLOW);
    newGame = new BitmapFont();
    newGame.setColor(Color.YELLOW);
    newGame.getData().setScale(2, 1);
    exit = new BitmapFont();
    exit.setColor(Color.YELLOW);
    exit.getData().setScale(2, 1);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    final int centerX = Gdx.graphics.getHeight() >> 1;
    final int centerY = Gdx.graphics.getWidth() >> 1;
    ScreenUtils.clear(Color.FIREBRICK);
    batch.begin();
    gameOver.draw(batch, "GAME OVER", centerX - 180, centerY);
    newGame.draw(batch, "new game?", centerX + 20, centerY - 156);
    exit.draw(batch, "exit", centerX + 20, centerY - 186);
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
    newGame.dispose();
    exit.dispose();
    batch.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == 66) {
      game.setScreen(new MainScreen((game)));
    } else if (keycode == 111) {
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
    Rectangle rectangle = getNewGameRectangle();
    Rectangle exit = getExitRectangle();
    Vector2 cursor = getPosition();
    if (rectangle.contains(cursor)) {
      dispose();
      game.setScreen(new MainScreen(game));
      return true;
    } else if (exit.contains(cursor)) {
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
    Vector2 cursor = getPosition();
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

    return new Rectangle(getCenterScreen().x + 18, getCenterScreen().y - 174, 150, 20);
  }

  private Rectangle getExitRectangle() {
    return new Rectangle(getCenterScreen().x + 18, getCenterScreen().y - 204, 150, 20);
  }

  private Vector2 getCenterScreen() {
    return new Vector2(Gdx.graphics.getHeight() >> 1, Gdx.graphics.getWidth() >> 1);
  }
}
