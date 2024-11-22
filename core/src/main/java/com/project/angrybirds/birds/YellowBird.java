package com.project.angrybirds.birds;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Birds {
    public YellowBird(World world, float x, float y) {
        super(world, x, y, 1, "yellow_bird.png");  // Damage = 1
    }

    @Override
    public void launch(float launchAngle, float force) {
        float impulseX = (float) (force * Math.cos(launchAngle));
        float impulseY = (float) (force * Math.sin(launchAngle));

        body.setType(BodyDef.BodyType.DynamicBody);
        body.applyLinearImpulse(impulseX, impulseY, body.getPosition().x, body.getPosition().y, true);

        System.out.println("Yellow bird launched quickly!");
    }
}
