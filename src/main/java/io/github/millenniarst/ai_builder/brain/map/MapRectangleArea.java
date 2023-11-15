package io.github.millenniarst.ai_builder.brain.map;

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

public class MapRectangleArea extends MapArea {

    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    public MapRectangleArea(int minX, int minZ, int maxX, int maxZ) {
        super();

        fillPos(minX, minZ, maxX, maxZ, MapPosition.MapPositionOpt.NONE);

        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public MapRectangleArea(int minX, int minZ, int maxX, int maxZ, MapPosition.MapPositionOpt opt) {
        super();

        fillPos(minX, minZ, maxX, maxZ, opt);

        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public void refreshSize() {
        for(int[] pos : getPositions().keySet()) {
            refreshSizePos(pos[0], pos[1]);
        }
    }

    public void putPos(int x, int z, MapPosition pos) {
        super.putPos(x, z, pos);

        refreshSizePos(x, z);
    }

    public MapPosition getAndCreatePos(int x, int z) {
        MapPosition pos = super.getAndCreatePos(x, z);

        refreshSizePos(x, z);
        return pos;
    }

    private void refreshSizePos(int x, int z) {
        if(x > maxX)
            maxX = x;
        else if(x < minX)
            minX = x;
        if(z > maxZ)
            maxZ = z;
        else if(z < minZ)
            minZ = z;
    }

    public int getMinX() {
        return minX;
    }
    public int getMinZ() {
        return minZ;
    }
    public int getMaxX() {
        return maxX;
    }
    public int getMaxZ() {
        return maxZ;
    }

    public int getSizeX() {
        return Math.abs(maxX - minX) +1;
    }
    public int getSizeZ() {
        return Math.abs(maxZ - minZ) +1;
    }
}
