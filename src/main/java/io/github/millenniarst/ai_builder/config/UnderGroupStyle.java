package io.github.millenniarst.ai_builder.config;

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

public class UnderGroupStyle {

	private RandomCollection<String> random;
	
	public UnderGroupStyle() {
		this.random = new RandomCollection<>();
	}
	public UnderGroupStyle(RandomCollection<String> random) {
		this.random = random;
	}

	public RandomCollection<String> getRandom() {
		return random;
	}
	public void setRandom(RandomCollection<String> random) {
		this.random = random;
	}
}
