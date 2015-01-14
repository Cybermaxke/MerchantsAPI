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
package me.cybermaxke.merchants.v1800;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import me.cybermaxke.merchants.api.MerchantOffer;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.MerchantRecipe;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;

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

	/**
	 * Links the offer to the merchant.
	 * 
	 * @param merchant the merchant
	 */
	void add(SMerchant merchant) {
		this.merchants.add(merchant);
	}

	/**
	 * Unlinks the offer from the merchant.
	 * 
	 * @param merchant the merchant
	 */
	void remove(SMerchant merchant) {
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
		// Get the state before
		boolean locked0 = this.isLocked();
		// Add the uses
		this.uses += uses;
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
	public boolean isLocked() {
		return this.maxUses >= 0 && this.uses >= this.maxUses;
	}

	@Override
	public ItemStack getBuyItem1() {
		return CraftItemStack.asNMSCopy(this.item1);
	}

	@Override
	public ItemStack getBuyItem2() {
		if (this.item2 == null) {
			return null;
		}

		return CraftItemStack.asNMSCopy(this.item2);
	}

	@Override
	public boolean hasSecondItem() {
		return this.item2 != null;
	}

	@Override
	public ItemStack getBuyItem3() {
		return CraftItemStack.asNMSCopy(this.result);
	}

	@Override
	public int e() {
		return this.uses;
	}

	@Override
	public int f() {
		return this.maxUses < 0 ? Integer.MAX_VALUE : this.maxUses;
	}

	@Override
	public void g() {
		this.addUses(1);
	}

	@Override
	public void a(int extra) {
		this.addMaxUses(extra);
	}

	@Override
	public boolean h() {
		return this.isLocked();
	}

	@Override
	public boolean j() {
		return false;
	}
}
