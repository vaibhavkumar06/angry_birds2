package com.project.angrybirds.structure;

import com.badlogic.gdx.physics.box2d.World;

public class IceStructure extends Structure {
    public IceStructure(World world, float x, float y, float width, float height) {
        super(x, y, width, height, 25, "ice_horizontal_thin.jpg");  // Ice is slightly harder to break than wood
        createBody(world);
    }
}

