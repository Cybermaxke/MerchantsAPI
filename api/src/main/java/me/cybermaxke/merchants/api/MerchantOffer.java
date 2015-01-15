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

import org.bukkit.inventory.ItemStack;

import com.google.common.base.Optional;

public interface MerchantOffer {

	/**
	 * Gets the first {@link ItemStack} that will be sold to the merchant.
	 * 
	 * @return the first item stack
	 */
	ItemStack getFirstItem();

	/**
	 * Gets the second {@link ItemStack} that will be sold to the
	 * merchant. This item can be {@link Optional#absent()}.
	 * 
	 * @return the second item stack
	 */
	Optional<ItemStack> getSecondItem();

	/**
	 * Gets the result {@link ItemStack} that the merchant will give.
	 * 
	 * @return the result item stack
	 */
	ItemStack getResultItem();

	/**
	 * Gets the maximum amount of uses of the merchant offer. Where -1
	 * means infinite uses.
	 * 
	 * @return the maximum uses
	 */
	int getMaxUses();

	/**
	 * Sets the maximum amount of uses of the merchant offer. Where -1
	 * means infinite uses.
	 * 
	 * @param uses the maximum uses
	 */
	void setMaxUses(int uses);

	/**
	 * Adds the extra amount to the maximum uses of the merchant offer.
	 * 
	 * @param extra the extra uses
	 */
	void addMaxUses(int extra);

	/**
	 * Gets the current amount of uses of the merchant offer.
	 * 
	 * @return the amount uses
	 */
	int getUses();

	/**
	 * Adds the uses amount to the current uses of the merchant offer.
	 * 
	 * @param uses the uses
	 */
	void addUses(int uses);

	/**
	 * Gets whether the offer is locked. This will happen if the amount of
	 * uses exceeds the maximum amount of uses.
	 * 
	 * <p>
	 * This feature will only work for versions 1.4+, since
	 * that was the first version that supported locked offers.
	 * If the version is older, it will always return false.
	 * </p>
	 * 
	 * @return is locked
	 */
	boolean isLocked();

	/**
	 * Clones the merchant offer.
	 * 
	 * @return the clone
	 */
	MerchantOffer clone();

}
