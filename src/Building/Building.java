package Building;

import java.util.HashMap;

import Main.RandomCollection;
import Model.AIBlockData;
import Model.Position;

public class Building {

	private String name;
	
	private HashMap<int[], Position> positions;
	
	protected Building(String name) {
		this.name = name;
	}
	
	public void addPos(Position pos) {
		int[] arg = {pos.getPosX(), pos.getPosZ(), pos.getPosY()};
		this.positions.put(arg, pos);
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<int[], Position> getPositions() {
		return positions;
	}
	public void setPositions(HashMap<int[], Position> positions) {
		this.positions = positions;
	}
}
