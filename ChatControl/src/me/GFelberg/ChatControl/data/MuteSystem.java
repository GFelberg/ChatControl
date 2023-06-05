package me.GFelberg.ChatControl.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.GFelberg.ChatControl.Main;

public class MuteSystem {

	public static HashMap<UUID, String> muted_players = new HashMap<UUID, String>();
	public static String muted, unmuted, muted_admin, unmuted_admin;
	public static String already_muted, already_unmuted, notmuted, exempt, playernotfound, noplayers;

	public static void loadVariables() {
		muted = Main.getInstance().getConfig().getString("MuteSystem.Muted").replace("&", "§");
		unmuted = Main.getInstance().getConfig().getString("MuteSystem.Unmuted").replace("&", "§");
		muted_admin = Main.getInstance().getConfig().getString("MuteSystem.MutedAdmin").replace("&", "§");
		unmuted_admin = Main.getInstance().getConfig().getString("MuteSystem.UnmutedAdmin").replace("&", "§");
		already_muted = Main.getInstance().getConfig().getString("MuteSystem.AlreadyMuted").replace("&", "§");
		already_unmuted = Main.getInstance().getConfig().getString("MuteSystem.AlreadyUnmuted").replace("&", "§");
		notmuted = Main.getInstance().getConfig().getString("MuteSystem.NotMuted").replace("&", "§");
		exempt = Main.getInstance().getConfig().getString("MuteSystem.Exempt").replace("&", "§");
		noplayers = Main.getInstance().getConfig().getString("MuteSystem.NoPlayers").replace("&", "§");
		playernotfound = Main.getInstance().getConfig().getString("MuteSystem.PlayerNotFound").replace("&", "§");
	}

	public void mutePlayer(Player p, Player selected) {

		if (selected == null) {
			p.sendMessage(playernotfound);
			return;
		}

		if (!(muted_players.containsKey(selected.getUniqueId()))) {
			if (selected.hasPermission("chatcontrol.exempt")) {
				p.sendMessage(exempt);
			} else {
				UUID uuid = selected.getUniqueId();
				muted_players.put(uuid, p.getName());
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

		if (muted_players.containsKey(selected.getUniqueId())) {
			muted_players.remove(selected.getUniqueId());
			selected.sendMessage(unmuted);
			p.sendMessage(unmuted_admin);
		} else {
			p.sendMessage(already_unmuted);
		}
	}

	public void checkPlayer(Player p, Player selected) {

		if (selected == null) {
			p.sendMessage(playernotfound);
			return;
		} else {
			if (muted_players.containsKey(selected.getUniqueId())) {
				p.sendMessage(already_muted);
			} else {
				p.sendMessage(notmuted);
			}
		}
	}

	public void muteList(Player p) {

		FileConfiguration customConfig = MuteConfig.getConfig();

		if (customConfig.getString("MutedPlayers") == null) {
			p.sendMessage(noplayers);
			return;
		} else {
			p.sendMessage("\n===============");
			p.sendMessage(ChatColor.AQUA + "Muted Players:");
			p.sendMessage("");
			p.sendMessage(ChatColor.BOLD + "Total: " + muted_players.size());
			for (UUID key : muted_players.keySet()) {
				p.sendMessage(ChatColor.YELLOW + " - " + muted_players.get(key));
			}
			p.sendMessage("===============\n");
		}
	}
}