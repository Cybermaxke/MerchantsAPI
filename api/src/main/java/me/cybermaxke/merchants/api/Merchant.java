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
	 * @return The title
	 */
	String getTitle();

	/**
	 * Gets whether the string in the json format is.
	 * 
	 * @return Is title in json format
	 */
	boolean isTitleJson();

	/**
	 * Sets the title of the merchant.
	 * 
	 * @param title The title
	 * @param jsonTitle Whether the title is in json format
	 */
	void setTitle(String title, boolean jsonTitle);

	/**
	 * Sets the title of the merchant.
	 * 
	 * @param title The title
	 */
	void setTitle(String title);

	/**
	 * Adds the {@link MerchantTradeListener} to the merchant.
	 * 
	 * @param listener The listener
	 * @return {@code true} if not added before
	 */
	boolean addListener(MerchantTradeListener listener);

	/**
	 * Removes the {@link MerchantTradeListener} from the merchant.
	 * 
	 * @param listener The listener
	 * @return {@code true} if added before
	 */
	boolean removeListener(MerchantTradeListener listener);

	/**
	 * Gets the {@link MerchantTradeListener}s of the merchant.
	 * 
	 * @return The trade listeners
	 */
	Collection<MerchantTradeListener> getListeners();

	/**
	 * Adds a new {@link MerchantOffer} to the merchant.
	 * 
	 * @param offer The offer
	 */
	void addOffer(MerchantOffer offer);

	/**
	 * Adds the {@link MerchantOffer}s to the merchant.
	 * 
	 * @param offers The offers
	 */
	void addOffers(Iterable<MerchantOffer> offers);

	/**
	 * Sorts all the {@link MerchantOffer}s of the merchant.
	 * 
	 * @param comparator The comparator
	 */
	void sortOffers(Comparator<MerchantOffer> comparator);

	/**
	 * Removes the specified {@link MerchantOffer} from the merchant.
	 * 
	 * @param offer The offer
	 */
	void removeOffer(MerchantOffer offer);

	/**
	 * Removes all the specified {@link MerchantOffer}s from the merchant.
	 * 
	 * @param offers The offers
	 */
	void removeOffers(Iterable<MerchantOffer> offers);

	/**
	 * Gets all the {@link MerchantOffer}s of the merchant.
	 * 
	 * @return The offers
	 */
	List<MerchantOffer> getOffers();

	/**
	 * Gets the offer at the index.
	 * 
	 * @param index The index
	 * @return The offer
	 */
	MerchantOffer getOfferAt(int index);

	/**
	 * Gets the amount of offers in this merchant.
	 * 
	 * @return The count
	 */
	int getOffersCount();

	/**
	 * Sets the {@link MerchantOffer} at the index.
	 * 
	 * @param index The index
	 * @param offer The offer
	 */
	void setOfferAt(int index, MerchantOffer offer);

	/**
	 * Inserts the {@link MerchantOffer} at the index.
	 * 
	 * @param index The index
	 * @param offer The offer
	 */
	void insetOfferAt(int index, MerchantOffer offer);

	/**
	 * Adds a customer to the merchant.
	 * 
	 * @param player The player
	 * @return {@code true} if not a customer before
	 */
	boolean addCustomer(Player player);

	/**
	 * Removes a customer from the merchant.
	 * 
	 * @param player The player
	 * @return {@code true} if a customer before
	 */
	boolean removeCustomer(Player player);

	/**
	 * Gets whether the merchant the customer has.
	 * 
	 * @param player The player
	 * @return {@code true} if customer
	 */
	boolean hasCustomer(Player player);

	/**
	 * Gets the customers of the merchant.
	 * 
	 * @return The customers
	 */
	Collection<Player> getCustomers();

}
