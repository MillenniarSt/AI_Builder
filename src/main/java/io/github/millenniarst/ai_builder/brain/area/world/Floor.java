package io.github.millenniarst.ai_builder.brain.area.world;

import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.brain.map.MapArea;
import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;
import io.github.millenniarst.ai_builder.brain.building.Building;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.config.component.FloorConfig;
import io.github.millenniarst.ai_builder.config.model.SingleModel;

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

public class Floor<M extends MapArea> {

	private final FloorConfig style;

	private final M root;
	private final int minY;
	private final int maxY;

	private final boolean inside;
	private final boolean ceiling;

	public Floor(FloorConfig style, M root, int minY, int maxY, boolean inside, boolean ceiling) {
		this.style = style;
		this.root = root;
		this.minY = minY;
		this.maxY = maxY;
		this.inside = inside;
		this.ceiling = ceiling;
	}

	public void build(Building<?> building) {
		AI_Builder.printDebug("Building floor " + this);
		building.getStyle().changeIndexs();

		if(root instanceof MapRectangleArea rectangleArea) {
			style.getModel().build(new Position(rectangleArea.getMinX(), rectangleArea.getMinZ(), minY), new Position(rectangleArea.getMaxX(), rectangleArea.getMaxZ(), maxY));

			if (style.getCenter() != null) {
				AI_Builder.printDebug("Building floor center model");
				int cx = rectangleArea.getSizeX() % 2;
				int cz = rectangleArea.getSizeZ() % 2;
				SingleModel center = style.getCenter()[cx + (cz * 2)];
				center.build(rectangleArea.getMinX() + rectangleArea.getSizeX() / 2, rectangleArea.getMinZ() - rectangleArea.getSizeZ() / 2, maxY);
			}
			AI_Builder.printDebug("Finish to build floor");
		}
	}

	public String toString() {
		return "Floor map= [" + root + "] minY= " + minY + " maxY " + maxY  + ", data= [style=[" + style + "],inside=" + inside + ",ceiling=" + ceiling;
	}

	public FloorConfig getStyle() {
		return style;
	}
	public boolean isInside() {
		return inside;
	}
	public boolean isCeiling() {
		return ceiling;
	}
	public M getRoot() {
		return root;
	}
	public int getMinY() {
		return minY;
	}
	public int getMaxY() {
		return maxY;
	}
}
