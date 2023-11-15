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

import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;

public class ParallelepipedArea extends PrismArea<MapRectangleArea> {

    public ParallelepipedArea(MapRectangleArea root) {
        super(root);
    }

    public void putPos(int x, int z, int y, Position pos) {
        super.putPos(x, z, y, pos);

        getRoot().refreshSize();
    }

    public Position getAndCreatePos(int x, int z, int y) {
        Position pos = super.getAndCreatePos(x, z, y);

        getRoot().refreshSize();
        return pos;
    }
}
