package me.GFelberg.ChatControl.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.utils.ChatControlUtils;

public class ChatControl implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("chatcontrol")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can be only made by players!");
				return true;
			}

			if (!(sender.hasPermission("chatcontrol.chatcontrol"))) {
				sender.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
				return true;
			}

			ChatControlUtils utils = new ChatControlUtils();

			if (args.length == 0) {
				utils.clearChat();
				return true;
			}

			if (args.length == 1) {
				Player p = (Player) sender;

				if (args[0].equalsIgnoreCase("reload")) {
					utils.reloadConfig(p);
				} else if (args[0].equalsIgnoreCase("lock")) {
					utils.lockChat(p);
				} else if (args[0].equalsIgnoreCase("unlock")) {
					utils.unlockChat(p);
				} else if (args[0].equalsIgnoreCase("help")) {
					utils.helpPage(p);
				}
				return true;
			}
		}
		return true;
	}
}
