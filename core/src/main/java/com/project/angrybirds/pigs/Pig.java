//package com.project.angrybirds.pigs;
//
//import com.badlogic.gdx.graphics.Texture;
//
//// Pig.java
//public abstract class Pig {
//    public float x, y;  // Position
//    public int health;  // Number of hits required to defeat the pig
//    protected Texture texture;
//
//    public Pig(float x, float y, int health, String texturePath) {
//        this.x = x;
//        this.y = y;
//        this.health = health;
//        this.texture = new Texture(texturePath); // Load specific texture
//    }
//
//    public boolean isAlive() {
//        return health > 0;
//    }
//
//    public void takeDamage(int damage) {
//        health -= damage;
//    }
//
//    public Texture getTexture() {
//        return texture;
//    }
//
//    public void dispose() {
//        if (texture != null) texture.dispose();
//    }
//}
//
package com.project.angrybirds.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Pig {
    public float x, y;  // Position
    public int health;  // Number of hits required to defeat the pig
    protected Texture texture;
    private Body body;  // Box2D body for the pig

    public Pig(float x, float y, int health, String texturePath) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.texture = new Texture(texturePath); // Load specific texture
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
