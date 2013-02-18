package de.EliteJan96.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Functions extends JavaPlugin {

	public static String isOnline(String string) {
		Player target = Bukkit.getServer().getPlayer(string);
		if (target != null) {
			string = ChatColor.YELLOW + string;
		} else {
			string = ChatColor.GRAY + string;
		}
		return string;
	}

}
