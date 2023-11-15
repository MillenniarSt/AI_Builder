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

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.exception.IdNotFoundException;
import io.github.millenniarst.ai_builder.exception.OverlapMapException;
import io.github.millenniarst.ai_builder.brain.map.house.MapRoom;
import io.github.millenniarst.ai_builder.brain.area.world.house.Room;
import io.github.millenniarst.ai_builder.config.Style;
import io.github.millenniarst.ai_builder.config.building.house.SmallHouseStyle;
import org.bukkit.ChatColor;

public class SmallHouse extends House<SmallHouseStyle> {

    private Room<MapRectangleArea> room;

    public SmallHouse(Style style, SmallHouseStyle smallHouseStyle) {
        super(style, smallHouseStyle);
    }

    public void build() {
        super.build();
        try {
            MapRoom<MapRectangleArea> mapRoom = MapRoom.createRectangleRoom(getStyleBuild().getRandomRoom(0), 0, 0);
            room = new Room<>(mapRoom, 0, getStyleBuild().getFloorHigh());
            room.build(this);
        } catch(NumberFormatException | AIObjectNotFoundException | OverlapMapException | IdNotFoundException exc) {
            AI_Builder.print("Fail to create build: please look to the console", ChatColor.RED);
            AI_Builder.printConsole(exc.getMessage(), ChatColor.RED);
        } catch(Exception exc) {
            AI_Builder.print("Fail to create build: please look to the console", ChatColor.RED);
            AI_Builder.printConsole(exc.getMessage(), ChatColor.RED);
        }
        AI_Builder.setExecute(false);
    }

    public Room<MapRectangleArea> getRoom() {
        return room;
    }
}
