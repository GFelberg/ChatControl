package me.GFelberg.ChatControl.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.Main;

public class ChatControlUtils {

	public static String prefix;

	public static void loadVariables() {
		prefix = Main.getInstance().getConfig().getString("ChatControl.Prefix").replace("&", "§");
	}

	public void reloadConfig(Player p) {

		if (!(p.hasPermission("chatcontrol.reload"))) {
			p.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
		} else {
			Main.getInstance().reloadConfig();
			Main.getInstance().loadVariables();
			p.sendMessage(prefix + " " + ChatColor.GREEN + "Plugin reloaded successfully!");
			Bukkit.getConsoleSender().sendMessage("=============================================");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "ChatControl Plugin reloaded");
			Bukkit.getConsoleSender().sendMessage("=============================================");
		}
	}

	public void helpPage(Player p) {
		HelpPageUtils helpUtils = new HelpPageUtils();
		p.sendMessage(ChatColor.WHITE + "-----------------------------------------");
		p.sendMessage(ChatColor.AQUA + "ChatControl - Help Commands");
		p.sendMessage(ChatColor.YELLOW + "/cc help : " + helpUtils.getHelp_page());
		p.sendMessage(ChatColor.YELLOW + "/cc : " + helpUtils.getHelp_chat());
		p.sendMessage(ChatColor.YELLOW + "/cc unlock : " + helpUtils.getHelp_unlockChat());
		p.sendMessage(ChatColor.YELLOW + "/cc lock : " + helpUtils.getHelp_lockChat());
		p.sendMessage(ChatColor.YELLOW + "/mute <player> : " + helpUtils.getHelp_mutePlayer());
		p.sendMessage(ChatColor.YELLOW + "/mute list : " + helpUtils.getHelp_listPlayers());
		p.sendMessage(ChatColor.YELLOW + "/unmute <player> : " + helpUtils.getHelp_unmutePlayer());
		p.sendMessage(ChatColor.YELLOW + "/cc reload : " + helpUtils.getHelp_reload());
		p.sendMessage(ChatColor.WHITE + "-----------------------------------------");
	}
}