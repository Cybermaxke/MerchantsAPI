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
package me.cybermaxke.merchants.v1604;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;

import net.minecraft.server.v1_6_R3.ItemStack;
import net.minecraft.server.v1_6_R3.MerchantRecipe;
import me.cybermaxke.merchants.api.MerchantOffer;

import com.google.common.base.Optional;

public class SMerchantOffer extends MerchantRecipe implements MerchantOffer {
	// The merchants this offer is added to
	private final Set<SMerchant> merchants = Collections.newSetFromMap(new WeakHashMap<SMerchant, Boolean>());

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

	// Links the offer to the merchant.
	protected void add(SMerchant merchant) {
		this.merchants.add(merchant);
	}

	// Unlinks the offer from the merchant.
	protected void remove(SMerchant merchant) {
		this.merchants.remove(merchant);
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
		if (this.maxUses == uses) {
			return;
		}

		// Get the state before
		boolean locked0 = this.isLocked();
		// Set the max uses
		this.maxUses = uses;
		// Get the state after
		boolean locked1 = this.isLocked();

		// Send the new offer list
		if (locked0 != locked1) {
			for (SMerchant merchant : this.merchants) {
				merchant.sendUpdate();
			}
		}
	}

	@Override
	public void addMaxUses(int extra) {
		if (this.maxUses >= 0 && extra != 0) {
			this.setMaxUses(this.maxUses + extra);
		}
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public void setUses(int uses) {
		if (this.uses == uses) {
			return;
		}

		// Get the state before
		boolean locked0 = this.isLocked();
		// Add the uses
		this.uses = uses;
		// Get the state after
		boolean locked1 = this.isLocked();

		// Send the new offer list
		if (locked0 != locked1) {
			for (SMerchant merchant : this.merchants) {
				merchant.sendUpdate();
			}
		}
	}

	@Override
	public void addUses(int uses) {
		if (uses != 0) {
			this.setUses(this.uses + uses);
		}
	}

	@Override
	public boolean isLocked() {
		return this.maxUses >= 0 && this.uses >= this.maxUses;
	}

	@Override
	public ItemStack getBuyItem1() {
		return convertSafely(this.item1);
	}

	@Override
	public ItemStack getBuyItem2() {
		return convertSafely(this.item2);
	}

	@Override
	public boolean hasSecondItem() {
		return this.item2 != null;
	}

	@Override
	public ItemStack getBuyItem3() {
		return convertSafely(this.result);
	}

	@Override
	public void f() {
		this.addUses(1);
	}

	@Override
	public void a(int extra) {
		this.addMaxUses(extra);
	}

	@Override
	public boolean g() {
		return this.isLocked();
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

	@SuppressWarnings("deprecation")
	private static ItemStack convertSafely(org.bukkit.inventory.ItemStack itemStack) {
		if (itemStack == null || itemStack.getTypeId() == 0 || itemStack.getAmount() == 0) {
			return null;
		}
		return CraftItemStack.asNMSCopy(itemStack);
	}
}
