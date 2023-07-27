package Model;

import Main.RandomCollection;

public class Position {

	private int posX;
	private int posZ;
	private int posY;
	
	private RandomCollection<AIBlockData> block;
	
	public Position(int posX, int posZ, int posY) {
		this.posX = posX;
		this.posZ = posZ;
		this.posY = posY;
		this.block = null;
	}
	public Position(int posX, int posZ, int posY, RandomCollection<AIBlockData> block) {
		this.posX = posX;
		this.posZ = posZ;
		this.posY = posY;
		this.block = block;
	}
	
	public String toString() {
		return posX + " - " + posZ + " - " + posY + " - ";
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosZ() {
		return posZ;
	}
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public RandomCollection<AIBlockData> getBlock() {
		return block;
	}
	public void setBlock(RandomCollection<AIBlockData> block) {
		this.block = block;
	}
}
