//package com.project.angrybirds;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//import com.badlogic.gdx.utils.Json;
//
//public class GameStateManager {
//    private static final String SAVE_FILE = "savegame.json";
//
//    // Method to save the game state
//    public void saveGame(GameState state) {
//        Json json = new Json();
//        String saveData = json.toJson(state);
//
//        FileHandle file = Gdx.files.local(SAVE_FILE);
//        file.writeString(saveData, false); // Overwrite if it exists
//        System.out.println("Game saved successfully!");
//    }
//
//    // Method to load the game state
//    public GameState loadGame() {
//        FileHandle file = Gdx.files.local(SAVE_FILE);
//
//        if (!file.exists()) {
//            System.out.println("No save file found!");
//            return null; // No saved game
//        }
//
//        Json json = new Json();
//        String saveData = file.readString();
//        return json.fromJson(GameState.class, saveData);
//    }
//}
//
//
