package io.github.millenniarst.ai_builder.util;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;

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

public class RandomCollection<Obj> {

	private ArrayList<Random<Obj>> collection;

	public RandomCollection() {
		this.collection = new ArrayList<>();
	}
	public RandomCollection(ArrayList<Random<Obj>> collection) {
		this.collection = collection;
	}
	
	public Obj getRandom() throws AIObjectNotFoundException {
		if(collection.isEmpty()) {
			throw new AIObjectNotFoundException("the arraylist is empty");
		} else {
			HashMap<int[], Obj> objects = new HashMap<>();
			int i = 0;
			for(Random<Obj> object : collection) {
				int[] arg = {i, i + object.getWeigh() -1};
				objects.put(arg, object.getObject());
				i = i + object.getWeigh();
			}
			int r = (int) (Math.random() * i);
			for(int[] arg : objects.keySet()) {
				if(r >= arg[0] && r <= arg[1]) {
					return objects.get(arg);
				}
			}
			throw new AIObjectNotFoundException("fail to found a random object");
		}
	}
	
	public void add(Random<Obj> random) {
		this.collection.add(random);
	}
	public void add(Obj object, int weigh) {
		this.collection.add(new Random<Obj>(object, weigh));
	}
	public void remove(Random<Obj> random) {
		this.collection.remove(random);
	}
	
	public String toString() {
		return collection.toString();
	}
	
	public ArrayList<Random<Obj>> getCollection() {
		return collection;
	}
	public void setCollection(ArrayList<Random<Obj>> collection) {
		this.collection = collection;
	}
}
