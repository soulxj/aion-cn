/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services.item;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.ItemActions;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.trade.PricesService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Sarynth modified by Wakizashi
 */
public class ItemRemodelService {

	/**
	 * @param player
	 * @param keepItemObjId
	 * @param extractItemObjId
	 */
	public static void remodelItem(Player player, int keepItemObjId, int extractItemObjId) {
		Storage inventory = player.getInventory();
		Item keepItem = inventory.getItemByObjId(keepItemObjId);
		Item extractItem = inventory.getItemByObjId(extractItemObjId);

		long remodelCost = PricesService.getPriceForService(1000, player.getRace());

		if (keepItem == null || extractItem == null) { // NPE check.
			return;
		}

		// Check Player Level
		if (player.getLevel() < 20) {

			PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT);
			return;
		}

		// Check Kinah
		if (player.getInventory().getKinah() < remodelCost) {
			PacketSendUtility.sendPacket(player,
				SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));
			return;
		}

		// Check for using "Pattern Reshaper" (168100000)
		if (extractItem.getItemTemplate().getTemplateId() == 168100000) {
			if (!keepItem.isSkinnedItem()) {
				PacketSendUtility.sendMessage(player, "That item does not have a remodeled skin to remove.");
				return;
			}
			// Remove Money
			if(!player.getInventory().tryDecreaseKinah(remodelCost))
				return;
			// Remove Pattern Reshaper
			player.getInventory().decreaseItemCount(extractItem, 1);

			// Revert item to ORIGINAL SKIN
			keepItem.setItemSkinTemplate(keepItem.getItemTemplate());

			// Remove dye color if item can not be dyed.
			if (!keepItem.getItemTemplate().isItemDyePermitted())
				keepItem.setItemColor(0);

			// Notify Player
			ItemPacketService.updateItemAfterInfoChange(player, keepItem);
			PacketSendUtility.sendPacket(player,
				SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_SUCCEED(new DescriptionId(keepItem.getItemTemplate().getNameId())));
			return;
		}
		// Check that types match.
		if (keepItem.getItemTemplate().getWeaponType() != extractItem.getItemSkinTemplate().getWeaponType()
			|| (extractItem.getItemSkinTemplate().getArmorType() != ArmorType.CLOTHES && keepItem.getItemTemplate().getArmorType() != extractItem
				.getItemSkinTemplate().getArmorType()) || keepItem.getItemTemplate().getArmorType() == ArmorType.CLOTHES
			|| keepItem.getItemTemplate().getItemSlot() != extractItem.getItemSkinTemplate().getItemSlot()) {
			PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(new DescriptionId(keepItem
				.getItemTemplate().getNameId()), new DescriptionId(extractItem.getItemSkinTemplate().getNameId())));
			return;
		}

		if (!keepItem.isRemodelable(player)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300478, new DescriptionId(keepItem.getItemTemplate().getNameId())));
			return;
		}

		if (!extractItem.isRemodelable(player)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300482, new DescriptionId(extractItem.getItemTemplate().getNameId())));
			return;
		}

		ItemTemplate skin = extractItem.getItemSkinTemplate();
		ItemActions actions = skin.getActions();
		if (extractItem.isSkinnedItem() && actions != null && actions.getRemodelAction() != null
			&& actions.getRemodelAction().getExtractType() == 2) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300482, new DescriptionId(extractItem.getItemTemplate().getNameId())));
			return;
		}
		// -- SUCCESS --

		// Remove Money
		player.getInventory().decreaseKinah(remodelCost);

		// Remove Item
		player.getInventory().decreaseItemCount(extractItem, 1);

		// REMODEL ITEM
		keepItem.setItemSkinTemplate(skin);

		// Transfer Dye
		keepItem.setItemColor(extractItem.getItemColor());

		// Notify Player
		ItemPacketService.updateItemAfterInfoChange(player, keepItem);
		PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(keepItem.getItemTemplate().getNameId())));
	}
}
