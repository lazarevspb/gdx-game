package ru.lazarev.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.lazarev.game.animation.Explosion;
import ru.lazarev.game.animation.MyAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static ru.lazarev.game.utils.GfxUtils.getAngle;
import static ru.lazarev.game.utils.GfxUtils.getPosition;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private MyAnimation turretAnimation;
    private MyAnimation bodyAnimation;
    private MyAnimation headAnimation;
    private List<Explosion> explosions;
    private TextureAtlas textureAtlas;
    private Rectangle rectangle;
    private Rectangle mouseRectangle;
    private Circle circle;

    private float x;
    private float y;
//    private Sprite headSprite;


    private int count;

    @Override
    public void create() {
        x = Gdx.graphics.getWidth() + 100;
        textureAtlas = new TextureAtlas("img/atlas/main.atlas");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        rectangle = new Rectangle(250, 250, 100, 200);
        mouseRectangle = new Rectangle();
        circle = new Circle(250, 300, 50);
        explosions = new ArrayList<>();
        turretAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-deployment"), Animation.PlayMode.NORMAL, 8, 1, 8);
        bodyAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-body"), Animation.PlayMode.LOOP, 2, 1, 16);
        headAnimation = new MyAnimation(textureAtlas.findRegion("turret-sprites-head-shot-idle"), Animation.PlayMode.NORMAL, 5, 1, 60);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.FOREST);
        boolean fire = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        turrelAttakHandling();
        targetMouse();
        ArrayList<Sprite> sprites = new ArrayList<>();
        for (int i = 0; i < sprites.size(); i++) {
            sprites.add(new Sprite());
        }
        Sprite headSprite = createFlyngSprite();
        flyingSprite(headSprite);

        hitHandling(fire, headSprite);
    }

    private Sprite createFlyngSprite() {
        return new Sprite(textureAtlas.findRegion("msTurret"));
    }

    private void flyingSprite(Sprite headSprite) {
        Vector2 headOrigin = new Vector2(headSprite.getWidth() / 2, headSprite.getRegionHeight() >> 1);
        headSprite.setOrigin(headOrigin.x, headOrigin.y);
        headSprite.setScale(1);
        x -= 0.9f;
        batch.begin();
        Vector2 headPosition = new Vector2(x, y);
        headSprite.setPosition(headPosition.x - headOrigin.x, headPosition.y - headOrigin.y);
        headSprite.setRotation(getAngle(headPosition) + 90);
        headSprite.draw(batch);
        batch.end();

        if (isGoingOffScreen(headPosition.x, headPosition.y)) {
          Gdx.app.exit();
         }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(
                headSprite.getBoundingRectangle().x,
                headSprite.getBoundingRectangle().y,
                headSprite.getBoundingRectangle().getWidth(),
                headSprite.getBoundingRectangle().getHeight());
        shapeRenderer.end();
    }

    private void targetMouse() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
        shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
        mouseRectangle.set(getPosition().x - 10, getPosition().y - 10, 20, 20);
        shapeRenderer.rect(mouseRectangle.x, mouseRectangle.y, mouseRectangle.getWidth(), mouseRectangle.getHeight());
    }

    private void turrelAttakHandling() {
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

    private void hitHandling(boolean fire, Sprite headSprite) {
        if ((fire & !headAnimation.isFinished()) || (!fire & !headAnimation.isFinished()))
            headAnimation.setTime(Gdx.graphics.getDeltaTime());
        if (fire & headAnimation.isFinished()) {
            headAnimation.resetTime();
            explosions.add(new Explosion(textureAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16, "audio/explosion.mp3"));
            if (headSprite.getBoundingRectangle().contains(getPosition())) {
                x = Gdx.graphics.getWidth() + 100;
                y = MathUtils.random(0, Gdx.graphics.getHeight() - headSprite.getHeight());
                count++;
            }
        }
        Gdx.graphics.setTitle("Спрайтов подбито: " + String.valueOf(count));
    }

    private boolean isGoingOffScreen(float x, float y) {
        return x - 10 <= 0;
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}