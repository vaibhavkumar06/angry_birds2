package com.project.angrybirds.pigs;

import com.badlogic.gdx.physics.box2d.*;

public class BabyPig extends Pig {
    private Body body; // Box2D body

    public BabyPig(World world, float x, float y) {
        super(x, y, 1, "babypig.png");  // 1 hit to defeat
        createBox2DBody(world);
    }

    private void createBox2DBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs are affected by gravity
        bodyDef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f); // Adjust radius as per pig size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.4f; // Bounciness

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this); // Attach the pig instance to the body for collision detection
    }

    public Body getBody() {
        return body;
    }
}
