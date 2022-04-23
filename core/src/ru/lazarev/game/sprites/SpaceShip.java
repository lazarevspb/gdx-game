package ru.lazarev.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.lazarev.game.utils.GfxUtils.getAngle;

public class SpaceShip {

    private final Sprite sprite;
    private float x;
    private float y;
    private float speed;

    public SpaceShip() {
        TextureAtlas textureAtlas = new TextureAtlas("img/atlas/main.atlas");
        this.sprite = new Sprite(textureAtlas.findRegion("msTurret"));
        this.speed = MathUtils.random(0.9f, 5.0f);
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
