package com.project.angrybirds.structure;

import com.badlogic.gdx.physics.box2d.World;

public class RockStructure extends Structure {
    public RockStructure(World world, float x, float y, float width, float height) {
        super(x, y, width, height, 3, "rock_horizontal.jpg");  // Rock is the hardest to break
        createBody(world);
    }
}

