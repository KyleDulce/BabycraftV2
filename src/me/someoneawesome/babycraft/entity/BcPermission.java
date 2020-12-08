package me.someoneawesome.babycraft.entity;

public enum BcPermission {
	ADMIN_RELOAD("Admin.reload"),
	ADMIN_DESPAWN("Admin.DespawnAll"),
	ADMIN_SAVE("Admin.saveConfig"), 
	ADMIN_SETTING("Admin.settings"), 
	
	SOLO("solo"),
	SAME_GENDER_CHILD("sameGender"),
	CHANGE_GENDER_CHILD("changeGender"),
	SAME_GENDER_MARRIAGE("sameGenderMarriage"),
	MULTI_MARRIAGE("multimarriage"),
	;
	String perm;
	private BcPermission(String perm) {
		this.perm = perm;
	}
	
	@Override
	public String toString() {
		return "babyCraft." + perm;
	}
}
