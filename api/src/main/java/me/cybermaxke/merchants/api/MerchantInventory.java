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

public interface MerchantInventory extends org.bukkit.inventory.MerchantInventory {
	
	/**
	 * Gets the {@link MerchantSession} of this inventory.
	 * 
	 * @return The merchant session
	 */
	MerchantSession getSession();

	/**
	 * Gets the {@link Merchant} that is assigned to this inventory.
	 * 
	 * @return The merchant
	 */
	Merchant getMerchant();

	/**
	 * Gets the index of the offer that is selected.
	 * 
	 * @return The index
	 */
	int getSelectedOfferIndex();

	/**
	 * Gets the {@link MerchantOffer} that is selected.
	 * 
	 * @return The offer
	 */
	MerchantOffer getSelectedOffer();

}
