package io.github.millenniarst.ai_builder.brain.area;

/*
 *           |\       /|                          __                 __    ___  __
 *           | \     / |   ______    /\    |     |  \  |   | | |    |  \  |    |  \
 *           |  \   /  |  /         /  \   |     |__/  |   | | |    |   | |___ |__/
 *           |   \_/   | |         /----\  |     |   \ |   | | |    |   | |    |  \
 *           |         |  \____   /      \ |     |___/  \_/  | |___ |__/  |___ |   \
 *           |         |       \
 *           |         |        |      AI Builder  ---   By Millenniar Studios
 *           |         | ______/
 */

import io.github.millenniarst.ai_builder.brain.map.MapArea;

public class PrismArea<M extends MapArea> extends Area {

    private final M root;

    private int minY;
    private int maxY;

    public PrismArea(M root) {
        super();

        this.root = root;
    }

    public void putPos(int x, int z, int y, Position pos) {
        if(root.containsPos(x, z)) {
            super.putPos(x, z, y, pos);

            refreshSizePos(y);
        }
    }

    public Position getAndCreatePos(int x, int z, int y) {
        Position pos;
        if(root.containsPos(x, z))
            pos = super.getAndCreatePos(x, z, y);
        else
            pos = new Position(x, z, y);

        refreshSizePos(y);
        return pos;
    }

    private void refreshSizePos(int y) {
        if(y > maxY)
            maxY = y;
        else if(y < minY)
            minY = y;
    }

    public void refreshSize() {
        for(int[] pos : getPositions().keySet()) {
            refreshSizePos(pos[2]);
        }
    }

    public M getRoot() {
        return root;
    }

    public int getMinY() {
        return minY;
    }
    public int getMaxY() {
        return maxY;
    }

    public int getSizeY() {
        return Math.abs(maxY - minY) +1;
    }
}
