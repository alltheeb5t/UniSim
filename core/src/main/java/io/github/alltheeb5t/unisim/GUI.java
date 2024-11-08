package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.systems.BuildingSystem;

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

    public GUI() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.bottom);

        table.setPosition(0, 0);

        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button.png"))));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("building_button_pressed.png"))));

        cateringButton = new ImageButton(style);
        accomButton = new ImageButton(style);
        studyButton = new ImageButton(style);
        funButton = new ImageButton(style);
        cateringCounter = new Label(String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.CATERING, GameScreen.getBuildings())), skin);
        accomCounter = new Label(String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ACCOMMODATION, GameScreen.getBuildings())), skin);
        studyCounter = new Label(String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.STUDY, GameScreen.getBuildings())), skin);
        funCounter = new Label(String.valueOf(BuildingSystem.getBuildingCount(StructureTypeComponent.ENTERTAINMENT, GameScreen.getBuildings())), skin);

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
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setWidth(width);
    }
}