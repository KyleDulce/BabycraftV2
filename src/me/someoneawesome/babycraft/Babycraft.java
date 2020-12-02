package me.someoneawesome.babycraft;

import org.bukkit.plugin.java.JavaPlugin;

public class Babycraft extends JavaPlugin {
	/**
	 * Base Class for plugin
	 */
	
	/**
	 * Base Constructor
	 * Initializes variables
	 */
	public Babycraft() {
		
	}
	
	@Override
	public void onEnable() {
		//Start Logger
		Debug.init();
		
		
	}
	
}
