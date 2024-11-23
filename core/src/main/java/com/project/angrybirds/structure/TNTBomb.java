package com.project.angrybirds.structure;

import com.badlogic.gdx.physics.box2d.World;

public class TNTBomb extends Structure {
    public TNTBomb(World world, float x, float y, float width, float height) {
        super(x, y, width, height,1,"tnt_bomb.jpg");  // Low durability; triggers explosion on impact
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (durability <= 0) {
            explode();  // Trigger explosion when broken
        }
    }

    private void explode() {
        // Logic to damage nearby pigs and structures
    }
}

