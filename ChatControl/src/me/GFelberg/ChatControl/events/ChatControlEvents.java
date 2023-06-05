package me.GFelberg.ChatControl.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.GFelberg.ChatControl.Main;
import me.GFelberg.ChatControl.data.ChatControlSystem;
import me.GFelberg.ChatControl.data.MuteConfig;
import me.GFelberg.ChatControl.data.MuteSystem;

public class ChatControlEvents implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();

		if (ChatControlSystem.chatDisabled) {
			if (!(p.hasPermission("chatcontrol.bypass"))) {
				p.sendMessage(ChatControlSystem.locked);
				event.setCancelled(true);
			} else {
				event.setCancelled(false);
			}
		}

		if (!(MuteSystem.muted_players.containsKey(p.getUniqueId()))) {
			return;
		} else {
			p.sendMessage(MuteSystem.muted);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		UUID uuid = p.getUniqueId();
		FileConfiguration customConfig = MuteConfig.getConfig();
		String path = "MutedPlayers." + uuid.toString();

		if (customConfig.getString(path) == null) {
			return;
		} else {
			MuteSystem.muted_players.put(uuid, customConfig.getString(path + ".Muted By"));
			customConfig.set("MutedPlayers." + uuid.toString(), null);
			MuteConfig.saveConfig();

			FileConfiguration config = Main.getInstance().getConfig();

			if (config.getBoolean("NotifyOperators")) {
				for (Player operator : Bukkit.getOnlinePlayers()) {
					if (operator.isOp() || operator.hasPermission("chatcontrol.notify")) {
						String msg1 = ChatColor.WHITE + "----------------------------";
						String msg2 = ChatColor.RED + "A muted player is now online:";
						String msg3 = ChatColor.RED + "UUID: " + ChatColor.YELLOW + p.getUniqueId();
						String msg4 = ChatColor.RED + "Name: " + ChatColor.YELLOW + p.getName();
						String msg5 = ChatColor.WHITE + "----------------------------";
						operator.sendMessage(msg1 + "\n" + msg2 + "\n" + msg3 + "\n" + msg4 + "\n" + msg5);
					}
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		UUID uuid = p.getUniqueId();

		if (!(MuteSystem.muted_players.containsKey(uuid))) {
			return;
		} else {
			FileConfiguration customConfig = MuteConfig.getConfig();
			customConfig.set("MutedPlayers." + uuid.toString() + ".Player", p.getName());
			customConfig.set("MutedPlayers." + uuid.toString() + ".Muted By", MuteSystem.muted_players.get(uuid));
			MuteConfig.saveConfig();
			MuteSystem.muted_players.remove(uuid);

			FileConfiguration config = Main.getInstance().getConfig();

			if (config.getBoolean("NotifyOperators")) {
				for (Player operator : Bukkit.getOnlinePlayers()) {
					if (operator.isOp() || operator.hasPermission("chatcontrol.notify")) {
						String msg1 = ChatColor.WHITE + "----------------------------";
						String msg2 = ChatColor.RED + "A muted player is now offline:";
						String msg3 = ChatColor.RED + "UUID: " + ChatColor.YELLOW + p.getUniqueId();
						String msg4 = ChatColor.RED + "Name: " + ChatColor.YELLOW + p.getName();
						String msg5 = ChatColor.WHITE + "----------------------------";
						operator.sendMessage(msg1 + "\n" + msg2 + "\n" + msg3 + "\n" + msg4 + "\n" + msg5);
					}
				}
			}
		}
	}
}