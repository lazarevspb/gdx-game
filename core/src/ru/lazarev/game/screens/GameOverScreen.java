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
  private final BitmapFont gameOver;
  private final BitmapFont newGame;
  private final SpaceFont spaceFont;

  public GameOverScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    gameOver = new BitmapFont();
    spaceFont = new SpaceFont(70);
    spaceFont.setColor(Color.YELLOW);
    newGame = new BitmapFont();
    newGame.setColor(Color.YELLOW);
    newGame.getData().setScale(2, 1);
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
    spaceFont.draw(batch, "GAME OVER", centerX - 180, centerY);
    newGame.draw(batch, "new game?", centerX + 20, centerY - 156);
    batch.end();

//    Rectangle rectangle = new Rectangle(centerX + 18, centerY-58, 150, 20);;
//    ShapeRenderer shapeRenderer = new ShapeRenderer();
//    shapeRenderer.begin(ShapeType.Line);
//    shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//    shapeRenderer.end();
//    shapeRenderer.dispose();
//    System.out.println("\"MainScreen\" = " + "MainScreen");

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
    gameOver.dispose();
    batch.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
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
    Vector2 vector = getPosition();
    if (rectangle.contains(vector)) {
      dispose();
      game.setScreen(new MainScreen(game));
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
    Rectangle rectangle = getNewGameRectangle();
    Vector2 vector = getPosition();
    if (rectangle.contains(vector)) {
      newGame.setColor(Color.BLUE);
      return true;
    } else {
      newGame.setColor(Color.YELLOW);
    }
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  private Rectangle getNewGameRectangle() {
    final int centerX = Gdx.graphics.getHeight() >> 1;
    final int centerY = Gdx.graphics.getWidth() >> 1;
    return new Rectangle(centerX + 18, centerY - 174, 150, 20);
  }

}
