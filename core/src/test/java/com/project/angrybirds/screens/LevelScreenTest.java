package com.project.angrybirds.screens;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelScreenTest {

    private MockMainGame game;
    private LevelScreen levelScreen;

    @BeforeEach
    void setUp() {
        // Initialize the mock MainGame and LevelScreen
        game = new MockMainGame();
        levelScreen = new LevelScreen(game, "SJ_2005");
        levelScreen.show();
    }

    @AfterEach
    void tearDown() {
        if (levelScreen != null) {
            levelScreen.dispose();
        }
        if (game != null) {
            game.dispose();
        }
    }

    @Test
    void testPlayerIdLabelDisplayed() {
        String playerIdText = levelScreen.playerIdLabel.getText().toString();
        assertEquals("Player ID: TestPlayer123", playerIdText);
    }

    @Test
    void testSettingsButtonExists() {
        assertNotNull(levelScreen.settingsButton);
        assertEquals("Settings", levelScreen.settingsButton.getText().toString());
    }

    @Test
    void testLevelButtonPositions() {
        assertEquals(800, (int) levelScreen.level1Button.getX());
        assertEquals(900, (int) levelScreen.level2Button.getX());
        assertEquals(1000, (int) levelScreen.level3Button.getX());
    }

    @Test
    void testStartLevel() {
        levelScreen.startLevel(1);
        assertEquals(1, game.levelStarted);

        levelScreen.startLevel(2);
        assertEquals(2, game.levelStarted);

        levelScreen.startLevel(3);
        assertEquals(3, game.levelStarted);

        levelScreen.startLevel(99);
        assertNotEquals(99, game.levelStarted);
    }

    // Mock implementation of MainGame for testing purposes
    static class MockMainGame extends MainGame {
        public int levelStarted = 0;

        @Override
        public void switchToLevel1Screen() {
            levelStarted = 1;
        }

        @Override
        public void switchToLevel2Screen() {
            levelStarted = 2;
        }

        @Override
        public void switchToLevel3Screen() {
            levelStarted = 3;
        }

        @Override
        public void create() {
            // Simulate any necessary initialization
        }

        @Override
        public void resize(int width, int height) {}

        @Override
        public void render() {}

        @Override
        public void pause() {}

        @Override
        public void resume() {}

        @Override
        public void dispose() {}
    }
}