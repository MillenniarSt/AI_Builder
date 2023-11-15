package io.github.millenniarst.ai_builder.config.building.house;

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.exception.RoomNotFoundException;
import io.github.millenniarst.ai_builder.exception.WallNotFoundException;
import io.github.millenniarst.ai_builder.config.component.MapRoomConfig;
import io.github.millenniarst.ai_builder.util.Random;
import io.github.millenniarst.ai_builder.util.RandomCollection;
import io.github.millenniarst.ai_builder.config.building.BuildingStyle;
import io.github.millenniarst.ai_builder.config.component.WallConfig;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class HouseStyle extends BuildingStyle {

    private RandomCollection<MapRoomConfig> roomWeigh;
    private RandomCollection<WallConfig> wallWeigh;

    private int wallsize;

    public HouseStyle(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean load(String path) {
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));

        roomWeigh.getCollection().clear();
        Set<String> rooms = file.getConfigurationSection("rooms").getKeys(false);
        if(rooms != null) {
            if(!rooms.isEmpty()) {
                for(String key : rooms) {
                    MapRoomConfig room = new MapRoomConfig(key);
                    if(!room.load(key)) {
                        AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load room style " + key);
                        this.setState(STATE_DISABLE);
                        return false;
                    }
                    roomWeigh.add(room, getInt(file, "rooms." + key, 1));
                }
            }
        }
        wallWeigh.getCollection().clear();
        Set<String> walls = file.getConfigurationSection("walls").getKeys(false);
        if(walls != null) {
            if(!walls.isEmpty()) {
                for(String key : walls) {
                    WallConfig wall = new WallConfig(key);
                    if(!wall.load(key)) {
                        AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load wall style " + key);
                        this.setState(STATE_DISABLE);
                        return false;
                    }
                    wallWeigh.add(wall, getInt(file, "walls." + key, 1));
                }
            }
        }

        this.wallsize = getInt(file, "wallsize", 1);

        this.setState(STATE_ENABLE);
        return super.load(path);
    }

    public MapRoomConfig getRandomRoom(int floor) throws AIObjectNotFoundException {
        AI_Builder.printDebug("Searching a room in the floor " + floor + " by array: " + this.getRoomWeigh());
        RandomCollection<MapRoomConfig> rooms = new RandomCollection<>();
        for(Random<MapRoomConfig> room : this.getRoomWeigh().getCollection()) {
            if(floor >= room.getObject().getMinFloor() && floor <= room.getObject().getMaxFloor()) {
                rooms.add(room);
            }
        }
        AI_Builder.printDebug("Searching a room by array: " + rooms);
        if(rooms.getCollection().isEmpty()) {
            throw new RoomNotFoundException(floor);
        } else {
            MapRoomConfig room = rooms.getRandom();
            AI_Builder.printDebug("Return room " + room);
            return room;
        }
    }
    public MapRoomConfig getRandomRoom(int floor, int lenghtX, int lenghtZ) throws AIObjectNotFoundException {
        AI_Builder.printDebug("Searching a room in the floor " + floor + ", lenght " + lenghtX + " - " + lenghtZ + " by array: " + this.getRoomWeigh());
        RandomCollection<MapRoomConfig> rooms = new RandomCollection<>();
        for(Random<MapRoomConfig> random : this.getRoomWeigh().getCollection()) {
            MapRoomConfig room = random.getObject();
            if(floor >= room.getMinFloor() && floor <= room.getMaxFloor() && (
                    (lenghtX >= room.getMinX() && lenghtX <= room.getMaxX() && lenghtZ >= room.getMinZ() && lenghtZ <= room.getMaxZ()) ||
                            (lenghtZ >= room.getMinX() && lenghtZ <= room.getMaxX() && lenghtX >= room.getMinZ() && lenghtX <= room.getMaxZ()))) {
                rooms.add(random);
            }
        }
        AI_Builder.printDebug("Searching a room by array: " + rooms);
        if(rooms.getCollection().isEmpty()) {
            throw new RoomNotFoundException(floor, lenghtX, lenghtZ);
        } else {
            MapRoomConfig room = rooms.getRandom();
            AI_Builder.printDebug("Return room " + room);
            return room;
        }
    }

    public WallConfig getRandomWall(int floor, int high, boolean inside) throws WallNotFoundException, AIObjectNotFoundException {
        AI_Builder.printDebug("Searching a wall in the floor " + floor + " with inside = " + inside + " by array: " + this.getWallWeigh());
        RandomCollection<WallConfig> walls = new RandomCollection<>();
        for(Random<WallConfig> wall : this.getWallWeigh().getCollection()) {
            if(floor >= wall.getObject().getMinFloor() && floor <= wall.getObject().getMaxFloor() &&
                    high >= wall.getObject().getMinHigh() && high <= wall.getObject().getMinHigh() &&
                    inside == wall.getObject().isInside()) {
                walls.add(wall);
            }
        }
        AI_Builder.printDebug("Searching a wall by array: " + walls);
        if(walls.getCollection().isEmpty()) {
            throw new WallNotFoundException(floor, high, inside);
        } else {
            WallConfig wall = walls.getRandom();
            AI_Builder.printDebug("Return wall " + wall);
            return wall;
        }
    }

    public String getDirectoryPath() {
        return super.getDirectoryPath() + "house\\";
    }

    public int getWallsize() {
        return wallsize;
    }
    public void setWallsize(int wallsize) {
        this.wallsize = wallsize;
    }
    public RandomCollection<MapRoomConfig> getRoomWeigh() {
        return roomWeigh;
    }
    public void setRoomWeigh(RandomCollection<MapRoomConfig> roomWeigh) {
        this.roomWeigh = roomWeigh;
    }
    public RandomCollection<WallConfig> getWallWeigh() {
        return wallWeigh;
    }
    public void setWallWeigh(RandomCollection<WallConfig> wallWeigh) {
        this.wallWeigh = wallWeigh;
    }
}
