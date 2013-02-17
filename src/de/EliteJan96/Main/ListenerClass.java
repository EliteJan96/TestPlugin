package de.EliteJan96.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerClass extends JavaPlugin implements Listener {
	
	private Plugin plugin;
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		PluginDescriptionFile desc = this.getDescription();
		plugin = Bukkit.getPluginManager().getPlugin(desc.getName());
		if (plugin.getConfig().contains("Bans." + event.getPlayer().getName() + ".status")) {
			if (plugin.getConfig().getBoolean("Bans." + event.getPlayer().getName() + ".status")) {
				if (plugin.getConfig().contains("Bans." + event.getPlayer().getName() + ".message")) {
					if (!plugin.getConfig().getString("Bans." + event.getPlayer().getName() + ".message").equalsIgnoreCase("")) {
						event.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "Du wurdest permanent gebannt. Grund: " + plugin.getConfig().getString("Bans." + event.getPlayer().getName() + ".message"));
					} else {
						event.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "Du wurdest permanent gebannt.");
					}
				} else {
					event.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "Du wurdest permanent gebannt.");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + " ist dem Spiel beigetreten.");
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getName() + " hat das Spiel verlassen.");
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		PluginDescriptionFile desc = this.getDescription();
		plugin = Bukkit.getPluginManager().getPlugin(desc.getName());
		if (event.getPlayer().hasPermission(desc.getName() + ".ignoreKicks")) {
			event.setCancelled(true);
		}
		event.setLeaveMessage(ChatColor.YELLOW + event.getPlayer().getName() + " hat das Spiel verlassen.");
	}
	
}
