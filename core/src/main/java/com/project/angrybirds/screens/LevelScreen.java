package com.project.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture level1UnlockedTexture, level2UnlockedTexture, level3UnlockedTexture;
    ImageButton level1Button;
    ImageButton level2Button;
    ImageButton level3Button;
    TextButton backButton;
    Label playerIdLabel;
    private Skin skin;
    private Texture backgroundTexture;
    private String playerId;

    private MainGame game;

    public LevelScreen(MainGame game, String playerId) {
        this.game = game;
        this.playerId = playerId;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Load textures
        level1UnlockedTexture = new Texture(Gdx.files.internal("level1_unlocked.png"));
        level2UnlockedTexture = new Texture(Gdx.files.internal("level2_unlocked.png"));
        level3UnlockedTexture = new Texture(Gdx.files.internal("level3_unlocked.png"));
        backgroundTexture = new Texture(Gdx.files.internal("l_background2.jpg"));

        // Load UI skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Player ID label
        playerIdLabel = new Label("Player ID: " + playerId, skin);
        playerIdLabel.setPosition(1750, Gdx.graphics.getHeight() - 40);
        stage.addActor(playerIdLabel);

        // Back button
        backButton = new TextButton("Back", skin);
        backButton.setPosition(1750, 50);
        backButton.setSize(120, 40);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToMainMenu();
            }
        });
        stage.addActor(backButton);

        // Level 1 button
        level1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level1UnlockedTexture)));
        level1Button.setPosition(800, 400);
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLevel(1); // Start Level 1
            }
        });
        stage.addActor(level1Button);

        // Level 2 button
        level2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level2UnlockedTexture)));
        level2Button.setPosition(900, 400);
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLevel(2); // Start Level 2
            }
        });
        stage.addActor(level2Button);

        // Level 3 button
        level3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level3UnlockedTexture)));
        level3Button.setPosition(1000, 400);
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLevel(3); // Start Level 3
            }
        });
        stage.addActor(level3Button);
    }

    private void switchToMainMenu() {
        System.out.println("Back button clicked, returning to Main Menu");
        game.switchToMainMenuScreen(); // Replace with your method to switch to the Main Menu screen
    }

    void startLevel(int level) {
        switch (level) {
            case 1:
                System.out.println("Starting Level 1");
                game.switchToLevel1Screen(); // Replace with your method to switch to Level 1
                break;
            case 2:
                System.out.println("Starting Level 2");
                game.switchToLevel2Screen(); // Replace with your method to switch to Level 2
                break;
            case 3:
                System.out.println("Starting Level 3");
                game.switchToLevel3Screen(); // Replace with your method to switch to Level 3
                break;
            default:
                System.out.println("Invalid level");
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
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
        batch.dispose();
        stage.dispose();
        level1UnlockedTexture.dispose();
        level2UnlockedTexture.dispose();
        level3UnlockedTexture.dispose();
        backgroundTexture.dispose();
    }
}
