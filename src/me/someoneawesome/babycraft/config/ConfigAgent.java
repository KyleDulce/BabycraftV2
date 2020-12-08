package me.someoneawesome.babycraft.config;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager.Profession;

import me.someoneawesome.babycraft.Babycraft;
import me.someoneawesome.babycraft.entity.Gender;

public class ConfigAgent {
	public static ConfigAgent instance;
	private static final int MAIN_VERSION = 1, PLAYER_VERSION = 1, CHILDREN_VERSION = 1;

	private ConfigObject main, player, children;

	// constructor
	public ConfigAgent(Babycraft plugin) {
		main = new ConfigObject(plugin, "config.yml", "config.yml");
		player = new ConfigObject(plugin, "players.yml", "players.yml");
		children = new ConfigObject(plugin, "children.yml", "children.yml");
	}

	public void loadFiles() {
		main.setup();
		player.setup();
		children.setup();

		// Verify versions
		int mainver = getMainVersion();
		if (mainver < MAIN_VERSION) {
			main.reset("Invalid version number. Reseting for Integrity");
		} else if (mainver > MAIN_VERSION) {
			main.reset(
					"Future version config file used. Please update Babycraft to the latest version before using this config file");
		}

		int playerver = getPlayerVersion();
		if (playerver < PLAYER_VERSION) {
			player.reset("Invalid version number. Reseting for Integrity");
		} else if (playerver > PLAYER_VERSION) {
			player.reset(
					"Future version config file used. Please update Babycraft to the latest version before using this config file");
		}

		int childver = getChildVersion();
		if (childver < CHILDREN_VERSION) {
			children.reset("Invalid version number. Reseting for Integrity");
		} else if (childver > CHILDREN_VERSION) {
			children.reset(
					"Future version config file used. Please update Babycraft to the latest version before using this config file");
		}
	}

	public void saveFiles() {
		main.save();
		player.save();
		children.save();
	}
	
	public void saveMain() {
		main.save();
	}
	
	public void savePlayer() {
		player.save();
	}
	
	public void saveChild() {
		children.save();
	}

	public void reloadFiles() {
		main.reload();
		player.reload();
		children.reload();
	}

	// Getters
	// Main
	public int getMainVersion() {
		return main.getInt(ConfigPath.VERSION, -1);
	}
	
	public long getRequestTimeout() {
		return main.getLong(ConfigPath.MAIN_REQUEST_TIMEOUT, 60l) * 20;
	}

	public boolean getAllowMultiMarriage() {
		return main.getBoolean(ConfigPath.MAIN_MULTI_MARRIAGE, true);
	}

	public boolean getChooseBabyGender() {
		return main.getBoolean(ConfigPath.MAIN_CHOOSE_BABY, false);
	}

	public boolean getSameGenderMarriage() {
		return main.getBoolean(ConfigPath.MAIN_ALLOW_SAME_GENDER_MARRIAGE, true);
	}
	
	public boolean getBroadcastMarriageAnnoucement() {
		return main.getBoolean(ConfigPath.MAIN_BROADCAST_MARRIAGE, true);
	}

	// player
	public int getPlayerVersion() {
		return player.getInt(ConfigPath.VERSION, -1);
	}

	public Gender getPlayerGender(UUID uuid) {
		return Gender.fromString(player.getString(ConfigPath.PLAYER_GENDER(uuid), ""));
	}

	public List<UUID> getPlayerPartners(UUID uuid) {
		List<String> raw = player.getStringList(ConfigPath.PLAYER_PARTNER(uuid));
		List<UUID> result = new ArrayList<>();

		for (String p : raw) {
			result.add(UUID.fromString(p));
		}
		return result;
	}

	public boolean getPregnantStatus(UUID uuid) {
		return player.getBoolean(ConfigPath.PLAYER_PREGNANT_STATUS(uuid), false);
	}

	public double getPregnantTime(UUID uuid) {
		return player.getDouble(ConfigPath.PLAYER_PREGNANT_TIMELEFT(uuid), 0);
	}

	public UUID getPregnantPartner(UUID uuid) {
		String in = player.getString(ConfigPath.PLAYER_PREGNANT_PARTNER(uuid), null);
		return (in != null)? UUID.fromString(in) : null;
	}

	public List<UUID> getPlayerChildren(UUID uuid) {
		List<String> raw = player.getStringList(ConfigPath.PLAYER_CHILDREN(uuid));
		List<UUID> result = new ArrayList<>();

		for (String p : raw) {
			result.add(UUID.fromString(p));
		}
		return result;
	}

	// children
	public int getChildVersion() {
		return children.getInt(ConfigPath.VERSION, -1);
	}

	public String getChildName(UUID uuid) {
		return children.getString(ConfigPath.CHILD_NAME(uuid), null);
	}

