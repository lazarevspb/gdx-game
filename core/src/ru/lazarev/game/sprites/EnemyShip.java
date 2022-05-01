package ru.lazarev.game.sprites;

import static ru.lazarev.game.utils.GfxUtils.getAngle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.lazarev.game.utils.GfxUtils;

public class EnemyShip {

    private final Sprite sprite;
    private float x;
    private float y;
    private float speed;

    @SuppressWarnings("unused")
    public float getSpeed() {
        return speed;
    }

    public EnemyShip() {
        TextureAtlas textureAtlas = new TextureAtlas("img/atlas/main.atlas");
        this.sprite = new Sprite(textureAtlas.findRegion("msTurret"));
        float speedRandom = MathUtils.random(0.9f, 7.0f);
        if (Gdx.graphics.getHeight() > 800) {
            speedRandom = speedRandom * 4;
        }
//        speed = speedRandom;
        speed = speedRandom/4; //временно, иначе быстро пролетают по экрану

        x = Gdx.graphics.getWidth() + 100;
        y = MathUtils.random(0, Gdx.graphics.getHeight() - sprite.getHeight());
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void render(SpriteBatch batch) {
        Vector2 headOrigin = new Vector2(sprite.getWidth() / 2, sprite.getRegionHeight() >> 1);
        sprite.setOrigin(headOrigin.x, headOrigin.y);
        sprite.setScale(1);
        x -= speed;
        Vector2 headPosition = new Vector2(x, y);
        sprite.setPosition(headPosition.x - headOrigin.x, headPosition.y - headOrigin.y);
        sprite.setRotation(getAngle(headPosition) + 90);
        if (Gdx.graphics.getHeight() > 800) {
            this.sprite.setScale(3f);
        }
        sprite.draw(batch);
    }

    public boolean isDamage() {
        return this.getSprite().getBoundingRectangle().contains(GfxUtils.getCursorPosition());
    }

    public void reuse() {
        this.setX(Gdx.graphics.getWidth() + 100);
        this.setY(MathUtils.random(0, Gdx.graphics.getHeight() - this.getSprite().getHeight()));
        float speedRandom = MathUtils.random(0.9f, 7.0f);
        if (Gdx.graphics.getHeight() > 800) {
            speedRandom = speedRandom * 4;
        }
        this.setSpeed(speedRandom);
    }


    private boolean isGoingOffScreen(float x) {
        return x - 10 <= 0;
    }

    public boolean isGoingOffScreen() {
        return isGoingOffScreen(this.x);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
