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
package me.cybermaxke.merchants.v18r3;

import static com.google.common.base.Preconditions.checkNotNull;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantAPI;
import me.cybermaxke.merchants.api.MerchantOffer;

import org.bukkit.inventory.ItemStack;

public class SMerchantAPI implements MerchantAPI {

	@Override
	public Merchant newMerchant(String title) {
		checkNotNull(title, "title");
		return new SMerchant(title, false);
	}

	@Override
	public Merchant newMerchant(String title, boolean jsonTitle) {
		checkNotNull(title, "title");
		return new SMerchant(title, jsonTitle);
	}

	@Override
	public MerchantOffer newOffer(ItemStack result, ItemStack item1, ItemStack item2) {
		checkNotNull(result, "result");
		checkNotNull(item1, "first item");

		return new SMerchantOffer(result, item1, item2);
	}

	@Override
	public MerchantOffer newOffer(ItemStack result, ItemStack item1) {
		checkNotNull(result, "result");
		checkNotNull(item1, "first item");

		return new SMerchantOffer(result, item1, null);
	}

}
