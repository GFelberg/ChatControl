package me.GFelberg.ChatControl.events;

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
import me.GFelberg.ChatControl.data.MuteConfig;
import me.GFelberg.ChatControl.utils.ChatControlUtils;
import me.GFelberg.ChatControl.utils.MuteUtils;

public class ChatControlEvents implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();

		if (ChatControlUtils.chatDisabled) {
			if (!(p.hasPermission("chatcontrol.bypass"))) {
				p.sendMessage(ChatControlUtils.locked);
				event.setCancelled(true);
			} else {
				event.setCancelled(false);
			}
		}

		if (!(MuteUtils.players.contains(p.getUniqueId()))) {
			return;
		} else {
			p.sendMessage(MuteUtils.muted);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		FileConfiguration custom = MuteConfig.getConfig();
		FileConfiguration config = Main.getInstance().getConfig();

		if (!(custom.contains("MutedPlayers." + p.getUniqueId().toString()))) {
			return;
		} else {
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
			MuteUtils.players.add(p.getUniqueId());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		FileConfiguration config = Main.getInstance().getConfig();

		if (!(MuteUtils.players.contains(p.getUniqueId()))) {
			return;
		} else {
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
			MuteUtils.players.remove(p.getUniqueId());
		}
	}
}