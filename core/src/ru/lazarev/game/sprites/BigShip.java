package ru.lazarev.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class BigShip extends BaseEnemy{
    private List<BaseEnemy> turrets;
    private int turretToDamage;

    public BigShip(String name, float speed, float health) {
        super(name, speed, health);
        turretToDamage = -1;
        turrets = new ArrayList<>();
        turrets.add(new BaseEnemy("msTurret", speed, 10));
        Vector2 tmpPos = new Vector2(position.x+140.0f, position.y + (skin.getHeight()-50.0f));
        turrets.get(turrets.size()-1).setPosition(tmpPos);

//        turrets.add(new BaseEnemy("msSmallGun", speed, 10));
//        turrets.add(new BaseEnemy("msSmallGun", speed, 10));
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < turrets.size(); i++) {
            turrets.get(i).draw(batch);
        }
    }

    @Override
    public void step() {
        super.step();
        for (int i = 0; i < turrets.size(); i++) {
            turrets.get(i).step();
            turrets.get(i).setRotate(turrets.get(i).position);
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
        if (turretToDamage>=0) {
            if (turrets.get(turretToDamage).damage(damage)<0) {
                turrets.remove(turretToDamage);
            }
            turretToDamage = -1;
            return health;
        } else return super.damage(damage);
    }
}
