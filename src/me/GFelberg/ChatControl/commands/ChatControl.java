package me.GFelberg.ChatControl.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.data.ChatControlSystem;
import me.GFelberg.ChatControl.utils.ChatControlUtils;

public class ChatControl implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("chatcontrol")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can be only made by players!");
				return true;
			}

			if (!(sender.hasPermission("chatcontrol.admin"))) {
				sender.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
				return true;
			}

			ChatControlUtils utils = new ChatControlUtils();
			ChatControlSystem sys = new ChatControlSystem();

			if (args.length == 0) {
				sys.clearChat();
				return true;
			}

			if (args.length == 1) {
				Player p = (Player) sender;

				if (args[0].equalsIgnoreCase("reload")) {
					utils.reloadConfig(p);
				} else if (args[0].equalsIgnoreCase("help")) {
					utils.helpPage(p);
				} else if (args[0].equalsIgnoreCase("lock")) {
					sys.lockChat(p);
				} else if (args[0].equalsIgnoreCase("unlock")) {
					sys.unlockChat(p);
				}
				return true;
			}
		}
		return true;
	}
}
