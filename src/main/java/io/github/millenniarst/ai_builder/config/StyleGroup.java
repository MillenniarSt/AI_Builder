package io.github.millenniarst.ai_builder.config;

import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.util.RandomCollection;

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
	private RandomCollection<UnderGroupStyle> random;
	
	private UnderGroupStyle under;
	
	public StyleGroup(String id) {
		this.id = id;
		this.random = new RandomCollection<>();
	}
	public StyleGroup(String id, RandomCollection<UnderGroupStyle> random) {
		this.id = id;
		this.random = random;
	}
	
	void changeIndex() throws AIObjectNotFoundException {
		under = random.getRandom();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public RandomCollection<UnderGroupStyle> getRandom() {
		return random;
	}
	public void setRandom(RandomCollection<UnderGroupStyle> random) {
		this.random = random;
	}
	public UnderGroupStyle getUnder() {
		return under;
	}
}
