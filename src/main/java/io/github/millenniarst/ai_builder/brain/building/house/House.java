package io.github.millenniarst.ai_builder.brain.building.house;

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

import io.github.millenniarst.ai_builder.brain.building.Building;
import io.github.millenniarst.ai_builder.config.Style;
import io.github.millenniarst.ai_builder.config.building.house.HouseStyle;

import java.util.ArrayList;

public class House<S extends HouseStyle> extends Building<S> {

    private ArrayList<int[]> wallPosNotUsed = new ArrayList<>();

    protected House(Style style, S houseStyle) {
        super(style, houseStyle);
    }

    public void addWallPos(int x, int z) {
        wallPosNotUsed.add(new int[]{x, z});
    }

    public boolean isWallPosUsed(int x, int z) {
        return wallPosNotUsed.contains(new int[]{x, z});
    }

    public void useWallPos(int x, int z) {
        wallPosNotUsed.remove(new int[]{x, z});
    }

    public void useWallPos(int x, int z1, int z2) {
        for(int i = z1; i <= z2; i++)
            wallPosNotUsed.remove(new int[]{x, i});
    }
}
