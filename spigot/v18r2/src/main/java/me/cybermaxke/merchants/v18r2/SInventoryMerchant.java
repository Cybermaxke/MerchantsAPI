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
package me.cybermaxke.merchants.v18r2;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.InventoryMerchant;

public class SInventoryMerchant extends InventoryMerchant {
	public final SMerchant merchant;

	// The current index of the inventory.
	public int currentIndex;

	public SInventoryMerchant(EntityPlayer human, SMerchant merchant) {
		super(human, merchant);
		this.merchant = merchant;
	}

	@Override
	public boolean a(EntityHuman human) {
		return this.merchant.hasCustomer((Player) human.getBukkitEntity());
	}

	@Override
	public void d(int i) {
		super.d(i);

		// Catch the current index
		this.currentIndex = i;
	}

}
