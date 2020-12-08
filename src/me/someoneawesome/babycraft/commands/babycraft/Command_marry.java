package me.someoneawesome.babycraft.commands.babycraft;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.commands.BcCommand;
import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.entity.BcPermission;
import me.someoneawesome.babycraft.entity.Gender;
import me.someoneawesome.babycraft.marry.MarriageRequest;

public class Command_marry implements BcCommand {

	@Override
	public void onCommand(CommandSender arg0, String[] arg1) {
		if(arg1.length > 0) {
			if(arg0 instanceof Player) {
			
				Player p = (Player) arg0;
				ConfigAgent config = ConfigAgent.instance;
				
				if (arg1[0].equalsIgnoreCase("list")) {
					//Collects list
					List<UUID> list = config.getPlayerPartners(p.getUniqueId());
					
					ArrayList<UUID> husbands = new ArrayList<>();
					ArrayList<UUID> wives = new ArrayList<>();
					ArrayList<UUID> others = new ArrayList<>();
					
					for(UUID cur : list) {
						Gender g = config.getPlayerGender(cur);
						if(g == Gender.MALE) {
							husbands.add(cur);
						} else if(g == Gender.FEMALE) {
							wives.add(cur); 
						} else {
							others.add(cur);
						}
					}
					
					if(husbands.size() + wives.size() + others.size() > 0) {
						ArrayList<String> msg = new ArrayList<String>();
						
						if(husbands.size() > 0) {
							msg.add(getPlayerList(ChatColor.BLUE, "Husband", husbands));
						}
						if(wives.size() > 0) {
							msg.add(getPlayerList(ChatColor.LIGHT_PURPLE, "wife", wives));
						}
						if(others.size() > 0) {
							msg.add(getPlayerList(ChatColor.AQUA, "Spouse", others));
						}
						String[] r = msg.toArray(new String[msg.size()]);
						arg0.sendMessage(r);
					} else {
						// no spouses
						arg0.sendMessage(ChatColor.RED + "You have no spouse :(");
					}
					//end of list command
				} else if(arg1[0].equalsIgnoreCase("divorce")) {
					//divorce command
					if(arg1.length > 1) {
						//Check if username is valid name
						Player other = Bukkit.getPlayer(arg1[1]);
						
						if(other != null) {
							UUID otherUID = other.getUniqueId();
							//remove from list
							//Check if exists in list
							if(config.getPlayerPartners(p.getUniqueId()).contains(otherUID)) {
								config.removePlayerPartner(p.getUniqueId(), otherUID);
								config.removePlayerPartner(otherUID, p.getUniqueId());
								config.savePlayer();
								arg0.sendMessage(ChatColor.RED + "The player " + arg1[1] + " is no longer your spouse!");
							} else {
								arg0.sendMessage(ChatColor.RED + "The player " + arg1[1] + " is not your spouse!");
							}
						} else {
							arg0.sendMessage(ChatColor.RED + "The player " + arg1[1] + " does not exist!");
						}
						
					} else {
						arg0.sendMessage(ChatColor.RED + "Usage: /babycraft marry divorce <playername>");
					}
					//end of divorce command
				} else if(arg1[0].equalsIgnoreCase("accept")) {
					if(MarriageRequest.requestContains(p.getUniqueId())) {
						if(!MarriageRequest.getRequest(p.getUniqueId()).accept(p.getUniqueId())) {
							arg0.sendMessage(ChatColor.RED + "You have to wait for your spouse to accept");
						}
					} else {
						arg0.sendMessage(ChatColor.RED + "You have no pending requests");
					}
					//end of accept
				} else if(arg1[0].equalsIgnoreCase("deny")) {
					if(MarriageRequest.requestContains(p.getUniqueId())) {
						MarriageRequest.getRequest(p.getUniqueId()).deny();
					} else {
						arg0.sendMessage(ChatColor.RED + "You have no pending requests");
					}
					//end of deny
				} else {
					//Send marriage request
						//player 1 uuid
						UUID p1id = p.getUniqueId();
						//Check permissions
						if(config.getAllowMultiMarriage() || config.getPlayerPartners(p1id).size() == 0 || p.hasPermission(BcPermission.MULTI_MARRIAGE.toString())) {
							
							//Checks if valid player
							Player p2 = Bukkit.getPlayer(arg1[0]);
							
							if(p2 != null) {
								UUID p2id = p2.getUniqueId();
								//Checks if already married
								if(!config.getPlayerPartners(p1id).contains(p2id)) {
									//checks if has multi perms
									if(config.getAllowMultiMarriage() || config.getPlayerPartners(p2id).size() == 0 || p.hasPermission(BcPermission.MULTI_MARRIAGE.toString())) {
										//checks if has gender perms
										if(config.getSameGenderMarriage() || (p.hasPermission(BcPermission.SAME_GENDER_MARRIAGE.toString()) && p2.hasPermission(BcPermission.SAME_GENDER_MARRIAGE.toString()))) {
											//Adds to request
											MarriageRequest.addToRequest(p1id, p2id, new MarriageRequest(p1id, p2id));
										} else {
											arg0.sendMessage(ChatColor.RED + "Either you or your spouse does not have permission to marry the same gender");
										}
									} else {
										arg0.sendMessage(ChatColor.RED + arg1[0] + " does not have permission to marry multiple players");
									}
								} else {
									arg0.sendMessage(ChatColor.RED + "The player " + arg1[0] + " is already your spouse");
								}
							} else {
								arg0.sendMessage(ChatColor.RED + "The player " + arg1[0] + " does not exist!");
							}
						} else {
							//no permission for multi marriage
							arg0.sendMessage(ChatColor.RED + "You have no permission to marry multiple players");
						}
					//end of request
				}
			} else {
				arg0.sendMessage(ChatColor.RED + "Only Players can run this command");
			}
		} else {
			arg0.sendMessage(ChatColor.RED + "Usage: /babycraft marry <list, divorce, accept, deny, playerusername>");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		
		List<String> options = new ArrayList<>();
		
		if(arg1.length > 0) {
			
			if(arg1[0].equalsIgnoreCase("divorce")) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getName().startsWith(arg1[0]))
						options.add(p.getName());
				}
			} else {
				if("list".startsWith(arg1[0]))
					options.add("list");
				
				if("divorce".startsWith(arg1[0]))
					options.add("divorce");
				
				if("accept".startsWith(arg1[0]))
					options.add("accept");
				
				if("deny".startsWith(arg1[0]))
					options.add("deny");
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getName().startsWith(arg1[0]))
						options.add(p.getName());
				}
			}
		} else {
			options.add("list");
			options.add("divorce");
			options.add("accept");
			options.add("deny");
			for(Player p : Bukkit.getOnlinePlayers()) {
				options.add(p.getName());
			}
		}
		
		return options;
	}

	@Override
	public boolean isCommand(String cmd) {
		return getName().equalsIgnoreCase(cmd);
	}

	@Override
	public String[] getUsage() {
		return new String[] {"/babycraft marry <list, divorce, accept, deny, playerusername>"};
	}

	@Override
	public String getName() {
		return "marry";
	}
	
	private String getPlayerList(ChatColor c, String prefix, List<UUID> players) {
		String r = c + prefix + ": ";
		for(UUID p : players) {
			r += Bukkit.getOfflinePlayer(p).getName() + ", ";
		}
		return r.substring(0, r.length() - 2);
	}

}
