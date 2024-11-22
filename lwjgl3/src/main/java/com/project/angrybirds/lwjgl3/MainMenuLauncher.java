package com.project.angrybirds.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.project.angrybirds.screens.Level1Screen;
import com.project.angrybirds.screens.MainGame;

public class MainMenuLauncher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new MainGame(), getDefaultConfiguration());
    }


    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Angry Birds Main Menu");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        // Set windowed mode with initial size
        configuration.setWindowedMode(800, 400);

        // Maximize the window to fill the screen
        configuration.setMaximized(true);

        // Ensure window decorations are enabled (default is true, but setting explicitly)
        configuration.setDecorated(true);

        configuration.setWindowIcon("icon.jpg", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }


}




