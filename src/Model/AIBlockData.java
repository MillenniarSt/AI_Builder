package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Main.RandomCollection;

public class AIBlockData {

	private String material;
	private BlockType type;
	private String group;
	
	private ArrayList<String> properties;
	
	public AIBlockData(String material) {
		this.material = material;
		this.type = BlockType.BLOCK;
		this.properties = new ArrayList<>();
	}
	public AIBlockData(BlockType type, String group) {
		this.material = null;
		this.type = type;
		this.group = group;
		this.properties = new ArrayList<>();
	}
	public AIBlockData(String material, BlockType type, String group) {
		this.material = material;
		this.type = type;
		this.group = group;
		this.properties = new ArrayList<>();
	}
	public AIBlockData(String material, ArrayList<String> properties) {
		this.material = material;
		this.type = BlockType.BLOCK;
		this.properties = properties;
	}
	public AIBlockData(BlockType type, String group, ArrayList<String> properties) {
		this.material = null;
		this.type = type;
		this.group = group;
		this.properties = properties;
	}
	public AIBlockData(String material, BlockType type, String group, ArrayList<String> properties) {
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
							output.getCollection().get(i).getObject().getProperties().add(property + "=" + propertiesGrp.get(property).get(i));
						} else {
							AIBlockData block = new AIBlockData(materials.get(materials.size() -1), BlockType.getType(types.get(types.size() -1)), groups.get(groups.size() -1));
							block.getProperties().add(property + "=" + propertiesGrp.get(property).get(i));
							output.add(block, 1);
						}
					}
				}
			}
		}
		return output;
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
	public ArrayList<String> getProperties() {
		return properties;
	}
	public void setProperties(ArrayList<String> properties) {
		this.properties = properties;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}
