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
package me.cybermaxke.merchants.v14r1;

import org.bukkit.entity.Player;

import me.cybermaxke.merchants.api.MerchantTradeListener;
import net.minecraft.server.v1_4_R1.EntityHuman;
import net.minecraft.server.v1_4_R1.EntityPlayer;
import net.minecraft.server.v1_4_R1.InventoryMerchant;
import net.minecraft.server.v1_4_R1.ItemStack;
import net.minecraft.server.v1_4_R1.SlotMerchantResult;

public class SSlotMerchantResult extends SlotMerchantResult {
	private final SMerchant merchant;

	public SSlotMerchantResult(EntityPlayer player, SMerchant merchant, InventoryMerchant inventory, int index, int x, int y) {
		super(player, merchant, inventory, index, x, y);
		this.merchant = merchant;
	}

	@Override
	public void a(EntityHuman human, ItemStack itemStack) {
		// Reset the on trade
		this.merchant.onTrade = null;

		// Handle it like default
		super.a(human, itemStack);

		// Catch the on trade
		if (this.merchant.onTrade != null) {
			for (MerchantTradeListener handler : this.merchant.handlers) {
				handler.onTrade(this.merchant, this.merchant.onTrade, (Player) human.getBukkitEntity());
			}
		}
	}
}
