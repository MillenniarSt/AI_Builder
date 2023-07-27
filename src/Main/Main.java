package Main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main plugin;
	private static boolean debug;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		getCommand("AIbuild").setExecutor(new CommandExe());
		System.out.println("AI_Builder plugin downloaded succefully");
	}
	
	@Override
	public void onDisable() {
		System.out.println("AI_Builder plugin disabled succefully");
	}

	public static void print(String message) {
		System.out.println(message);
		CommandExe.getExecutor().sendMessage(message);
	}
	public static void printDebug(String message) {
		if(debug)
			System.out.println(message);
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	public static boolean isDebug() {
		return debug;
	}
	public static void setDebug(boolean debug) {
		Main.debug = debug;
	}
}
