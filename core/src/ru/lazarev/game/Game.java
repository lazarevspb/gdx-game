package ru.lazarev.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private MyAnimation turretAnimation;
    private MyAnimation bodyAnimation;
    private MyAnimation headAnimation;
    private List<Explosion> explosions;
    private TextureAtlas textureAtlas;

    @Override
    public void create() {
        textureAtlas = new TextureAtlas("img/atlas/main.atlas");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        explosions = new ArrayList<>();
        turretAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-deployment"), Animation.PlayMode.NORMAL, 8, 1, 8);
        bodyAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-body"), Animation.PlayMode.LOOP, 2, 1, 16);
        headAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-head-shot-idle"), Animation.PlayMode.NORMAL, 5, 1, 60);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.FOREST);
        float rotation = 360 - MathUtils.atan2(getPosition().x - 25, getPosition().y - 34) * MathUtils.radiansToDegrees;
        boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
        shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
        shapeRenderer.end();

        batch.begin();
        if (!turretAnimation.isFinished()) {
            turretAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(turretAnimation.getRegion(), 0, 0);
        } else {
            bodyAnimation.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(bodyAnimation.getRegion(), 0, 0);
            batch.draw(headAnimation.getRegion(), 11, 12, 14, 22, headAnimation.getRegion().getRegionWidth(), headAnimation.getRegion().getRegionHeight(), 1, 1, rotation, false);
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

        if ((fire & !headAnimation.isFinished()) || (!fire & !headAnimation.isFinished()))
            headAnimation.setTime(Gdx.graphics.getDeltaTime());

        if (fire & headAnimation.isFinished()) {
            headAnimation.resetTime();
            explosions.add(
                    new Explosion(
                            textureAtlas.findRegion("explosion-sprite"), Animation.PlayMode.NORMAL, 4, 4, 16, "audio/explosion.mp3"));
        }
        Gdx.graphics.setTitle(String.valueOf(explosions.size()));
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}