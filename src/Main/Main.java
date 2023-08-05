package Main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import Style.BuildingPalaceStyle;
import Style.BuildingStyle;
import Style.Loader;
import Style.Style;

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

public class Main extends JavaPlugin {

	public static final String NAME = "AI_Builder";
	private static Main plugin;
	
	private boolean debug;
	private ConsoleCommandSender console;
	private boolean execute;
	
	@Override
	public void onEnable() {
		plugin = this;
		plugin.debug = false;
		plugin.execute = false;
		console = Bukkit.getServer().getConsoleSender();
		
		
		console.sendMessage("Enabling plugin AI_Builder...");
		
		console.sendMessage("");
		console.sendMessage(ChatColor.BOLD + " |\\       /|                          __                 __    ___  __");
		console.sendMessage(ChatColor.BOLD + " | \\     / |   ______    /\\    |     |  \\  |   | | |    |  \\  |    |  \\");
		console.sendMessage(ChatColor.BOLD + " |  \\   /  |  /         /  \\   |     |__/  |   | | |    |   | |___ |__/");
		console.sendMessage(ChatColor.BOLD + " |   \\_/   | |         /----\\  |     |   \\ |   | | |    |   | |    |  \\");
		console.sendMessage(ChatColor.BOLD + " |         |  \\____   /      \\ |     |___/  \\_/  | |___ |__/  |___ |   \\");
		console.sendMessage(ChatColor.BOLD + " |         |       \\");
		console.sendMessage(ChatColor.BOLD + " |         |        |      AI Builder  ---   By Millenniar Studios");
		console.sendMessage(ChatColor.BOLD + " |         | ______/");
		console.sendMessage("");
		
		getCommand("AIbuild").setExecutor(new CommandExe());
		console.sendMessage("Load AI_Builder commands succefly");
		
		console.sendMessage("Setting AI_Builder config...");
		setConfig(new File(Loader.CONFIG + "\\buildings\\"));
		setConfig(new File(Loader.CONFIG + "\\rooms\\"));
		setConfig(new File(Loader.CONFIG + "\\floors\\"));
		setConfig(new File(Loader.CONFIG + "\\walls\\"));
		setConfig(new File(Loader.CONFIG + "\\doors\\"));
		setConfig(new File(Loader.CONFIG + "\\windows\\"));
		setConfig(new File(Loader.CONFIG + "\\styles\\"));
		console.sendMessage("Loading AI_Builder config...");
		for(File file : new File(Loader.CONFIG + "\\styles\\").listFiles()) {
			if(file.isFile()) {
				String name = file.getName().substring(0, file.getName().lastIndexOf(".") - 1);
				Style style = new Style(name);
				if(style.load(name)) {
					Style.enabledStyles.put(style.getName(), style);
					console.sendMessage("Load style " + name + " with name " + style.getName());
				} else {
					console.sendMessage(ChatColor.RED + "Fail to load style " + name);
				}
			} else {
				console.sendMessage(ChatColor.YELLOW + "File (Style) " + file.getName() + " skipped, it is not a file");
			}
		}
		for(File file : new File(Loader.CONFIG + "\\buildings\\palaces\\").listFiles()) {
			if(file.isFile()) {
				String name = file.getName().substring(0, file.getName().lastIndexOf(".") - 1);
				BuildingPalaceStyle palace = new BuildingPalaceStyle(name, name);
				if(palace.load(name)) {
					BuildingStyle.enabledBuildings.put(palace.getName(), palace);
					console.sendMessage("Load building palace style " + name);
				} else {
					console.sendMessage(ChatColor.RED + "Fail to load building palace style " + name + " with name " + palace.getName());
				}
			} else {
				console.sendMessage(ChatColor.YELLOW + "File (Building) " + file.getName() + " skipped, it is not a file");
			}
		}
		console.sendMessage("Load AI_Builder config succefly");
		
		System.out.println(ChatColor.GREEN + "AI_Builder plugin enabled succefully");
	}
	
	@Override
	public void onDisable() {
		CommandExe.reset();
		plugin.execute = false;
		plugin.debug = false;
		System.out.println("AI_Builder plugin disabled succefully");
	}

	public void setConfig(File directory) {
		for(File file : directory.listFiles()) {
			if(file.isDirectory())
				setConfig(file);
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.options().copyDefaults(true);
				config.save(file);
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void print(String message) {
		plugin.console.sendMessage(message);
		CommandExe.getExecutor().sendMessage(message);
	}
	public static void print(String message, ChatColor color) {
		plugin.console.sendMessage(color + message);
		CommandExe.getExecutor().sendMessage(message);
	}
	public static void printConsole(String message) {
		plugin.console.sendMessage(message);
	}
	public static void printConsole(String message, ChatColor color) {
		plugin.console.sendMessage(color + message);
	}
	public static void printDebug(String message) {
		if(isDebug())
			plugin.console.sendMessage(ChatColor.GRAY + message);
	}
	
	public static Main getPlugin() {
		return plugin;
	}

	public static boolean isDebug() {
		return plugin.debug;
	}
	public static void setDebug(boolean debug) {
		plugin.debug = debug;
	}
	public static ConsoleCommandSender getConsole() {
		return plugin.console;
	}
	public static void setConsole(ConsoleCommandSender console) {
		plugin.console = console;
	}
	public static boolean isExecute() {
		return plugin.execute;
	}
	public static void setExecute(boolean execute) {
		plugin.execute = execute;
	}
}
