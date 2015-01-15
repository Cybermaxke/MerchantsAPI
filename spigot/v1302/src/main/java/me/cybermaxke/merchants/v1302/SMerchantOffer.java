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
package me.cybermaxke.merchants.v1302;

import org.bukkit.craftbukkit.inventory.CraftItemStack;

import net.minecraft.server.ItemStack;
import net.minecraft.server.MerchantRecipe;
import me.cybermaxke.merchants.api.MerchantOffer;

import com.google.common.base.Optional;

public class SMerchantOffer extends MerchantRecipe implements MerchantOffer {
	private final org.bukkit.inventory.ItemStack item1;
	private final org.bukkit.inventory.ItemStack item2;
	private final org.bukkit.inventory.ItemStack result;

	private int maxUses = -1;
	private int uses;

	public SMerchantOffer(org.bukkit.inventory.ItemStack result, org.bukkit.inventory.ItemStack item1, org.bukkit.inventory.ItemStack item2) {
		super(null, null, null);

		this.result = result;
		this.item1 = item1;
		this.item2 = item2;
	}

	@Override
	public org.bukkit.inventory.ItemStack getFirstItem() {
		return this.item1;
	}

	@Override
	public Optional<org.bukkit.inventory.ItemStack> getSecondItem() {
		if (this.item2 == null) {
			return Optional.absent();
		}

		return Optional.of(this.item2.clone());
	}

	@Override
	public org.bukkit.inventory.ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	@Override
	public void setMaxUses(int uses) {
		this.maxUses = uses;
	}

	@Override
	public void addMaxUses(int extra) {
		if (this.maxUses >= 0) {
			this.setMaxUses(this.maxUses + extra);
		}
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public void addUses(int uses) {
		this.uses += uses;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public ItemStack getBuyItem1() {
		return CraftItemStack.createNMSItemStack(this.item1);
	}

	@Override
	public ItemStack getBuyItem2() {
		if (this.item2 == null) {
			return null;
		}

		return CraftItemStack.createNMSItemStack(this.item2);
	}

	@Override
	public boolean hasSecondItem() {
		return this.item2 != null;
	}

	@Override
	public ItemStack getBuyItem3() {
		return CraftItemStack.createNMSItemStack(this.result);
	}

	@Override
	public void f() {
		this.addUses(1);
	}

	@Override
	public SMerchantOffer clone() {
		org.bukkit.inventory.ItemStack result = this.result.clone();
		org.bukkit.inventory.ItemStack item1 = this.item1.clone();
		org.bukkit.inventory.ItemStack item2 = this.item2 != null ? this.item2.clone() : null;

		SMerchantOffer clone = new SMerchantOffer(result, item1, item2);
		clone.maxUses = this.maxUses;
		clone.uses = this.uses;

		return clone;
	}
}
