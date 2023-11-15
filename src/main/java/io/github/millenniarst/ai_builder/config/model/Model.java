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

public class Model extends WallModel {

    private Schematic east;
    private Schematic west;

    private Schematic eastUp;
    private Schematic westUp;
    private Schematic northEast;
    private Schematic northWest;
    private Schematic southEast;
    private Schematic southWest;
    private Schematic eastDown;
    private Schematic westDown;

    private Schematic northEastUp;
    private Schematic northWestUp;
    private Schematic southEastUp;
    private Schematic southWestUp;
    private Schematic northEastDown;
    private Schematic northWestDown;
    private Schematic southEastDown;
    private Schematic southWestDown;

    public void build(Position origin, Position end) {
        build(origin.getPosX(), origin.getPosZ(), origin.getPosY(), end.getPosX(), end.getPosZ(), end.getPosY());
    }
    public void build(int originX, int originZ, int originY, int endX, int endZ, int endY) {
        base.buildRepeat3(new Position(originX + getSpaceX(west), originZ + getSpaceZ(south), originY + getSpaceY(down)), new Position(endX - getSpaceX(east), endZ - getSpaceZ(north), endY - getSpaceY(up)));

        north.buildRepeat2North(new Position(originX + getSpaceX(northWest), endZ, originY + getSpaceY(northDown)), new Position(endX - getSpaceX(northEast), endZ, endY - getSpaceY(northUp)), 0, 0);
        east.buildRepeat2East(new Position(endX, endZ - getSpaceZ(northEast), originY + getSpaceY(eastDown)), new Position(endX, originZ + getSpaceZ(southEast), endY - getSpaceY(northUp)), 0, 0);
        south.buildRepeat2South(new Position(endX - getSpaceX(southEast), originZ, originY + getSpaceY(northDown)), new Position(originX + getSpaceX(southWest), originZ, endY - getSpaceY(northUp)), 0, 0);
        west.buildRepeat2West(new Position(originX, originZ + getSpaceZ(southWest), originY + getSpaceY(eastDown)), new Position(originX, endZ - getSpaceZ(northWest), endY - getSpaceY(northUp)), 0, 0);
        down.buildRepeat2Down(new Position(originX + getSpaceX(westDown), originZ + getSpaceZ(southDown), originY), new Position(endX - getSpaceX(eastDown), endZ - getSpaceZ(northDown), originY), 0);
        up.buildRepeat2Up(new Position(originX + getSpaceX(westUp), originZ + getSpaceZ(southUp), endY), new Position(endX - getSpaceX(eastUp), endZ - getSpaceZ(northUp), endY), 0);

        northEast.buildRepeatUp(new Position(endX, endZ, originY + getSpaceY(northEastDown)), new Position(endX, endZ, endY - getSpaceY(northEastUp)));
        southEast.buildRepeatUp(new Position(endX, originZ, originY + getSpaceY(southEastDown)), new Position(endX, originZ, endY - getSpaceY(southEastUp)));
        southWest.buildRepeatUp(new Position(originX, originZ, originY + getSpaceY(southWestDown)), new Position(originX, originZ, endY - getSpaceY(southWestUp)));
        northWest.buildRepeatUp(new Position(originX, endZ, originY + getSpaceY(northWestDown)), new Position(originX, endZ, endY - getSpaceY(northWestUp)));
        northDown.buildRepeatNorth(new Position(originX + getSpaceX(northWestDown), endZ, originY), new Position(endX - getSpaceX(northEastDown), endZ, originY), 0);
        eastDown.buildRepeatEast(new Position(endX, endZ - getSpaceZ(northEastDown), originY), new Position(endX, originZ + getSpaceZ(southEastDown), originY), 0);
        southDown.buildRepeatSouth(new Position(endX - getSpaceX(southEastDown), originZ, originY), new Position(originX + getSpaceX(southWestDown), originZ, originY), 0);
        westDown.buildRepeatWest(new Position(originX, originZ + getSpaceZ(southWestDown), originY), new Position(originX, endZ - getSpaceZ(northWestDown), originY), 0);
        northUp.buildRepeatNorth(new Position(originX + getSpaceX(northWestUp), endZ, endY), new Position(endX - getSpaceX(northEastUp), endZ, endY), 0);
        eastUp.buildRepeatEast(new Position(endX, endZ - getSpaceZ(northEastUp), endY), new Position(endX, originZ + getSpaceZ(southEastUp), endY), 0);
        southUp.buildRepeatSouth(new Position(endX - getSpaceX(southEastUp), originZ, endY), new Position(originX + getSpaceX(southWestUp), originZ, endY), 0);
        westUp.buildRepeatWest(new Position(originX, originZ + getSpaceZ(southWestUp), endY), new Position(originX, endZ - getSpaceZ(northWestUp), endY), 0);

        northEastDown.clone().rotateMirrorXZ().build(endX, endZ, originY);
        southEastDown.cloneRotate(90).rotateMirrorXZ().build(endX, originZ, originY);
        southWestDown.cloneRotate(180).rotateMirrorXZ().build(originX, originZ, originY);
        northWestDown.cloneRotate(270).rotateMirrorXZ().build(originX, endZ, originY);
        northEastUp.build(endX, endZ, endY);
        southEastUp.cloneRotate(90).build(endX, originZ, endY);
        southWestUp.cloneRotate(180).build(originX, originZ, endY);
        northWestUp.cloneRotate(270).build(originX, endZ, endY);
    }
    protected int getSpaceX(Schematic schematic) {
        return schematic.getState().equals(Loader.STATE_ENABLE) ? (schematic.getMinX() -1) * -1 : 0;
    }
    protected int getSpaceZ(Schematic schematic) {
        return schematic.getState().equals(Loader.STATE_ENABLE) ? (schematic.getMinZ() -1) * -1 : 0;
    }

