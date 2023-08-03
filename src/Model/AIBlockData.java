package Model;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import Exception.AIObjectNotFoundException;
import Main.RandomCollection;
import Main.Style;

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

public class AIBlockData {

	private String material;
	private BlockType type;
	private String group;
	
	private HashMap<String, String> properties;
	
	public AIBlockData(String material) {
		this.material = material;
		this.type = BlockType.BLOCK;
		this.properties = new HashMap<>();
	}
	public AIBlockData(BlockType type, String group) {
		this.material = null;
		this.type = type;
		this.group = group;
		this.properties = new HashMap<>();
	}
	public AIBlockData(String material, BlockType type, String group) {
		this.material = material;
		this.type = type;
		this.group = group;
		this.properties = new HashMap<>();
	}
	public AIBlockData(String material, HashMap<String, String> properties) {
		this.material = material;
		this.type = BlockType.BLOCK;
		this.properties = properties;
	}
	public AIBlockData(BlockType type, String group, HashMap<String, String> properties) {
		this.material = null;
		this.type = type;
		this.group = group;
		this.properties = properties;
	}
	public AIBlockData(String material, BlockType type, String group, HashMap<String, String> properties) {
		this.material = material;
		this.type = type;
		this.group = group;
		this.properties = properties;
	}
	
	public static RandomCollection<AIBlockData> getCollection(List<String> materials, List<String> types, List<String> groups, HashMap<String, List<String>> propertiesGrp) {
		RandomCollection<AIBlockData> output = new RandomCollection<>();
		if(materials != null) {
			if(!materials.isEmpty()) {
				for(String material : materials) {
					output.add(new AIBlockData(material), 1);
				}
			}
		}
		if(types != null) {
			if(!types.isEmpty()) {
				for(String type : types) {
					if(output.getCollection().get(types.indexOf(type)) != null) {
						output.getCollection().get(types.indexOf(type)).getObject().setType(BlockType.getType(type));
					} else {
						output.add(new AIBlockData(materials.get(materials.size() -1), BlockType.getType(type), null), 1);
					}
				}
			}
		}
		if(groups != null) {
			if(!groups.isEmpty()) {
				for(String group : groups) {
					if(output.getCollection().get(groups.indexOf(group)) != null) {
						output.getCollection().get(groups.indexOf(group)).getObject().setGroup(group);;
					} else {
						output.add(new AIBlockData(materials.get(materials.size() -1), BlockType.getType(types.get(types.size() -1)), group), 1);
					}
				}
			}
		}
		if(propertiesGrp != null) {
			if(!propertiesGrp.isEmpty()) {
				for(String property : propertiesGrp.keySet()) {
					for(int i = 0; i < propertiesGrp.get(property).size(); i++) {
						if(output.getCollection().get(i) != null) {
							output.getCollection().get(i).getObject().getProperties().put(property, propertiesGrp.get(property).get(i));
						} else {
							AIBlockData block = new AIBlockData(materials.get(materials.size() -1), BlockType.getType(types.get(types.size() -1)), groups.get(groups.size() -1));
							block.getProperties().put(property, propertiesGrp.get(property).get(i));
							output.add(block, 1);
						}
					}
				}
			}
		}
		return output;
	}
	
	public Material getCompleteMaterial(Style style) {
		try {
			String materialGroup = style.getGroups().get(group).getCollection().getRandom();
			return Material.getMaterial(BlockType.change(materialGroup, type));
		} catch (AIObjectNotFoundException exc) {
			exc.printStackTrace();
		}
		return null;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public String getProperty(String property) {
		return properties.get(property);
	}
	public void setProperty(String property, String value) {
		properties.put(property, value);
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}
