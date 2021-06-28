package me.someoneawesome.babycraft.baby.creation;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.Debug;

public abstract class BabyCreationAction {
	
	private static HashMap<UUID, BabyCreationAction> creators = new HashMap<>();
	
	public static boolean containsPlayer(UUID p) {
		return creators.containsKey(p);
	}
	
	public static BabyCreationAction getAction(UUID p) {
		return creators.get(p);
	}
	
	public static boolean hasSameAction(UUID p, UUID p2) {
		return creators.get(p) == creators.get(p2);
	}
	
	public static void startCreation(UUID p1, UUID p2) {
		//insert specific type here
		new ClickCreation(p1, p2);
	}
	
	protected UUID p1;
	protected UUID p2;
	
	public BabyCreationAction(UUID p1, UUID p2) {
		//cache data
		this.p1 = p1;
		this.p2 = p2;
		
		//add to running creators
		creators.put(p1, this);
		creators.put(p2, this);
		
		Player pp1 = Bukkit.getPlayer(p1);
		Player pp2 = Bukkit.getPlayer(p2);
		
		Debug.log(pp1.getName() + " and " + pp2.getName() + " are creating a baby");
	}
	
	public void completeCreation() {
		//Send to pregnancy stage
		Player pp1 = Bukkit.getPlayer(p1);
		Player pp2 = Bukkit.getPlayer(p2);
		pp1.sendMessage("Completed Creation Stage");
		pp2.sendMessage("Completed Creation Stage");
	}
	
	public void cancel() {
		//set
		creators.remove(p1);
		creators.remove(p2);
		
		//Tell user
		Player pp1 = Bukkit.getPlayer(p1);
		Player pp2 = Bukkit.getPlayer(p2);
		
		pp1.sendMessage(ChatColor.RED + "Child creation was canceled");
		pp2.sendMessage(ChatColor.RED + "Child creation was canceled");
	}
	
}
