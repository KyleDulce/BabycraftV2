package me.someoneawesome.babycraft.commands.babycraft;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.commands.BcCommand;
import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.entity.Gender;

public class Command_list implements BcCommand{

	@Override
	public void onCommand(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof Player) {
			Player p = (Player) arg0;
			
			ConfigAgent config = ConfigAgent.instance;
			
			List<UUID> children = config.getPlayerChildren(p.getUniqueId());
			List<String> msgs = new ArrayList<>();
			 
			//Go through each child
			for(UUID c : children) {
				String name = config.getChildName(c);
				Gender g = config.getChildGender(c);
				
				msgs.add(ChatColor.translateAlternateColorCodes('&', ((g == Gender.FEMALE)? "&d" : "&b") + name));
			}
			
			p.sendMessage((String[]) msgs.toArray());
		} else {
			arg0.sendMessage(ChatColor.RED + "Only players can run this command");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

	@Override
	public boolean isCommand(String cmd) {
		return getName().equalsIgnoreCase(cmd);
	}

	@Override
	public String[] getUsage() {
		return new String[] {"/babycraft list"};
	}

	@Override
	public String getName() {
		return "list";
	}

}
