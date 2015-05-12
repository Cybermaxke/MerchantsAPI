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
package me.cybermaxke.merchants.v16r3;

import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.EntityPlayer;
import net.minecraft.server.v1_6_R3.InventoryMerchant;

import org.bukkit.entity.Player;

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
	public void c(int i) {
		super.c(i);

		// Catch the current index
		this.currentIndex = i;
	}

}
