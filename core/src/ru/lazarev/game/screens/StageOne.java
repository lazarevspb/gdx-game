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
import ru.lazarev.game.game_objects.EnemyShip;
import ru.lazarev.game.game_objects.Turret;
import ru.lazarev.game.utils.TargetPointer;

public class StageOne implements Screen, InputProcessor {

  private final SpriteBatch batch;
  private final Game game;
  @SuppressWarnings("FieldCanBeLocal")
  private final int totalShips = 2;
  private final List<EnemyShip> enemyShips;
  private final TargetPointer targetPointer;
  private int numberOfHits;

  private int spent;

  private final Sprite nightSkySprite;
  private final Turret turret;

  public StageOne(Game game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
    batch = new SpriteBatch();
    targetPointer = new TargetPointer();
    enemyShips = new ArrayList<>();
    turret = new Turret();
    @SuppressWarnings("SpellCheckingInspection")
    Texture nightSky = new Texture("img/farback.gif");
    nightSkySprite = new Sprite(nightSky);
    addSpaceShips(totalShips);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.BLACK);
    boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

    batch.begin();
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
          spent++;
        }
      }
      if (enemyShip.isGoingOffScreen()) {
        dispose();
        game.setScreen(new GameOverScreen(game));
      }
      if (numberOfHits == 5) {
        dispose();
        game.setScreen(new StageTwo(game));
      }
    }






    if (spent == enemyShips.size()) {
      spent = 0;
      addSpaceShips(1);
    }

    Gdx.graphics.setTitle(String.format("???????????????? ??????????????: %d, ?????????? ????????????: %d", numberOfHits, enemyShips.size()));
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
//    batch.dispose();
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

  private void addSpaceShips(int count) {
    for (int i = 0; i < count; i++) {
      enemyShips.add(new EnemyShip());
    }
  }
}
