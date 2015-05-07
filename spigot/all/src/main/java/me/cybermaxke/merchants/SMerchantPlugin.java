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
package me.cybermaxke.merchants;

import java.util.logging.Level;

import me.cybermaxke.merchants.api.MerchantAPI;
import me.cybermaxke.merchants.api.Merchants;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SMerchantPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		String version = this.getNmsVersion().replace("_", "").toLowerCase();
		if (version.isEmpty()) {
			// This package version wasn't changed yet in 1.3.2
			if (Bukkit.getVersion().contains("MC: 1.3.2")) {
				version = "v13r1";
			}
			version = "none";
		}
		String clazzName = this.getClass().getPackage().getName() + "." + version + ".SMerchantPlugin";

		try {
			Class<?> clazz = Class.forName(clazzName);

			if (MerchantAPI.class.isAssignableFrom(clazz)) {
				try {
					MerchantAPI api = (MerchantAPI) clazz.newInstance();
					Merchants.set(api);
					return;
				} catch (Exception e) {
				}
			}

			this.getLogger().log(Level.WARNING, "Plugin could not be loaded, version {" + version + "} it's implementation is invalid!");
			this.setEnabled(false);
		} catch (ClassNotFoundException e) {
			this.getLogger().log(Level.WARNING, "Plugin could not be loaded, version {" + version + "} is not supported!");
			this.setEnabled(false);
		}
	}

	private String getNmsVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replaceFirst(".", "");
	}

}
