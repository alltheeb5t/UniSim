package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.CampusMap;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;
import io.github.alltheeb5t.unisim.map_objects.MapObstruction;
import io.github.alltheeb5t.unisim.systems.BuildingSystem;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;

public class MapObstructionFactory {
    /**
     * Generate a generic map obstruction. Generic obstructions have collision boxes but cannot be dragged.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param world
     * @param texture
     * @return
     */
    public static MapObstruction makeMapObstruction(float x, float y, float width, float height, World world, Texture texture) {
        Body newObjectBody = BodyFactory.createBody(x, y, width, height, false, world);
        
        Image newObjectImageObject = new Image(texture);
        newObjectImageObject.setPosition(x-width/2, y-height/2);
        newObjectImageObject.setSize(width, height);
        
        MapObstruction newMapObstruction = new MapObstruction(x, y, width, height, newObjectImageObject, newObjectBody);

        BuildingSystem.syncBodyPosition(newMapObstruction);

        return newMapObstruction;

    }

    /**
     * Makes a new map Obstruction where the height is determined based on aspect ratio of input image
     * @param x
     * @param y
     * @param width
     * @param world
     * @param texture
     * @return
     */
    public static MapObstruction makeMapObstruction(float x, float y, float width, World world, Texture texture) {
        return makeMapObstruction(x, y, width, width/(1.0f*texture.getWidth()/texture.getHeight()), world, texture);
    }
    
    /**
     * Generates a new building object. Buildings still have an Obstruction object but they allow this object to be dragged around
     * @param mapObstructionFootprint
     * @param campusMap
     * @return
     */
    public static MapBuilding makeMapBuilding(float x, float y, StructureTypeComponent type, SatisfactionComponent satisfaction, CampusMap campusMap) {
        
        StructureNameComponent newBuildingName;
        MapObstruction mapObstructionFootprint;

        switch (type) {
            case CATERING:
                newBuildingName = new StructureNameComponent("Piazza");
                mapObstructionFootprint = makeMapObstruction(x, y, 120, campusMap.getWorld(), new Texture("piazza.png"));
                break;
            case ACCOMMODATION:
                newBuildingName = new StructureNameComponent("John West Taylor Court");
                mapObstructionFootprint = makeMapObstruction(x, y, 60, campusMap.getWorld(), new Texture("john_west_taylor_court.png"));
                break;
            default:
                newBuildingName = new StructureNameComponent("Piazza");
                mapObstructionFootprint = makeMapObstruction(x, y, 120, campusMap.getWorld(), new Texture("piazza.png"));
        }
        
        campusMap.getStage().addActor(mapObstructionFootprint.getImageObject());
        MapInputSystem.registerDraggableObstruction(campusMap.getDragAndDrop(), campusMap.getStage(), mapObstructionFootprint);

        return new MapBuilding(mapObstructionFootprint, newBuildingName, type, satisfaction);
    }
}
