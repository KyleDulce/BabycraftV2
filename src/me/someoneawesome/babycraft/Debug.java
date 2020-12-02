package me.someoneawesome.babycraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.*;

public class Debug {
	
	public static Debug instance;
	
	public static void success(String s) {
		instance.logGoodToConsole(s);
	}
	
	public static void log(String s) {
		instance.logToConsole(s);
	}
	
	public static void warn(String s) {
		instance.warnToConsole(s);
	}
	
	public static void error(String s, StackTraceElement[] trace) {
		instance.errorToConsole(s, trace);
	}
	
	public static void init() {
		instance = new Debug();
		instance.logger = Bukkit.getLogger();
	}
	
	private Logger logger;
	
	public void logGoodToConsole(String s) {
		logger.log(Level.INFO, ChatColor.GREEN + s);
	}
	
	public void logToConsole(String s) {
		logger.log(Level.INFO, s);
	}
	
	public void warnToConsole(String s) {
		logger.log(Level.WARNING, ChatColor.YELLOW + s);
	}
	
	public void errorToConsole(String s, StackTraceElement[] trace) {
		logger.log(Level.SEVERE, ChatColor.RED + s);
		for(StackTraceElement e : trace)
			logger.log(Level.SEVERE, e.toString());
	}
	
}
