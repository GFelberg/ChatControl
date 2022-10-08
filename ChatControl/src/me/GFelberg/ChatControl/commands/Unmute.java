package me.GFelberg.ChatControl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.utils.MuteUtils;

public class Unmute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("unmute")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can be only made by players!");
				return true;
			}

			if (!(sender.hasPermission("chatcontrol.chatcontrol"))) {
				sender.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Usage: /unmute <player>");
				return true;
			}

			if (args.length == 1) {
				Player p = (Player) sender;
				Player selected = Bukkit.getServer().getPlayer(args[0]);
				MuteUtils utils = new MuteUtils();
				utils.unmutePlayer(p, selected);
				return true;
			}
		}
		return true;
	}
}
