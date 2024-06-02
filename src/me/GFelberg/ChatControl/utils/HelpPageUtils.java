package me.GFelberg.ChatControl.utils;

import me.GFelberg.ChatControl.Main;

public class HelpPageUtils {

	public String getHelp_page() {
		return Main.getInstance().getConfig().getString("Help.Page").replace("&", "§");
	}

	public String getHelp_chat() {
		return Main.getInstance().getConfig().getString("Help.Chat").replace("&", "§");
	}

	public String getHelp_unlockChat() {
		return Main.getInstance().getConfig().getString("Help.UnlockChat").replace("&", "§");
	}

	public String getHelp_lockChat() {
		return Main.getInstance().getConfig().getString("Help.LockChat").replace("&", "§");
	}

	public String getHelp_mutePlayer() {
		return Main.getInstance().getConfig().getString("Help.MutePlayer").replace("&", "§");
	}

	public String getHelp_unmutePlayer() {
		return Main.getInstance().getConfig().getString("Help.UnmutePlayer").replace("&", "§");
	}

	public String getHelp_listPlayers() {
		return Main.getInstance().getConfig().getString("Help.ListPlayers").replace("&", "§");
	}

	public String getHelp_reload() {
		return Main.getInstance().getConfig().getString("Help.Reload").replace("&", "§");
	}
}