package me.GFelberg.ChatControl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.data.MuteSystem;

public class Mute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("mute")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can be only made by players!");
				return true;
			}

			if (!(sender.hasPermission("chatcontrol.chatcontrol"))) {
				sender.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Usage: /mute <player>");
				return true;
			}

			Player p = (Player) sender;
			MuteSystem sys = new MuteSystem();

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					sys.muteList(p);
				}
				Player selected = Bukkit.getServer().getPlayer(args[0]);
				sys.mutePlayer(p, selected);
				return true;
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("check")) {
				Player selected = Bukkit.getServer().getPlayer(args[1]);
				sys.checkPlayer(p, selected);
				return true;
			}
		}
		return true;
	}
}
