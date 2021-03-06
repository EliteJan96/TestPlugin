package de.EliteJan96.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin {

	public static String noPerm = ChatColor.RED + "Du hast leider keine ausreichenden Rechte um diesen Befehl auszuführen.";
	public static List<String> vanish = new ArrayList<String>();
	public static HashMap<String, String> msg = new HashMap<String, String>();
	
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
				
				if (cmd.getName().equalsIgnoreCase("tp")) {
					if (args.length == 1) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						if (target != null) {
							Location targetLoc = null;
							targetLoc = target.getLocation();
							if (p.getVehicle() != null) {
								p.teleport(targetLoc);
							} else {
								List<Entity> entitylist = p.getNearbyEntities(3, 3, 3);
								for (Entity e : entitylist) {
									e.remove();
									Bukkit.getWorld(e.getLocation().getWorld().getName()).spawnEntity(e.getLocation(), e.getType());
								}
								p.teleport(targetLoc);
							}
						} else {
							p.sendMessage(ChatColor.RED + "Der Spieler ist nicht online.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Verwendung: /tp <Spieler>");
					}
				}
				
				if (cmd.getName().equalsIgnoreCase("team")) {
					PermissionManager pex = PermissionsEx.getPermissionManager();
					String owner, admin;
					owner = admin = "";
					for (int i = 0; i < pex.getGroup("Owner").getUsers().length; i++) {
						owner = owner + Functions.isOnlineTeam(pex.getGroup("Owner").getUsers()[i].getName()) + " " + ChatColor.DARK_AQUA + "| ";
					}
					for (int i = 0; i < pex.getGroup("Admin").getUsers().length; i++) {
						admin = admin + Functions.isOnlineTeam(pex.getGroup("Admin").getUsers()[i].getName()) + " " + ChatColor.DARK_AQUA + "| ";
					}
					p.sendMessage(ChatColor.GREEN + "[]" + ChatColor.GOLD + " --> " + ChatColor.DARK_AQUA + "Team" + ChatColor.GOLD + " <-- " + ChatColor.GREEN + "[]");
					p.sendMessage(ChatColor.DARK_RED + "Owner: " + ChatColor.RESET + owner.substring(0, owner.length() - 2));
					p.sendMessage(ChatColor.RED + "Admins: " + ChatColor.RESET + admin.substring(0, admin.length() - 2));
				}
				
				if (cmd.getName().equalsIgnoreCase("vanish")) {
					if (vanish.contains(p.getName())) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (!player.hasPermission(desc.getName() + ".vanish.see")) {
								player.showPlayer(p);
							}
						}
						p.sendMessage(ChatColor.GREEN + "Du bist nun wieder sichtbar.");
					} else {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (!player.hasPermission(desc.getName() + ".vanish.see")) {
								player.hidePlayer(p);
							}
						}
						p.sendMessage(ChatColor.GREEN + "Du bist nun unsichtbar.");
					}
				}
				
				if (cmd.getName().equalsIgnoreCase("msg")) {
					if (Functions.isOnline(args[0], p, desc) == true) {
						if (args.length > 2) {
							PermissionManager pex = PermissionsEx.getPermissionManager();
							if (pex.has(Bukkit.getPlayer(args[0]), desc.getName() + ".msg.admin") && !(pex.has(p, desc.getName() + ".msg.admin"))) {
								p.sendMessage(ChatColor.DARK_RED + "Admins reagieren oft nicht auf MSGs. Bitte wende dich für Fragen an einen Supporter.");
							}
							StringBuilder sb = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								if (i != 0)
									sb.append(' ');
								sb.append(args[i]);
							}
							String nachricht = sb.toString();
							Functions.sendMsg(p.getName(), args[0], nachricht);
						} else {
							p.sendMessage(ChatColor.RED + "Verwendung: /msg <Spieler> <Nachricht>");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Der Spieler wurde nicht gefunden.");
					}
				}
				
				if (cmd.getName().equalsIgnoreCase("r")) {
					if (msg.containsKey(p.getName())) {
						if (Functions.isOnline(msg.get(p.getName()), p, desc)) {
							PermissionManager pex = PermissionsEx.getPermissionManager();
							if (pex.has(Bukkit.getPlayer(msg.get(p.getName())), desc.getName() + ".msg.admin") && !(pex.has(p, desc.getName() + ".msg.admin"))) {
								p.sendMessage(ChatColor.DARK_RED + "Admins reagieren oft nicht auf MSGs. Bitte wende dich für Fragen an einen Supporter.");
							}
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								if (i != 0)
									sb.append(' ');
								sb.append(args[i]);
							}
							String nachricht = sb.toString();
							Functions.sendMsg(p.getName(), msg.get(p.getName()), nachricht);
						} else {
							p.sendMessage(ChatColor.RED + "Der Spieler ist nicht mehr online.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Du hast keinen, dem du antworten kannst.");
					}
				}
				
			} else {
				p.sendMessage(noPerm);
			}
			
		} else {
			
			//Konsolencommands
			
			if (cmd.getName().equalsIgnoreCase("heile")) {
				if (args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if (target != null) {
						target.setHealth(20);
						target.setFoodLevel(20);
						target.sendMessage(ChatColor.GOLD + "Du wurdest von der Konsole geheilt.");
						sender.sendMessage(ChatColor.GOLD + "Du hast " + sender.getName() + " geheilt.");
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "Fehler: " + ChatColor.RED + "Der Spieler ist nicht online.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Verwendung: /heile <Spieler>");
				}
			}
			
			if (cmd.getName().equalsIgnoreCase("ban")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Verwendung: /ban <Spieler> [Grund]");
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
					sender.sendMessage(ChatColor.RED + "Der Spieler wurde gebannt.");
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
					sender.sendMessage(ChatColor.RED + "Der Spieler wurde gebannt.");
				}
				
			}
			
			if (cmd.getName().equalsIgnoreCase("entban")) {
				if (args.length == 1) {
					String target = args[0];
					this.getConfig().addDefault("Bans." + target + ".status", false);
					this.getConfig().set("Bans." + target + ".message", "");
					sender.sendMessage(ChatColor.GREEN + "Der Spieler wurde entbannt.");
				} else {
					sender.sendMessage(ChatColor.RED + "Verwendung: /entban <Spieler>");
				}
				
			}
			
			if (cmd.getName().equalsIgnoreCase("team")) {
				PermissionManager pex = PermissionsEx.getPermissionManager();
				String owner, admin;
				owner = admin = "";
				for (int i = 0; i < pex.getGroup("Owner").getUsers().length; i++) {
					owner = owner + Functions.isOnlineTeam(pex.getGroup("Owner").getUsers()[i].getName()) + " " + ChatColor.DARK_AQUA + "| ";
				}
				for (int i = 0; i < pex.getGroup("Admin").getUsers().length; i++) {
					admin = admin + Functions.isOnlineTeam(pex.getGroup("Admin").getUsers()[i].getName()) + " " + ChatColor.DARK_AQUA + "| ";
				}
				sender.sendMessage(ChatColor.GREEN + "[]" + ChatColor.GOLD + " --> " + ChatColor.DARK_AQUA + "Team" + ChatColor.GOLD + " <-- " + ChatColor.GREEN + "[]");
				sender.sendMessage(ChatColor.DARK_RED + "Owner: " + ChatColor.RESET + owner);
				sender.sendMessage(ChatColor.RED + "Admins: " + ChatColor.RESET + admin);
			}
			
		}
		
		return true;
	}

}
