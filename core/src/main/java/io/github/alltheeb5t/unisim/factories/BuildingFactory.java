package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;

public class BuildingFactory {
        /**
     * Generates a new building object.
     * Buildings consist of images and bounding boxes (like other structures) but also a name and type.
     * Current implementation only has a single building of each type.
     * @param x Centre point X
     * @param y Centre point Y
     * @param typeComponent Image and bounding box properties will depend on the type of building created.
     * @return
     */
    public static BuildingEntity makeMapBuilding(float x, float y, StructureTypeComponent typeComponent) {
        
        StructureNameComponent newBuildingName;
        Image imageComponent;
        BoundingBoxComponent boundingBoxComponent;

        switch (typeComponent) {
            case CATERING:
                newBuildingName = new StructureNameComponent("Roger Kirk Centre");
                imageComponent = ImageComponentFactory.makeImageComponent(x, y, 120, new Texture("roger_kirk.png"));
                boundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(imageComponent);
                break;
            case ACCOMMODATION:
                newBuildingName = new StructureNameComponent("John West Taylor Court");
                imageComponent = ImageComponentFactory.makeImageComponent(x, y, 60, new Texture("john_west_taylor_court.png"));
                boundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(imageComponent);
                break;
            case ENTERTAINMENT:
                newBuildingName = new StructureNameComponent("York Barbican");
                imageComponent = ImageComponentFactory.makeImageComponent(x, y, 150, new Texture("york_barbican.png"));
                boundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(imageComponent);
                break;
            case STUDY:
                newBuildingName = new StructureNameComponent("Law & Management Building");
                imageComponent = ImageComponentFactory.makeImageComponent(x, y, 100, new Texture("law_and_management.png"));
                boundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(imageComponent);
                break;
            default:
                newBuildingName = new StructureNameComponent("Roger Kirk Centre");
                imageComponent = ImageComponentFactory.makeImageComponent(x, y, 120, new Texture("roger_kirk.png"));
                boundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(imageComponent);
        }

        return new BuildingEntity(boundingBoxComponent, imageComponent, new SatisfactionComponent(), typeComponent, newBuildingName);
    }
}
