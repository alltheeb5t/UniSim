package io.github.alltheeb5t.unisim;

import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private Table buttonTable;
    private Table statTable;

    private Label cateringLabel;
    private Label accomLabel;
    private Label studyLabel;
    private Label funLabel;

    private ImageButton cateringButton;
    private ImageButton accomButton;
    private ImageButton studyButton;
    private ImageButton funButton;

    private Label cateringCounter;
    private Label accomCounter;
    private Label studyCounter;
    private Label funCounter;

    private String caterNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.CATERING, GameScreen.getBuildings()));
    private String accomNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ACCOMMODATION, GameScreen.getBuildings()));
    private String studyNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.STUDY, GameScreen.getBuildings()));
    private String funNum = String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ENTERTAINMENT, GameScreen.getBuildings()));

    private Label timer;
    private Label satisfaction;

    private String timerText = GameTimerSystem.timeDisplay(GameScreen.getGameTimer());
    private String satisfactionText = String.valueOf(df.format(SatisfactionSystem.getAverageSatisfaction(CampusMapEntity.getSatisfactionComponents())));

    public GUI() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

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
        
        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button_pressed.png"))));

        // ─── Creating items to display ────────────────────────
        cateringLabel = new Label("Catering", skin);
        accomLabel = new Label("Accomodation", skin);
        studyLabel = new Label("Study", skin);
        funLabel = new Label("Recreation", skin);

        cateringButton = new ImageButton(style);
        accomButton = new ImageButton(style);
        studyButton = new ImageButton(style);
        funButton = new ImageButton(style);

        cateringCounter = new Label(caterNum, skin);
        accomCounter = new Label(accomNum, skin);
        studyCounter = new Label(studyNum, skin);
        funCounter = new Label(funNum, skin);

        timer = new Label(timerText, skin);
        timer.setFontScale(2);

        satisfaction = new Label(satisfactionText, skin);
        satisfaction.setFontScale(2);

        // ─── Adding functionality to the buttons ────────────────────────
        cateringButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BuildingEntity newBuildingEntity = BuildingFactory.makeMapBuilding(x, y + 100, StructureTypeComponent.CATERING);
                CampusMapSystem.addBuildingToMap(GameScreen.getCampusMap(), newBuildingEntity.getBoundingBoxComponent(), newBuildingEntity.getImageComponent(), newBuildingEntity.getSatisfactionComponent());
                GameScreen.getLibGdxRenderingEntity().getStage().addActor(newBuildingEntity.getImageComponent());
                MapInputSystem.registerDraggableObstruction(GameScreen.getLibGdxRenderingEntity(), newBuildingEntity, GameScreen.getCampusMap());
                GameScreen.getBuildings().add(newBuildingEntity);
            }
        });
        accomButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BuildingEntity newBuildingEntity = BuildingFactory.makeMapBuilding(x, y + 100, StructureTypeComponent.ACCOMMODATION);
                CampusMapSystem.addBuildingToMap(GameScreen.getCampusMap(), newBuildingEntity.getBoundingBoxComponent(), newBuildingEntity.getImageComponent(), newBuildingEntity.getSatisfactionComponent());
                GameScreen.getLibGdxRenderingEntity().getStage().addActor(newBuildingEntity.getImageComponent());
                MapInputSystem.registerDraggableObstruction(GameScreen.getLibGdxRenderingEntity(), newBuildingEntity, GameScreen.getCampusMap());
                GameScreen.getBuildings().add(newBuildingEntity);
            }
        });
        studyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BuildingEntity newBuildingEntity = BuildingFactory.makeMapBuilding(x, y + 100, StructureTypeComponent.STUDY);
                CampusMapSystem.addBuildingToMap(GameScreen.getCampusMap(), newBuildingEntity.getBoundingBoxComponent(), newBuildingEntity.getImageComponent(), newBuildingEntity.getSatisfactionComponent());
                GameScreen.getLibGdxRenderingEntity().getStage().addActor(newBuildingEntity.getImageComponent());
                MapInputSystem.registerDraggableObstruction(GameScreen.getLibGdxRenderingEntity(), newBuildingEntity, GameScreen.getCampusMap());
                GameScreen.getBuildings().add(newBuildingEntity);
            }
        });
        funButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BuildingEntity newBuildingEntity = BuildingFactory.makeMapBuilding(x, y + 100, StructureTypeComponent.ENTERTAINMENT);
                CampusMapSystem.addBuildingToMap(GameScreen.getCampusMap(), newBuildingEntity.getBoundingBoxComponent(), newBuildingEntity.getImageComponent(), newBuildingEntity.getSatisfactionComponent());
                GameScreen.getLibGdxRenderingEntity().getStage().addActor(newBuildingEntity.getImageComponent());
                MapInputSystem.registerDraggableObstruction(GameScreen.getLibGdxRenderingEntity(), newBuildingEntity, GameScreen.getCampusMap());
                GameScreen.getBuildings().add(newBuildingEntity);
            }
        });

        // ─── Formatting the buttonTable ────────────────────────
        buttonTable.add(cateringLabel);
        buttonTable.add(accomLabel);
        buttonTable.add(studyLabel);
        buttonTable.add(funLabel);
        buttonTable.row();
        buttonTable.add(cateringButton);
        buttonTable.add(accomButton);
        buttonTable.add(studyButton);
        buttonTable.add(funButton);
        buttonTable.row();
        buttonTable.add(cateringCounter);
        buttonTable.add(accomCounter);
        buttonTable.add(studyCounter);
        buttonTable.add(funCounter);

        // ─── Formatting the statTable ────────────────────────
        statTable.padTop(5);
        statTable.add(satisfaction).padRight(20);
        statTable.add(timer).padRight(10);
        
        stage.addActor(buttonTable);
        stage.addActor(statTable);
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
    }
}