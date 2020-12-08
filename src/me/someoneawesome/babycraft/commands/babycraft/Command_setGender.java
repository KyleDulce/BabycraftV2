package me.someoneawesome.babycraft.commands.babycraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.someoneawesome.babycraft.Debug;
import me.someoneawesome.babycraft.commands.BcCommand;
import me.someoneawesome.babycraft.config.ConfigAgent;
import me.someoneawesome.babycraft.entity.Gender;

public class Command_setGender implements BcCommand {

	@Override
	public void onCommand(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof Player) {
			
			Player p = (Player) arg0;
			
			if(arg1.length == 1) {
				
				Gender g = Gender.fromString(arg1[0]);
				
				if(g == Gender.NULL) {
					p.sendMessage(ChatColor.RED + "You did not add a listed gender. Please state either 'Male' 'Female' or 'Other'");
				} else {
					ConfigAgent.instance.setPlayerGender(p.getUniqueId(), g);
					ConfigAgent.instance.savePlayer();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGender set to " + g.toStringColoredCode()));
					Debug.log(p.getName() + " had set their gender to " + g.toString());
				}
			} else {
				cmdError(p);
			}
		} else {
			arg0.sendMessage(ChatColor.RED + "Only Players can run this command!");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		if (arg1.length == 1) {
			List<String> list = new ArrayList<>();

			for (Gender s : Gender.values()) {
				if (s.toString().toLowerCase().startsWith(arg1[0].toLowerCase())) {
					list.add(s.toString());
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public boolean isCommand(String cmd) {
		return getName().equalsIgnoreCase(cmd);
	}

	@Override
	public String[] getUsage() {
		return new String[] { "/babycraft &esetGender <male/female/other>" };
	}

	@Override
	public String getName() {
		return "setgender";
	}

	private void cmdError(Player p) {
		p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Usage: " + ChatColor.GREEN + "" + ChatColor.BOLD
				+ "/babycraft &esetGender <male/female/other>");
	}

}
