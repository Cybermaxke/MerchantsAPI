/*
 * This file is part of MerchantsAPI.
 *
 * Copyright (c) Cybermaxke
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
     * @return The first item stack
     */
    ItemStack getFirstItem();

    /**
     * Gets the second {@link ItemStack} that will be sold to the
     * merchant. This item can be {@link Optional#absent()}.
     *
     * @return The second item stack
     */
    Optional<ItemStack> getSecondItem();

    /**
     * Gets the result {@link ItemStack} that the merchant will give.
     *
     * @return The result item stack
     */
    ItemStack getResultItem();

    /**
     * Gets the maximum amount of uses of the merchant offer. Where -1
     * means infinite uses.
     *
     * @return The maximum uses
     */
    int getMaxUses();

    /**
     * Sets the maximum amount of uses of the merchant offer. Where -1
     * means infinite uses.
     *
     * @param uses The maximum uses
     */
    void setMaxUses(int uses);

    /**
     * Adds the extra amount to the maximum uses of the merchant offer.
     *
     * @param extra The extra uses
     */
    void addMaxUses(int extra);

    /**
     * Gets the current amount of uses of the merchant offer.
     *
     * @return The amount uses
     */
    int getUses();

    /**
     * Sets the amount of uses of the merchant offer.
     *
     * @param uses The uses
     */
    void setUses(int uses);

    /**
     * Adds the uses amount to the current uses of the merchant offer.
     *
     * @param uses The uses
     */
    void addUses(int uses);

    /**
     * Gets whether the offer is locked. This will happen if the amount of
     * uses exceeds the maximum amount of uses.
     *
     * <p>
     * This feature will be handled different in versions before 1.4,
     * because this the version was that the feature implemented was.
     * There is a workaround for this problem and when the offer locked
     * is it will be hidden from the offer list.
     * </p>
     *
     * @return Is locked
     */
    boolean isLocked();

    /**
     * Clones the merchant offer.
     *
     * @return The clone
     */
    MerchantOffer clone();

}
