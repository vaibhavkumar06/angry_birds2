package com.project.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Birds {
    public Body body; // Box2D body for the bird
    public Texture texture;

    public Birds(World world, float x, float y, int damage, String texturePath) {
        this.texture = new Texture(texturePath);

        // Create Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        this.body = world.createBody(bodyDef);

        // Create shape and fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f); // Adjust radius for bird size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f; // Some bounciness

        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    public abstract void launch(float launchAngle, float force);

    public void render(Batch batch) {
        batch.draw(texture, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 1f, 1f);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
