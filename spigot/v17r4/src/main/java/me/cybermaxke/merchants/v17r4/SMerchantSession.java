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

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantSession;

public class SMerchantSession implements MerchantSession {

    private final Merchant merchant;
    private final Inventory inventory;
    private final Player customer;

    SMerchantSession(Merchant merchant, Inventory inventory, Player customer) {
        this.inventory = inventory;
        this.merchant = merchant;
        this.customer = customer;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public Player getCustomer() {
        return this.customer;
    }

    @Override
    public Merchant getMerchant() {
        return this.merchant;
    }
}
