//package com.project.angrybirds.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//
//public class LevelScreen implements Screen {
//    private Stage stage;
//    private SpriteBatch batch;
//    private Texture level1UnlockedTexture, level2LockedTexture, level3LockedTexture;
//    private ImageButton level1Button, level2Button, level3Button;
//    private TextButton settingsButton;
//    private Label playerIdLabel;
//    private Skin skin;
//    private Texture backgroundTexture;
//    private boolean isLevel2Unlocked = false;
//    private boolean isLevel3Unlocked = false;
//    private String playerId;
//
//    private MainGame game;
//
//    public LevelScreen(MainGame game, String playerId) {
//        this.game = game;
//        this.playerId = playerId;
//        stage = new Stage(new ScreenViewport());
//        batch = new SpriteBatch();
//        Gdx.input.setInputProcessor(stage);
//
//        // Load textures
//        level1UnlockedTexture = new Texture(Gdx.files.internal("level1_unlocked.png"));
//        level2LockedTexture = new Texture(Gdx.files.internal("level2_unlocked.png"));
//        level3LockedTexture = new Texture(Gdx.files.internal("level3_unlocked.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("l_background2.jpg"));
//
//        // Load UI skin
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//
//        // Player ID label
//        playerIdLabel = new Label("Player ID: " + playerId, skin);
//        playerIdLabel.setPosition(1750, Gdx.graphics.getHeight() - 40);
//        stage.addActor(playerIdLabel);
//
//        // Settings button
//        settingsButton = new TextButton("Settings", skin);
//        settingsButton.setPosition(1750, 50);
//        settingsButton.setSize(120, 40);
//        settingsButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                openSettings();
//            }
//        });
//        stage.addActor(settingsButton);
//
//        // Level 1 button
//        level1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level1UnlockedTexture)));
//        level1Button.setPosition(800, 400);
//        level1Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.switchToLevel3Screen(); // Navigate to Level 1 screen
//            }
//        });
//        stage.addActor(level1Button);
//
//        // Level 2 button
//        level2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level2LockedTexture)));
//        level2Button.setPosition(900, 400);
//        level2Button.setDisabled(true); // Initially disabled
//        level2Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (!isLevel2Unlocked) {
//                    displayMessage("Unlock Level 1 first");
//                } else {
//                    startLevel(2);
//                    unlockLevel(3);
//                }
//            }
//        });
//        stage.addActor(level2Button);
//
//        // Level 3 button
//        level3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(level3LockedTexture)));
//        level3Button.setPosition(1000, 400);
//        level3Button.setDisabled(true); // Initially disabled
//        level3Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (!isLevel3Unlocked) {
//                    displayMessage("Unlock Level 1 & 2 first");
//                } else {
//                    startLevel(3);
//                }
//            }
//        });
//        stage.addActor(level3Button);
//    }
//
//    private void openSettings() {
//        System.out.println("Settings button clicked");
//    }
//
//    private void startLevel(int level) {
//        System.out.println("Starting Level " + level);
//    }
//
//    private void unlockLevel(int level) {
//        if (level == 2) {
//            level2Button.setDisabled(false);
//            isLevel2Unlocked = true;
//            System.out.println("Level 2 Unlocked");
//        } else if (level == 3) {
//            level3Button.setDisabled(false);
//            isLevel3Unlocked = true;
//            System.out.println("Level 3 Unlocked");
//        }
//    }
//
//    private void displayMessage(String message) {
//        System.out.println(message);
//    }
//
//    @Override
//    public void show() {}
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
//        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.end();
//
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void pause() {}
//
//    @Override
//    public void resume() {}
//
//    @Override
//    public void hide() {}
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        stage.dispose();
//        level1UnlockedTexture.dispose();
//        level2LockedTexture.dispose();
//        level3LockedTexture.dispose();
//        backgroundTexture.dispose();
//    }
//}

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
    TextButton settingsButton;
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

        // Settings button
        settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition(1750, 50);
        settingsButton.setSize(120, 40);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSettings();
            }
        });
        stage.addActor(settingsButton);

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

    private void openSettings() {
        System.out.println("Settings button clicked");
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
