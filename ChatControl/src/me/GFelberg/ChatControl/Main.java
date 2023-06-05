package me.GFelberg.ChatControl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.GFelberg.ChatControl.commands.ChatControl;
import me.GFelberg.ChatControl.commands.Mute;
import me.GFelberg.ChatControl.commands.Unmute;
import me.GFelberg.ChatControl.data.MuteConfig;
import me.GFelberg.ChatControl.data.MuteSystem;
import me.GFelberg.ChatControl.events.ChatControlEvents;
import me.GFelberg.ChatControl.utils.ChatControlUtils;

public class Main extends JavaPlugin {

	private static Main instance;

	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		loadVariables();
		loadCommands();
		loadMuteConfig();
		getServer().getPluginManager().registerEvents(new ChatControlEvents(), this);
		Bukkit.getConsoleSender().sendMessage("----------------------------");
		Bukkit.getConsoleSender().sendMessage("ChatControl Plugin Enabled!");
		Bukkit.getConsoleSender().sendMessage("Plugin developed by GFelberg");
		Bukkit.getConsoleSender().sendMessage("----------------------------");
	}

	public static Main getInstance() {
		return instance;
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("----------------------------");
		Bukkit.getConsoleSender().sendMessage("ChatControl Plugin Disabled!");
		Bukkit.getConsoleSender().sendMessage("Plugin developed by GFelberg");
		Bukkit.getConsoleSender().sendMessage("----------------------------");
	}

	public void loadVariables() {
		ChatControlUtils.loadVariables();
		MuteSystem.loadVariables();
	}

	public void loadCommands() {
		getCommand("chatcontrol").setExecutor(new ChatControl());
		getCommand("mute").setExecutor(new Mute());
		getCommand("unmute").setExecutor(new Unmute());
	}

	public void loadMuteConfig() {
		MuteConfig.setupConfig();
		MuteConfig.getConfig().options().copyDefaults(true);
		MuteConfig.saveConfig();
	}
}