//package com.project.angrybirds.lwjgl3;
//
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.project.angrybirds.screens.LevelScreen;
//import com.badlogic.gdx.Game;
//
//public class LevelScreenLauncher {
//    public static void main(String[] args) {
//        if (StartupHelper.startNewJvmIfRequired()) return;
//        createApplication();
//    }
//
//    private static Lwjgl3Application createApplication() {
//        return new Lwjgl3Application(new Game() {
//            private String playerId;
//
//            @Override
//            public void create() {
//                playerId = "SanskarJain";
//                this.setScreen(new LevelScreen(playerId));
//            }
//        }, getDefaultConfiguration());
//    }
//
//    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
//        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
//        configuration.setTitle("Angry Birds - Level Screen");
//        configuration.useVsync(true);
//        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
//        configuration.setWindowedMode(640, 480);
//        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
//        return configuration;
//    }
//}
