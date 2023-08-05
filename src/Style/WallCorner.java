package Style;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.RandomCollection;
import Model.AIBlockData;
import Model.BlockType;

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

public class WallCorner implements Loader {

	private String state;
	
	private AIBlockData materials;
	private int size;
	
	private int relX;
	private int relZ;
	
	public WallCorner() {
		this.state = STATE_VOID;
	}
	
	public WallCorner(AIBlockData materials, int size, int relX, int relZ) {
		this.materials = materials;
		this.size = size;
		this.relX = relX;
		this.relZ = relZ;
		this.state = STATE_ENABLE;
	}
	
	@Override
	public boolean load(String path) {
		path = "walls\\corners\\" + path;
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		String material = file.getString("materials.material", null);
		String type = file.getString("materials.type", null);
		String group = file.getString("materials.group", "default");
		HashMap<String, String> properties = new HashMap<>();
		Set<String> propertiesConf = file.getConfigurationSection("materials.properties").getKeys(false);
		if(propertiesConf != null) {
			if(!propertiesConf.isEmpty()) {
				for(String property : propertiesConf) {
					properties.put(property, file.getString("materials.properties." + property));
				}
			}
		}
		materials = new AIBlockData(material, BlockType.getType(type), group, properties);
		
		int size = file.getInt("size", 1);
		if(size < 1)
			size = 1;
		this.size = size;
		int relX = file.getInt("relX", 1);
		if(relX < 1)
			relX = 1;
		this.relX = relX;
		int relZ = file.getInt("relZ", 1);
		if(relZ < 1)
			relZ = 1;
		this.relZ = relZ;
		
		this.setState(STATE_ENABLE);
		return true;
	}
	
	public String toString() {
		return "materials=" + materials + ",size=" + size + ",relX=" + relX + ",relZ=" + relZ;
	}
	
	@Override
	public String getState() {
		return state;
	}
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	public AIBlockData getMaterials() {
		return materials;
	}
	public RandomCollection<AIBlockData> getMaterialsCollection() {
		RandomCollection<AIBlockData> random = new RandomCollection<>();
		random.add(materials, 1);
		return random;
	}
	public void setMaterials(AIBlockData materials) {
		this.materials = materials;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getRelX() {
		return relX;
	}
	public void setRelX(int relX) {
		this.relX = relX;
	}
	public int getRelZ() {
		return relZ;
	}
	public void setRelZ(int relZ) {
		this.relZ = relZ;
	}
}
