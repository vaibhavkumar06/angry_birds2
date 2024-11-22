//package com.project.angrybirds.structure;
//
//public class TNTBomb extends Structure {
//    public TNTBomb(float x, float y) {
//        super(x, y, 1,"tnt_bomb.jpg");  // Low durability; triggers explosion on impact
//    }
//
//    @Override
//    public void takeDamage(int damage) {
//        super.takeDamage(damage);
//        if (durability <= 0) {
//            explode();  // Trigger explosion when broken
//        }
//    }
//
//    private void explode() {
//        // Logic to damage nearby pigs and structures
//    }
//}
//
