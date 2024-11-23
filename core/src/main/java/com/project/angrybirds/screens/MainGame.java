package com.project.angrybirds.screens;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    private MainMenu mainMenu;
    private LevelScreen levelScreen;
    private String playerId;

    @Override
    public void create() {

        playerId = "SJ_2005";
        mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }

    public void switchToLevelScreen() {
        if (levelScreen == null) {
            levelScreen = new LevelScreen(this, playerId); // Pass 'this' and 'playerId' to the constructor
        }
        setScreen(levelScreen);
    }


    public void switchToLevel1Screen() {
        this.setScreen(new Level1Screen(this));
    }

    public void switchToLevel2Screen() {
        this.setScreen(new Level2Screen(this));
    }

    public void switchToLevel3Screen() {
        this.setScreen(new Level3Screen(this));
    }


    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void render() {
        super.render(); // important!
    }

}
