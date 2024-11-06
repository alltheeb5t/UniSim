package io.github.alltheeb5t.unisim.factories;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.entities.LibGdxRenderingEntity;
import io.github.alltheeb5t.unisim.map_objects.MapObstacleComponent;

public class ObstaclesFactory {

    /**
     * Generates an obstacle which appears on the map as an orchard.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static List<MapObstacleComponent> makeMapOrchard(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity) {

        List<MapObstacleComponent> orchard = new LinkedList<>();
        int numOfTrees = 17;

        for (int i = 0; i < numOfTrees; i++) {
            Random rand = new Random(); //randomly places each tree in the orchard
            float positionX = rand.nextInt(50) + x - 25;
            float positionY = rand.nextInt(80) + y - 40;

            Image nextTreeTopImage = ImageComponentFactory.makeImageComponent(positionX, positionY, 5, new Texture("assets/MapObstacles/tree_square.png"));
            BoundingBoxComponent nextTreeTopBoundingBox = BoundingBoxComponentFactory.makeBoundingBoxComponent(nextTreeTopImage);
            libGdxRenderingEntity.getStage().addActor(nextTreeTopImage);
            orchard.add(new MapObstacleComponent(nextTreeTopBoundingBox, nextTreeTopImage));

            Image nextTreeTrunkImage = ImageComponentFactory.makeImageComponent(positionX, positionY -2.5f, 1, 2, new Texture("assets/MapObstacles/tree_trunk_square.png"));
            BoundingBoxComponent nextTreeTrunkBoundingBox = BoundingBoxComponentFactory.makeBoundingBoxComponent(nextTreeTrunkImage);
            libGdxRenderingEntity.getStage().addActor(nextTreeTrunkImage);
            orchard.add(new MapObstacleComponent(nextTreeTrunkBoundingBox, nextTreeTrunkImage));
        }

        return orchard;
    }

    /**
     * Generates an obstacle which appears on the map as a road.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static MapObstacleComponent makeMapRoad(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity) { 
        
        Image roadImage = ImageComponentFactory.makeImageComponent(x, y, 15, 2000, new Texture("assets/MapObstacles/road_square.png"));
        BoundingBoxComponent roadBoundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(roadImage);
        libGdxRenderingEntity.getStage().addActor(roadImage);
        return new MapObstacleComponent(roadBoundingBoxComponent, roadImage);
    }

    /**
     * Generates an obstacle which appears on the map as a lake.
     * @param x  
     * @param y
     * @param campusMap
     * @param round     determines the shape of the lake - larger number is more skewed lake
     * @return
     */
    public static List<MapObstacleComponent> makeMapLake(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity, int round) {

        List<MapObstacleComponent> lake = new LinkedList<>();
        
        int rowNumSquares = 3;
        float newX = x;
        float newY = y;
        for (int row = 0; row < 30; row++) { //30 rows is arbitrary. determines size of the lake
            for (int col = 0; col < rowNumSquares; col++) {
                newX += 10;
                Image nextLakeImage = ImageComponentFactory.makeImageComponent(newX, newY, 10, 5, new Texture("assets/MapObstacles/lake_square.png"));
                BoundingBoxComponent nextLakeBoundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(nextLakeImage);
                libGdxRenderingEntity.getStage().addActor(nextLakeImage);
                lake.add(new MapObstacleComponent(nextLakeBoundingBoxComponent, nextLakeImage));
            }

            newY -= 5;

            if  (row < 10) {
                newX -= 10 * (rowNumSquares + round);
                rowNumSquares += 2;
            }
            else if (row < 20) {
                newX -= 10 * (rowNumSquares - 1 + round);
            }
            else {
                newX -= 10 * (rowNumSquares - 2 + round);
                rowNumSquares -= 2;
            }
        }

        return lake;
    }

    /**
     * Generates an obstacle which appears on the map as a mountain.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static List<MapObstacleComponent> makeMapMountain(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity) {

        List<MapObstacleComponent> mountain = new LinkedList<>();
        
        int width = 0;
        float newY = y + 5;
        int mountainHeight = 31; //31 blocks each 5 meters in height.
        for (int row = 0; row < mountainHeight; row++) {
            newY -= 5;
            width += 10;

            Image nextLevelImage;

            if  (row < 6) { //first 6 rows are snow on top of the mountain.
                nextLevelImage = ImageComponentFactory.makeImageComponent(x, newY, width, 5, new Texture("assets/MapObstacles/mountain_peak_square.png"));
            }
            else {
                nextLevelImage = ImageComponentFactory.makeImageComponent(x, newY, width, 5, new Texture("assets/MapObstacles/mountain_square.png"));
            }

            BoundingBoxComponent nextLevelBoundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(nextLevelImage);

            mountain.add(new MapObstacleComponent(nextLevelBoundingBoxComponent, nextLevelImage));
            libGdxRenderingEntity.getStage().addActor(nextLevelImage);
        }

        return mountain;
    }

    /**
     * Generates an obstacle which appears on the map as a river.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static MapObstacleComponent makeMapRiver(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity) {

        Image riverImage = ImageComponentFactory.makeImageComponent(x, y, 10, 800, new Texture("assets/MapObstacles/lake_square.png"));
        BoundingBoxComponent riverBoundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(riverImage);
        libGdxRenderingEntity.getStage().addActor(riverImage);
        return new MapObstacleComponent(riverBoundingBoxComponent, riverImage);
    }

    /**
     * Generates an obstacle which appears on the map as a bridge.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static MapObstacleComponent makeMapBridge(float x, float y, LibGdxRenderingEntity libGdxRenderingEntity) {
        Image bridgeImage = ImageComponentFactory.makeImageComponent(x, y, 20, 5, new Texture("assets/MapObstacles/tree_trunk_square.png"));
        BoundingBoxComponent bridgeBoundingBoxComponent = BoundingBoxComponentFactory.makeBoundingBoxComponent(bridgeImage);
        libGdxRenderingEntity.getStage().addActor(bridgeImage);
        return new MapObstacleComponent(bridgeBoundingBoxComponent, bridgeImage);
    }
}
