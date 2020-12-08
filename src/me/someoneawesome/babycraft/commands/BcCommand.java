package me.someoneawesome.babycraft.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface BcCommand {
	/**
	 * Base parent class for Command
	 */
	
	public void onCommand(CommandSender arg0, String[] arg1);
	public List<String> onTabComplete(CommandSender arg0, String[] arg1);
	public boolean isCommand(String cmd);
	public String[] getUsage();
	public String getName();
}
