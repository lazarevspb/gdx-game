package ru.lazarev.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;
import ru.lazarev.game.fonts.SpaceFont;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

public class Turret {
    private static final String PATCH_ATLAS = "img/atlas/main.atlas";
    private static final String TURRET_DEPLOYMENT = "turret-sprites-deployment";
    private static final String TURRET_BODY = "turret-sprites-body";
    private static final String TURRET_IDLE = "turret-sprites-head-shot-idle";
    private final MyAnimation deploymentAnimation;
    private final MyAnimation bodyAnimation;
    private final MyAnimation headAnimation;
    private final TextureAtlas textureAtlas;

    SpriteBatch batch;
    private final List<Explosion> explosions;

    private int numberOfHits;

    public Turret() {
        batch = new SpriteBatch();
        explosions = new ArrayList<>();
        textureAtlas = new TextureAtlas(PATCH_ATLAS);

        deploymentAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_DEPLOYMENT), Animation.PlayMode.NORMAL, 8, 1, 8);
        bodyAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_BODY), Animation.PlayMode.LOOP, 2, 1, 16);
        headAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_IDLE), Animation.PlayMode.NORMAL, 5, 1, 60);
    }

    public void render() {
        getTurretAttackHandling();
    }
    public void hitHandling(boolean fire, EnemyShip ship) {
        if ((fire & !headAnimation.isFinished()) || (!fire & !headAnimation.isFinished())) {
            headAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (fire & headAnimation.isFinished()) {
            headAnimation.resetTime();
            explosions.add(
                    new Explosion(textureAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16,
                            "audio/explosion.mp3"));
            if (ship.getSprite().getBoundingRectangle().contains(getPosition())) {
                ship.setX(Gdx.graphics.getWidth() + 100);
                ship.setY(MathUtils.random(0, Gdx.graphics.getHeight() - ship.getSprite().getHeight()));
                float speedRandom = MathUtils.random(0.9f, 7.0f);
                if (Gdx.graphics.getHeight() > 800) {
                    speedRandom = speedRandom * 4;
                }
                ship.setSpeed(speedRandom);
                numberOfHits++;
            }
        }
        Gdx.graphics.setTitle("Спрайтов подбито: " + numberOfHits);
    }

    public void getTurretAttackHandling() {
        float rotation = 360 - MathUtils.atan2(getPosition().x - 25, getPosition().y - 34) * MathUtils.radiansToDegrees;
        batch.begin();
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
    }
}
