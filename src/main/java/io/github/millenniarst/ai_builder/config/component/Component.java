package io.github.millenniarst.ai_builder.config.component;

import io.github.millenniarst.ai_builder.config.Loader;

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

public class Component implements Loader {

	private String id;
	private String state;

	protected Component(String id) {
		setId(id);
		this.state = STATE_VOID;
	}

	@Override
	public boolean load(String path) {
		setId(path);
		return true;
	}
	
	public String toString() {
		return id;
	}
	
	@Override
	public String getState() {
		return state;
	}
	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getDirectoryPath() {
		return "\\";
	}

	private void setId(String id) {
		this.id = id.replace("\\", ".").replace("/", ".");
	}
	public String getId() {
		return id;
	}
}
