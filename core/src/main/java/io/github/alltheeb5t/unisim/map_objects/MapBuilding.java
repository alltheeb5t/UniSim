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

    /**
     * Generates a new body object. 
     * Method taken from this (https://www.youtube.com/watch?v=6QKhSctuMcs) helpful tutorial by 'Small Pixel Games' on YouTube
     * @param x
     * @param y
     * @param width
     * @param height
     * @param isStatic
     * @param world
     * @return
     */
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
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

    /**
     * Constructor allowing specification of arbitrary width and height parameters.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param world
     * @param texture
     */
    public MapBuilding(float x, float y, float width, float height, World world, Texture texture) {
        super(texture);
        
        body = createBody(x, y, width, height, false, world);
        this.width = width;
        this.height = height;

        setSize(width,height);
        setPosition(x-width/2, y-height/2);  
    }

    /**
     * Constructor uses the ratio of the image file to define height
     * @param x
     * @param y
     * @param width
     * @param world
     * @param texture
     */
    public MapBuilding(float x, float y, float width, World world, Texture texture) {
        this(x, y, width, width/(texture.getWidth()/texture.getHeight()), world, texture);
    }

    /**
     * Called internally by the drag handler. Overridden method moves Box2D Body to same position
     */
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        body.setTransform(x+width/2, y+height/2, 0);
        System.out.println("Debug X: "+x+" Y: "+y);
    }
}
