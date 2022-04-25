package ru.lazarev.game.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class TargetPointer {
    private final ShapeRenderer shapeRenderer;

    public TargetPointer() {
        shapeRenderer = new ShapeRenderer();
        mouseRectangle = new Rectangle();
    }

    private final Rectangle mouseRectangle;

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(GfxUtils.getCursorPosition().x - 10, GfxUtils.getCursorPosition().y, GfxUtils.getCursorPosition().x + 10, GfxUtils.getCursorPosition().y);
        shapeRenderer.line(GfxUtils.getCursorPosition().x, GfxUtils.getCursorPosition().y - 10, GfxUtils.getCursorPosition().x, GfxUtils.getCursorPosition().y + 10);
        mouseRectangle.set(GfxUtils.getCursorPosition().x - 10, GfxUtils.getCursorPosition().y - 10, 20, 20);
        shapeRenderer.rect(mouseRectangle.x, mouseRectangle.y, mouseRectangle.getWidth(), mouseRectangle.getHeight());
        shapeRenderer.end();
    }
}
