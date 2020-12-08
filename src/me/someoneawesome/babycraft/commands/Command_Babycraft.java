package me.someoneawesome.babycraft.commands;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.someoneawesome.babycraft.commands.babycraft.*;

public class Command_Babycraft implements BcCommand {

	private BcCommand[] commands = {new Command_setGender(), new Command_list(), new Command_marry()};
	
	@Override
	public void onCommand(CommandSender arg0, String[] arg1) {
		//Tries to execute commands
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1[0])) {
				cmd.onCommand(arg0, removeFirstElement(arg1));
				return;
			}
		}
		
		//Failed to execute, shows subcommands
		List<String> cmds = new ArrayList<>();
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b&lBabycraft Subcommands"));
		for(int x = 0; x < commands.length; x++) {
			String[] s = commands[x].getUsage();
			for(String str : s) {
				cmds.add(ChatColor.translateAlternateColorCodes('&',"&b" + str));
			}
		}
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		
		arg0.sendMessage((String[]) cmds.toArray());
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		//Check for partial Completion, if so show those recomendations
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1[0])) {
				return cmd.onTabComplete(arg0, removeFirstElement(arg1));
			}
		}
		
		//Show the other options
		//Gets list of all subcommands
		List<String> list = new ArrayList<>();
		
		if(arg1.length > 0) {
			for(BcCommand cmd : commands) {
				if(cmd.getName().toLowerCase().startsWith(arg1[0].toLowerCase())) {
					list.add(cmd.getName());
				}
			}
		} else {
			for(BcCommand cmd : commands) {
				list.add(cmd.getName());
			}
		}
		
		Collections.sort(list);
		return list;
	}

	@Override
	public boolean isCommand(String cmd) {
		return cmd.equalsIgnoreCase(getName());
	}

	@Override
	public String[] getUsage() {
		List<String> cmds = new ArrayList<>();
		for(int x = 0; x < commands.length; x++) {
			String[] s = commands[x].getUsage();
			for(String str : s) {
				cmds.add(str);
			}
		}
		return (String[]) cmds.toArray();
	}

	@Override
	public String getName() {
		return "babycraft";
	}

	private String[] removeFirstElement(String[] array) {
		List<String> n = new ArrayList<String>();
		for(int x = 1; x < array.length; x++)
			n.add(array[x]);
		
		String[] r = new String[n.size()];
		r = n.toArray(r);
		return r;
	}
}
