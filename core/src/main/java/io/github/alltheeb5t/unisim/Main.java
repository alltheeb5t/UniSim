package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.alltheeb5t.unisim.Constants;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private int screenWidth, screenHeight;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera();

        // Sets to fit-all mode (no zoom) initially
        orthographicCamera.setToOrtho(false, screenWidth, screenHeight);
        
        viewport = new StretchViewport(Constants.CAMPUS_MAX_X, Constants.CAMPUS_MAX_Y, orthographicCamera);
        setScreen(new GameScreen(orthographicCamera, viewport));
    }
}
