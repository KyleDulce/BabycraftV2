package me.someoneawesome.babycraft.entity;

public enum Gender {
	MALE("male", "&bmale"), FEMALE("female", "&dfemale"), OTHER("other", "&f"), NULL("", "");
	
	private String str;
	private String CodedChatcolor;
	
	private Gender(String s, String s2) {
		str = s;
		CodedChatcolor = s2;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public String toStringColoredCode() {
		return CodedChatcolor;
	}
	
	public static Gender fromString(String s) {
		if(s.equalsIgnoreCase(MALE.toString())) {
			return MALE;
		} else if(s.equalsIgnoreCase(FEMALE.toString())) {
			return FEMALE;
		} else if(s.equalsIgnoreCase(OTHER.toString())) {
			return OTHER;
		}
		return NULL;
	}
}
