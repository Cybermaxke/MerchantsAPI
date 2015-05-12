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

public interface MerchantAPI {

	/**
	 * Creates a new merchant with the title.
	 * 
	 * @param title the title
	 * @return the merchant
	 */
	Merchant newMerchant(String title);
	
	/**
	 * Creates a new merchant with the title.
	 * 
	 * @param title the title
	 * @param jsonTitle title in json format
	 * @return the merchant
	 */
	Merchant newMerchant(String title, boolean jsonTitle);

	/**
	 * Creates a new merchant offer with the trade items.
	 * 
	 * @param result the resulting item stack
	 * @param firstItem the first item stack
	 * @param secondItem the second item stack
	 * @return the merchant offer
	 */
	MerchantOffer newOffer(ItemStack result, ItemStack firstItem, @Nullable ItemStack secondItem);

	/**
	 * Creates a new merchant offer with the trade items.
	 * 
	 * @param result the resulting item stack
	 * @param item1 the first item stack
	 * @return the merchant offer
	 */
	MerchantOffer newOffer(ItemStack result, ItemStack item1);

}
