package io.github.millenniarst.ai_builder.config.building.house;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SmallHouseStyle extends HouseStyle {

    private int floorHigh;

    public SmallHouseStyle(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean load(String path) {
        path = getDirectoryPath() + path;
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));

        this.floorHigh = getInt(file, "floor-high", 1, 3);

        this.setState(STATE_ENABLE);
        return super.load(path);
    }

    public String getDirectoryPath() {
        return super.getDirectoryPath() + "small\\";
    }

    public String toString() {
        return this.getName() + ": floor high " + this.floorHigh + ", wall size " + this.getWallsize();
    }

    public int getFloorHigh() {
        return floorHigh;
    }
    public void setFloorHigh(int floorHigh) {
        this.floorHigh = floorHigh;
    }
}
