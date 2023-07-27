package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Building.Building;
import Main.Loader;
import Main.RandomCollection;

public class Model implements Loader {

	private HashMap<int[], Position> model;
	
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	
	private String state;
	
	public static final String STATE_ENABLE = "enable";
	public static final String STATE_VOID = "void";
	public static final String STATE_DISABLE = "disable";
	public static final String STATE_ERROR = "error";

	public Model() {
		this.model = new HashMap<>();
		this.sizeX = 0;
		this.sizeZ = 0;
		this.sizeY = 0;
		this.state = STATE_VOID;
	}
	public Model(HashMap<int[], Position> model) {
		this.model = model;
		refreshSize();
		this.state = STATE_ENABLE;
	}
	
	public void build(Building building, Position pos) {
		this.build(building, pos.getPosX(), pos.getPosZ(), pos.getPosY());
	}
	public void build(Building building, int x, int z, int y) {
		for(int ix = 0; ix <= this.getSizeX() -1; ix++) {
			for(int iz = 0; iz <= this.getSizeZ() -1; iz++) {
				for(int iy = 0; iy <= this.getSizeY() -1; iy++) {
					building.setPos(x + ix, z + iz, y + iy, this.getDataPos(ix, iz, iy));
				}
			}
		}
	}
	
	public void rotate(int angle) {
		if(angle == 90) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosZ());
				pos.setPosZ(pos.getPosX() * -1);
			}
		} else if(angle == 180 || angle == -180) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosZ() * -1);
				pos.setPosZ(pos.getPosX());
			}
		} else if(angle == 270 || angle == -90) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosX() * -1);
				pos.setPosZ(pos.getPosZ() * -1);
			}
		} else if(angle != 0 && angle != 360) {
			throw new NumberFormatException("The rotate angle must be: -180, -90, 0, 90, 180, 270 or 360");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Model clone() {
		return new Model((HashMap<int[], Position>) this.model.clone());
	}
	
	public void refreshSize() {
		int minX = 0;
		int minZ = 0;
		int minY = 0;
		int maxX = 0;
		int maxZ = 0;
		int maxY = 0;
		for(int[] pos : model.keySet()) {
			if(pos[0] > maxX)
				maxX = pos[0];
			else if(pos[0] < minX)
				minX = pos[0];
			if(pos[1] > maxZ)
				maxZ = pos[1];
			else if(pos[1] < minZ)
				minZ = pos[1];
			if(pos[2] > maxY)
				maxY = pos[2];
			else if(pos[2] < minY)
				minY = pos[2];
		}
		this.sizeX = Math.abs(maxX - minX) + 1;
		this.sizeZ = Math.abs(maxZ - minZ) + 1;
		this.sizeY = Math.abs(maxY - minY) + 1;
	}
	
	@Override
	public boolean load(String path) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		model.clear();
		
		try {
			Set<String> grpX = file.getConfigurationSection("model").getKeys(false);
			for(String posX : grpX) {
				int x = Integer.parseInt(posX);
				Set<String> grpY = file.getConfigurationSection("model." + posX).getKeys(false);
				for(String posY : grpY) {
					int y = Integer.parseInt(posY);
					Set<String> grpZ = file.getConfigurationSection("model." + posX + "." + posY).getKeys(false);
					for(String posZ : grpZ) {
						int z = Integer.parseInt(posZ);
						String position = "model." + posX + "." + posY + "." + posZ + ".";
						
						boolean random = file.getBoolean(position + "random", false);
						if(random) {
							List<String> materials = file.getStringList(position + "material");
							List<String> types = file.getStringList(position + "type");
							List<String> groups = file.getStringList(position + "group");
							HashMap<String, List<String>> properties = null;
							Set<String> propertiesConf = file.getConfigurationSection(position + "properties").getKeys(false);
							if(!propertiesConf.isEmpty() && propertiesConf != null) {
								properties = new HashMap<>();
								for(String property : propertiesConf) {
									properties.put(property, file.getStringList(position + "properties." + property));
								}
							}
							addPos(x, z, y, AIBlockData.getCollection(materials, types, groups, properties));
						} else {
							String material = file.getString(position + "material", null);
							String type = file.getString(position + "type", null);
							String group = file.getString(position + "group", "default");
							ArrayList<String> properties = new ArrayList<>();
							Set<String> propertiesConf = file.getConfigurationSection(position + "properties").getKeys(false);
							if(!propertiesConf.isEmpty() && propertiesConf != null) {
								for(String property : propertiesConf) {
									properties.add(property + "=" + file.getString(position + "properties." + property));
								}
							}
							RandomCollection<AIBlockData> unique = new RandomCollection<>();
							unique.add(new AIBlockData(material, BlockType.getType(type), group, properties), 1);
							addPos(x, z, y, unique);
						}
					}
				}
			}
			refreshSize();
			this.state = STATE_ENABLE;
			return true;
		} catch(NumberFormatException exc) {
			exc.printStackTrace();
			model.clear();
			this.sizeX = 0;
			this.sizeZ = 0;
			this.sizeY = 0;
			this.state = STATE_ERROR;
		}
		return false;
	}
	
	public void addPos(Position pos) {
		int[] arg = {pos.getPosX(), pos.getPosZ(), pos.getPosY()};
		this.model.put(arg, pos);
	}
	public void addPos(int x, int z, int y, RandomCollection<AIBlockData> block) {
		int[] arg = {x, z, y};
		this.model.put(arg, new Position(x, z, y, block));
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
		return this.model.get(arg);
	}
	public RandomCollection<AIBlockData> getDataPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.model.get(arg).getBlock();
	}
	
	public String toString() {
		return state;
	}
	
	public HashMap<int[], Position> getModel() {
		return model;
	}
	public void setModel(HashMap<int[], Position> model) {
		this.model = model;
	}
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public int getSizeZ() {
		return sizeZ;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
