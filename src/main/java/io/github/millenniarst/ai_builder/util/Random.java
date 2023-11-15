package io.github.millenniarst.ai_builder.util;

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

public class Random<Obj> {

	private Obj object;
	private int weigh;
	
	public Random(Obj object, int weigh) {
		this.object = object;
		this.weigh = weigh;
	}
	
	public String toString() {
		return object + ":" + weigh;
	}
	
	public Obj getObject() {
		return object;
	}
	public void setObject(Obj object) {
		this.object = object;
	}
	public int getWeigh() {
		return weigh;
	}
	public void setWeigh(int weigh) {
		this.weigh = weigh;
	}
}
