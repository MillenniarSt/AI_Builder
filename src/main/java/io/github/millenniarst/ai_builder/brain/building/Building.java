package io.github.millenniarst.ai_builder.brain.building;

import java.util.HashMap;

import io.github.millenniarst.ai_builder.AI_Builder;
import org.bukkit.Location;
import org.bukkit.block.Block;

import io.github.millenniarst.ai_builder.brain.area.AIBlockData;
import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.config.building.BuildingStyle;
import io.github.millenniarst.ai_builder.config.Style;

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

public class Building<S extends BuildingStyle> {
	
	private Style style;
	private S styleBuild;
	
	private HashMap<int[], Block> oldPositions;
	private HashMap<int[], Position> positions;
	
	private Location position;

	protected Building(Style style, S styleBuild) {
		this.style = style;
		this.styleBuild = styleBuild;
		this.positions = new HashMap<>();
	}

	protected void build() {
		AI_Builder.printDebug("Build: " + this);
		AI_Builder.print("Starting to create build " + this.getStyleBuild().getName());
		AI_Builder.setExecute(true);
	}
	
	public void addPos(Position pos) {
		addPos(pos.getPosX(), pos.getPosZ(), pos.getPosY());
	}
	public void addPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		this.positions.put(arg, new Position(x, z, y));
	}
	public void addPos(int x, int z, int y, AIBlockData block) {
		int[] arg = {x, z, y};
		this.positions.put(arg, new Position(x, z, y, block));
	}
	public void setPos(int x, int z, int y, AIBlockData block) {
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
	public AIBlockData getDataPos(int x, int z, int y) {
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
	public S getStyleBuild() {
		return styleBuild;
	}
	public void setStyleBuild(S styleBuild) {
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
