package com.project.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.project.angrybirds.birds.*;
import com.project.angrybirds.pigs.BabyPig;
import com.project.angrybirds.pigs.DaddyPig;
import com.project.angrybirds.pigs.Pig;
import com.project.angrybirds.pigs.TeenPig;
import com.project.angrybirds.structure.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Level2Screen implements Screen {

    private MainGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture catapultTexture;
    private SpriteBatch batch;
    private Birds redBird;
    private Birds eagleBird;
    private Birds blackBird;
    private Birds currentBird;
    private Queue<Birds> birdsQueue;
    private boolean isDragging = false;
    private World world;
    private int level;
    private Body catapultBody;
    private boolean isBirdTransitioning = false;
    private float transitionSpeed = 2f;

    //adding structures
    private LinkedList<Structure> structures;
    private LinkedList<Pig> pigs;

    //    private Stage stage;
    private boolean isPaused = false;



    private static final float PIXELS_PER_METER = 100f;
    private static final float GROUND_LEVEL = 1.6f;
    private static final float CATAPULT_TOP_X = 5f; //6
    private static final float CATAPULT_TOP_Y = 3.5f;   //3.5
    private static final float MAX_DRAG_DISTANCE = 3f;
    private static final float RESET_POSITION_X = 16f; // Right-side x-coordinate where birds reappear after launch

    public Level2Screen(MainGame game) {
        this.game = game;
        Box2D.init();
        world = new World(new com.badlogic.gdx.math.Vector2(0, -9.8f), true);
        batch = new SpriteBatch();



//        body.setUserData(this);
    }

    private void showPauseMenu() {
        // Create a Table for the menu
        Table pauseMenu = new Table();
        pauseMenu.setFillParent(true);

        // Create a Skin for the buttons
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Use a prebuilt skin or your custom skin


        // Resume Button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                pauseMenu.setVisible(false);
                loadGameState(); // Load game state on resume
            }
        });

        // Save Button
        TextButton saveButton = new TextButton("Save Game", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGameState(); // Save game state on button press
            }
        });


// Exit Button
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the application
            }
        });

        // Add buttons to the table
        pauseMenu.add(resumeButton).pad(10).row();
        pauseMenu.add(saveButton).pad(10).row();
        pauseMenu.add(exitButton).pad(10).row();

        // Add the table to the stage
        stage.addActor(pauseMenu);
    }


    private void initializeStructures() {
        structures = new LinkedList<>();

        // Base layer of structures
        structures.add(new WoodStructure(world, 10.5f, 2.5f, 0.25f, 2f));
        structures.add(new IceStructure(world, 11f, 2.25f, 0.25f, 1.5f));
        structures.add(new IceStructure(world, 12f, 2.25f, 0.25f, 1.5f));
        structures.add(new WoodStructure(world, 12.5f, 2.5f, 0.25f, 2f));

        // Top layer of structures
        structures.add(new IceStructure(world, 11.5f, 2.85f, 1.25f, 0.25f));
        structures.add(new WoodStructure(world, 11.5f, 3.6f, 2.25f, 0.25f));

        // First layer
        structures.add(new WoodStructure(world, 11f, 4.5f, 0.25f, 1.5f));
        structures.add(new WoodStructure(world, 12f, 4.5f, 0.25f, 1.5f));

        // First top
        structures.add(new RockStructure(world, 11.5f, 5.25f, 1.25f, 0.25f));
        structures.add(new WoodStructure(world, 11f, 5.45f, 0.25f, 0.25f));
        structures.add(new WoodStructure(world, 12f, 5.45f, 0.25f, 0.25f));

        // Create Box2D bodies for structures
        for (Structure structure : structures) {
            createStructureBody(structure);
        }
    }

    private void createStructureBody(Structure structure) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(structure.x, structure.y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(structure.width / 2, structure.height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f; // Adjust density
        fixtureDef.friction = 0.8f; // Increase friction
        fixtureDef.restitution = 0.0f; // Reduce bouncing

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-structure.width / 2, -structure.height / 2),
            new Vector2(structure.width / 2, -structure.height / 2));

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 5f; // High friction at the base

        body.createFixture(fixtureDef);
        body.createFixture(groundFixtureDef);

        shape.dispose();
        groundShape.dispose();

        // Add damping to stabilize motion
        body.setLinearDamping(0.5f);
        body.setAngularDamping(0.5f);

        body.setUserData(structure);
        structure.setBody(body);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createGroundBody();
        // Add background
        backgroundTexture = new Texture(Gdx.files.internal("game_background.jpg"));
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

