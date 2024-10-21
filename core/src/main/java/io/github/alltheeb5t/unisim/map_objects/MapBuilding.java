package io.github.alltheeb5t.unisim.map_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MapBuilding {

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

    public MapBuilding(float x, float y, float width, float height, World world) {
        body = createBody(x, y, width, height, false, world);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        buildingTexture = new Texture("piazza.png");
    }

    /**
     * Called by main render method to render this specific building
     * @param batch
     */
    public void render(SpriteBatch batch) {
        // The x and y coordinates represent the centre of an object
        batch.draw(buildingTexture, x-(width/2), y-(height/2), width, height);
    }
}
