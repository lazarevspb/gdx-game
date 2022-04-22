package ru.lazarev.game;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;
import ru.lazarev.game.screens.MainScreen;
import ru.lazarev.game.sprites.SpaceShip;

public class myGame extends Game {
    private static final int COUNT_SPACE_SHIPS = 2;
    private static final String PATCH_ATLAS = "img/atlas/main.atlas";
    private static final String TURRET_DEPLOYMENT = "turret-sprites-deployment";
    private static final String TURRET_BODY = "turret-sprites-body";
    private static final String TURRET_IDLE = "turret-sprites-head-shot-idle";
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private MyAnimation turretAnimation;
    private MyAnimation bodyAnimation;
    private MyAnimation headAnimation;
    private List<Explosion> explosions;

    private List<SpaceShip> spaceShips;
    private TextureAtlas textureAtlas;
    private Rectangle mouseRectangle;
    private int numberOfHits;

    @Override
    public void create() {
        textureAtlas = new TextureAtlas(PATCH_ATLAS);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mouseRectangle = new Rectangle();
        explosions = new ArrayList<>();
        spaceShips = new ArrayList<>();
        fillSpaceShips();
        turretAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_DEPLOYMENT), Animation.PlayMode.NORMAL, 8, 1, 8);
        bodyAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_BODY), Animation.PlayMode.LOOP, 2, 1, 16);
        headAnimation = new MyAnimation(textureAtlas.findRegion(TURRET_IDLE), Animation.PlayMode.NORMAL, 5, 1, 60);
        this.setScreen(new MainScreen(this));
    }

    @Override
    public void render() {
        super.render();
/*        ScreenUtils.clear(Color.FOREST);
        boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        getTurretAttackHandling();
        getTargetMouse();
        getSpaceShip();

        for (SpaceShip spaceShip : spaceShips) {
            hitHandling(fire, spaceShip);
            if (spaceShip.isGoingOffScreen()) {
                Gdx.app.exit();
            }
        }*/
    }

    private void getSpaceShip() {
        batch.begin();
        for (SpaceShip spaceShip : spaceShips) {
            spaceShip.render(batch);
        }
        batch.end();
    }

    private void getTargetMouse() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
        shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
        mouseRectangle.set(getPosition().x - 10, getPosition().y - 10, 20, 20);
        shapeRenderer.rect(mouseRectangle.x, mouseRectangle.y, mouseRectangle.getWidth(), mouseRectangle.getHeight());
        shapeRenderer.end();
    }

    private void getTurretAttackHandling() {
        float rotation = 360 - MathUtils.atan2(getPosition().x - 25, getPosition().y - 34) * MathUtils.radiansToDegrees;
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
    }

    private void hitHandling(boolean fire, SpaceShip ship) {
        if ((fire & !headAnimation.isFinished()) || (!fire & !headAnimation.isFinished()))
            headAnimation.setTime(Gdx.graphics.getDeltaTime());
        if (fire & headAnimation.isFinished()) {
            headAnimation.resetTime();
            explosions.add(new Explosion(textureAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16, "audio/explosion.mp3"));
            if (ship.getSprite().getBoundingRectangle().contains(getPosition())) {
                ship.setX(Gdx.graphics.getWidth() + 100);
                ship.setY(MathUtils.random(0, Gdx.graphics.getHeight() - ship.getSprite().getHeight()));
                ship.setSpeed(MathUtils.random(0.9f, 7.0f));
                numberOfHits++;
            }
        }
        Gdx.graphics.setTitle("Спрайтов подбито: " + numberOfHits);
    }

    private void fillSpaceShips() {
        for (int i = 0; i < COUNT_SPACE_SHIPS; i++) {
            spaceShips.add(new SpaceShip());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}