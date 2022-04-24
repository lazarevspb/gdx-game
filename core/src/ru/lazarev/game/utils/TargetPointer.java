package ru.lazarev.game.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static ru.lazarev.game.utils.GfxUtils.getPosition;

public class TargetPointer {
    private final ShapeRenderer shapeRenderer;

    public TargetPointer() {
        shapeRenderer = new ShapeRenderer();
        mouseRectangle = new Rectangle();
    }

    private final Rectangle mouseRectangle;

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(getPosition().x - 10, getPosition().y, getPosition().x + 10, getPosition().y);
        shapeRenderer.line(getPosition().x, getPosition().y - 10, getPosition().x, getPosition().y + 10);
        mouseRectangle.set(getPosition().x - 10, getPosition().y - 10, 20, 20);
        shapeRenderer.rect(mouseRectangle.x, mouseRectangle.y, mouseRectangle.getWidth(), mouseRectangle.getHeight());
        shapeRenderer.end();
    }
}
