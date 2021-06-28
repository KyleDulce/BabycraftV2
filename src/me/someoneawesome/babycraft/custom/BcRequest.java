package me.someoneawesome.babycraft.custom;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.Babycraft;
import me.someoneawesome.babycraft.config.ConfigAgent;

public abstract class BcRequest {
private static HashMap<UUID, BcRequest> requests = new HashMap<>();
	
	public static void addToRequest(UUID p1, UUID p2, BcRequest req) {
		requests.put(p1, req);
		requests.put(p2, req);
	}
	
	public static void removeRequest(BcRequest req) {
		requests.remove(req.requester);
		requests.remove(req.reciever);
	}
	
	public static void removeRequest(UUID p) {
		removeRequest(requests.get(p));
	}
	
	public static BcRequest getRequest(UUID p) {
		return requests.get(p);
	}
	
	public static boolean requestContains(UUID p) {
		return requests.containsKey(p);
	}
	
	protected UUID requester;
	protected UUID reciever;
	private int schedulerTask;
	
	public BcRequest(UUID requester, UUID reciever) {
		//store data
		this.requester = requester;
		this.reciever = reciever;
	}
	
	protected void sendRequest(String verbIn, String nounin, String acceptCommand, String denyCommand) {
		//Get requester and reciver player
		Player prequester = Bukkit.getPlayer(requester);
		Player preciever = Bukkit.getPlayer(reciever);
		
		//Send request
		FormattedChatSender message = new FormattedChatSender(prequester.getName() + " wants to " + verbIn + " you ", ChatColor.AQUA);
		message.appendMessage("[ACCEPT] ", ChatColor.GREEN);
		message.setClickEvent_RunCommand(acceptCommand);
		message.setHoverEvent(new FormattedChatSender("Click to accept " + nounin + " with " + prequester, ChatColor.GOLD));
		message.appendMessage("[DENY] ", ChatColor.RED);
		message.setClickEvent_RunCommand(denyCommand);
		message.setHoverEvent(new FormattedChatSender("Click to deny " + nounin + " with " + prequester, ChatColor.GOLD));
		message.appendMessage(" or click the options", ChatColor.AQUA);
		
		//tell user
		FormattedChatSender warnMsg = new FormattedChatSender("Your request was sent, do ", ChatColor.GREEN);
		warnMsg.appendMessage("[" + denyCommand + "] ", ChatColor.DARK_GREEN);
		warnMsg.setClickEvent_SuggestCommand(denyCommand);
		warnMsg.setBold();
		warnMsg.appendMessage("to cancel", ChatColor.GREEN);
		
		//Get scheduler to autocancel
		schedulerTask = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Babycraft.instance, new Runnable() {
			@Override
			public void run() {
				requestTimeout();
			}
		}, ConfigAgent.instance.getRequestTimeout());
		
		message.sendMessage(preciever);
		warnMsg.sendMessage(prequester);
	}
	
	public boolean accept(UUID u) {
		if(reciever == u) {	
			cancelTask();
			Player prequester = Bukkit.getPlayer(requester);
			Player preciever = Bukkit.getPlayer(reciever);
			prequester.sendMessage(ChatColor.GREEN + "your request was accepted");
			preciever.sendMessage(ChatColor.GREEN + "you accepted your request");
			
			commit();
			removeRequest(this);
			return true;
		} else {
			return false;
		}
	}
	
	public void deny() {
		cancelTask();
		removeRequest(this);
		Player prequester = Bukkit.getPlayer(requester);
		Player preciever = Bukkit.getPlayer(reciever);
		prequester.sendMessage(ChatColor.RED + "your request was denied");
		preciever.sendMessage(ChatColor.GREEN + "you denied your own request");
	}
	
	public void requestTimeout() {
		removeRequest(this);
		Player prequester = Bukkit.getPlayer(requester);
		Player preciever = Bukkit.getPlayer(reciever);
		prequester.sendMessage(ChatColor.RED + "your request timed out");
		preciever.sendMessage(ChatColor.GREEN + "the request timed out");
	}
	
	public abstract void commit();
	
	public void cancelTask() {
		Bukkit.getScheduler().cancelTask(schedulerTask);
	}
}
