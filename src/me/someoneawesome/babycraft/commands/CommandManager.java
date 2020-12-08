package me.someoneawesome.babycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.*;

public class CommandManager implements TabExecutor {

	private BcCommand[] commands = {new Command_Babycraft(), new Command_BcAdmin()};
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Find ParentCommand
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1.getName())) {
				return cmd.onTabComplete(arg0, arg3);
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1.getName())) {
				cmd.onCommand(arg0, arg3);
				return true;
			}
		}
		
		List<String> cmds = new ArrayList<>();
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b&lCommands"));
		for(int x = 0; x < commands.length; x++) {
			String[] s = commands[x].getUsage();
			for(String str : s) {
				cmds.add(ChatColor.translateAlternateColorCodes('&',"&b" + str));
			}
		}
		cmds.add(ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		
		arg0.sendMessage((String[]) cmds.toArray());
		
		return true;
	}

}
