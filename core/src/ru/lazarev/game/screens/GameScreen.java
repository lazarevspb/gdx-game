package ru.lazarev.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;
import ru.lazarev.game.sprites.EnemyShip;
import ru.lazarev.game.sprites.Turret;
import ru.lazarev.game.utils.TargetPointer;

public class GameScreen implements Screen, InputProcessor {

  private final SpriteBatch batch;
  private final Game game;
  private static final int COUNT_SPACE_SHIPS = 2;
  private final List<EnemyShip> enemyShips;
  private final TargetPointer targetPointer;
  private int numberOfHits;

  private Sprite nightSkySprite;
  private Texture nightSky;

  private final Turret turret;

  public GameScreen(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    targetPointer = new TargetPointer();
    enemyShips = new ArrayList<>();
    turret = new Turret();
    nightSky = new Texture("img/farback.gif");
    nightSkySprite = new Sprite(nightSky);
    fillSpaceShips();
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.BLACK);
    boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

    batch.begin();
//    batch.draw(nightSky, 0, 0);

    if (Gdx.graphics.getWidth() > 800) {
      nightSkySprite.setScale(2.5f);
    } else {
      nightSkySprite.setScale(0.9f);
      nightSkySprite.setPosition(-100, -100);

    }
    nightSkySprite.draw(batch);
    turret.render(batch);
    getSpaceShip();
    batch.end();

    targetPointer.render();
    for (EnemyShip enemyShip : enemyShips) {
      turret.hitHandling(fire);
      if (fire) {
        if (enemyShip.isDamage()) {
          enemyShip.reuse();
          numberOfHits++;
        }
      }

      if (enemyShip.isGoingOffScreen()) {
        dispose();
        game.setScreen(new GameOverScreen(game));
      }
    }

    Gdx.graphics.setTitle("Спрайтов подбито: " + numberOfHits);
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
    if (keycode == Input.Keys.ESCAPE) {
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
//    Gdx.app.log("App:", "x: " + screenX + ", y: " + screenY);
//    Gdx.app.log("App:", "graphicsX: " + Gdx.graphics.getWidth() + ", graphicsY: " + Gdx.graphics.getHeight());
//    return true;
    return false;
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
    for (EnemyShip enemyShip : enemyShips) {
      enemyShip.render(batch);
    }
  }

  private void fillSpaceShips() {
    for (int i = 0; i < COUNT_SPACE_SHIPS; i++) {
      enemyShips.add(new EnemyShip());
    }
  }
}
