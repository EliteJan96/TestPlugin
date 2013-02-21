package de.EliteJan96.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Functions extends JavaPlugin {

	public static String isOnlineTeam(String string) {
		Player target = Bukkit.getServer().getPlayer(string);
		if (target != null) {
			string = ChatColor.YELLOW + string;
		} else {
			string = ChatColor.GRAY + string;
		}
		return string;
	}
	
	public static boolean isOnline(String spieler, Player send, PluginDescriptionFile desc) {
		Player rec = Bukkit.getPlayer(spieler);
		if (rec != null) {
			if (Main.vanish.contains(spieler) && !(send.hasPermission(desc.getName() + ".vanish.see"))) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public static void sendMsg(String sender, String reciever, String nachricht) {
		Player sender2 = Bukkit.getPlayer(sender);
		Player reciever2 = Bukkit.getPlayer(reciever);
		
		if (sender2 != null && reciever2 != null) {
		
		sender2.sendMessage(ChatColor.GRAY + "[mir -> " + ChatColor.GOLD + reciever2.getName() + ChatColor.GRAY + "] " + ChatColor.GREEN + nachricht);
		reciever2.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + sender2.getName() + ChatColor.GRAY + "] " + ChatColor.GREEN + nachricht);
		
		} else {
			System.out.println("Achtung! Es liegt ein Fehler im /msg-Command vor! Bitte beheben.");
		}
	}

}
