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
package me.cybermaxke.merchants.v1710;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MerchantRecipe;
import me.cybermaxke.merchants.api.MerchantOffer;

import com.google.common.base.Optional;

public class SMerchantOffer extends MerchantRecipe implements MerchantOffer {
	private static Field fieldUses;
	private static Field fieldMaxUses;

	// Whether they are changed by spigot from private to public
	private static boolean publicFields;

	// The merchants this offer is added to
	private final Set<SMerchant> merchants = Collections.newSetFromMap(new WeakHashMap<SMerchant, Boolean>());

	private final org.bukkit.inventory.ItemStack item1;
	private final org.bukkit.inventory.ItemStack item2;
	private final org.bukkit.inventory.ItemStack result;

	private int maxUses0 = -1;
	private int uses0;

	private boolean maxUsesChange;
	private boolean usesChange;

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

	// Copies the uses from this class to the underlying fields
	protected void copyUses() {
		if (fieldUses == null) {
			try {
				fieldUses = MerchantRecipe.class.getDeclaredField("uses");
				fieldMaxUses = MerchantRecipe.class.getDeclaredField("maxUses");
			} catch (Exception e) {
				e.printStackTrace();
			}

			publicFields = Modifier.isPublic(fieldUses.getModifiers());
		}

		try {
			if (this.usesChange) {
				if (!publicFields) {
					fieldUses.setAccessible(true);
				}
				fieldUses.set(this, this.uses0);
			}
			if (this.maxUsesChange) {
				if (!publicFields) {
					fieldMaxUses.setAccessible(true);
				}
				fieldMaxUses.set(this, this.maxUses0 < 0 ? Integer.MAX_VALUE : this.maxUses0);
			}
			this.usesChange = false;
			this.maxUsesChange = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return this.maxUses0;
	}

	@Override
	public void setMaxUses(int uses) {
		if (uses == this.maxUses0) {
			return;
		}

		// Track the change
		this.maxUsesChange = true;
		// Get the state before
		boolean locked0 = this.isLocked();
		// Set the max uses
		this.maxUses0 = uses;
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
		if (this.maxUses0 >= 0) {
			this.setMaxUses(this.maxUses0 + extra);
		}
	}

	@Override
	public int getUses() {
		return this.uses0;
	}

	@Override
	public void setUses(int uses) {
		if (this.uses0 == uses) {
			return;
		}

		// Track the change
		this.usesChange = true;
		// Get the state before
		boolean locked0 = this.isLocked();
		// Add the uses
		this.uses0 = uses;
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
			this.setUses(this.uses0 + uses);
		}
	}

	@Override
	public boolean isLocked() {
		return this.maxUses0 >= 0 && this.uses0 >= this.maxUses0;
	}

	@Override
	public ItemStack getBuyItem1() {
		return convertSafely(this.item1);
	}

	@Override
	public ItemStack getBuyItem2() {
		if (this.item2 == null) {
			return null;
		}

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
		clone.maxUses0 = this.maxUses0;
		clone.uses0 = this.uses0;

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
