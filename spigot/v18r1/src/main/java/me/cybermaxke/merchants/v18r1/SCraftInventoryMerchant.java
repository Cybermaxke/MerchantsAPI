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
package me.cybermaxke.merchants.v18r1;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftInventoryMerchant;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantInventory;
import me.cybermaxke.merchants.api.MerchantOffer;

public class SCraftInventoryMerchant extends CraftInventoryMerchant implements MerchantInventory {

	public SCraftInventoryMerchant(SInventoryMerchant merchant) {
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

}
