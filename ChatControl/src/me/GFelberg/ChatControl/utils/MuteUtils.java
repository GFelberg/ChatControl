package me.GFelberg.ChatControl.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.Main;
import me.GFelberg.ChatControl.data.MuteConfig;

public class MuteUtils {

	public static List<UUID> players = new ArrayList<UUID>();
	public static String muted, unmuted, muted_admin, unmuted_admin;
	public static String already_muted, already_unmuted, notmuted, exempt, playernotfound;

	public static void loadVariables() {
		muted = Main.getInstance().getConfig().getString("MuteSystem.Muted").replace("&", "§");
		unmuted = Main.getInstance().getConfig().getString("MuteSystem.Unmuted").replace("&", "§");
		muted_admin = Main.getInstance().getConfig().getString("MuteSystem.MutedAdmin").replace("&", "§");
		unmuted_admin = Main.getInstance().getConfig().getString("MuteSystem.UnmutedAdmin").replace("&", "§");
		already_muted = Main.getInstance().getConfig().getString("MuteSystem.AlreadyMuted").replace("&", "§");
		already_unmuted = Main.getInstance().getConfig().getString("MuteSystem.AlreadyUnmuted").replace("&", "§");
		notmuted = Main.getInstance().getConfig().getString("MuteSystem.NotMuted").replace("&", "§");
		exempt = Main.getInstance().getConfig().getString("MuteSystem.Exempt").replace("&", "§");
		playernotfound = Main.getInstance().getConfig().getString("MuteSystem.PlayerNotFound").replace("&", "§");
	}

	public void mutePlayer(Player p, Player selected) {

		if (selected == null) {
			p.sendMessage(playernotfound);
			return;
		}

		FileConfiguration custom = MuteConfig.getConfig();

		if (!(players.contains(selected.getUniqueId()))) {
			if (selected.isOp() || selected.hasPermission("chatcontrol.exempt")) {
				p.sendMessage(exempt);
			} else {
				custom.set("MutedPlayers." + selected.getUniqueId().toString() + ".Name", selected.getName());
				MuteConfig.saveConfig();
				MuteConfig.reloadConfig();
				players.add(selected.getUniqueId());
				selected.sendMessage(muted);
				p.sendMessage(muted_admin);
			}
		} else {
			p.sendMessage(already_muted);
		}
	}

	public void unmutePlayer(Player p, Player selected) {

		if (selected == null) {
			p.sendMessage(playernotfound);
			return;
		}

		FileConfiguration custom = MuteConfig.getConfig();

		if (players.contains(selected.getUniqueId())) {
			custom.set("MutedPlayers." + selected.getUniqueId().toString(), null);
			MuteConfig.saveConfig();
			MuteConfig.reloadConfig();
			players.remove(selected.getUniqueId());
			selected.sendMessage(unmuted);
			p.sendMessage(unmuted_admin);
		} else {
			p.sendMessage(already_unmuted);
		}
	}

	public void listPlayers(Player p) {
		p.sendMessage("\n---------------");
		p.sendMessage(ChatColor.AQUA + "Muted Players: \n");
		for (UUID uuids : players) {
			p.sendMessage(ChatColor.YELLOW + " - " + uuids);
		}
		p.sendMessage("---------------\n");
	}

	public void checkPlayer(Player p, Player selected) {

		if (selected == null) {
			p.sendMessage(playernotfound);
			return;
		}

		if (players.contains(p.getUniqueId())) {
			p.sendMessage(already_muted);
		} else {
			p.sendMessage(notmuted);
		}
	}
}