package io.github.alltheeb5t.unisim.map_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class MapBuilding extends MapObstruction {
    public MapBuilding(float x, float y, float width, float height, World world, Texture texture) {
        super(x, y, width, height, world, texture);
    }

    public MapBuilding(float x, float y, float width, World world, Texture texture) {
        super(x, y, width, world, texture);
    }
}
