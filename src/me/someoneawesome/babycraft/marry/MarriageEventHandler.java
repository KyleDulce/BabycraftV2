package me.someoneawesome.babycraft.marry;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.custom.FormattedChatSender;

public class MarriageEventHandler implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
		//Event for kissing spouse
		//Checks if entity is valid and is player
		Entity entity = event.getRightClicked();
		if(entity != null && entity instanceof Player) {
			Player self = event.getPlayer();
			Player other = (Player) entity;
			
			//Checks if players are married
			ConfigAgent config = ConfigAgent.instance;
			if(config.getPlayerPartners(self.getUniqueId()).contains(other.getUniqueId())) {
				//Do kiss action
				//Get location of both players
				Location selfLoc = self.getLocation();
				Location otherLoc = self.getLocation();
				
				//Spawn particles
				//for self
				selfLoc.getWorld().spawnParticle(Particle.HEART, selfLoc, 10, 1.5, 1.5, 1.5);
				otherLoc.getWorld().spawnParticle(Particle.HEART, otherLoc, 10, 1.5, 1.5, 1.5);
				
				FormattedChatSender msg = new FormattedChatSender("You kissed your spouse", ChatColor.GREEN);
				msg.sendActionBar(self);
				msg.sendActionBar(other);
			}
		}
	}
	
}
