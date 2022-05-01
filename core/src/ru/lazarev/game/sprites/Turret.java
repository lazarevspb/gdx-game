package ru.lazarev.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;
import ru.lazarev.game.utils.GfxUtils;

public class Turret {

  private static final String PATCH_ATLAS = "img/atlas/main.atlas";
  private static final String TURRET_DEPLOYMENT = "turret-sprites-deployment";
  private static final String TURRET_BODY = "turret-sprites-body";
  private static final String TURRET_IDLE = "turret-sprites-head-shot-idle";
  private final MyAnimation deploymentAnimation;
  private final MyAnimation bodyAnimation;
  private final MyAnimation headAnimation;
  private final TextureAtlas textureAtlas;
  private final List<Explosion> explosions;


  public Turret() {
    explosions = new ArrayList<>();
    textureAtlas = new TextureAtlas(PATCH_ATLAS);

    deploymentAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_DEPLOYMENT),
        Animation.PlayMode.NORMAL, 8, 1, 8);
    bodyAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_BODY), Animation.PlayMode.LOOP,
        2, 1, 16);
    headAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_IDLE), Animation.PlayMode.NORMAL,
        5, 1, 60);
  }

  public void render(SpriteBatch batch) {
    getTurretAttackHandling(batch);
  }

  public void hitHandling(boolean fire) {
    if ((fire & !headAnimation.isFinished()) || (!fire & !headAnimation.isFinished())) {
      headAnimation.setTime(Gdx.graphics.getDeltaTime());
    }
    if (fire & headAnimation.isFinished()) {
      headAnimation.resetTime();
      explosions.add(
          new Explosion(textureAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16, "audio/explosion.mp3"));
    }
  }

  private void getTurretAttackHandling(SpriteBatch batch) {
    float rotation = 360
        - MathUtils.atan2(GfxUtils.getCursorPosition().x - 25, GfxUtils.getCursorPosition().y - 34) * MathUtils.radiansToDegrees;
    if (!deploymentAnimation.isFinished()) {
      deploymentAnimation.setTime(Gdx.graphics.getDeltaTime());

      if (Gdx.graphics.getHeight() > 800) {
        batch.draw(deploymentAnimation.getRegion(), 0, 0, 200, 200);
      } else {
        batch.draw(deploymentAnimation.getRegion(), 0, 0);
      }

    } else {
      bodyAnimation.setTime(Gdx.graphics.getDeltaTime());
      if (Gdx.graphics.getHeight() > 800) {
        batch.draw(bodyAnimation.getRegion(), 0, 0, 200, 200);
        batch.draw(headAnimation.getRegion(), 14, 40, 70, 80, 200, 200, 1, 1, rotation, false);
      } else {
        batch.draw(bodyAnimation.getRegion(), 0, 0);
        batch.draw(headAnimation.getRegion(), 11, 12, 12, 24,
            headAnimation.getRegion().getRegionWidth(), headAnimation.getRegion().getRegionHeight(),
            1, 1, rotation, false);
      }
    }

    ListIterator<Explosion> iterator = explosions.listIterator();
    while (iterator.hasNext()) {
      Explosion explosion = iterator.next();
      explosion.setTime(Gdx.graphics.getDeltaTime());
      if (explosion.isFinished()) {
        explosion.dispose();
        iterator.remove();
      } else {
        batch.draw(explosion.getRegion(), explosion.getPos().x, explosion.getPos().y);
      }
    }
  }
}
