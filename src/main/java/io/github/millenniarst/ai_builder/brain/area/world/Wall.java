package io.github.millenniarst.ai_builder.brain.area.world;

import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.brain.area.PrismArea;
import io.github.millenniarst.ai_builder.brain.map.MapArea;
import io.github.millenniarst.ai_builder.brain.map.MapPosition;
import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;
import io.github.millenniarst.ai_builder.brain.building.Building;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.config.component.DoorConfig;
import io.github.millenniarst.ai_builder.config.model.Schematic;
import io.github.millenniarst.ai_builder.config.component.WallConfig;
import io.github.millenniarst.ai_builder.config.component.Window;

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

public class Wall<M extends MapArea> extends PrismArea<M> {

	private WallConfig config;

	private boolean inside;
	private DoorConfig doorConfig;
	private Position doorPos;
	private Window window;

	private boolean corner = true;
	private boolean angleLeft;
	private boolean angleRight;
	
	private final int id;
	
	public Wall(WallConfig config, M root, MapPosition doorPos, boolean inside, int id, boolean angleLeft, boolean angleRight) {
		super(root);
		this.config = config;
		this.inside = inside;
		this.doorConfig = null;
		if(doorPos != null)
			this.doorPos = new Position(doorPos.getPosX(), doorPos.getPosZ(), getMinY());
		this.window = null;
		this.id = id;

		this.angleLeft = angleLeft;
		this.angleRight = angleRight;
		
		refreshSize();
	}
	
