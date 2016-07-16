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
	
	private static final String ERROR_INSTANCE_MISSING = "merchants api is not loaded";
	private static final String ERROR_SET_INSTANCE = "merchants api instance can only be set once";
	
	private static MerchantAPI getOrThrowError() {
		if (instance == null) {
			throw new IllegalStateException(ERROR_INSTANCE_MISSING);
		}
		return instance;
	}

	/**
	 * Creates a new {@link Merchant} with the specified title.
	 * 
	 * @param title The title
	 * @return The merchant
	 */
	public static Merchant newMerchant(String title) {
		return getOrThrowError().newMerchant(title);
	}
	
	/**
	 * Creates a new {@link Merchant} with the specified title.
	 * 
	 * @param title The title
	 * @param jsonTitle Whether the title is in json format
	 * @return The merchant
	 */
	public static Merchant newMerchant(String title, boolean jsonTitle) {
		return getOrThrowError().newMerchant(title, jsonTitle);
	}

	/**
	 * Creates a new {@link MerchantOffer} with the trade items.
	 * 
	 * @param result The resulting item stack
	 * @param firstItem The first item stack
	 * @param secondItem The second item stack
	 * @return The merchant offer
	 */
	public static MerchantOffer newOffer(ItemStack result, ItemStack firstItem,
			@Nullable ItemStack secondItem) {
		return getOrThrowError().newOffer(result, firstItem, secondItem);
	}

	/**
	 * Creates a new {@link MerchantOffer} with the trade items.
	 * 
	 * @param result The resulting item stack
	 * @param firstItem The first item stack
	 * @return The merchant offer
	 */
	public static MerchantOffer newOffer(ItemStack result, ItemStack firstItem) {
		return getOrThrowError().newOffer(result, firstItem);
	}
	
	/**
	 * Gets the instance of the merchants api.
	 * 
	 * @return The instance
	 */
	public static MerchantAPI get() {
		return instance;
	}

	/**
	 * Sets the instance of the merchants api.
	 * 
	 * @param api The instance
	 */
	public static void set(MerchantAPI api) {
		if (instance != null) {
			throw new IllegalStateException(ERROR_SET_INSTANCE);
		}
		instance = api;
	}
}
