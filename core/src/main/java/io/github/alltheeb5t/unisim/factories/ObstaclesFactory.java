package io.github.alltheeb5t.unisim.factories;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import io.github.alltheeb5t.unisim.CampusMap;
import io.github.alltheeb5t.unisim.map_objects.MapObstacleComponent;
import io.github.alltheeb5t.unisim.map_objects.PlacementRestrictionComponent;

public class ObstaclesFactory {

    /**
     * Generates an obstacle which appears on the map as an orchard.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static MapObstacleComponent makeMapOrchard(float x, float y, CampusMap campusMap) {

        MapObstacleComponent orchard = new MapObstacleComponent();
        int numOfTrees = 17;

        for (int i = 0; i < numOfTrees; i++) {
            Random rand = new Random(); //randomly places each tree in the orchard
            float positionX = rand.nextInt(50) + x - 25;
            float positionY = rand.nextInt(80) + y - 40;
            PlacementRestrictionComponent nextTreeTop = PlacementRestrictionFactory.makePlacementRestrictionComponent(positionX, positionY, 5, campusMap.getWorld(), new Texture("assets\\MapObstacles\\tree_square.png"));
            campusMap.getStage().addActor(nextTreeTop.getImageObject());
            orchard.addAnotherSquare(nextTreeTop);
            PlacementRestrictionComponent nextTreeTrunk = PlacementRestrictionFactory.makePlacementRestrictionComponent(positionX, positionY - 2.5f, 1,2, campusMap.getWorld(), new Texture("assets\\MapObstacles\\tree_trunk_square.png"));
            campusMap.getStage().addActor(nextTreeTrunk.getImageObject());
            orchard.addAnotherSquare(nextTreeTrunk);
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
    public static MapObstacleComponent makeMapRoad(float x, float y, CampusMap campusMap) { 
        
        PlacementRestrictionComponent road = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 15,2000, campusMap.getWorld(), new Texture("assets\\MapObstacles\\road_square.png"));
        campusMap.getStage().addActor(road.getImageObject());
        return new MapObstacleComponent(road);
    }

    /**
     * Generates an obstacle which appears on the map as a lake.
     * @param x  
     * @param y
     * @param campusMap
     * @param round     determines the shape of the lake - larger number is more squewed lake
     * @return
     */
    public static MapObstacleComponent makeMapLake(float x, float y, CampusMap campusMap, int round) {

        MapObstacleComponent lake = new MapObstacleComponent();
        
        int rowNumSquares = 3;
        float newX = x;
        float newY = y;
        for (int row = 0; row < 30; row++) { //30 rows is arbitrary. determines size of the lake
            PlacementRestrictionComponent nextSquare;
            for (int col = 0; col < rowNumSquares; col++) {
                newX += 10;
                nextSquare  =  PlacementRestrictionFactory.makePlacementRestrictionComponent(newX, newY, 10, 5, campusMap.getWorld(), new Texture("assets\\MapObstacles\\lake_square.png"));
                campusMap.getStage().addActor(nextSquare.getImageObject());
                lake.addAnotherSquare(nextSquare); 
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
    public static MapObstacleComponent makeMapMountain(float x, float y, CampusMap campusMap) {

        MapObstacleComponent mountain = new MapObstacleComponent();
        
        int width = 0;
        float newY = y + 5;
        int mountainHeight = 31; //31 blocks each 5 meters in height.
        for (int row = 0; row < mountainHeight; row++) {
            PlacementRestrictionComponent nextLevel;
            newY -= 5;
            width += 10;

            if  (row < 6) { //first 6 rows are snow on top of the mountain.
                nextLevel = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, newY, width,5, campusMap.getWorld(), new Texture("assets\\MapObstacles\\mountain_peak_square.png"));
            }
            else {
                nextLevel = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, newY, width,5, campusMap.getWorld(), new Texture("assets\\MapObstacles\\mountain_square.png"));
            }

            mountain.addAnotherSquare(nextLevel);
            campusMap.getStage().addActor(nextLevel.getImageObject());
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
    public static MapObstacleComponent makeMapRiver(float x, float y, CampusMap campusMap) {

        PlacementRestrictionComponent river = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 10,800, campusMap.getWorld(), new Texture("assets\\MapObstacles\\lake_square.png"));
        campusMap.getStage().addActor(river.getImageObject());
        return new MapObstacleComponent(river);
    }

    /**
     * Generates an obstacle which appears on the map as a bridge.
     * @param x  
     * @param y
     * @param campusMap
     * @return
     */
    public static MapObstacleComponent makeMapBridge(float x, float y, CampusMap campusMap) {

        PlacementRestrictionComponent bridge = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 20,5, campusMap.getWorld(), new Texture("assets\\MapObstacles\\tree_trunk_square.png"));
        campusMap.getStage().addActor(bridge.getImageObject());
        return new MapObstacleComponent(bridge);
    }
}
