package com.project.angrybirds.birds;

import com.badlogic.gdx.physics.box2d.World;

public class BlackBird extends Birds {
    public BlackBird(World world, float x, float y) {
        super(world, x, y, 3, "black_bird.png");  // Assuming "black_bird.png" is in your assets folder
    }

    @Override
    public void launch(float launchAngle, float force) {
        System.out.println("Black bird launched and explodes on impact!");

        // Apply Box2D impulse for launching
        body.setType(com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody); // Set body to dynamic
        float impulseX = (float) (force * Math.cos(launchAngle));
        float impulseY = (float) (force * Math.sin(launchAngle));
        body.applyLinearImpulse(impulseX, impulseY, body.getWorldCenter().x, body.getWorldCenter().y, true);

        // Add explosion logic here (e.g., damage nearby objects on collision)
    }
}
