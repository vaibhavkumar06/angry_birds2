//package com.project.angrybirds.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.project.angrybirds.birds.Birds;
//import com.project.angrybirds.birds.BlackBird;
//import com.project.angrybirds.birds.RedBird;
//import com.project.angrybirds.birds.YellowBird;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
//public class Level1Screen implements Screen {
//
//    private MainGame game;
//    private Stage stage;
//    private Texture backgroundTexture;
//    private Texture catapultTexture;
//    private SpriteBatch batch;
//    private Birds redBird;
//    private Birds yellowBird;
//    private Birds blackBird;
//    private Birds currentBird;
//    private Queue<Birds> birdsQueue;
//    private boolean isDragging = false;
//    private World world;
//    private Body catapultBody;
//    private boolean isBirdTransitioning = false;
//    private float transitionSpeed = 2f;
//
//    private static final float PIXELS_PER_METER = 100f;
//    private static final float GROUND_LEVEL = 1.6f;
//    private static final float CATAPULT_TOP_X = 6f;
//    private static final float CATAPULT_TOP_Y = 3f;
//    private static final float MAX_DRAG_DISTANCE = 3f;
//    private static final float RESET_POSITION_X = 1f; // Ground-level x-coordinate where birds reappear
//
//    public Level1Screen(MainGame game) {
//        this.game = game;
//        Box2D.init();
//        world = new World(new com.badlogic.gdx.math.Vector2(0, -9.8f), true);
//        batch = new SpriteBatch();
//    }
//
//    @Override
//    public void show() {
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//
//        // Add background
//        backgroundTexture = new Texture(Gdx.files.internal("game_background.jpg"));
//        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
//        background.setFillParent(true);
//        stage.addActor(background);
//
//        // Add catapult
//        catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
//        createCatapult();
//
//        // Initialize birds
//        initializeBirds();
//
//        // Add back button
//        addBackButton();
//    }
//
//    private void initializeBirds() {
//        // Initialize birds at ground level
//        redBird = new RedBird(world, RESET_POSITION_X, GROUND_LEVEL);
//        yellowBird = new YellowBird(world, RESET_POSITION_X + 1f, GROUND_LEVEL);
//        blackBird = new BlackBird(world, RESET_POSITION_X + 2f, GROUND_LEVEL);
//
//        // Queue the birds
//        birdsQueue = new LinkedList<>();
//        birdsQueue.add(redBird);
//        birdsQueue.add(yellowBird);
//        birdsQueue.add(blackBird);
//
//        // Set all birds as StaticBody initially
//        for (Birds bird : birdsQueue) {
//            bird.body.setType(BodyDef.BodyType.StaticBody);
//        }
//
//        // Start transition for the first bird
//        currentBird = birdsQueue.poll();
//        startBirdTransition(currentBird);
//    }
//
//    private void addBackButton() {
//        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//        TextButton backButton = new TextButton("Back", skin);
//        backButton.setSize(100, 50);
//        backButton.setPosition(10, Gdx.graphics.getHeight() - 60);
//
//        backButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                game.setScreen(new LevelScreen(game, "SJ_2005"));
//            }
//        });
//
//        stage.addActor(backButton);
//    }
//
//    private void createCatapult() {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(CATAPULT_TOP_X, GROUND_LEVEL);
//
//        catapultBody = world.createBody(bodyDef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(0.5f, 1f);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.friction = 0.5f;
//
//        catapultBody.createFixture(fixtureDef);
//        shape.dispose();
//    }
//
//    private void startBirdTransition(Birds bird) {
//        isBirdTransitioning = true;
//    }
//
//    private void moveBirdToCatapult(Birds bird) {
//        bird.body.setTransform(CATAPULT_TOP_X, CATAPULT_TOP_Y, 0);
//        bird.body.setLinearVelocity(0, 0);
//        bird.body.setAngularVelocity(0);
//        bird.body.setType(BodyDef.BodyType.StaticBody);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        world.step(1 / 60f, 6, 2);
//
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//
//        if (isBirdTransitioning) {
//            // Transition the bird towards the catapult
//            float currentX = currentBird.body.getPosition().x;
//            float currentY = currentBird.body.getPosition().y;
//
//            float dx = CATAPULT_TOP_X - currentX;
//            float dy = CATAPULT_TOP_Y - currentY;
//
//            float distance = (float) Math.sqrt(dx * dx + dy * dy);
//            if (distance < 0.1f) {
//                isBirdTransitioning = false;
//                moveBirdToCatapult(currentBird);
//            } else {
//                float stepX = dx / distance * transitionSpeed * delta;
//                float stepY = dy / distance * transitionSpeed * delta;
//                currentBird.body.setTransform(currentX + stepX, currentY + stepY, 0);
//            }
//        }
//
//        if (Gdx.input.isTouched() && !isBirdTransitioning) {
//            isDragging = true;
//
//            float mouseX = Gdx.input.getX() / PIXELS_PER_METER;
//            float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) / PIXELS_PER_METER;
//
//            float dx = mouseX - CATAPULT_TOP_X;
//            float dy = mouseY - CATAPULT_TOP_Y;
//            float dragDistance = (float) Math.sqrt(dx * dx + dy * dy);
//
//            if (dragDistance > MAX_DRAG_DISTANCE) {
//                float scalingFactor = MAX_DRAG_DISTANCE / dragDistance;
//                dx *= scalingFactor;
//                dy *= scalingFactor;
//            }
//
//            currentBird.body.setTransform(CATAPULT_TOP_X + dx, CATAPULT_TOP_Y + dy, 0);
//        } else if (isDragging) {
//            isDragging = false;
//
//            float launchDx = currentBird.body.getPosition().x - CATAPULT_TOP_X;
//            float launchDy = currentBird.body.getPosition().y - CATAPULT_TOP_Y;
//
//            currentBird.body.setType(BodyDef.BodyType.DynamicBody);
//            currentBird.body.applyLinearImpulse(
//                -launchDx * 5f,
//                -launchDy * 5f,
//                currentBird.body.getWorldCenter().x,
//                currentBird.body.getWorldCenter().y,
//                true
//            );
//        }
//
//        if (currentBird != null && currentBird.body.getPosition().x > 10f) { // Arbitrary screen edge x-coordinate
//            resetBirdToGround(currentBird);
//            if (!birdsQueue.isEmpty()) {
//                currentBird = birdsQueue.poll();
//                startBirdTransition(currentBird);
//            } else {
//                currentBird = null;
//            }
//        }
//
//        batch.begin();
//        batch.draw(catapultTexture,
//            catapultBody.getPosition().x * PIXELS_PER_METER - 50,
//            catapultBody.getPosition().y * PIXELS_PER_METER - 50,
//            100, 200);
//
//        drawBird(redBird);
//        drawBird(yellowBird);
//        drawBird(blackBird);
//
//        batch.end();
//    }
//
//    private void resetBirdToGround(Birds bird) {
//        bird.body.setType(BodyDef.BodyType.StaticBody);
//        bird.body.setTransform(RESET_POSITION_X, GROUND_LEVEL, 0); // Reset position to ground
//    }
//
//    private void drawBird(Birds bird) {
//        float birdSize = 0.5f * PIXELS_PER_METER;
//        batch.draw(bird.texture,
//            bird.body.getPosition().x * PIXELS_PER_METER - birdSize / 2,
//            bird.body.getPosition().y * PIXELS_PER_METER - birdSize / 2,
//            birdSize, birdSize);
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
//        stage.dispose();
//        backgroundTexture.dispose();
//        catapultTexture.dispose();
//        batch.dispose();
//        world.dispose();
//        redBird.dispose();
//        yellowBird.dispose();
//        blackBird.dispose();
//    }
//}
//

