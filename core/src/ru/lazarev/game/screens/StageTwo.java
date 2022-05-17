package ru.lazarev.game.screens;

import static ru.lazarev.game.utils.GfxUtils.getAngle;
import static ru.lazarev.game.utils.GfxUtils.getCursorPosition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;
import ru.lazarev.game.game_objects.BaseEnemy;
import ru.lazarev.game.game_objects.BigShip;
//import ru.lazarev.game.sprites.Turret;

public class StageTwo implements Screen, InputProcessor {

  final Game game;

  SpriteBatch batch;
  ShapeRenderer shapeRenderer;
  MyAnimation turretAnm;
  MyAnimation bodyAnim;
  MyAnimation headAnim;
  List<Explosion> explosions;
  Sprite headSpr;
  public static TextureAtlas mainAtlas;
  float x;
  float y;
  int totalHit;
  int enemyAll;
  int enemyOnScreen;
  List<BigShip> enemyList;
  float enemyTime;
  float enemyTimeCount;
  float damage;

//  private final Turret turret;

  public StageTwo(Game game) {
//    turret = new Turret();
    this.game = game;
    Gdx.input.setInputProcessor(this);

    enemyList = new ArrayList<>();
    enemyOnScreen = 1;
    enemyTime = 1;
    enemyTimeCount = 0;
    enemyAll = 3;

    damage = 1;
    totalHit = 0;
    batch = new SpriteBatch();
    x = Gdx.graphics.getWidth() + 100;
    shapeRenderer = new ShapeRenderer();
    mainAtlas = new TextureAtlas("img/atlas/main.atlas");
    explosions = new ArrayList<>();
    turretAnm = new MyAnimation(mainAtlas.findRegion("turret-sprites-deployment"), Animation.PlayMode.NORMAL, 8, 1, 8);
    bodyAnim = new MyAnimation(mainAtlas.findRegion("turret-sprites-body"), Animation.PlayMode.LOOP, 2, 1, 16);
    headAnim = new MyAnimation(mainAtlas.findRegion("turret-sprites-head-shot-idle"), Animation.PlayMode.NORMAL, 5, 1, 60);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }
    ScreenUtils.clear(Color.FOREST);

    boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

    batch.begin();
//    turret.render(batch);

    if (!turretAnm.isFinished()) {
      turretAnm.setTime(Gdx.graphics.getDeltaTime());
      batch.draw(turretAnm.getRegion(), 0, 0);
    } else {
      bodyAnim.setTime(Gdx.graphics.getDeltaTime());
      batch.draw(bodyAnim.getRegion(), 0, 0);
      batch.draw(headAnim.getRegion(), 11, 12, 14, 22, headAnim.getRegion().getRegionWidth(),
          headAnim.getRegion().getRegionHeight(), 1, 1, getAngle(new Vector2(0, 0)), false);
    }

    for (BaseEnemy enemy : enemyList) {
      enemy.step();
      enemy.draw(batch);
    }

    ListIterator<Explosion> iterator = explosions.listIterator();
    while (iterator.hasNext()) {
      Explosion ex = iterator.next();
      ex.setTime(Gdx.graphics.getDeltaTime());
      if (ex.isFinished()) {
        ex.dispose();
        iterator.remove();
      } else {
        batch.draw(ex.getRegion(), ex.getPos().x, ex.getPos().y);
      }
    }
    batch.end();

    if ((fire & !headAnim.isFinished()) || (!fire & !headAnim.isFinished())) {
      headAnim.setTime(Gdx.graphics.getDeltaTime());
    }
    if (fire & headAnim.isFinished()) {
      headAnim.resetTime();
      explosions.add(
          new Explosion(mainAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16,
              "audio/explosion.mp3", damage));
      ListIterator<BigShip> iterator1 = enemyList.listIterator(enemyList.size());
      while (iterator1.hasPrevious()) {
        BaseEnemy enemy = iterator1.previous();
        if (enemy.isHit(getCursorPosition())) {
          if (enemy.damage(explosions.get(explosions.size() - 1).getDamage()) < 0) {
            iterator1.remove();
          }
          explosions.get(explosions.size() - 1).setDamage(0);
          totalHit++;
          break;
        }
      }
    }

    Gdx.graphics.setTitle("Врагов подбито: " + totalHit);

    enemyTimeCount += Gdx.graphics.getDeltaTime();
    if (enemyTimeCount > enemyTime && enemyList.size() < enemyOnScreen) {
      enemyTimeCount = 0;
      enemyList.add(new BigShip("enemy", 0.5f, 10));
      enemyAll--;
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
//        bodyAnim.dispose();
//        headAnim.dispose();
//        turretAnm.dispose();
    shapeRenderer.dispose();
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
}
