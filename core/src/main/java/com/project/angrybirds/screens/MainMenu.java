package com.project.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.angrybirds.screens.GameStateManager;
import java.util.HashMap;


public class MainMenu implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Skin skin;
    private MainGame game;

    private TextButton resumeButton;
    private TextButton newGameButton;
    private TextButton exitButton;

    public MainMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background setup
        backgroundTexture = new Texture(Gdx.files.internal("mmb.png"));
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

        // Skin setup
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Buttons setup
        resumeButton = new TextButton("Restore Game", skin);
        resumeButton.setPosition(900, 480);
        resumeButton.setSize(150, 40);
        stage.addActor(resumeButton);

        newGameButton = new TextButton("New Game", skin);
        newGameButton.setPosition(900, 420);
        newGameButton.setSize(150, 40);
        stage.addActor(newGameButton);

        exitButton = new TextButton("Exit Game", skin);
        exitButton.setPosition(900, 360);
        exitButton.setSize(150, 40);
        stage.addActor(exitButton);

        // Add listeners to buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Load game state
                HashMap<String, Object> gameState = GameStateManager.loadGame();

                if (gameState != null && gameState.containsKey("levelNumber")) {
                    int savedLevel = (int) gameState.get("levelNumber");
                    System.out.println("Restoring game at level " + savedLevel);
                    game.switchToLevelScreen(); // Switch to the saved level
                } else {
                    System.out.println("No saved game found or invalid save data.");
                }
            }
        });

//        resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                int savedLevel = game.getGameStateManager().loadSavedLevel(); // Retrieves the saved level number
//
//                if (savedLevel == 1) {
//                    game.switchToLevel1Screen();
//                } else if (savedLevel == 2) {
//                    game.switchToLevel2Screen();
//                } else if (savedLevel == 3) {
//                    game.switchToLevel3Screen();
//                } else {
//                    System.out.println("No saved game found or invalid level.");
//                }
//            }
//        });


        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.switchToLevelScreen(); // Start a new game at level 1
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}
