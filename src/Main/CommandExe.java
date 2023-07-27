package Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandExe implements CommandExecutor {

	private static Player executor;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String name, String[] arg) {
		if(sender instanceof Player || sender instanceof ConsoleCommandSender) {
			if(name.equals("AIbuild")) {
				if(arg[0].equals("debug")) {
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

	public static Player getExecutor() {
		return executor;
	}
	public static void setExecutor(Player executor) {
		CommandExe.executor = executor;
	}
}
