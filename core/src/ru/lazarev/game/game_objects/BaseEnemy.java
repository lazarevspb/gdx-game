package ru.lazarev.game.game_objects;


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

  protected Vector2 position;
  protected Vector2 rotate;
  protected Vector2 origine;
  protected float health;
  protected float speed;
  protected float damage;
  protected Sprite sprite;
  protected boolean isDamaged;

  public BaseEnemy(String name, float speed, float health) {

    isDamaged = false;
    sprite = mainAtlas.createSprite(name);
    position = new Vector2();
    position.x = Gdx.graphics.getWidth();
    position.y = MathUtils.random(0, Gdx.graphics.getHeight() - sprite.getHeight());
    sprite.setPosition(position.x, position.y);
    origine = new Vector2(sprite.getWidth() / 2, sprite.getRegionHeight() >> 1);
    sprite.setOrigin(origine.x, origine.y);

    this.speed = speed;
    this.health = health;

    Vector2 headOrigin = new Vector2(sprite.getWidth() / 2, sprite.getRegionHeight() >> 1);
    Vector2 headPosition = new Vector2(position.x, position.y);
    sprite.setOrigin(headOrigin.x, headOrigin.y);
    sprite.setScale(1);
    sprite.setPosition(headPosition.x - headOrigin.x, headPosition.y - headOrigin.y);

  }

  public BaseEnemy(String name, float speed, float health, float originX, float originY) {
    this(name, speed, health);
    sprite.setOrigin(originX, originY);

  }

  public float damage(float damage) {
    isDamaged = true;
    health -= damage;
    return health;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setRotate(Vector2 position) {
    sprite.rotate(getAngle(position) + 90);
  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public void setOrigine(Vector2 origine) {
    this.origine = origine;
  }

  public void step() {
    position.x -= speed;
    sprite.setPosition(position.x, position.y);
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public boolean isHit(Vector2 pos) {
    Rectangle tRect = sprite.getBoundingRectangle();
    return tRect.contains(pos);
  }

  public void draw(SpriteBatch batch) {
    if (isDamaged) {
      isDamaged = false;
      sprite.setColor(Color.RED);
    } else {
      sprite.setColor(Color.WHITE);
    }
    sprite.draw(batch);
  }
}
