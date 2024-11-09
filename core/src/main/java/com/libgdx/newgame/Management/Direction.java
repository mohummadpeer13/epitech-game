package com.libgdx.newgame.Management;

public enum Direction {
    UP(90),
    DOWN(-90),
    LEFT(230),
    RIGHT(-30);

    private final float angle;

    Direction(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
