package com.project.angrybirds.screens;

import java.io.*;
import java.util.HashMap;

public class GameStateManager {
    private static final String SAVE_FILE = "gameState.dat";

    public static void saveGame(HashMap<String, Object> gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
            System.out.println("Game state saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }

    public static HashMap<String, Object> loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            System.out.println("Game state loaded successfully!");
            return (HashMap<String, Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game state: " + e.getMessage());
            return null;
        }
    }
}

