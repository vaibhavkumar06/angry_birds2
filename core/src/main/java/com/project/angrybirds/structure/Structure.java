package com.project.angrybirds.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

// Structure.java
public abstract class Structure {
    public float x, y;  // Position
    public float width, height;     //Size
    public int durability;  // Structure strength, higher values are harder to break
    protected Texture texture;

    public Structure(float x, float y, float width, float height, int durability, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.durability = durability;
        this.texture = new Texture(texturePath); // Load specific texture
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Default structure size (adjust as needed)

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this); // Attach structure as user data
    }

    private Body body;

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public Texture getTexture() {
        return texture;
    }

    public void takeDamage(int damage) {
        durability -= damage;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}

