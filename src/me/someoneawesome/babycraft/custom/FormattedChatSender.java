package me.someoneawesome.babycraft.custom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Content;

public class FormattedChatSender {
	
	List<TextComponent> txt;
	
	public FormattedChatSender(String message) {
		txt = new ArrayList<>();
		txt.add(new TextComponent(message));
	}
	
	public FormattedChatSender(String message, ChatColor color) {
		txt = new ArrayList<>();
		txt.add(new TextComponent(message));
		txt.get(0).setColor(color.asBungee());
	}
	
	public void appendMessage(String message, ChatColor color) {
		txt.add(new TextComponent(message));
		txt.get(txt.size() - 1).setColor(color.asBungee());
	}
	
	public void setColor(ChatColor color) {
		txt.get(txt.size() - 1).setColor(color.asBungee());
	}
	
	public void setClickEvent_RunCommand(String cmd) {
		txt.get(txt.size() - 1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
	}
	
	public void setClickEvent_SuggestCommand(String cmd) {
		txt.get(txt.size() - 1).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));
	}
	
	public void setHoverEvent(FormattedChatSender s) {
		Content[] r = s.txt.toArray(new Content[s.txt.size()]);
		txt.get(txt.size() - 1).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, r));
	}
	
	public void setBold() {
		txt.get(txt.size() - 1).setBold(true);
	}
	
	public void setItalic() {
		txt.get(txt.size() - 1).setItalic(true);
	}
	
	public void setUnderlined() {
		txt.get(txt.size() - 1).setUnderlined(true);
	}
	
	public void sendMessage(CommandSender reciver) {
		TextComponent[] r = txt.toArray(new TextComponent[txt.size()]);
		reciver.spigot().sendMessage(r);
	}
	
	public void sendActionBar(Player p) {
		TextComponent[] r = txt.toArray(new TextComponent[txt.size()]);
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, r);
	}
	
}
