package io.github.alltheeb5t.unisim;

import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;
import io.github.alltheeb5t.unisim.entities.CampusMapEntity;
import io.github.alltheeb5t.unisim.factories.BuildingFactory;
import io.github.alltheeb5t.unisim.systems.BuildingSystem;
import io.github.alltheeb5t.unisim.systems.CampusMapSystem;
import io.github.alltheeb5t.unisim.systems.GameTimerSystem;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;
import io.github.alltheeb5t.unisim.systems.SatisfactionSystem;

public class GUI {
    private Stage stage;
    public Stage getStage() {
        return stage;
    }
    private Skin skin;

    public Stack cateringButton;
    private Stack accomButton;
    private Stack studyButton;
    private Stack funButton;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private Table buttonTable;
    private Table statTable;

    private Label cateringLabel;
    private Label accomLabel;
    private Label studyLabel;
    private Label funLabel;

    private Label cateringCounter;
    private Label accomCounter;
    private Label studyCounter;
    private Label funCounter;

    private String caterNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.CATERING, GameScreen.getBuildings()));
    private String accomNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ACCOMMODATION, GameScreen.getBuildings()));
    private String studyNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.STUDY, GameScreen.getBuildings()));
    private String funNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ENTERTAINMENT, GameScreen.getBuildings()));
    private DragAndDrop dragAndDrop;

    private Label timer;
    private Label satisfaction;

    private Image tutorial;
    private Image overlay;

    private String timerText = GameTimerSystem.timeDisplay(GameScreen.getGameTimer());
    private String satisfactionText = String.valueOf(df.format(SatisfactionSystem.getAverageSatisfaction(CampusMapEntity.getSatisfactionComponents())));

    public GUI() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        dragAndDrop = new DragAndDrop();

        // Creates the buttonTable and positions it in the bottom middle
        buttonTable = new Table();
        buttonTable.setWidth(stage.getWidth());
        buttonTable.align(Align.center|Align.bottom);
        buttonTable.setPosition(0, 0);

        // Creates the statTable and positions it in the top right
        statTable = new Table();
        statTable.setWidth(stage.getWidth());
        statTable.align(Align.right|Align.top);
        statTable.setPosition(0, Gdx.graphics.getHeight());
   
        // ─── Adding functionality to the buttons ────────────────────────
        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button_pressed.png"))));

        cateringButton = new Stack();
        cateringButton.add(new ImageButton(style));
        registerButtonDraggable(cateringButton, StructureTypeComponent.CATERING);
        
        accomButton = new Stack();
        accomButton.add(new ImageButton(style));
        registerButtonDraggable(accomButton, StructureTypeComponent.ACCOMMODATION);
        

        studyButton = new Stack();
        studyButton.add(new ImageButton(style));
        registerButtonDraggable(studyButton, StructureTypeComponent.STUDY);

        funButton = new Stack();
        funButton.add(new ImageButton(style));
        registerButtonDraggable(funButton, StructureTypeComponent.ENTERTAINMENT);

        // ─── Creating items to display ────────────────────────
        cateringLabel = new Label("Catering", skin);
        accomLabel = new Label("Accomodation", skin);
        studyLabel = new Label("Study", skin);
        funLabel = new Label("Recreation", skin);

        cateringCounter = new Label(caterNum, skin);
        accomCounter = new Label(accomNum, skin);
        studyCounter = new Label(studyNum, skin);
        funCounter = new Label(funNum, skin);

        tutorial = new Image(new TextureRegion(new Texture(Gdx.files.internal("tutorial.png"))));
        tutorial.setScale((float) 0.5);
        tutorial.setPosition(((Gdx.graphics.getWidth() / 2) - tutorial.getWidth() / 4), ((Gdx.graphics.getHeight() / 2) - tutorial.getHeight() / 4));

        overlay = new Image(new TextureRegion(new Texture(Gdx.files.internal("dark_overlay.png"))));
        overlay.setWidth(Gdx.graphics.getWidth());
        overlay.setHeight(Gdx.graphics.getHeight());
        overlay.setPosition(0, 0);


        tutorial.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tutorial.remove();
                overlay.remove();
                GameTimerSystem.toggleTimer(GameScreen.getGameTimer());
            }
        });

        overlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tutorial.remove();
                overlay.remove();
                GameTimerSystem.toggleTimer(GameScreen.getGameTimer());
            }
        });

        // ─── Formatting the buttonTable ────────────────────────
        buttonTable.add(cateringLabel);
        buttonTable.add(accomLabel);
        buttonTable.add(studyLabel);
        buttonTable.add(funLabel);
        buttonTable.row();
        buttonTable.add(cateringButton).width(96);
        buttonTable.add(accomButton).width(96);
        buttonTable.add(studyButton).width(96);
        buttonTable.add(funButton).width(96);
        buttonTable.row();
        buttonTable.add(cateringCounter);
        buttonTable.add(accomCounter);
        buttonTable.add(studyCounter);
        buttonTable.add(funCounter);

        timer = new Label(timerText, skin);
        timer.setFontScale(2);

        satisfaction = new Label(satisfactionText, skin);
        satisfaction.setFontScale(2);

        // ─── Formatting the statTable ────────────────────────
        statTable.padTop(5);
        statTable.add(satisfaction).padRight(20);
        statTable.add(timer).padRight(10);

        
        stage.addActor(buttonTable);
        stage.addActor(statTable);
        stage.addActor(overlay);
        stage.addActor(tutorial);
    }

    public void render() {
        caterNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.CATERING, GameScreen.getBuildings()));
        accomNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ACCOMMODATION, GameScreen.getBuildings()));
        studyNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.STUDY, GameScreen.getBuildings()));
        funNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ENTERTAINMENT, GameScreen.getBuildings()));

        cateringCounter.setText(caterNum);
        accomCounter.setText(accomNum);
        studyCounter.setText(studyNum);
        funCounter.setText(funNum);

        timerText = GameTimerSystem.timeDisplay(GameScreen.getGameTimer());
        satisfactionText = String.valueOf(df.format(SatisfactionSystem.getAverageSatisfaction(CampusMapEntity.getSatisfactionComponents())));

        if (Float.valueOf(satisfactionText) < 50) {
            satisfaction.setColor(255, 0, 0, 255);
        } else {
            satisfaction.setColor(255, 255, 255, 255);
        }

        timer.setText(timerText);
        satisfaction.setText(satisfactionText);
        
        stage.draw();
    }

    // Ensures the tables stay in place if the resolution changes
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        buttonTable.setWidth(width);
        statTable.setHeight(0);
        statTable.setWidth(width);
        statTable.setPosition(0, Gdx.graphics.getHeight());
        tutorial.setPosition(((Gdx.graphics.getWidth() / 2) - tutorial.getWidth() / 4), ((Gdx.graphics.getHeight() / 2) - tutorial.getHeight() / 4));
        overlay.setWidth(Gdx.graphics.getWidth());
        overlay.setHeight(Gdx.graphics.getHeight());
        overlay.setPosition(0, 0);
    }

    /**
     * Used to automatically perform all the necessary setup to create a draggable building object on top of a button
     * @param buttonStack
     * @param buildingType
     */
    public void registerButtonDraggable(Stack buttonStack, StructureTypeComponent buildingType) {
        // placementBuildingEntity is never added to the map, its final coordinates are used to spawn a new building and then it is returned to the button
        BuildingEntity placementBuildingEntity = BuildingFactory.makeMapBuilding(-1,-1, buildingType);
        buttonStack.add(placementBuildingEntity.getImageComponent());

        dragAndDrop.addSource(new DragAndDrop.Source(placementBuildingEntity.getImageComponent()) {
            @Override
			public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(getActor());
                stage.addActor(placementBuildingEntity.getImageComponent());
                
                // If you are, for example, zoomed in a long way. The building on the map must be much larger than its icon on the button
                float zoom = GameScreen.getLibGdxRenderingEntity().getCamera().zoom;
                placementBuildingEntity.getImageComponent().setSize(placementBuildingEntity.getBoundingBoxComponent().getRectangularBoundingBox().getWidth()/zoom,
                placementBuildingEntity.getBoundingBoxComponent().getRectangularBoundingBox().getHeight()/zoom);

                // Centre mouse pointer on the building being dragged
                dragAndDrop.setDragActorPosition(getActor().getWidth() / 2, -getActor().getHeight() / 2); 
                return payload;
			}

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                // The placementBuildingEntity resides entirely in the ScreenViewport-based stage. The coordinates on this stage must thus be converted to
                // the equivalent coordinates on the main Map stage 

                Vector3 GUIStageCoords = stage.getCamera().unproject(new Vector3(
                    placementBuildingEntity.getImageComponent().getX(),
                    placementBuildingEntity.getImageComponent().getY(),
                    0
                ));

                Vector3 CampusMapCoords = GameScreen.getLibGdxRenderingEntity().getCamera().unproject(GUIStageCoords);

                // We can leave the image component at the coordinates it has been dragged to but set the bounding box to represent its effective position on
                // campus map. This way, the collision detection will work as intended
                placementBuildingEntity.getBoundingBoxComponent().getRectangularBoundingBox().setPosition(CampusMapCoords.x, CampusMapCoords.y);
                
                // Turn image red if a collision is detected
                if (!CampusMapSystem.isPlacementAllowed(GameScreen.getCampusMap(), placementBuildingEntity)) {
                    placementBuildingEntity.getImageComponent().setColor(255, 0, 0, 255);
                } else {
                    placementBuildingEntity.getImageComponent().setColor(255, 255, 255, 255);
                }
            }

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                // If the user stops dragging in a valid position, a new building is generated and added to the map
				if (CampusMapSystem.isPlacementAllowed(GameScreen.getCampusMap(), placementBuildingEntity)) {
                    // The coordinates of the bounding box represent an accurate position on the campus map as described above
                    // Using these negates the requirement to recalculate from the image coordinates
                    Rectangle finalPosBoundingBox = placementBuildingEntity.getBoundingBoxComponent().getRectangularBoundingBox();

                    BuildingEntity newBuildingEntity = BuildingFactory.makeMapBuilding(finalPosBoundingBox.x+finalPosBoundingBox.width/2,finalPosBoundingBox.y+finalPosBoundingBox.height/2, buildingType);
                    BuildingSystem.updatePlacementPosition(newBuildingEntity); // Mark the image as having been assigned a valid placement

                    CampusMapSystem.addBuildingToMap(GameScreen.getCampusMap(), newBuildingEntity.getBoundingBoxComponent(), newBuildingEntity.getImageComponent(), newBuildingEntity.getSatisfactionComponent());
                    GameScreen.getLibGdxRenderingEntity().getStage().addActor(newBuildingEntity.getImageComponent());
                    MapInputSystem.registerDraggableObstruction(GameScreen.getLibGdxRenderingEntity(), newBuildingEntity, GameScreen.getCampusMap());
                    GameScreen.getBuildings().add(newBuildingEntity);
                }

                // Reset draggable element colour and return it to button
                placementBuildingEntity.getImageComponent().setColor(255, 255, 255, 255);
                buttonStack.add(placementBuildingEntity.getImageComponent());
                placementBuildingEntity.getImageComponent().setScaling(Scaling.fit);


			}
        });
    }
}