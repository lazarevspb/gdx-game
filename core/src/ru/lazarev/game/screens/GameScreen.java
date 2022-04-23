package ru.lazarev.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.sprites.EnemyShip;
import ru.lazarev.game.sprites.Turret;

import java.util.ArrayList;
import java.util.List;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

public class GameScreen implements Screen, InputProcessor {

  private final SpriteBatch batch;
  private final Game game;
  private static final int COUNT_SPACE_SHIPS = 2;
  private final List<EnemyShip> enemyShips;
  private final ShapeRenderer shapeRenderer;
  private final Rectangle mouseRectangle;

  private final Turret turret;
  public GameScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    shapeRenderer = new ShapeRenderer();
    mouseRectangle = new Rectangle();
    enemyShips = new ArrayList<>();
    turret = new Turret();
    fillSpaceShips();
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
    boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    turret.render();
    getTargetMouse();
    getSpaceShip();

    for (EnemyShip enemyShip : enemyShips) {
     turret.hitHandling(fire, enemyShip);
      if (enemyShip.isGoingOffScreen()) {
        dispose();
        game.setScreen(new GameOverScreen(game));
      }
    }
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
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == 111) {
      game.setScreen(new GameOverScreen(game));
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
    Gdx.app.log("App:", "graphicsX: " + Gdx.graphics.getWidth() + ", graphicsY: " + Gdx.graphics.getHeight());
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  private void getSpaceShip() {
    batch.begin();
    for (EnemyShip enemyShip : enemyShips) {
      enemyShip.render(batch);
    }
    batch.end();
  }

  private void getTargetMouse() {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10,
        getPosition().y);
    shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x,
        getPosition().y + 10);
    mouseRectangle.set(getPosition().x - 10, getPosition().y - 10, 20, 20);
    shapeRenderer.rect(mouseRectangle.x, mouseRectangle.y, mouseRectangle.getWidth(),
        mouseRectangle.getHeight());
    shapeRenderer.end();
  }

  private void fillSpaceShips() {
    for (int i = 0; i < COUNT_SPACE_SHIPS; i++) {
      enemyShips.add(new EnemyShip());
    }
  }
}
