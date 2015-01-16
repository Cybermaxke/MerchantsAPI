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
package me.cybermaxke.merchants.v1502;

import static com.google.common.base.Preconditions.checkNotNull;
import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantAPI;
import me.cybermaxke.merchants.api.MerchantOffer;
import me.cybermaxke.merchants.api.Merchants;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SMerchantPlugin extends JavaPlugin implements MerchantAPI {

	@Override
	public void onEnable() {
		Merchants.set(this);
	}

	@Override
	public Merchant newMerchant(String title) {
		checkNotNull(title, "The title cannot be null!");
		return new SMerchant(title);
	}

	@Override
	public MerchantOffer newOffer(ItemStack result, ItemStack item1, ItemStack item2) {
		checkNotNull(result, "The result cannot be null!");
		checkNotNull(item1, "The first item cannot be null!");

		return new SMerchantOffer(result, item1, item2);
	}

	@Override
	public MerchantOffer newOffer(ItemStack result, ItemStack item1) {
		checkNotNull(result, "The result cannot be null!");
		checkNotNull(item1, "The first item cannot be null!");

		return new SMerchantOffer(result, item1, null);
	}

}
