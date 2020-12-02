package me.someoneawesome.babycraft.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CommandManager implements TabExecutor {

	private BcCommand[] commands = {};
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Find ParentCommand
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1)) {
				return cmd.onTabComplete(arg0, arg3);
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		for(BcCommand cmd : commands) {
			if(cmd.isCommand(arg1)) {
				cmd.onCommand(arg0, arg3);
				return true;
			}
		}
		
		String[] cmds = new String[commands.length + 3];
		cmds[0] = ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		cmds[1] = ChatColor.translateAlternateColorCodes('&', "&b&lCommands");
		for(int x = 0; x < commands.length; x++) {
			if(commands[x].isCommand(arg1)) {
				cmds[x + 2] = ChatColor.translateAlternateColorCodes('&',"&b" + commands[x].getUsage());
			}
		}
		cmds[cmds.length - 1] = ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		return true;
	}

}