// Create a Skin for the button
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Use default or custom skin

// Create the pause button
        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(100, 50); // Adjust size if needed
        pauseButton.setPosition(10, Gdx.graphics.getHeight() - pauseButton.getHeight() - 10);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    showPauseMenu();
                    isPaused = true;
                }
            }
        });

// Add the pause button to the stage
        stage.addActor(pauseButton);

        // Add catapult
        catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
        createCatapult();

        // Initialize birds
        initializeBirds();

        // Add back button
        addBackButton();

        // Initialize structures
        initializeStructures();

        initializePigs();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check if a bird collides with a structure
                Object userDataA = fixtureA.getBody().getUserData();
                Object userDataB = fixtureB.getBody().getUserData();

                // Bird hits structure
                if (userDataA instanceof Birds && userDataB instanceof Structure) {
                    handleBirdHitsStructure((Birds) userDataA, (Structure) userDataB);
                } else if (userDataB instanceof Birds && userDataA instanceof Structure) {
                    handleBirdHitsStructure((Birds) userDataB, (Structure) userDataA);
                }

                // Bird hits pig
                if (userDataA instanceof Birds && userDataB instanceof Pig) {
                    handleBirdHitsPig((Birds) userDataA, (Pig) userDataB);
                } else if (userDataB instanceof Birds && userDataA instanceof Pig) {
                    handleBirdHitsPig((Birds) userDataB, (Pig) userDataA);
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

    }

//    private void handleBirdHitsPig(Birds bird, Pig pig) {
//        if (pig.isAlive()) {
//            pig.takeDamage(1); // Reduce health by 1
//            if (!pig.isAlive()) {
//                world.destroyBody(pig.getBody()); // Destroy the pig's body
//                pig.setBody(null);
//
//                // Move structures or pigs based on bird's final impact
//                adjustNearbyObjects(bird.body.getPosition().x, bird.body.getPosition().y);
//            }
//        }
//    }

    private void handleBirdHitsPig(Birds bird, Pig pig) {
        if (pig.isAlive()) {
            pig.takeDamage(1); // Reduce health by 1
            if (!pig.isAlive()) {
                world.destroyBody(pig.getBody()); // Destroy the pig's body
                pig.setBody(null);
                adjustNearbyObjects(bird.body.getPosition().x, bird.body.getPosition().y);
            }
        }
    }

    private void adjustNearbyObjects(float impactX, float impactY) {
        float adjustmentRadius = 2f; // Define a radius for nearby objects
        float displacement = 0.5f;  // Displacement for affected objects

        // Adjust structures
        for (Structure structure : structures) {
            if (structure.getBody() != null) {
                float distance = Math.abs(structure.x - impactX);
                if (distance < adjustmentRadius) {
                    structure.setBody(null); // Unlink current body
                    structure.x += displacement; // Adjust position
                    createStructureBody(structure); // Recreate body with new position
                }
            }
        }

        // Adjust pigs
        for (Pig pig : pigs) {
            if (pig.isAlive() && pig.getBody() != null) {
                float distance = Math.abs(pig.getBody().getPosition().x - impactX);
                if (distance < adjustmentRadius) {
                    pig.getBody().setTransform(pig.getBody().getPosition().x + displacement, pig.getBody().getPosition().y, 0);
                }
            }
        }
    }

    private void createGroundBody() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, GROUND_LEVEL);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(0, 0), new Vector2(20, 0)); // Adjust length as needed

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }

