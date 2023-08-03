package Main;

import java.util.ArrayList;

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

public class StyleGroup {

	private String id;
	private ArrayList<RandomCollection<String>> random;
	
	private int index;
	
	public StyleGroup(String id) {
		this.id = id;
		this.random = new ArrayList<>();
		index = 0;
	}
	public StyleGroup(String id, ArrayList<RandomCollection<String>> random) {
		this.id = id;
		this.random = random;
		index = 0;
	}
	
	void changeIndex() {
		index = (int) (Math.random() * random.size());
	}
	public RandomCollection<String> getCollection() {
		return random.get(index);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<RandomCollection<String>> getRandom() {
		return random;
	}
	public void setRandom(ArrayList<RandomCollection<String>> random) {
		this.random = random;
	}
}
