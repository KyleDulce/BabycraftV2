package me.someoneawesome.babycraft.baby.creation;

import java.util.UUID;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ClickCreation extends BabyCreationAction {

	private boolean p1s = false;
	private boolean p2s = false;
	
	public ClickCreation(UUID p1, UUID p2) {
		super(p1, p2);
		//tell users
		Player pp1 = Bukkit.getPlayer(p1);
		Player pp2 = Bukkit.getPlayer(p2);
		
		pp1.sendMessage(ChatColor.AQUA + "You can make a child with your partner now, right click your partner");
		pp2.sendMessage(ChatColor.AQUA + "You can make a child with your partner now, right click your partner");
	}
	
	public static class CreationListener implements Listener {
		@EventHandler
		public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
			//Get entity
			Entity entity = event.getRightClicked();
			if(entity != null && entity instanceof Player) {
				UUID self = event.getPlayer().getUniqueId();
				UUID other = ((Player) entity).getUniqueId();
				
				//checks if both in same creator
				if(containsPlayer(self) && containsPlayer(other) && hasSameAction(self, other)) {
					BabyCreationAction a = getAction(self);
					if (a instanceof ClickCreation) {
						ClickCreation c = (ClickCreation) a;
						//set true
						if(self == c.p1) {
							c.p1s = true;
							
						} else {
							c.p2s = true;
						}
						event.getPlayer().sendMessage("Please wait for your partner to click too");
						//check complete
						if(c.p1s && c.p2s) {
							//Are complete, call completed
							Location l = event.getPlayer().getLocation();
							l.getWorld().spawnParticle(Particle.HEART, l, 30, 2, 2, 2);
							
							c.completeCreation();
						}
					}
				}
			}
		}
	}

}
