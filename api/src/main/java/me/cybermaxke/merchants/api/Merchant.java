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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;

public interface Merchant {

	/**
	 * Gets the title of the merchant.
	 * 
	 * @return the title
	 */
	String getTitle();

	/**
	 * Adds a new offer to the merchant.
	 * 
	 * @param offer the offer
	 */
	void addOffer(MerchantOffer offer);

	/**
	 * Adds the offers to the merchant.
	 * 
	 * @param offers the offers
	 */
	void addOffers(Iterable<MerchantOffer> offers);

	/**
	 * Sorts all the offers of the merchant.
	 * 
	 * @param comparator the comparator
	 */
	void sortOffers(Comparator<MerchantOffer> comparator);

	/**
	 * Removes the offer from the merchant.
	 * 
	 * @param offer the offer
	 */
	void removeOffer(MerchantOffer offer);

	/**
	 * Removes the offers from the merchant.
	 * 
	 * @param offers the offers
	 */
	void removeOffers(Iterable<MerchantOffer> offers);

	/**
	 * Gets the offers of the merchant.
	 * 
	 * @return the offers
	 */
	List<MerchantOffer> getOffers();

	/**
	 * Adds a customer to the merchant.
	 * 
	 * @param player the player
	 * @return true if not a customer before
	 */
	boolean addCustomer(Player player);

	/**
	 * Removes a customer from the merchant.
	 * 
	 * @param player the player
	 * @return true if a customer before
	 */
	boolean removeCustomer(Player player);

	/**
	 * Gets whether the merchant the customer has.
	 * 
	 * @param player the player
	 * @return true if customer
	 */
	boolean hasCustomer(Player player);

	/**
	 * Gets the customers of the merchant.
	 * 
	 * @return the customers
	 */
	Collection<Player> getCustomers();

}