package com.project.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.project.angrybirds.birds.Birds;
import com.project.angrybirds.birds.BlackBird;
import com.project.angrybirds.birds.RedBird;
import com.project.angrybirds.birds.YellowBird;
import com.project.angrybirds.structure.IceStructure;
import com.project.angrybirds.structure.RockStructure;
import com.project.angrybirds.structure.Structure;
import com.project.angrybirds.structure.WoodStructure;

import java.util.LinkedList;
import java.util.Queue;

public class Level1Screen implements Screen {

    private MainGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture catapultTexture;
    private SpriteBatch batch;
    private Birds redBird;
    private Birds yellowBird;
    private Birds blackBird;
    private Birds currentBird;
    private Queue<Birds> birdsQueue;
    private boolean isDragging = false;
    private World world;
    private Body catapultBody;
    private boolean isBirdTransitioning = false;
    private float transitionSpeed = 2f;

    //adding structures
    private LinkedList<Structure> structures;

    private static final float PIXELS_PER_METER = 100f;
    private static final float GROUND_LEVEL = 1.6f;
    private static final float CATAPULT_TOP_X = 5f; //6
    private static final float CATAPULT_TOP_Y = 3.5f;   //3.5
    private static final float MAX_DRAG_DISTANCE = 3f;
    private static final float RESET_POSITION_X = 12f; // Right-side x-coordinate where birds reappear after launch

