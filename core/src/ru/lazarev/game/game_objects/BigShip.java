package ru.lazarev.game.game_objects;

import static ru.lazarev.game.utils.GfxUtils.getAngle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class BigShip extends BaseEnemy {

  private final List<BaseEnemy> turrets;
  private int turretToDamage;


  public BigShip(String name, float speed, float health) {
    super(name, speed, health);
    turretToDamage = -1;
    turrets = new ArrayList<>();
    getAdditionalWeapons();
  }

  public BigShip(String name, float speed, float health, int column, int lines, int fps) {
    super(name, speed, health, column, lines, fps);
    turretToDamage = -1;
    turrets = new ArrayList<>();
    turrets.add(new BaseEnemy("msSmallGun", speed, 10, 4, 1, 16));
    turrets.get(turrets.size() - 1)
        .setPosition(new Vector2(position.x + 150.0f, position.y + (sprite.getHeight() - 35.0f)));
    turrets.get(turrets.size() - 1).setOrigine(new Vector2(7, 12));
    turrets.get(turrets.size() - 1).setDif(0);
    turrets.get(turrets.size() - 1).setDamage(0.01f);

    turrets.add(new BaseEnemy("msSmallGun", speed, 10, 4, 1, 16));
    turrets.get(turrets.size() - 1).setPosition(
        new Vector2(position.x + 150.0f + 45, position.y + (sprite.getHeight() - 35.0f)));
    turrets.get(turrets.size() - 1).setOrigine(new Vector2(7, 12));
    turrets.get(turrets.size() - 1).setDif(0);
    turrets.get(turrets.size() - 1).setDamage(0.01f);

    turrets.add(new BaseEnemy("msTurret", speed, 10, 3, 1, 6));
    turrets.get(turrets.size() - 1).setPosition(
        new Vector2(position.x + 150.0f + 80, position.y + (sprite.getHeight() - 50.0f)));
    turrets.get(turrets.size() - 1).setOrigine(new Vector2(16, 21));
    turrets.get(turrets.size() - 1).setDamage(0.1f);
  }

  private void getAdditionalWeapons() {
    float x = 130.0f;
    for (int i = 0; i < 3; i++) {
      turrets.add(new BaseEnemy("msTurret", speed, 1, 25, 21));
      x = i == 0 ? x : x + 45.0f;
      turrets.get(i)
          .setPosition(new Vector2(position.x + x, position.y + (sprite.getHeight() - 50.0f)));
    }
  }

  @Override
  public void draw(SpriteBatch batch) {
    super.draw(batch);
    for (BaseEnemy turret : turrets) {
      turret.draw(batch);
    }
  }

  @Override
  public void step() {
    super.step();
    for (BaseEnemy turret : turrets) {
      turret.step();
      turret.sprite.setRotation(getAngle(turret.getPosition()) + 90);
    }
  }

  @Override
  public boolean isHit(Vector2 pos) {
    for (int i = 0; i < turrets.size(); i++) {
      if (turrets.get(i).isHit(pos)) {
        turretToDamage = i;
        return true;
      }
    }
    return super.isHit(pos);
  }

  @Override
  public float damage(float damage) {
    if (turretToDamage >= 0) {
      if (turrets.get(turretToDamage).damage(damage) < 0) {
        turrets.remove(turretToDamage);
      }
      turretToDamage = -1;
      return health;
    } else {
      return super.damage(damage);
    }
  }
}
