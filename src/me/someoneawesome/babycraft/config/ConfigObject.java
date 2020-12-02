package me.someoneawesome.babycraft.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import me.someoneawesome.babycraft.Babycraft;
import me.someoneawesome.babycraft.Debug;

public class ConfigObject {
	
	//plugin cache
	private Babycraft plugin;
	
	private FileConfiguration config;
	private File file;
	private String filename;
	
	public ConfigObject(Babycraft plugin, String filename) {
		this.plugin = plugin;
		this.filename = filename;
	}
	
	public boolean setup() {
		//Checks if directory exists, if not create it
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		//tests if config exists
		file = new File(plugin.getDataFolder(), filename);
		if(!file.exists()) {
			//It does not exist
			Debug.warn(filename + " does not exist! Creating file");
			try {
				file.createNewFile();
				Debug.success(filename + " was created successfully");
			} catch (IOException e) {
				Debug.error(filename + "Failed to be created, stopping creation", e.getStackTrace());
				return false;
			}
		}
		
		//yaml config check
		try {
			config = YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			Debug.error(filename + " Yaml configureation failed to parse, reseting that file and marking old one as old", e.getStackTrace());
			reset("Yaml Format error");
			return false;
		}
		Debug.success(filename + " was loaded successfully");
		return true;
	}
	
	public void save() {
		Debug.log(filename + " is attempting to save");
		try {
			config.save(file);
			Debug.log(filename + " has been saved");
		} catch (IOException e) {
			Debug.error("Error! " + filename + " failed to save!", e.getStackTrace());
		}
	}
	
	public boolean reload() {
		Debug.log("Reloading " + filename);
		file = new File(plugin.getDataFolder(), filename);
		if(!file.exists()) {
			//It does not exist
			Debug.warn(filename + " does not exist! Creating file");
			try {
				file.createNewFile();
				Debug.success(filename + " was created successfully");
			} catch (IOException e) {
				Debug.error(filename + "Failed to be created, stopping creation", e.getStackTrace());
				return false;
			}
		}
		
		//yaml config check
		try {
			config = YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			Debug.error(filename + " Yaml configureation failed to parse, reseting that file and marking old one as old", e.getStackTrace());
			reset("Yaml Format error");
			return false;
		}
		Debug.success(filename + " was reloaded successfully");
		return true;
	}
	
	public void reset(String reason) {
		//Tell user
		Debug.warn(filename + " is being reloaded for the following reason: " + reason);
		
		//Create new file
		String newfilename = filename + "-old";
		File newConfig = new File(plugin.getDataFolder(), newfilename + ".txt");
		
		int x = 0;
		
		//Keeps editing the file until valid name can be found
		while(newConfig.exists()) {
			x++;
			newConfig = new File(plugin.getDataFolder(), newfilename + x + ".txt");
		}
		
		//Writes old file to new file
		try {
			FileWriter writer = new FileWriter(newConfig);
			FileReader reader = new FileReader(file);
			
			writer.write(reader.read());
			writer.close();
			reader.close();
		} catch (IOException e) {
			Debug.error("Could not create 'OLD' files, giving up and continuing", e.getStackTrace());
		}
		
		//Creates new file
		try {
			file.createNewFile();
			Debug.success(filename + " was successfully recreated");
		} catch (IOException e) {
			Debug.error("Error creating new " + filename + " file. Giving up", e.getStackTrace());
		}
		
		try {
			config = YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			Debug.error("Unexpected Error. If you see this something is obviously wrong and I don't know why. This error message should never be called. Just know the code is just giving up.", e.getStackTrace());
		}
		
		Debug.success("Reset successful!");
	}
	
	public void set(String path, Object obj) {
		config.set(path, obj);
	}
	
	public int getInt(String path) {
		return config.getInt(path);
	}
	
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	
	public String getString(String path) {
		return config.getString(path);
	}
	
	public double getDouble(String path) {
		return config.getDouble(path);
	}
	
	public Color getColor(String path) {
		return config.getColor(path); 
	}
	
	public ItemStack getItemStack(String path) {
		return config.getItemStack(path); 
	}
	
	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}
	
	
}
