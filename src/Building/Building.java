package Building;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;

import Main.RandomCollection;
import Main.Style;
import Model.AIBlockData;
import Model.Position;

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

public class Building {
	
	private Style style;
	private BuildingStyle styleBuild;
	
	private HashMap<int[], Block> oldPositions;
	private HashMap<int[], Position> positions;
	
	private Location position;
	
	public Building(Style style, BuildingStyle styleBuild) {
		this.style = style;
		this.styleBuild = styleBuild;
		this.positions = new HashMap<>();
	}
	protected Building(Style style) {
		this.style = style;
		this.styleBuild = null;
		this.positions = new HashMap<>();
	}
	
	public void addPos(Position pos) {
		addPos(pos.getPosX(), pos.getPosZ(), pos.getPosY());
	}
	public void addPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		this.positions.put(arg, new Position(x, z, y));
	}
	public void addPos(int x, int z, int y, RandomCollection<AIBlockData> block) {
		int[] arg = {x, z, y};
		this.positions.put(arg, new Position(x, z, y, block));
	}
	public void setPos(int x, int z, int y, RandomCollection<AIBlockData> block) {
		Position pos = getPos(x, z, y);
		if(pos == null) {
			addPos(x, z, y, block);
		} else {
			pos.setBlock(block);
		}
	}
	public Position getPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.positions.get(arg);
	}
	public RandomCollection<AIBlockData> getDataPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.positions.get(arg).getBlock();
	}
	
	public HashMap<int[], Position> getPositions() {
		return positions;
	}
	public void setPositions(HashMap<int[], Position> positions) {
		this.positions = positions;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public BuildingStyle getStyleBuild() {
		return styleBuild;
	}
	public void setStyleBuild(BuildingStyle styleBuild) {
		this.styleBuild = styleBuild;
	}
	public HashMap<int[], Block> getOldPositions() {
		return oldPositions;
	}
	public void setOldPositions(HashMap<int[], Block> oldPositions) {
		this.oldPositions = oldPositions;
	}
	public Location getPosition() {
		return position;
	}
	public void setPosition(Location position) {
		this.position = position;
	}
}
