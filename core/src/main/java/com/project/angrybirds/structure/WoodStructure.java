package com.project.angrybirds.structure;

import com.badlogic.gdx.physics.box2d.World;

public class WoodStructure extends Structure {
    public WoodStructure(World world, float x, float y, float width, float height) {
        super(x, y, width, height, 50, "wood_horizontal_thin.jpg");  // Wood is the easiest to break
        createBody(world);
    }
}

