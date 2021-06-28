package me.someoneawesome.babycraft;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.someoneawesome.babycraft.baby.creation.ClickCreation;
import me.someoneawesome.babycraft.commands.CommandManager;
import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.marry.MarriageEventHandler;
/**
 * Base Class for plugin
 */
public class Babycraft extends JavaPlugin {
	
	public static Babycraft instance;

	private CommandManager cmdManager;
	
	/**
	 * Base Constructor
	 * Initializes variables
	 */
	public Babycraft() {
		//Cache plugin
		instance = this;
		//Cache ConfigAgent
		ConfigAgent.instance = new ConfigAgent(this);
		//Cache Command Manager
		cmdManager = new CommandManager();
	}
	
	@Override
	public void onEnable() {
		//Start Logger
		Debug.init(this);
		
		Debug.log("Enabling Babycraft");
		
		//Initialize Config agent
		ConfigAgent.instance.loadFiles();
		
		//Setup commands
		getCommand("babycraft").setExecutor(cmdManager);
		getCommand("babycraft").setTabCompleter(cmdManager);
		
		getCommand("bcadmin").setExecutor(cmdManager);
		getCommand("bcadmin").setTabCompleter(cmdManager);
		
		//Setup listeners
		PluginManager plManager = getServer().getPluginManager();
		
		//marriage events
		plManager.registerEvents(new MarriageEventHandler(), this);
		
		//Creation events
		plManager.registerEvents(new ClickCreation.CreationListener(), this);
		
		Debug.log("Babycraft Enabled");
	}
	
	@Override
	public void onDisable() {
		
		//Saves files
		ConfigAgent.instance.saveFiles();
		
		Debug.log("Babycraft Disabled");
	}
	
}