	public void build(Building<?> building) throws AIObjectNotFoundException {
		AI_Builder.printDebug("Building wall " + this);
		building.getStyle().changeIndexs();

		if(getRoot() instanceof MapRectangleArea rectangleArea) {
			int variationCorner = 0;
			if (config.getCorner() != null) {
				variationCorner = config.getCorner().getSize() - config.getCorner().getRelX();
				if (variationCorner < 0)
					variationCorner = 0;
			}
			int windows = 0;
			int dis = 0;
			int disWindows = 0;
			int disWindowsAdd = 0;
			if (doorConfig == null) {
				this.window = config.getWindow().getRandom();
				windows = ((id % 2 == 0 ? rectangleArea.getSizeX() : rectangleArea.getSizeZ()) - variationCorner) / (window.getModel().getBase().getSizeX() + window.getPrefDistance());
				dis = ((id % 2 == 0 ? rectangleArea.getSizeX() : rectangleArea.getSizeZ()) - variationCorner) % (window.getModel().getBase().getSizeX() + window.getPrefDistance());
				disWindows = (dis / windows) + window.getPrefDistance();
				disWindowsAdd = dis % windows;
			}

			if (id == 0) {
				if (config.getCorner() != null && corner) {
					AI_Builder.printDebug("Building wall's corner");

					config.getCorner().getModel().build(rectangleArea.getMinX() - (angleLeft ? config.getCorner().getRelX() : 0), rectangleArea.getMaxZ() + config.getCorner().getRelZ(), getMinY(), getMaxY());
					config.getCorner().getModel().build(rectangleArea.getMaxX() + (angleLeft ? config.getCorner().getRelX() : 0), rectangleArea.getMaxZ() + config.getCorner().getRelZ(), getMinY(), getMaxY());
				}
				AI_Builder.printDebug("Building wall's models");
				config.getModel().build(rectangleArea.getMinX(), rectangleArea.getMaxZ(), getMinY(), rectangleArea.getMaxX(), getMaxY());
				if (doorConfig != null) {
					AI_Builder.printDebug("Building wall's door");
					doorConfig.getModel().buildRotate(doorPos, 90);
				} else {
					AI_Builder.printDebug("Building wall's windows");
					int x = rectangleArea.getMinX() + variationCorner + disWindows;
					for (int i = 1; i <= windows; i++) {
						if (disWindowsAdd > 0)
							x++;
						window.getModel().build(x, rectangleArea.getMaxZ(), getMinY() + window.getHigh());
						x = x + window.getModel().getBase().getSizeX() + disWindows;
						disWindowsAdd--;
					}
				}

			} else if (id == 1) {
				if (config.getCorner() != null) {
					AI_Builder.printDebug("Building wall's corner");
					for (int x = 0; x > config.getCorner().getSize() * -1; x--) {
						for (int z = 0; z > config.getCorner().getSize() * -1; z--) {
							for (int y = 0; y < high; y++) {
								building.setPos(origin.getPosX() + size - 1 + config.getCorner().getRelZ() + x,
										origin.getPosZ() + config.getCorner().getRelX() + z,
										origin.getPosY() + y, config.getCorner().getModel());
							}
						}
					}
				}
				AI_Builder.printDebug("Building wall's models");
				config.getModel().rotateY(90).build(origin, end);
				if (doorConfig != null) {
					AI_Builder.printDebug("Building wall's door");
					doorConfig.getModel().build(doorConfig.getOrigin());
				} else {
					AI_Builder.printDebug("Building wall's windows");
					Schematic windowModel = window.getModel().getBase().cloneRotate(90);
					int z = origin.getPosZ() - variationCorner - disWindows;
					for (int i = 1; i <= windows; i++) {
						if (disWindowsAdd > 0)
							z--;
						windowModel.build(end.getPosX(), z, origin.getPosY() + window.getHigh());
						z = z - windowModel.getSizeX() - disWindows;
						disWindowsAdd--;
					}
				}

			} else if (id == 2) {
				if (config.getCorner() != null) {
					AI_Builder.printDebug("Building wall's corner");
					for (int x = 0; x > config.getCorner().getSize() * -1; x--) {
						for (int z = 0; z < config.getCorner().getSize(); z++) {
							for (int y = 0; y < high; y++) {
								building.setPos(origin.getPosX() + config.getCorner().getRelX() + x,
										origin.getPosZ() - size + 1 + config.getCorner().getRelZ() + z,
										origin.getPosY() + y, config.getCorner().getModel());
							}
						}
					}
				}
				AI_Builder.printDebug("Building wall's models");
				config.getModel().rotateY(180).build(origin, end);
				if (doorConfig != null) {
					AI_Builder.printDebug("Building wall's door");
					doorConfig.getModel().build(doorConfig.getOrigin(), 180);
				} else {
					AI_Builder.printDebug("Building wall's windows");
					Schematic windowModel = window.getModel().getBase().cloneRotate(180);
					int x = origin.getPosX() - variationCorner - disWindows;
					for (int i = 1; i <= windows; i++) {
						if (disWindowsAdd > 0)
							x--;
						windowModel.build(x, end.getPosZ(), origin.getPosY() + window.getHigh());
						x = x - windowModel.getSizeX() - disWindows;
						disWindowsAdd--;
					}
				}

			} else if (id == 3) {
				if (config.getCorner() != null) {
					for (int x = 0; x < config.getCorner().getSize(); x++) {
						for (int z = 0; z < config.getCorner().getSize(); z++) {
							for (int y = 0; y < high; y++) {
								building.setPos(origin.getPosX() - size + 1 + config.getCorner().getRelZ() + x,
										origin.getPosZ() - config.getCorner().getRelX() + z,
										origin.getPosY() + y, config.getCorner().getModel());
							}
						}
					}
				}
				AI_Builder.printDebug("Building wall's models");
				config.getModel().rotateY(270).build(origin, end);
				if (doorConfig != null) {
					AI_Builder.printDebug("Building wall's door");
					doorConfig.getModel().build(doorConfig.getOrigin(), 270);
				} else {
					AI_Builder.printDebug("Building wall's windows");
					Schematic windowModel = window.getModel().getBase().cloneRotate(270);
					int z = origin.getPosZ() + variationCorner + disWindows;
					for (int i = 1; i <= windows; i++) {
						if (disWindowsAdd > 0)
							z++;
						windowModel.build(end.getPosX(), z, origin.getPosY() + window.getHigh());
						z = z + windowModel.getSizeX() + disWindows;
						disWindowsAdd--;
					}
				}
			}
		}
	}
	
	public String toString() {
		return "Wall root= [" + getRoot() + "] with length " + length + " size " + size + " high " + high + " and data = [style=[" +
				config + "],inside=" + inside + ",door=" + doorConfig + ",window=" + window + "]";
	}
	
	public WallConfig getConfig() {
		return config;
	}
	public void setConfig(WallConfig config) {
		this.config = config;
	}
	public int getId() {
		return id;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public DoorConfig isDoor() {
		return doorConfig;
	}
	public void setDoor(DoorConfig doorConfig) {
		this.doorConfig = doorConfig;
	}
	public Window getWindow() {
		return window;
	}
	public void setWindow(Window window) {
		this.window = window;
	}
	public DoorConfig getDoorConfig() {
		return doorConfig;
	}
	public void setCorner(boolean corner) {
		this.corner = corner;
	}
	public boolean isCorner() {
		return corner;
	}
	public void setAngleLeft(boolean angleLeft) {
		this.angleLeft = angleLeft;
	}
	public void setAngleRight(boolean angleRight) {
		this.angleRight = angleRight;
	}
	public boolean isAngleLeft() {
		return angleLeft;
	}
	public boolean isAngleRight() {
		return angleRight;
	}
}