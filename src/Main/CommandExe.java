package Main;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import Building.Building;
import Building.BuildingPalace;
import Building.BuildingPalaceStyle;
import Building.BuildingStyle;

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

public class CommandExe implements CommandExecutor {

	private static Player executor;
	private static World currentWorld;
	private static Building currentBuilding;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String name, String[] arg) {
		if(sender instanceof Player || sender instanceof ConsoleCommandSender) {
			if(name.equals("AIbuild")) {
				if(arg[0].equals("build") && sender instanceof Player && arg.length >= 4) {
					if(!Main.isExecute()) {
						if(arg[1].equals("palace")) {
							BuildingPalaceStyle buildingStyle = (BuildingPalaceStyle) BuildingStyle.enabledBuildings.get(arg[2]);
							Style style = Style.enabledStyles.get(arg[3]);
							BuildingPalace palace = null;
							if(buildingStyle == null)
								sender.sendMessage("No found building style " + arg[2]);
							else if(style == null)
								sender.sendMessage("No found style " + arg[3]);
							else {
								palace = new BuildingPalace(style, buildingStyle);
								if(arg.length >= 6) {
									try {
										if(Integer.parseInt(arg[4]) < 1 || Integer.parseInt(arg[5]) < 1)
											throw new NumberFormatException();
										palace.setFloors(Integer.parseInt(arg[4]));
										palace.setRooms(Integer.parseInt(arg[5]));
									} catch(NumberFormatException exc) {
										sender.sendMessage("The number of rooms and floors must be 1+");
										return false;
									}
								}
								currentBuilding = palace;
								executor = (Player) sender;
								currentWorld = executor.getWorld();
								currentBuilding.setPosition(executor.getLocation());
								palace.build();
								reset();
								return true;
							}
						}
					} else {
						sender.sendMessage("A build is building now, you can not throw this command");
					}
				} else if(arg[0].equals("debug")) {
					if(arg.length == 1) {
						Main.setDebug(true);
						return true;
					} else if(arg.length == 2) {
						if(arg[1].equals("on")) {
							Main.setDebug(true);
							return true;
						} else if(arg[1].equals("off")) {
							Main.setDebug(false);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void reset() {
		executor = null;
		currentWorld = null;
		currentBuilding = null;
	}

	public static Player getExecutor() {
		return executor;
	}
	public static World getCurrentWorld() {
		return currentWorld;
	}
	public static Building getCurrentBuilding() {
		return currentBuilding;
	}
}
