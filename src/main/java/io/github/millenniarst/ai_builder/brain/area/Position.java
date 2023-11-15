package io.github.millenniarst.ai_builder.brain.area;

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

public class Position {

	private int posX;
	private int posZ;
	private int posY;
	
	private AIBlockData block;
	
	public Position(int posX, int posZ, int posY) {
		this.posX = posX;
		this.posZ = posZ;
		this.posY = posY;
		this.setBlock(null);
	}
	public Position(int posX, int posZ, int posY, AIBlockData block) {
		this.posX = posX;
		this.posZ = posZ;
		this.posY = posY;
		this.setBlock(block);
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
	public AIBlockData getBlock() {
		return block;
	}
	public void setBlock(AIBlockData block) {
		this.block = block;
	}
	/*public void setBlock(RandomCollection<AIBlockData> random) {
		this.block = random;
		if(random != null) {
			try {
				AIBlockData material = random.getRandom();
				Block block = CommandExe.getCurrentWorld().getBlockAt(CommandExe.getCurrentBuilding().getPosition().getBlockX() + posX, 
						CommandExe.getCurrentBuilding().getPosition().getBlockY() + posY, 
						CommandExe.getCurrentBuilding().getPosition().getBlockZ() + posZ);
				block.setType(material.getCompleteMaterial(CommandExe.getCurrentBuilding().getStyle()));
				
				BlockData data = block.getBlockData();
				if(data instanceof Ageable && material.getProperty("age") != null) {
					try {
						int age = Integer.parseInt(material.getProperty("age"));
						((Ageable) data).setAge(age);
					} catch(NumberFormatException exc) { }
				}
				if(data instanceof AnaloguePowerable && material.getProperty("power") != null) {
					try {
						int power = Integer.parseInt(material.getProperty("power"));
						((AnaloguePowerable) data).setPower(power);
					} catch(NumberFormatException exc) { }
				}
				if(data instanceof Attachable && material.getProperty("attached") != null) {
					try {
						boolean attach = Boolean.parseBoolean(material.getProperty("attached"));
						((Attachable) data).setAttached(attach);
					} catch(Exception exc) { }
				}
				if(data instanceof Bisected && material.getProperty("half") != null) {
					Half half = Half.valueOf(material.getProperty("half"));
					if(half != null)
						((Bisected) data).setHalf(half);
				}
				if(data instanceof Directional && material.getProperty("facing") != null) {
					BlockFace face = BlockFace.valueOf(material.getProperty("facing"));
					if(face != null)
						((Directional) data).setFacing(face);
				}
				if(data instanceof FaceAttachable && material.getProperty("attached_face") != null) {
					AttachedFace attachedFace = AttachedFace.valueOf(material.getProperty("attached_face"));
					if(attachedFace != null)
						((FaceAttachable) data).setAttachedFace(attachedFace);
				}
				if(data instanceof Levelled && material.getProperty("level") != null) {
					try {
						int level = Integer.parseInt(material.getProperty("level"));
						((Levelled) data).setLevel(level);
					} catch(NumberFormatException exc) { }
				}
				if(data instanceof Lightable && material.getProperty("lit") != null) {
					try {
						boolean lit = Boolean.parseBoolean(material.getProperty("lit"));
						((Lightable) data).setLit(lit);
					} catch(Exception exc) { }
				}
				if(data instanceof MultipleFacing) {
					try {
						if(material.getProperty("north") != null)
							((MultipleFacing) data).setFace(BlockFace.NORTH, Boolean.parseBoolean(material.getProperty("north")));
						if(material.getProperty("east") != null)
							((MultipleFacing) data).setFace(BlockFace.EAST, Boolean.parseBoolean(material.getProperty("east")));
						if(material.getProperty("south") != null)
							((MultipleFacing) data).setFace(BlockFace.SOUTH, Boolean.parseBoolean(material.getProperty("south")));
						if(material.getProperty("west") != null)
							((MultipleFacing) data).setFace(BlockFace.WEST, Boolean.parseBoolean(material.getProperty("west")));
						if(material.getProperty("up") != null)
							((MultipleFacing) data).setFace(BlockFace.UP, Boolean.parseBoolean(material.getProperty("up")));
						if(material.getProperty("down") != null)
							((MultipleFacing) data).setFace(BlockFace.DOWN, Boolean.parseBoolean(material.getProperty("down")));
					} catch(Exception exc) { }
				}
				if(data instanceof Openable && material.getProperty("open") != null) {
					try {
						boolean open = Boolean.parseBoolean(material.getProperty("open"));
						((Openable) data).setOpen(open);
					} catch(Exception exc) { }
				}
				if(data instanceof Orientable && material.getProperty("axis") != null) {
					Axis axis = Axis.valueOf(material.getProperty("axis"));
					if(axis != null)
						((Orientable) data).setAxis(axis);
				}
				if(data instanceof Powerable && material.getProperty("powered") != null) {
					try {
						boolean powered = Boolean.parseBoolean(material.getProperty("powered"));
						((Powerable) data).setPowered(powered);
					} catch(Exception exc) { }
				}
				if(data instanceof Rail && material.getProperty("shape") != null) {
					Shape shape = Shape.valueOf(material.getProperty("shape"));
					if(shape != null)
						((Rail) data).setShape(shape);
				}
				if(data instanceof Rotatable && material.getProperty("rotation") != null) {
					BlockFace rotation = BlockFace.valueOf(material.getProperty("rotation"));
					if(rotation != null)
						((Rotatable) data).setRotation(rotation);
				}
				if(data instanceof Snowable && material.getProperty("snowy") != null) {
					try {
						boolean snowy = Boolean.parseBoolean(material.getProperty("snowy"));
						((Snowable) data).setSnowy(snowy);
					} catch(Exception exc) { }
				}
				if(data instanceof Waterlogged && material.getProperty("waterlogged") != null) {
					try {
						boolean waterlogged = Boolean.parseBoolean(material.getProperty("waterlogged"));
						((Waterlogged) data).setWaterlogged(waterlogged);
					} catch(Exception exc) { }
				}
				if(data instanceof Bed && material.getProperty("part") != null) {
					Part part = Part.valueOf(material.getProperty("part"));
					if(part != null)
						((Bed) data).setPart(part);
				}
				if(data instanceof Beehive && material.getProperty("honey_level") != null) {
					try {
						int honey_level = Integer.parseInt(material.getProperty("honey_level"));
						((Beehive) data).setHoneyLevel(honey_level);
					} catch(NumberFormatException exc) { }
				}
				if(data instanceof Bell && material.getProperty("attachment") != null) {
					Attachment attachment = Attachment.valueOf(material.getProperty("attachment"));
					if(attachment != null)
						((Bell) data).setAttachment(attachment);
				}
				if(data instanceof BigDripleaf && material.getProperty("tilt") != null) {
					Tilt tilt = Tilt.valueOf(material.getProperty("tilt"));
					if(tilt != null)
						((BigDripleaf) data).setTilt(tilt);
				}
				if(data instanceof BubbleColumn && material.getProperty("drag") != null) {
					try {
						boolean drag = Boolean.parseBoolean(material.getProperty("drag"));
						((BubbleColumn) data).setDrag(drag);
					} catch(Exception exc) { }
				}
				if(data instanceof Cake && material.getProperty("bites") != null) {
					try {
						int bites = Integer.parseInt(material.getProperty("bites"));
						((Cake) data).setBites(bites);
					} catch(NumberFormatException exc) { }
				}
				if(data instanceof Campfire && material.getProperty("signal_fire") != null) {
					try {
						boolean signal_fire = Boolean.parseBoolean(material.getProperty("signal_fire"));
						((Campfire) data).setSignalFire(signal_fire);
					} catch(Exception exc) { }
				}
			} catch (AIObjectNotFoundException exc) {
				exc.printStackTrace();
			}
		}
	}*/
}
