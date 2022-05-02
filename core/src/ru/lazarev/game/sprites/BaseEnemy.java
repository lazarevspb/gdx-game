package ru.lazarev.game.sprites;


import static ru.lazarev.game.screens.StageTwo.mainAtlas;
import static ru.lazarev.game.utils.GfxUtils.getAngle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BaseEnemy {
    protected Vector2 position, origine, rotate;
    protected float health, speed, damage;
    protected Sprite skin;
    protected boolean isDamaged;

    public BaseEnemy(String name, float speed, float health){
        isDamaged = false;
        skin = mainAtlas.createSprite(name);
        position = new Vector2();
        position.x = Gdx.graphics.getWidth();
        position.y = MathUtils.random(0, Gdx.graphics.getHeight()-skin.getHeight());
        skin.setPosition(position.x, position.y);
        origine = new Vector2(skin.getWidth()/2, skin.getRegionHeight()/2);
        skin.setOrigin(origine.x, origine.y);
        skin.setScale(1);
        this.speed = speed;
        this.health = health;
    }

    public float getDamage() {
        return damage;
    }

    public float damage(float damage){
        isDamaged = true;
        health -= damage;
        return health;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setRotate(Vector2 pos){
        skin.rotate(getAngle(pos));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setOrigine(Vector2 origine) {
        this.origine = origine;
    }

    public void step(){
        position.x -= speed;
        skin.setPosition(position.x, position.y);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isHit(Vector2 pos){
        Rectangle tRect = skin.getBoundingRectangle();
        return  tRect.contains(pos);
    }

    public void draw(SpriteBatch batch){
        if (isDamaged) {
            isDamaged = false;
            skin.setColor(Color.RED);
        } else skin.setColor(Color.WHITE);
        skin.draw(batch);
    }
}
