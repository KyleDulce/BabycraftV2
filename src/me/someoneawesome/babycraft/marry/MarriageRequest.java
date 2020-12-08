package me.someoneawesome.babycraft.marry;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.Babycraft;
import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.custom.FormattedChatSender;

public class MarriageRequest {
	
	private static HashMap<UUID, MarriageRequest> requests = new HashMap<>();
	
	public static void addToRequest(UUID p1, UUID p2, MarriageRequest req) {
		requests.put(p1, req);
		requests.put(p2, req);
	}
	
	public static void removeRequest(MarriageRequest req) {
		requests.remove(req.requester);
		requests.remove(req.reciever);
	}
	
	public static void removeRequest(UUID p) {
		removeRequest(requests.get(p));
	}
	
	public static MarriageRequest getRequest(UUID p) {
		return requests.get(p);
	}
	
	public static boolean requestContains(UUID p) {
		return requests.containsKey(p);
	}
	
	private UUID requester;
	private UUID reciever;
	private int schedulerTask;
	
	public MarriageRequest(UUID requester, UUID reciever) {
		//store data
		this.requester = requester;
		this.reciever = reciever;
		
		//Get requester and reciver player
		Player prequester = Bukkit.getPlayer(requester);
		Player preciever = Bukkit.getPlayer(reciever);
		
		//Send request
		FormattedChatSender message = new FormattedChatSender(prequester.getName() + " wants to marry you ", ChatColor.AQUA);
		message.appendMessage("[ACCEPT] ", ChatColor.GREEN);
		message.setClickEvent_RunCommand("/babycraft marry accept");
		message.setHoverEvent(new FormattedChatSender("Click to accept marriage with " + prequester, ChatColor.GOLD));
		message.appendMessage("[DENY] ", ChatColor.RED);
		message.setClickEvent_RunCommand("/babycraft marry deny");
		message.setHoverEvent(new FormattedChatSender("Click to deny marriage with " + prequester, ChatColor.GOLD));
		message.appendMessage("Click options", ChatColor.AQUA);
		
		//tell user
		FormattedChatSender warnMsg = new FormattedChatSender("Your request was sent, do ", ChatColor.GREEN);
		warnMsg.appendMessage("[/babycraft marry deny] ", ChatColor.DARK_GREEN);
		warnMsg.setClickEvent_SuggestCommand("/babycraft marry deny");
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
			prequester.sendMessage(ChatColor.GREEN + "your marriage request was accepted");
			preciever.sendMessage(ChatColor.GREEN + "you accepted your marriage request");
			
			commitMarriage();
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
		prequester.sendMessage(ChatColor.RED + "your marriage request was denied");
		preciever.sendMessage(ChatColor.GREEN + "you denied your marriage request");
	}
	
	public void requestTimeout() {
		removeRequest(this);
		Player prequester = Bukkit.getPlayer(requester);
		Player preciever = Bukkit.getPlayer(reciever);
		prequester.sendMessage(ChatColor.RED + "your marriage request timed out");
		preciever.sendMessage(ChatColor.GREEN + "the marriage request timed out");
	}
	
	public void commitMarriage() {
		//cache config agent
		ConfigAgent agent = ConfigAgent.instance;
		
		//Add to file
		agent.addPlayerPartner(requester, reciever);
		agent.addPlayerPartner(reciever, requester);
		agent.savePlayer();
		
		//broadcast
		if(agent.getBroadcastMarriageAnnoucement()) {
			Player prequester = Bukkit.getPlayer(requester);
			Player preciever = Bukkit.getPlayer(reciever);
			
			Bukkit.broadcastMessage(prequester.getName() + " and " + preciever.getName() + " just got married!");
		}
	}
	
	public void cancelTask() {
		Bukkit.getScheduler().cancelTask(schedulerTask);
	}
	
}
