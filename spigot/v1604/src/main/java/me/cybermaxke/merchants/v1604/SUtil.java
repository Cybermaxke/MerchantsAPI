/**
 * This file is part of MerchantsAPI.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * MerchantsAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MerchantsAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MerchantsAPI. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.merchants.v1604;

import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

public class SUtil {
	private static final BiMap<String, ChatColor> lookupColors = HashBiMap.create();
	private static final BiMap<String, ChatColor> lookupStyles = HashBiMap.create();
	
	static {
		lookupColors.put("white", ChatColor.WHITE);
		lookupColors.put("black", ChatColor.BLACK);
		lookupColors.put("yellow", ChatColor.YELLOW);
		lookupColors.put("gold", ChatColor.GOLD);
		lookupColors.put("aqua", ChatColor.AQUA);
		lookupColors.put("dark_aqua", ChatColor.DARK_AQUA);
		lookupColors.put("blue", ChatColor.BLUE);
		lookupColors.put("dark_blue", ChatColor.DARK_BLUE);
		lookupColors.put("light_purple", ChatColor.LIGHT_PURPLE);
		lookupColors.put("dark_purple", ChatColor.DARK_PURPLE);
		lookupColors.put("red", ChatColor.RED);
		lookupColors.put("dark_red", ChatColor.DARK_RED);
		lookupColors.put("green", ChatColor.GREEN);
		lookupColors.put("dark_green", ChatColor.DARK_GREEN);
		lookupColors.put("gray", ChatColor.GRAY);
		lookupColors.put("dark_gray", ChatColor.DARK_GRAY);
		lookupStyles.put("bold", ChatColor.BOLD);
		lookupStyles.put("italic", ChatColor.ITALIC);
		lookupStyles.put("underlined", ChatColor.UNDERLINE);
		lookupStyles.put("strikethrough", ChatColor.STRIKETHROUGH);
		lookupStyles.put("obfuscated", ChatColor.MAGIC);
	}

	public static String fromJson(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object object = parser.parse(json);
		
		StringBuilder builder = new StringBuilder();
		
		Data data = new Data();
		data.builder = builder;
		data.lastColor = ChatColor.WHITE;
		data.lastStyles = Sets.newHashSet();
		
		apply(object, data);
		
		return builder.toString();
	}
	
	private static void apply(Object element, Data data) {
		if (!(element instanceof JSONObject) && !(element instanceof JSONArray)) {
			return;
		}
		
		ChatColor lastColor0 = data.lastColor;
		Set<ChatColor> lastStyles0 = Sets.newHashSet(data.lastStyles);
		
		if (element instanceof JSONObject) {
			JSONObject object = (JSONObject) element;
			if (object.containsKey("color")) {
				ChatColor color = lookupColors.get(object.get("color"));
			
				if (color != null) {
					if (data.lastColor != color) {
						data.builder.append(color);
					}
				}
			}
			for (Entry<String, ChatColor> style : lookupStyles.entrySet()) {
				String key = style.getKey();
				ChatColor value = style.getValue();
			
				if (object.containsKey(key)) {
					if (data.lastStyles.add(value)) {
						data.builder.append(value);
					}
				} else {
					data.lastStyles.remove(value);
				}
			}
			if (object.containsKey("extra")) {
				apply(object.get("extra"), data);
			}
		} else if (element instanceof JSONArray) {
			JSONArray array = (JSONArray) element;
			for (Object object0 : array) {
				apply(object0, data);
			}
		}
		
		data.lastColor = lastColor0;
		data.lastStyles = lastStyles0;
	}
	
	private static class Data {
		private StringBuilder builder;
		private ChatColor lastColor;
		private Set<ChatColor> lastStyles;
	}
}
