package io.github.millenniarst.ai_builder.config.model;

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
import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.config.Loader;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SingleModel implements Loader {

    protected String state;

    protected Schematic base;

    public SingleModel() {
        this.state = Loader.STATE_VOID;
    }

    public void build(Position pos) {
        build(pos.getPosX(), pos.getPosZ(), pos.getPosY());
    }
    public void build(int x, int z, int y) {
        base.build(x, z, y);
    }

    public void buildRotate(Position pos, int rotate) {
        buildRotate(pos.getPosX(), pos.getPosZ(), pos.getPosY(), rotate);
    }
    public void buildRotate(int x, int z, int y, int rotate) {
        rotateY(rotate).build(x, z, y);
    }

    public SingleModel rotateY(int rotate) {
        SingleModel model = new SingleModel();

        model.base = base.rotateY(rotate);

        model.setState(getState());
        return model;
    }

    @Override
    public boolean load(String path) {
        path = "models\\" + path;
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));

        base = new Schematic();
        path = file.getString("base", null);
        if(path != null) {
            if(!base.load(path)) {
                AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load schematic: " + path);
                setState(Loader.STATE_DISABLE);
                return false;
            }
        } else {
            setState(Loader.STATE_ERROR);
            return false;
        }

        setState(Loader.STATE_ENABLE);
        return true;
    }

    @Override
    public String toString() {
        return "[base=" + base + "]";
    }

    @Override
    public String getState() {
        return state;
    }
    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getDirectoryPath() {
        return null;
    }

    public Schematic getBase() {
        return base;
    }
}
