package com.project.angrybirds.birds;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class EagleBird extends Birds {
    public EagleBird(World world, float x, float y) {
        super(world, x, y, 2, "Mighty_Eagle.png");  // Damage = 2
    }

    @Override
    public void launch(float launchAngle, float force) {
        float impulseX = (float) (force * Math.cos(launchAngle));
        float impulseY = (float) (force * Math.sin(launchAngle));

        body.setType(BodyDef.BodyType.DynamicBody);
        body.applyLinearImpulse(impulseX, impulseY, body.getPosition().x, body.getPosition().y, true);

        System.out.println("Eagle bird launched with moderate damage!");
    }
}
