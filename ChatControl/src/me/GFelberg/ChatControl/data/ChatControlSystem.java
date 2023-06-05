package me.GFelberg.ChatControl.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.Main;
import me.GFelberg.ChatControl.utils.ChatControlUtils;

public class ChatControlSystem {

	public static String cleared, locked, unlocked, alreadylocked, alreadyunlocked;
	public static volatile boolean chatDisabled = false;

	public static void loadVariables() {
		cleared = Main.getInstance().getConfig().getString("ChatControl.Message").replace("&", "§");
		locked = Main.getInstance().getConfig().getString("ChatControl.Locked").replace("&", "§");
		unlocked = Main.getInstance().getConfig().getString("ChatControl.Unlocked").replace("&", "§");
		alreadylocked = Main.getInstance().getConfig().getString("ChatControl.AlreadyLocked").replace("&", "§");
		alreadyunlocked = Main.getInstance().getConfig().getString("ChatControl.AlreadyUnlocked").replace("&", "§");
	}

	public void clearChat() {

		for (int i = 0; i < 130; i++) {
			Bukkit.broadcastMessage("");
		}
		Bukkit.broadcastMessage(ChatControlUtils.prefix + " " + cleared);
	}

	public void lockChat(Player p) {

		if (!(chatDisabled)) {
			chatDisabled = true;
			Bukkit.broadcastMessage(ChatControlUtils.prefix + " " + locked);
		} else {
			p.sendMessage(alreadylocked);
		}
	}

	public void unlockChat(Player p) {

		if (chatDisabled) {
			chatDisabled = false;
			Bukkit.broadcastMessage(ChatControlUtils.prefix + " " + unlocked);
		} else {
			p.sendMessage(alreadyunlocked);
		}
	}
}
