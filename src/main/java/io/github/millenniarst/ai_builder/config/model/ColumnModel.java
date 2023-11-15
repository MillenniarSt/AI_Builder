package io.github.millenniarst.ai_builder.config.model;

import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.config.Loader;

public class ColumnModel extends SingleModel {

    protected Schematic down;
    protected Schematic up;

    public void build(Position origin, Position end) {
        build(origin.getPosX(), origin.getPosZ(), origin.getPosY(), end.getPosY());
    }
    public void build(int x, int z, int originY, int endY) {
        base.buildRepeatUp(new Position(x, z, originY + getSpaceY(down)), new Position(x, z, endY - getSpaceY(up)));

        down.build(x, z, originY);
        up.build(x, z, originY);
    }

    public void buildRotate(Position origin, Position end, int rotate) {
        buildRotate(origin.getPosX(), origin.getPosZ(), origin.getPosY(), end.getPosY(), rotate);
    }
    public void buildRotate(int x, int z, int originY, int endY, int rotate) {
        rotateY(rotate).build(x, z, originY, endY);
    }

    @Override
    public ColumnModel rotateY(int rotate) {
        ColumnModel column = new ColumnModel();

        column.base = base.rotateY(rotate);
        column.down = down.rotateY(rotate);
        column.up = up.rotateY(rotate);

        column.setState(getState());
        return column;
    }

    protected int getSpaceY(Schematic schematic) {
        return schematic.getState().equals(Loader.STATE_ENABLE) ? (schematic.getMinY() -1) * -1 : 0;
    }

    public Schematic getDown() {
        return down;
    }
    public Schematic getUp() {
        return up;
    }
}
