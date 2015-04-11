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
package me.cybermaxke.merchants.api;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

public class Merchants {
	private static MerchantAPI instance;
	
	private static final String ERROR_INSTANCE_MISSING = "The merchants api is not loaded!";
	private static final String ERROR_SET_INSTANCE = "The merchants api can only be set once!";

	/**
	 * Creates a new merchant with the title.
	 * 
	 * @param title the title
	 * @return the merchant
	 */
	public static Merchant newMerchant(String title) {
		if (instance == null) {
			throw new IllegalStateException(ERROR_INSTANCE_MISSING);
		}
		
		return instance.newMerchant(title);
	}
	
	/**
	 * Creates a new merchant with the title.
	 * 
	 * @param title the title
	 * @param jsonTitle title in json format
	 * @return the merchant
	 */
	public static Merchant newMerchant(String title, boolean jsonTitle) {
		if (instance == null) {
			throw new IllegalStateException(ERROR_INSTANCE_MISSING);
		}
		
		return instance.newMerchant(title, jsonTitle);
	}

	/**
	 * Creates a new merchant offer with the trade items.
	 * 
	 * @param result the resulting item stack
	 * @param item1 the first item stack
	 * @param item2 the second item stack
	 * @return the merchant offer
	 */
	public static MerchantOffer newOffer(ItemStack result, ItemStack item1, @Nullable ItemStack item2) {
		if (instance == null) {
			throw new IllegalStateException(ERROR_INSTANCE_MISSING);
		}
		
		return instance.newOffer(result, item1, item2);
	}

	/**
	 * Creates a new merchant offer with the trade items.
	 * 
	 * @param result the resulting item stack
	 * @param item1 the first item stack
	 * @return the merchant offer
	 */
	public static MerchantOffer newOffer(ItemStack result, ItemStack item1) {
		if (instance == null) {
			throw new IllegalStateException(ERROR_INSTANCE_MISSING);
		}
		
		return instance.newOffer(result, item1);
	}
	
	/**
	 * Gets the instance of the merchants api.
	 * 
	 * @return the instance
	 */
	public static MerchantAPI get() {
		return instance;
	}

	/**
	 * Sets the instance of the merchants api.
	 * 
	 * @param api the instance
	 */
	public static void set(MerchantAPI api) {
		if (instance != null) {
			throw new IllegalStateException(ERROR_SET_INSTANCE);
		}

		instance = api;
	}
}
