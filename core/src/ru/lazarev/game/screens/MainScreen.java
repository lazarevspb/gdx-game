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

public class MainScreen implements Screen, InputProcessor {

  private final Game game;
  private final SpriteBatch batch;
  private final BitmapFont bitmapFont;
  private final SpaceFont spaceFont;

  public MainScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    bitmapFont = new BitmapFont();
    bitmapFont.setColor(Color.YELLOW);
    bitmapFont.getData().setScale(2, 1);
    spaceFont = new SpaceFont(70);
    spaceFont.setColor(Color.YELLOW);

  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLUE);
    batch.begin();
    bitmapFont.draw(batch, "Start Game", 58, 100);
    spaceFont.draw(batch, "STAR WARS", 58, 250);
    batch.end();

//    Rectangle rectangle = getStartGameRectangle();
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
    batch.dispose();
    bitmapFont.dispose();
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
    Rectangle rectangle = getStartGameRectangle();
    Vector2 vector = getPosition();
    if (rectangle.contains(vector)) {
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
    Rectangle rectangle = getStartGameRectangle();
    Vector2 vector = getPosition();
    if (rectangle.contains(vector)) {
      bitmapFont.setColor(Color.RED);
      return true;
    } else {
      bitmapFont.setColor(Color.YELLOW);
    }
    return false;


  }

  private Rectangle getStartGameRectangle() {
    return new Rectangle(56, 84, 150, 20);
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
