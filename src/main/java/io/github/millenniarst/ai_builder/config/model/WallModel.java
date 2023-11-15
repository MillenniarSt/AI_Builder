package io.github.millenniarst.ai_builder.config.model;

import io.github.millenniarst.ai_builder.brain.area.Position;

public class WallModel extends ColumnModel {

    protected Schematic north;
    protected Schematic south;
    protected Schematic northDown;
    protected Schematic southDown;
    protected Schematic northUp;
    protected Schematic southUp;

    public void build(Position origin, Position end) {
        build(origin.getPosX(), origin.getPosZ(), origin.getPosY(), end.getPosX(), end.getPosY());
    }
    public void build(int originX, int z, int originY, int endX, int endY) {

    }

    public void buildRotate(Position origin, Position end, int rotate) {
        buildRotate(origin.getPosX(), origin.getPosZ(), origin.getPosY(), end.getPosZ(), end.getPosY(), rotate);
    }
    public void buildRotate(int originX, int z, int originY, int endX, int endY, int rotate) {
        rotateY(rotate).build(originX, z, originY, endX, endY);
    }

    @Override
    public WallModel rotateY(int angle) {
        WallModel wall = new WallModel();

        wall.base = base.rotateY(angle);
        wall.down = down.rotateY(angle);
        wall.up = up.rotateY(angle);

        if(angle / 90 % 4 == 1) {
            wall.north = north.rotateY(angle);
            wall.south = south.rotateY(angle);
            wall.northDown = northDown.rotateY(angle);
            wall.southDown = southDown.rotateY(angle);
            wall.northUp = northUp.rotateY(angle);
            wall.southUp = southUp.rotateY(angle);
        }
        wall.north = north.rotateY(angle);
        wall.south = south.rotateY(angle);
        wall.northDown = northDown.rotateY(angle);
        wall.southDown = southDown.rotateY(angle);
        wall.northUp = northUp.rotateY(angle);
        wall.southUp = southUp.rotateY(angle);

        wall.setState(getState());
        return wall;
    }

    public Schematic getNorth() {
        return north;
    }
    public Schematic getSouth() {
        return south;
    }
    public Schematic getNorthDown() {
        return northDown;
    }
    public Schematic getSouthDown() {
        return southDown;
    }
    public Schematic getNorthUp() {
        return northUp;
    }
    public Schematic getSouthUp() {
        return southUp;
    }
}
