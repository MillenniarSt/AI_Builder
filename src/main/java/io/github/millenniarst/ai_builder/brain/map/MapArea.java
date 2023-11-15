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

import java.util.HashMap;

public class MapArea {

    private final HashMap<int[], MapPosition> positions;

    public MapArea() {
        this.positions = new HashMap<>();
    }

    public void putPos(int x, int z, MapPosition.MapPositionOpt posOpt) {
        putPos(x, z, new MapPosition(x, z, posOpt));
    }

    public void putPos(int x, int z, MapPosition pos) {
        positions.put(new int[]{x, z}, pos);
    }

    public void fillPos(int x1, int z1, int x2, int z2, MapPosition.MapPositionOpt posOpt) {
        for(int x = x1; x <= x2; x++) {
            for(int z = z1; z <= z2; z++) {
                putPos(x, z, posOpt);
            }
        }
    }

    public MapPosition getPos(int x, int z) {
        return positions.get(new int[]{x, z});
    }

    public MapPosition getAndCreatePos(int x, int z) {
        MapPosition pos = positions.get(new int[]{x, z});
        if(pos == null)
            putPos(x, z, new MapPosition(x, z));
        return pos;
    }

    public boolean containsPos(int x, int z) {
        return positions.containsKey(new int[]{x, z});
    }

    protected HashMap<int[], MapPosition> getPositions() {
        return positions;
    }
}
