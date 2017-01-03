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
package me.cybermaxke.merchants.v17r4;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryMerchant;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantInventory;
import me.cybermaxke.merchants.api.MerchantOffer;
import me.cybermaxke.merchants.api.MerchantSession;

public class SCraftInventoryMerchant extends CraftInventoryMerchant implements MerchantInventory {

    SCraftInventoryMerchant(SInventoryMerchant merchant) {
        super(merchant);
    }

    @Override
    public Merchant getMerchant() {
        return ((SInventoryMerchant) this.inventory).merchant;
    }

    @Override
    public int getSelectedOfferIndex() {
        return ((SInventoryMerchant) this.inventory).currentIndex;
    }

    @Override
    public MerchantOffer getSelectedOffer() {
        return this.getMerchant().getOfferAt(this.getSelectedOfferIndex());
    }

    @Override
    public MerchantSession getSession() {
        return ((SInventoryMerchant) this.inventory).getOwner();
    }
}
