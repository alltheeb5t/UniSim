package io.github.alltheeb5t.unisim;

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

import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;
import io.github.alltheeb5t.unisim.entities.LibGdxRenderingEntity;
import io.github.alltheeb5t.unisim.factories.BuildingFactory;
import io.github.alltheeb5t.unisim.systems.BuildingSystem;
import io.github.alltheeb5t.unisim.systems.CampusMapSystem;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;

public class GUI {
    private Stage stage;
    public Stage getStage() {
        return stage;
    }
    private Skin skin;

    private Table table;
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

    public GUI() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.bottom);

        table.setPosition(0, 0);

        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("test_button.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("test_button_pressed.png"))));

        cateringButton = new ImageButton(style);
        accomButton = new ImageButton(style);
        studyButton = new ImageButton(style);
        funButton = new ImageButton(style);
        cateringCounter = new Label(caterNum, skin);
        accomCounter = new Label(accomNum, skin);
        studyCounter = new Label(studyNum, skin);
        funCounter = new Label(funNum, skin);

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

        table.add(cateringButton);
        table.add(accomButton);
        table.add(studyButton);
        table.add(funButton);
        table.row();
        table.add(cateringCounter);
        table.add(accomCounter);
        table.add(studyCounter);
        table.add(funCounter);
        
        stage.addActor(table);
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
        stage.draw();
    }
}