    public Level1Screen(MainGame game) {
        this.game = game;
        Box2D.init();
        world = new World(new com.badlogic.gdx.math.Vector2(0, -9.8f), true);
        batch = new SpriteBatch();

        // Initialize the structures list
        structures = new LinkedList<>(); // Can also use ArrayList
    }

    private void initializeStructures() {
        structures = new LinkedList<>();


        structures.add(new IceStructure(world, 15.75f, 2.85f, 1.75f, 0.25f)); // A small ice block
        structures.add(new WoodStructure(world, 15f, 2f, 0.25f, 1.5f)); // A tall wooden block
        structures.add(new WoodStructure(world, 16.5f, 2f, 0.25f, 1.5f)); // A tall wooden block
//        structures.add(new RockStructure(world, 15f, 4f, 0.25f, 1f)); // A wide rock block

        // Create Box2D bodies for structures
        for (Structure structure : structures) {
            createStructureBody(structure);
        }
    }

    private void createStructureBody(Structure structure) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Structures are static by default
        bodyDef.position.set(structure.x, structure.y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f); // Assume each structure is 1x1 meter in size; adjust as needed

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // Slight bounce on collision

        body.createFixture(fixtureDef);
        shape.dispose();

        structure.setBody(body); // Link the structure to its body
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Add background
        backgroundTexture = new Texture(Gdx.files.internal("game_background.jpg"));
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

        // Add catapult
        catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
        createCatapult();

        // Initialize birds
        initializeBirds();

        // Add back button
        addBackButton();

        // Initialize structures
        initializeStructures();

        // Add Structures
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check if a bird collides with a structure
                Object userDataA = fixtureA.getBody().getUserData();
                Object userDataB = fixtureB.getBody().getUserData();

                if (userDataA instanceof Birds && userDataB instanceof Structure) {
                    handleBirdHitsStructure((Birds) userDataA, (Structure) userDataB);
                } else if (userDataB instanceof Birds && userDataA instanceof Structure) {
                    handleBirdHitsStructure((Birds) userDataB, (Structure) userDataA);
                }
            }

            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    private void handleBirdHitsStructure(Birds bird, Structure structure) {
        structure.takeDamage(50); // Reduce structure durability (example: 50 damage)
        if (structure.isBroken()) {
            world.destroyBody(structure.getBody()); // Remove broken structure from the physics world
            structure.setBody(null); // Unlink body
        }
    }


    private void initializeBirds() {
        // Initialize birds at ground level near the catapult initially
        redBird = new RedBird(world, CATAPULT_TOP_X - 1f, GROUND_LEVEL);
        yellowBird = new YellowBird(world, CATAPULT_TOP_X - 2f, GROUND_LEVEL);
        blackBird = new BlackBird(world, CATAPULT_TOP_X - 3f, GROUND_LEVEL);

        // Queue the birds
        birdsQueue = new LinkedList<>();
        birdsQueue.add(redBird);
        birdsQueue.add(yellowBird);
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
        backButton.setPosition(10, Gdx.graphics.getHeight() - 60);

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
            100, 150);



        drawBird(redBird);
        drawBird(yellowBird);
        drawBird(blackBird);

        for (Structure structure : structures) {
            batch.draw(structure.getTexture(),
                (structure.x - structure.width / 2) * PIXELS_PER_METER,  // Center the texture
                (structure.y - structure.height / 2) * PIXELS_PER_METER,
                structure.width * PIXELS_PER_METER,                     // Scale width
                structure.height * PIXELS_PER_METER);                   // Scale height
        }


        batch.end();
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        catapultTexture.dispose();
        batch.dispose();
        world.dispose();
        redBird.dispose();
        yellowBird.dispose();
        blackBird.dispose();

        for (Structure structure : structures) {
            structure.dispose();
        }

    }
}
