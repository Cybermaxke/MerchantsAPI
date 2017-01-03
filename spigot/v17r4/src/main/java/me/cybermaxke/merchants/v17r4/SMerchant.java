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

import static com.google.common.base.Preconditions.checkNotNull;
import static me.cybermaxke.merchants.v17r4.SMerchantAPI.SPIGOT;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.json.simple.parser.ParseException;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantOffer;
import me.cybermaxke.merchants.api.MerchantTradeListener;

import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IMerchant;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MerchantRecipe;
import net.minecraft.server.v1_7_R4.MerchantRecipeList;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SMerchant implements IMerchant, Merchant {

    // The recipes list
    private final MerchantRecipeList offers = new MerchantRecipeList();

    // The customers
    private final Set<Player> customers = Sets.newHashSet();

    // The title of the merchant
    private String title;
    private boolean jsonTitle;

    // The title that will be send
    private String sendTitle;

    // The trade handlers
    final Set<MerchantTradeListener> handlers = Sets.newHashSet();

    // Internal use only
    SMerchantOffer onTrade;

    SMerchant(String title, boolean jsonTitle) {
        this.setTitle(title, jsonTitle);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isTitleJson() {
        return this.jsonTitle;
    }

    @Override
    public void setTitle(String title, boolean jsonTitle) {
        checkNotNull(title, "title");

        // The old title
        final String oldTitle = this.sendTitle;
        final String newTitle;

        if (jsonTitle) {
            try {
                newTitle = SUtil.fromJson(title);
            } catch (ParseException e) {
                throw new IllegalArgumentException("invalid json format (" + title + ")", e);
            }
        } else {
            newTitle = title;
        }

        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        this.sendTitle = newTitle;
        this.jsonTitle = jsonTitle;
        this.title = title;

        // Send a update
        if (!this.sendTitle.equals(oldTitle)) {
            sendTitleUpdate();
        }
    }

    @Override
    public void setTitle(String title) {
        this.setTitle(title, false);
    }

    @Override
    public boolean addListener(MerchantTradeListener listener) {
        checkNotNull(listener, "listener");
        return this.handlers.add(listener);
    }

    @Override
    public boolean removeListener(MerchantTradeListener listener) {
        checkNotNull(listener, "listener");
        return this.handlers.remove(listener);
    }

    @Override
    public Collection<MerchantTradeListener> getListeners() {
        return Lists.newArrayList(this.handlers);
    }

    @Override
    public int getOffersCount() {
        return this.offers.size();
    }

    @Override
    public MerchantOffer getOfferAt(int index) {
        if (index < 0 || index >= this.offers.size()) {
            throw new IndexOutOfBoundsException("index (" + index + ") out of bounds min (0) and max (" + this.offers.size() + ")");
        }

        return (MerchantOffer) this.offers.get(index);
    }

    @Override
    public void setOfferAt(int index, MerchantOffer offer) {
        checkNotNull(offer, "offer");

        if (index < 0 || index >= this.offers.size()) {
            throw new IndexOutOfBoundsException("index (" + index + ") out of bounds min (0) and max (" + this.offers.size() + ")");
        }

        final SMerchantOffer old = (SMerchantOffer) this.offers.set(index, offer);
        old.remove(this);

        // Send the new offer list
        sendUpdate();
    }

    @Override
    public void insetOfferAt(int index, MerchantOffer offer) {
        checkNotNull(offer, "offer");

        if (index < 0 || index >= this.offers.size()) {
            throw new IndexOutOfBoundsException("index (" + index + ") out of bounds min (0) and max (" + this.offers.size() + ")");
        }

        this.offers.add(index, offer);
    }

    @Override
    public void removeOffer(MerchantOffer offer) {
        checkNotNull(offer, "offer");

        if (this.offers.remove(offer)) {
            // Unlink the offer
            ((SMerchantOffer) offer).remove(this);

            // Send the new offer list
            sendUpdate();
        }
    }

    @Override
    public void removeOffers(Iterable<MerchantOffer> offers) {
        checkNotNull(offers, "offers");

        // Only update if necessary
        if (!offers.iterator().hasNext()) {
            return;
        }

        if (this.offers.removeAll(Lists.newArrayList(offers))) {
            // Unlink the offers
            for (MerchantOffer offer : offers) {
                ((SMerchantOffer) offer).remove(this);
            }

            // Send the new offer list
            sendUpdate();
        }
    }

    @Override
    public void addOffer(MerchantOffer offer) {
        checkNotNull(offer, "offer");

        if (this.offers.contains(offer)) {
            return;
        }

        // Add the offer
        this.offers.add(offer);

        // Link the offer
        ((SMerchantOffer) offer).add(this);

        // Send the new offer list
        sendUpdate();
    }

    @Override
    public void addOffers(Iterable<MerchantOffer> offers) {
        checkNotNull(offers, "offers");

        // Only update if necessary
        if (!offers.iterator().hasNext()) {
            return;
        }

        // Add and link the offers
        for (MerchantOffer offer : offers) {
            if (this.offers.contains(offer)) {
                continue;
            }
            this.offers.add(offer);
            ((SMerchantOffer) offer).add(this);
        }

        // Send the new offer list
        sendUpdate();
    }

    @Override
    public void sortOffers(Comparator<MerchantOffer> comparator) {
        checkNotNull(comparator, "comparator");

        // Only sort if necessary
        if (this.offers.size() <= 1) {
            return;
        }

        // Sort the offers
        Collections.sort(this.offers, comparator);

        // Send the new offer list
        sendUpdate();
    }

    @Override
    public List<MerchantOffer> getOffers() {
        return Lists.newArrayList(this.offers);
    }

    @Override
    public boolean addCustomer(Player player) {
        checkNotNull(player, "player");

        if (this.customers.add(player)) {
            final EntityPlayer player0 = ((CraftPlayer) player).getHandle();
            Container container0 = null;

            try {
                container0 = new SContainerMerchant(player0, this);
                container0 = CraftEventFactory.callInventoryOpenEvent(player0, container0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (container0 == null) {
                this.customers.remove(player);
                return false;
            }

            final int window = player0.nextContainerCounter();

            player0.activeContainer = container0;
            player0.activeContainer.windowId = window;
            player0.activeContainer.addSlotListener(player0);

            // Open the window
            player0.playerConnection.sendPacket(new PacketPlayOutOpenWindow(window, 6, this.sendTitle, 3, true));

            // Write the recipe list
            final PacketDataSerializer content;
            // Create the proper packet serializer
            if (SPIGOT) {
                content = new PacketDataSerializer(Unpooled.buffer(), player0.playerConnection.networkManager.getVersion());
            } else {
                content = new PacketDataSerializer(Unpooled.buffer());
            }
            content.writeInt(window);
            this.offers.a(content);

            // Send the offers
            player0.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", content));

            return true;
        }

        return false;
    }

    @Override
    public boolean removeCustomer(Player player) {
        checkNotNull(player, "player");

        if (this.customers.remove(player)) {
            player.closeInventory();
            return true;
        }

        return false;
    }

    @Override
    public boolean hasCustomer(Player player) {
        checkNotNull(player, "player");
        return this.customers.contains(player);
    }

    @Override
    public Collection<Player> getCustomers() {
        return Lists.newArrayList(this.customers);
    }

    @Override
    public MerchantRecipeList getOffers(EntityHuman human) {
        return this.offers;
    }

    @Override
    public void a(MerchantRecipe recipe) {
        // Increment uses
        recipe.f();

        // Used by the custom merchant result slot
        this.onTrade = (SMerchantOffer) recipe;
    }

    @Override
    public void a_(EntityHuman arg0) {
        // Not used
    }

    @Override
    public void a_(ItemStack arg0) {
        // Not used
    }

    @Override
    public EntityHuman b() {
        // Not used
        return null;
    }

    private void sendTitleUpdate() {
        // Re-send the open window message to update the window name
        for (Player customer : this.customers) {
            final EntityPlayer player0 = ((CraftPlayer) customer).getHandle();
            player0.playerConnection.sendPacket(
                    new PacketPlayOutOpenWindow(player0.activeContainer.windowId, 6, this.sendTitle, 3, true));
            player0.updateInventory(player0.activeContainer);
        }
    }

    // Called when the merchant requires a update
    void sendUpdate() {
        if (this.customers.isEmpty()) {
            return;
        }

        if (SPIGOT) {
            final Collection<EntityPlayer>[] collections = split(this.customers);

            // Copy the uses fields if needed
            if (collections[1] != null || collections[2] != null || collections[3] != null) {
                for (Object offer : this.offers) {
                    ((SMerchantOffer) offer).copyUses();
                }
            }

            if (collections[0] != null) {
                this.sendUpdateWithProtocol(27, collections[0]);
            }
            if (collections[1] != null) {
                sendUpdateWithProtocol(28, collections[1]);
            }
            if (collections[2] != null) {
                sendUpdateWithProtocol(29, collections[2]);
            }
            if (collections[3] != null) {
                sendUpdateWithProtocol(47, collections[3]);
            }
        } else {
            sendUpdateWithSerializer(new PacketDataSerializer(Unpooled.buffer()),
                    Collections2.transform(this.customers, new Function<Player, EntityPlayer>() {

                        @Override
                        public EntityPlayer apply(Player player) {
                            return ((CraftPlayer) player).getHandle();
                        }

                    }));
        }
    }

    private void sendUpdateWithProtocol(int protocol, Iterable<EntityPlayer> players) {
        sendUpdateWithSerializer(new PacketDataSerializer(Unpooled.buffer(), protocol), players);
    }

    private void sendUpdateWithSerializer(PacketDataSerializer serializer, Iterable<EntityPlayer> players) {
        // Write the recipe list
        this.offers.a(serializer);

        // Send a packet to all the players
        for (EntityPlayer player0 : players) {
            // Every player has a different window id
            final PacketDataSerializer content1 = new PacketDataSerializer(Unpooled.buffer());
            content1.writeInt(player0.activeContainer.windowId);
            content1.writeBytes(serializer);

            player0.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", content1));
        }
    }

    private Collection<EntityPlayer>[] split(Iterable<Player> players) {
        Collection<EntityPlayer> list0 = null;
        Collection<EntityPlayer> list1 = null;
        Collection<EntityPlayer> list2 = null;
        Collection<EntityPlayer> list3 = null;

        for (Player player : players) {
            final EntityPlayer player0 = ((CraftPlayer) player).getHandle();

            final int version = player0.playerConnection.networkManager.getVersion();
            if (version < 28) {
                if (list0 == null) {
                    list0 = Lists.newArrayList();
                }
                list0.add(player0);
            } else if (version < 29) {
                if (list1 == null) {
                    list1 = Lists.newArrayList();
                }
                list1.add(player0);
            } else if (version < 47) {
                if (list2 == null) {
                    list2 = Lists.newArrayList();
                }
                list2.add(player0);
            } else {
                if (list3 == null) {
                    list3 = Lists.newArrayList();
                }
                list3.add(player0);
            }
        }

        // list0 < 28; list1 < 29; list3 < 47; list4 >= 47
        return new Collection[] { list0, list1, list2, list3 };
    }
}