	public List<UUID> getChildParents(UUID uuid) {
		List<String> raw = children.getStringList(ConfigPath.CHILD_PARENTS(uuid));
		List<UUID> result = new ArrayList<>();

		for (String p : raw) {
			result.add(UUID.fromString(p));
		}
		return result;
	}

	public Gender getChildGender(UUID uuid) {
		return Gender.fromString(children.getString(ConfigPath.CHILD_GENDER(uuid), ""));
	}

	public Location getChildHome(UUID uuid) {
		World w = Bukkit.getWorld(UUID.fromString(children.getString(ConfigPath.CHILD_HOME_WORLD(uuid), Bukkit.getWorlds().get(0).getUID().toString())));
		double x = children.getDouble(ConfigPath.CHILD_HOME_X(uuid), 0);
		double y = children.getDouble(ConfigPath.CHILD_HOME_Y(uuid), 0);
		double z = children.getDouble(ConfigPath.CHILD_HOME_Z(uuid), 0);

		return new Location(w, x, y, z);
	}

	public Profession getChildColor(UUID uuid) {
		return Profession.valueOf(children.getString(ConfigPath.CHILD_COLOR(uuid), Profession.LIBRARIAN.toString()));
	}

	// Setters
	// Main

	public void setAllowMultiMarriage(boolean b) {
		main.set(ConfigPath.MAIN_MULTI_MARRIAGE, b);
	}

	public void setChooseBabyGender(boolean b) {
		main.set(ConfigPath.MAIN_CHOOSE_BABY, b);
	}

	public void setSameGenderMarriage(boolean b) {
		main.set(ConfigPath.MAIN_ALLOW_SAME_GENDER_MARRIAGE, b);
	}
	
	public void setBroadcastMarriageAnnoucement(boolean b) {
		main.set(ConfigPath.MAIN_BROADCAST_MARRIAGE, b);
	}

	// player
	public void setPlayerGender(UUID uuid, Gender g) {
		player.set(ConfigPath.PLAYER_GENDER(uuid), g.toString());
	}

	public void setPlayerPartners(UUID uuid, List<UUID> l) {
		List<String> result = new ArrayList<>();

		for (UUID p : l) {
			result.add(p.toString());
		}
		player.set(ConfigPath.PLAYER_PARTNER(uuid), result);
	}
	
	public void addPlayerPartner(UUID player, UUID part) {
		List<UUID> list = getPlayerPartners(player);
		list.add(part);
		setPlayerPartners(player, list);
	}

	public boolean removePlayerPartner(UUID player, UUID part) {
		List<UUID> list = getPlayerPartners(player);
		boolean result = list.remove(part);
		setPlayerPartners(player, list);
		return result;
	}
	
	public void setPregnantStatus(UUID uuid, boolean b) {
		player.set(ConfigPath.PLAYER_PREGNANT_STATUS(uuid), b);
	}

	public void setPregnantTime(UUID uuid, double d) {
		player.set(ConfigPath.PLAYER_PREGNANT_TIMELEFT(uuid), d);
	}

	public void setPregnantPartner(UUID uuid, UUID u) {
		player.set(ConfigPath.PLAYER_PREGNANT_PARTNER(uuid), u);
	}

	public void setPlayerChildren(UUID uuid, List<UUID> l) {
		List<String> result = new ArrayList<>();

		for (UUID p : l) {
			result.add(p.toString());
		}
		player.set(ConfigPath.PLAYER_CHILDREN(uuid), result);
	}
	
	public void addPlayerChildren(UUID player, UUID child) {
		List<UUID> list = getPlayerChildren(player);
		list.add(child);
		setPlayerChildren(player, list);
	}
	
	public boolean removePlayerChildren(UUID player, UUID child) {
		List<UUID> list = getPlayerChildren(player);
		boolean result = list.remove(child);
		setPlayerChildren(player, list);
		return result;
	}

	// children
	public void setChildName(UUID uuid, String s) {
		children.set(ConfigPath.CHILD_NAME(uuid), s);
	}

	public void setChildParents(UUID uuid, List<UUID> l) {
		List<String> result = new ArrayList<>();

		for (UUID p : l) {
			result.add(p.toString());
		}
		children.set(ConfigPath.CHILD_PARENTS(uuid), result);
	}

	public void setChildGender(UUID uuid, Gender g) {
		children.set(ConfigPath.CHILD_GENDER(uuid), g.toString());
	}

	public void setChildHome(UUID uuid, Location l) {
		children.set(ConfigPath.CHILD_HOME_WORLD(uuid), l.getWorld().getUID());
		children.set(ConfigPath.CHILD_HOME_X(uuid), l.getX());
		children.set(ConfigPath.CHILD_HOME_Y(uuid), l.getY());
		children.set(ConfigPath.CHILD_HOME_Z(uuid), l.getZ());
	}

	public void setChildColor(UUID uuid, Profession p) {
		children.set(ConfigPath.CHILD_COLOR(uuid), p.toString());
	}
}