//    private void handleBirdHitsStructure(Birds bird, Structure structure) {
//        structure.takeDamage(50); // Apply damage to the structure
//        if (structure.isBroken()) {
//            world.destroyBody(structure.getBody()); // Destroy the structure's body
//            structure.setBody(null);
//
//            // Move structures or pigs based on bird's final impact
//            adjustNearbyObjects(bird.body.getPosition().x, bird.body.getPosition().y);
//        }
//    }

    private void handleBirdHitsStructure(Birds bird, Structure structure) {
        structure.takeDamage(50); // Apply damage to the structure
        if (structure.isBroken()) {
            world.destroyBody(structure.getBody()); // Destroy the structure's body
            structure.setBody(null);
            adjustNearbyObjects(bird.body.getPosition().x, bird.body.getPosition().y);
        }
    }

    private void initializePigs() {
        pigs = new LinkedList<>();

        // Position pigs on top of the structures
        pigs.add(new BabyPig(world, 11.6f, 5.35f)); // Pig on top of the rock structure
        pigs.add(new TeenPig(world, 11.6f, 2.75f)); // Pig on ground
        pigs.add(new DaddyPig(world, 11.65f, 3.7f)); // Pig on top of the base top layer wood structure
//        pigs.add(new BabyPig(world, 10f, 4f)); // Pig on top of the ice structure
    }


    private void initializeBirds() {
        // Initialize birds at ground level near the catapult initially
        redBird = new RedBird(world, CATAPULT_TOP_X - 1f, GROUND_LEVEL);
        eagleBird = new EagleBird(world, CATAPULT_TOP_X - 2f, GROUND_LEVEL);
        blackBird = new BlackBird(world, CATAPULT_TOP_X - 3f, GROUND_LEVEL);

        // Queue the birds
        birdsQueue = new LinkedList<>();
        birdsQueue.add(redBird);
        birdsQueue.add(eagleBird);
        birdsQueue.add(blackBird);

        // Set all birds as StaticBody initially
        for (Birds bird : birdsQueue) {
            bird.body.setType(BodyDef.BodyType.StaticBody);
        }

        // Start transition for the first bird
        currentBird = birdsQueue.poll();
        startBirdTransition(currentBird);
    }

    private void addBackButton() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton backButton = new TextButton("Back", skin);
        backButton.setSize(100, 50);
        backButton.setPosition(Gdx.graphics.getWidth()-120, Gdx.graphics.getHeight() - 60);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game, "SJ_2005"));
            }
        });

        stage.addActor(backButton);
    }

    private void createCatapult() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(CATAPULT_TOP_X, GROUND_LEVEL);

        catapultBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        catapultBody.createFixture(fixtureDef);
        shape.dispose();
    }

    private void startBirdTransition(Birds bird) {
        isBirdTransitioning = true;
    }

    private void moveBirdToCatapult(Birds bird) {
        bird.body.setTransform(CATAPULT_TOP_X, CATAPULT_TOP_Y, 0);
        bird.body.setLinearVelocity(10, 10);
        bird.body.setAngularVelocity(0);
        bird.body.setType(BodyDef.BodyType.StaticBody);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If the game is paused, render only the pause menu
        if (isPaused) {
            // Render the pause menu
            stage.act(delta);
            stage.draw();
            return; // Skip further rendering
        }

        world.step(1 / 60f, 6, 2);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (isBirdTransitioning) {
            // Transition the bird towards the catapult
            float currentX = currentBird.body.getPosition().x;
            float currentY = currentBird.body.getPosition().y;

            float dx = CATAPULT_TOP_X - currentX;
            float dy = CATAPULT_TOP_Y - currentY;

            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < 0.1f) {
                isBirdTransitioning = false;
                moveBirdToCatapult(currentBird);
            } else {
                float stepX = dx / distance * transitionSpeed * delta;
                float stepY = dy / distance * transitionSpeed * delta;
                currentBird.body.setTransform(currentX + stepX, currentY + stepY, 0);
            }
        }

        if (Gdx.input.isTouched() && !isBirdTransitioning) {
            isDragging = true;

            float mouseX = Gdx.input.getX() / PIXELS_PER_METER;
            float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) / PIXELS_PER_METER;

            float dx = mouseX - CATAPULT_TOP_X;
            float dy = mouseY - CATAPULT_TOP_Y;
            float dragDistance = (float) Math.sqrt(dx * dx + dy * dy);

            if (dragDistance > MAX_DRAG_DISTANCE) {
                float scalingFactor = MAX_DRAG_DISTANCE / dragDistance;
                dx *= scalingFactor;
                dy *= scalingFactor;
            }

            currentBird.body.setTransform(CATAPULT_TOP_X + dx, CATAPULT_TOP_Y + dy, 0);
        } else if (isDragging) {
            isDragging = false;

            float launchDx = currentBird.body.getPosition().x - CATAPULT_TOP_X;
            float launchDy = currentBird.body.getPosition().y - CATAPULT_TOP_Y;

            currentBird.body.setType(BodyDef.BodyType.DynamicBody);
            currentBird.body.applyLinearImpulse(
                -launchDx * 5f,
                -launchDy * 5f,
                currentBird.body.getWorldCenter().x,
                currentBird.body.getWorldCenter().y,
                true
            );
        }

        if (currentBird != null && currentBird.body.getPosition().x > 10f) { // Arbitrary screen edge x-coordinate
            resetBirdToGround(currentBird);
            if (!birdsQueue.isEmpty()) {
                currentBird = birdsQueue.poll();
                startBirdTransition(currentBird);
            } else {
                currentBird = null;
            }
        }

        batch.begin();
        batch.draw(catapultTexture,
            catapultBody.getPosition().x * PIXELS_PER_METER - 30,
            catapultBody.getPosition().y * PIXELS_PER_METER - 30,
            100, 100);

        drawBird(redBird);
        drawBird(eagleBird);
        drawBird(blackBird);

        for (Structure structure : structures) {
            batch.draw(structure.getTexture(),
                (structure.getBody().getPosition().x- structure.width / 2) * PIXELS_PER_METER,  // Center the texture
                (structure.getBody().getPosition().y - structure.height / 2) * PIXELS_PER_METER,
                structure.width * PIXELS_PER_METER,                     // Scale width
                structure.height * PIXELS_PER_METER);                   // Scale height
        }

        // Ensure pigs don't fall below the ground level
        for (Pig pig : pigs) {
            if (pig.isAlive()) {
                Body body = null;

                // Safely get the body for the specific pig type
                if (pig instanceof BabyPig) {
                    body = ((BabyPig) pig).getBody();
                } else if (pig instanceof TeenPig) {
                    body = ((TeenPig) pig).getBody();
                } else if (pig instanceof DaddyPig) {
                    body = ((DaddyPig) pig).getBody();
                }

                if (body != null) {
                    // Ensure the pig doesn't fall below ground level
                    if (body.getPosition().y < GROUND_LEVEL) {
                        body.setTransform(body.getPosition().x, GROUND_LEVEL, 0);  // Reset to ground level
                    }

                    batch.draw(pig.getTexture(),
                        (body.getPosition().x - 0.3f) * PIXELS_PER_METER, // Center the pig
                        (body.getPosition().y - 0.3f) * PIXELS_PER_METER,
                        0.6f * PIXELS_PER_METER, // Width
                        0.6f * PIXELS_PER_METER); // Height
                }
            }
        }

        batch.end();
    }

    //Save game
    public void saveGameState() {
        HashMap<String, Object> gameState = new HashMap<>();
        gameState.put("levelNumber", level);
        GameStateManager.saveGame(gameState);
    }

    //load game
    public void loadGameState() {
        HashMap<String, Object> gameState = GameStateManager.loadGame();
        if (gameState != null) {
            level = (int) gameState.getOrDefault("levelNumber", level);
        }
    }


    private void resetBirdToGround(Birds bird) {
        bird.body.setType(BodyDef.BodyType.StaticBody);
        bird.body.setTransform(RESET_POSITION_X, GROUND_LEVEL, 0); // Reset position to the right side of the screen
    }

    private void drawBird(Birds bird) {
        float birdSize = 0.5f * PIXELS_PER_METER;
        batch.draw(bird.texture,
            bird.body.getPosition().x * PIXELS_PER_METER - birdSize / 2,
            bird.body.getPosition().y * PIXELS_PER_METER - birdSize / 2,
            birdSize, birdSize);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        catapultTexture.dispose();
        batch.dispose();
        world.dispose();
        redBird.dispose();
        eagleBird.dispose();
        blackBird.dispose();

        for (Structure structure : structures) {
            if (structure.getBody() != null) {
                float currentY = structure.getBody().getPosition().y;

                // Ensure the structure doesn't fall below ground level
                if (currentY < GROUND_LEVEL + structure.height / 2) {
                    structure.getBody().setTransform(
                        structure.getBody().getPosition().x, // Keep the same x position
                        GROUND_LEVEL + structure.height / 2, // Reset to ground level
                        structure.getBody().getAngle()      // Preserve the rotation
                    );
                }
            }
        }


        for (Pig pig : pigs) {
            pig.dispose();
        }
    }
}

