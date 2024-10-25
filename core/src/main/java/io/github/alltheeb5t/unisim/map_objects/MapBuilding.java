package io.github.alltheeb5t.unisim.map_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MapBuilding extends Image {

    protected float x, y, width, height;
    protected Body body;
    protected Texture buildingTexture;

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public MapBuilding(float x, float y, float width, float height, World world, Texture texture) {
        super(texture);
        setSize(width,height);
        setPosition(x-width/2, y-height/2);
        body = createBody(x, y, width, height, false, world);
        this.width = width;
        this.height = height;
    }

    public MapBuilding(float x, float y, float width, World world, Texture texture) {
        this(x, y, width, width/(texture.getWidth()/texture.getHeight()), world, texture);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    public Body getBody() {
        return body;
    }
}
