package de.EliteJan96.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static String noPerm = ChatColor.RED + "Du hast leider keine ausreichenden Rechte um diesen Befehl auszuführen.";
	
	@Override
	public void onEnable() {
		PluginDescriptionFile desc = this.getDescription();
		System.out.println("[" + desc.getName() + "] wurde aktiviert.");
		System.out.println("[" + desc.getName() + "] by " + desc.getAuthors() + ", Version: " + desc.getVersion() + "");
		Bukkit.getPluginManager().registerEvents(new ListenerClass(), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("[TestPlugin] wurde deaktiviert.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			PluginDescriptionFile desc = this.getDescription();
			
			if (p.hasPermission(desc.getName() + "." + cmd.getName())) {
				
				
				//Heilen-Command
				if (cmd.getName().equalsIgnoreCase("heile")) {
					if (p.hasPermission("TestPlugin.heile.andere")) {
						if (args.length == 0) {
							p.setHealth(20);
							p.setFoodLevel(20);
							p.sendMessage(ChatColor.GOLD + "Du wurdest geheilt.");
						} else if (args.length == 1) {
							Player target = Bukkit.getServer().getPlayer(args[0]);
							if (target != null) {
								target.setHealth(20);
								target.setFoodLevel(20);
								target.sendMessage(ChatColor.GOLD + "Du wurdest von " + sender.getName() + " geheilt.");
								p.sendMessage(ChatColor.GOLD + "Du hast " + sender.getName() + " geheilt.");
							} else {
								p.sendMessage(ChatColor.DARK_RED + "Fehler: " + ChatColor.RED + "Der Spieler ist nicht online.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Verwendung: /heile [Spieler]");
						}
					} else {
						p.setHealth(20);
						p.setFoodLevel(20);
						p.sendMessage(ChatColor.GOLD + "Du wurdest geheilt.");
					}
				}
				
				if (cmd.getName().equalsIgnoreCase("ban")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "Verwendung: /ban <Spieler> [Grund]");
					} else if (args.length == 1) {
						String target = args[0];
						if (Bukkit.getPlayer(target) != null) {
							if (this.getConfig().contains("Bans." + target + ".status")) {
								this.getConfig().set("Bans." + target + ".status", true);
							} else {
								this.getConfig().addDefault("Bans." + target + ".status", true);
							}
							Bukkit.getPlayer(target).kickPlayer(ChatColor.DARK_RED + "Du wurdest permanent gebannt.");
						} else {
							this.getConfig().addDefault("Bans." + target + ".status", true);
						}
						p.sendMessage(ChatColor.RED + "Der Spieler wurde gebannt.");
					} else if (args.length > 1) {
						String target = args[0];
						if (Bukkit.getPlayer(target) != null) {
							StringBuilder sb = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								if (i != 0)
									sb.append(' ');
								sb.append(args[i]);
							}
							String nachricht = sb.toString();
							if (this.getConfig().contains("Bans." + target + ".status")) {
								this.getConfig().set("Bans." + target + ".status", true);
								this.getConfig().set("Bans." + target + ".message", nachricht);
							} else {
								this.getConfig().addDefault("Bans." + target + ".status", true);
								this.getConfig().addDefault("Bans." + target + ".message", nachricht);
							}
							Bukkit.getPlayer(target).kickPlayer(ChatColor.DARK_RED + "Du wurdest permanent gebannt.");
						} else {
							StringBuilder sb = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								if (i != 0)
									sb.append(' ');
								sb.append(args[i]);
							}
							String nachricht = sb.toString();
							if (this.getConfig().contains("Bans." + target + ".status")) {
								this.getConfig().set("Bans." + target + ".status", true);
								this.getConfig().set("Bans." + target + ".message", nachricht);
							} else {
								this.getConfig().addDefault("Bans." + target + ".status", true);
								this.getConfig().addDefault("Bans." + target + ".message", nachricht);
							}
						}
						p.sendMessage(ChatColor.RED + "Der Spieler wurde gebannt.");
					}
					
				}
				
				if (cmd.getName().equalsIgnoreCase("entban")) {
					if (args.length == 1) {
						String target = args[0];
						this.getConfig().addDefault("Bans." + target + ".status", false);
						this.getConfig().set("Bans." + target + ".message", "");
						p.sendMessage(ChatColor.GREEN + "Der Spieler wurde entbannt.");
					} else {
						p.sendMessage(ChatColor.RED + "Verwendung: /entban <Spieler>");
					}
					
				}
				
				
			} else {
				p.sendMessage(noPerm);
			}
			
		} else {
			
			if (cmd.getName().equalsIgnoreCase("heile")) {
				
			}
			
		}
		
		return true;
	}

}
