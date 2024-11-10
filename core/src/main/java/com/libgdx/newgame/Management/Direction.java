package com.libgdx.newgame.Management;

public enum Direction {
    UP(0),
    DOWN(-180),
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
