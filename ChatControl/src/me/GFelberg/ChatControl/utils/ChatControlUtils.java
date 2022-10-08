package me.GFelberg.ChatControl.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.Main;
import me.GFelberg.ChatControl.data.MuteConfig;

public class ChatControlUtils {

	public static String prefix, cleared, locked, unlocked, alreadylocked, alreadyunlocked;
	public static volatile boolean chatDisabled = false;

	public static void loadVariables() {
		prefix = Main.getInstance().getConfig().getString("ChatControl.Prefix").replace("&", "§");
		cleared = Main.getInstance().getConfig().getString("ChatControl.Message").replace("&", "§");
		locked = Main.getInstance().getConfig().getString("ChatControl.Locked").replace("&", "§");
		unlocked = Main.getInstance().getConfig().getString("ChatControl.Unlocked").replace("&", "§");
		alreadylocked = Main.getInstance().getConfig().getString("ChatControl.AlreadyLocked").replace("&", "§");
		alreadyunlocked = Main.getInstance().getConfig().getString("ChatControl.AlreadyUnlocked").replace("&", "§");
	}

	public void reloadConfig(Player p) {

		if (!(p.hasPermission("chatcontrol.reload"))) {
			p.sendMessage(ChatColor.RED + "You dont have permission to perform this command!");
		} else {
			Main.getInstance().reloadConfig();
			Main.getInstance().loadVariables();
			MuteConfig.reloadConfig();
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

	public void clearChat() {

		for (int i = 0; i < 130; i++) {
			Bukkit.broadcastMessage("");
		}
		Bukkit.broadcastMessage(prefix + " " + cleared);
	}

	public void lockChat(Player p) {

		if (!(chatDisabled)) {
			chatDisabled = true;
			Bukkit.broadcastMessage(locked);
		} else {
			p.sendMessage(alreadylocked);
		}
	}

	public void unlockChat(Player p) {

		if (chatDisabled) {
			chatDisabled = false;
			Bukkit.broadcastMessage(unlocked);
		} else {
			p.sendMessage(alreadyunlocked);
		}
	}
}