    public Model rotateY(int angle) {
        Model model = new Model();
        model.setState(getState());
        model.base = base;

        if(angle / 90 % 4 == 1) {
            model.north = west;
            model.east = north;
            model.south = east;
            model.west = south;

            model.northWest = southWest;
            model.northEast = northWest;
            model.southEast = northEast;
            model.southWest = southEast;
            model.northDown = westDown;
            model.eastDown = northDown;
            model.southDown = eastDown;
            model.westDown = southDown;
            model.northUp = westUp;
            model.eastUp = northUp;
            model.southUp = eastUp;
            model.westUp = southUp;

            model.northWestDown = southWestDown;
            model.northEastDown = northWestDown;
            model.southEastDown = northEastDown;
            model.southWestDown = southEastDown;
            model.northWestUp = southWestUp;
            model.northEastUp = northWestUp;
            model.southEastUp = northEastUp;
            model.southWestUp = southEastUp;
        }

        return model;
    }

    @Override
    public boolean load(String path) {
        path = "models\\" + path;
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));

        base = loadSchematic("base", file.getString("base", null));
        if(!base.getState().equals(Loader.STATE_ENABLE))
            return false;

        north = loadSchematic("north", file.getString("north", null));
        if(!north.getState().equals(Loader.STATE_ENABLE))
            return false;
        east = loadSchematic("east", file.getString("east", null));
        if(!east.getState().equals(Loader.STATE_ENABLE))
            return false;
        south = loadSchematic("south", file.getString("south", null));
        if(!south.getState().equals(Loader.STATE_ENABLE))
            return false;
        west = loadSchematic("west", file.getString("west", null));
        if(!west.getState().equals(Loader.STATE_ENABLE))
            return false;
        up = loadSchematic("up", file.getString("up", null));
        if(!up.getState().equals(Loader.STATE_ENABLE))
            return false;
        down = loadSchematic("down", file.getString("down", null));
        if(!down.getState().equals(Loader.STATE_ENABLE))
            return false;

        northUp = loadSchematic("north-up", file.getString("north-up", null));
        if(!northUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        eastUp = loadSchematic("east-up", file.getString("east-up", null));
        if(!eastUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        southUp = loadSchematic("south-up", file.getString("south-up", null));
        if(!southUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        westUp = loadSchematic("west-up", file.getString("west-up", null));
        if(!westUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        northEast = loadSchematic("north-east", file.getString("north-east", null));
        if(!northEast.getState().equals(Loader.STATE_ENABLE))
            return false;
        northWest = loadSchematic("north-west", file.getString("north-west", null));
        if(!northWest.getState().equals(Loader.STATE_ENABLE))
            return false;
        southEast = loadSchematic("south-east", file.getString("south-east", null));
        if(!southEast.getState().equals(Loader.STATE_ENABLE))
            return false;
        southWest = loadSchematic("south-west", file.getString("south-west", null));
        if(!southWest.getState().equals(Loader.STATE_ENABLE))
            return false;
        northDown = loadSchematic("north-down", file.getString("north-down", null));
        if(!northDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        eastDown = loadSchematic("east-down", file.getString("east-down", null));
        if(!eastDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        southDown = loadSchematic("south-down", file.getString("south-down", null));
        if(!southDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        westDown = loadSchematic("west-down", file.getString("west-down", null));
        if(!westDown.getState().equals(Loader.STATE_ENABLE))
            return false;

        northEastUp = loadSchematic("north-east-up", file.getString("north-east-up", null));
        if(!northEastUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        northWestUp = loadSchematic("north-west-up", file.getString("north-west-up", null));
        if(!northWestUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        southEastUp = loadSchematic("south-east-up", file.getString("south-east-up", null));
        if(!southEastUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        southWestUp = loadSchematic("south-west-up", file.getString("south-west-up", null));
        if(!southWestUp.getState().equals(Loader.STATE_ENABLE))
            return false;
        northEastDown = loadSchematic("north-east-down", file.getString("north-east-down", null));
        if(!northEastDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        northWestDown = loadSchematic("north-west-down", file.getString("north-west-down", null));
        if(!northWestDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        southEastDown = loadSchematic("south-east-down", file.getString("south-east-down", null));
        if(!southEastDown.getState().equals(Loader.STATE_ENABLE))
            return false;
        southWestDown = loadSchematic("south-west-down", file.getString("south-west-down", null));
        if(!southWestDown.getState().equals(Loader.STATE_ENABLE))
            return false;

        setState(Loader.STATE_ENABLE);
        return true;
    }

    private Schematic loadSchematic(String key, String path) {
        Schematic schematic = new Schematic();
        if(path != null) {
            if(!schematic.load(path)) {
                AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load " + key + " schematic: " + path);
                setState(Loader.STATE_DISABLE);
            }
        }
        return schematic;
    }

    public String toString() {
        return "[base=" + base +
                ",north=" + north + ",south=" + south + ",east=" + east + ",west=" + west + ",down=" + down + ",up=" + up +
                ",northWest=" + northWest + ",northEast=" + northEast + ",southWest=" + southWest + ",southEast=" + southEast +
                ",northDown=" + northDown + ",eastDown=" + eastDown + ",southDown=" + southDown + ",westDown=" + westDown +
                ",northUp=" + northUp + ",eastUp=" + eastUp + ",westUp=" + westUp + ",southUp=" + southUp +
                ",northWestDown=" + northWestDown + ",northEastDown=" + northEastDown + ",southWestDown=" + southWestDown + ",southEastDown=" + southEastDown +
                ",northWestUp=" + northWestUp + ",northEastUp=" + northEastUp + ",southWestUp=" + southWestUp + ",southEastUp=" + southEastUp + "]";
    }

    public Schematic getEast() {
        return east;
    }
    public Schematic getWest() {
        return west;
    }
    public Schematic getEastUp() {
        return eastUp;
    }
    public Schematic getWestUp() {
        return westUp;
    }
    public Schematic getNorthEast() {
        return northEast;
    }
    public Schematic getNorthWest() {
        return northWest;
    }
    public Schematic getSouthEast() {
        return southEast;
    }
    public Schematic getSouthWest() {
        return southWest;
    }
    public Schematic getEastDown() {
        return eastDown;
    }
    public Schematic getWestDown() {
        return westDown;
    }
    public Schematic getNorthEastUp() {
        return northEastUp;
    }
    public Schematic getNorthWestUp() {
        return northWestUp;
    }
    public Schematic getSouthEastUp() {
        return southEastUp;
    }
    public Schematic getSouthWestUp() {
        return southWestUp;
    }
    public Schematic getNorthEastDown() {
        return northEastDown;
    }
    public Schematic getNorthWestDown() {
        return northWestDown;
    }
    public Schematic getSouthEastDown() {
        return southEastDown;
    }
    public Schematic getSouthWestDown() {
        return southWestDown;
    }
}