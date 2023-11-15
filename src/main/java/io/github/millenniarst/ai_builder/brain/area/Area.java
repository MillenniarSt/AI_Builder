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

import java.util.HashMap;

public class Area {

    private final HashMap<int[], Position> positions;

    public Area() {
        this.positions = new HashMap<>();
    }

    public void putPos(int x, int z, int y, AIBlockData block) {
        putPos(x, z, y, new Position(x, z, y, block));
    }

    public void putPos(int x, int z, int y, Position pos) {
        positions.put(new int[]{x, z, y}, pos);
    }

    public void fillPos(int x1, int z1, int y1, int x2, int z2, int y2, AIBlockData block) {
        for(int x = x1; x <= x2; x++) {
            for(int z = z1; z <= z2; z++) {
                for(int y = y1; y <= y2; y++) {
                    putPos(x, z, y, block);
                }
            }
        }
    }

    public Position getPos(int x, int z, int y) {
        return positions.get(new int[]{x, z, y});
    }

    public Position getAndCreatePos(int x, int z, int y) {
        Position pos = positions.get(new int[]{x, z, y});
        if(pos == null)
            putPos(x, z, y, new Position(x, z, y));
        return pos;
    }

    public boolean containsPos(int x, int z, int y) {
        return positions.containsKey(new int[]{x, z, y});
    }

    protected HashMap<int[], Position> getPositions() {
        return positions;
    }
}
