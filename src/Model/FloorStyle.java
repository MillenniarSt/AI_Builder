package Model;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;

public class FloorStyle extends CustomStyle {

	private Model corner;
	private Model side;
	private Model[] center;
	private Model repeat;
	
	private boolean ceiling;
	private boolean inside;

	public FloorStyle(String id) {
		super(id);
		this.corner = new Model();
		this.side = new Model();
		this.center = new Model[4];
		this.repeat = new Model();
		this.inside = false;
		this.ceiling = false;
	}
	public FloorStyle(Model corner, Model side, Model[] center, Model repeat, boolean inside, boolean ceiling, String id) {
		super(id);
		this.corner = corner;
		this.side = side;
		this.center = center;
		this.repeat = repeat;
		this.inside = inside;
		this.ceiling = ceiling;
	}
	
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		String cornerPath = file.getString("corner", null);
		if(cornerPath != null)
			this.corner.load("floors\\" + cornerPath);
		else
			this.corner = null;
		String sidePath = file.getString("side", null);
		if(sidePath != null)
			this.side.load("floors\\" + sidePath);
		else
			this.side = null;
		String repeatPath = file.getString("repeat", null);
		if(repeatPath != null)
			this.repeat.load("floors\\" + repeatPath);
		else
			return false;
		List<String> centers = file.getStringList("center");
		if(centers != null) {
			if(!centers.isEmpty()) {
				for(int i = 0; i < 4; i++) {
					String centerPath = centers.get(i);
					if(centerPath != null) {
						center[i] = new Model();
						center[i].load("floors\\" + centerPath);
					} else {
						return false;
					}
				}
			}
		}
		
		ceiling = file.getBoolean("ceiling", false);
		inside = file.getBoolean("inside", false);
		
		return true;
	}
	
	public String toString() {
		return "corner=" + corner + ",side=" + side + ",center=" + center + ",repeat=" + repeat +  ",ceiling=" + ceiling +  ",inside=" + inside;
	}
	
	public Model getSide() {
		return side;
	}
	public void setSide(Model side) {
		this.side = side;
	}
	public Model getCorner() {
		return corner;
	}
	public void setCorner(Model corner) {
		this.corner = corner;
	}
	public Model[] getCenter() {
		return center;
	}
	public void setCenter(Model[] center) {
		this.center = center;
	}
	public Model getRepeat() {
		return repeat;
	}
	public void setRepeat(Model repeat) {
		this.repeat = repeat;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public boolean isCeiling() {
		return ceiling;
	}
	public void setCeiling(boolean ceiling) {
		this.ceiling = ceiling;
	}
}
