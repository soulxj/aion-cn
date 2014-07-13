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
package com.aionemu.gameserver.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EnchantsConfig;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatEnchantFunction;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.stats.listeners.ItemEquipmentListener;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.EnchantItemAction;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.item.ItemPacketService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.item.ItemSocketService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.AuditLogger;

/**
 * @author ATracer
 * @modified Wakizashi, Source, vlog
 */
public class EnchantService {

   private static final Logger log = LoggerFactory.getLogger(EnchantService.class);

   /**
    * @param player
    * @param targetItem
    * @param parentItem
    */
   public static boolean breakItem(Player player, Item targetItem, Item parentItem) {
	  Storage inventory = player.getInventory();

	  if (inventory.getItemByObjId(targetItem.getObjectId()) == null)
		 return false;
	  if (inventory.getItemByObjId(parentItem.getObjectId()) == null)
		 return false;

	  ItemTemplate itemTemplate = targetItem.getItemTemplate();
	  int quality = itemTemplate.getItemQuality().getQualityId();

	  if (!itemTemplate.isArmor() && !itemTemplate.isWeapon()) {
		 AuditLogger.info(player, "Player try break dont compatible item type.");
		 return false;
	  }

	  if (!itemTemplate.isArmor() && !itemTemplate.isWeapon()) {
		 AuditLogger.info(player, "Break item hack, armor/weapon iD changed.");
		 return false;
	  }

	  // Quality modifier
	  if (itemTemplate.isSoulBound() && !itemTemplate.isArmor())
		 quality += 1;
	  else if (!itemTemplate.isSoulBound() && itemTemplate.isArmor())
		 quality -= 1;

	  int number = 0;
	  int level = 1;
	  switch (quality) {
		 case 0: // JUNK
		 case 1: // COMMON
			number = Rnd.get(1, 2);
			level = Rnd.get(-4, 10);
			break;
		 case 2: // RARE
			number = Rnd.get(1, 4);
			level = Rnd.get(-3, 20);
			break;
		 case 3: // LEGEND
			number = Rnd.get(1, 6);
			level = Rnd.get(-2, 30);
			break;
		 case 4: // UNIQUE
			number = Rnd.get(1, 8);
			level = Rnd.get(-1, 50);
			break;
		 case 5: // EPIC
			number = Rnd.get(1, 10);
			level = Rnd.get(0, 70);
			break;
		 case 6: // MYTHIC
		 case 7:
			number = Rnd.get(1, 12);
			level = Rnd.get(0, 80);
			break;
	  }

	  // You can't add stone < 166000000
	  if (level < 1)
		 level = 1;
	  int enchantItemLevel = targetItem.getItemTemplate().getLevel() + level;
	  int enchantItemId = 166000000 + enchantItemLevel;

	  if (inventory.delete(targetItem) != null) {
		 if (inventory.decreaseByObjectId(parentItem.getObjectId(), 1))
			ItemService.addItem(player, enchantItemId, number);
	  }
	  else
		 AuditLogger.info(player, "Possible break item hack, do not remove item.");
	  return true;
   }

   /**
    * @param player
    * @param parentItem the enchantment stone
    * @param targetItem the item to enchant
    * @param supplementItem the item, giving additional chance
    * @return true, if successful
    */
   public static boolean enchantItem(Player player, Item parentItem, Item targetItem, Item supplementItem) {
	  ItemTemplate enchantStone = parentItem.getItemTemplate();
	  int enchantStoneLevel = enchantStone.getLevel();
	  int targetItemLevel = targetItem.getItemTemplate().getLevel();
	  int enchantitemLevel = targetItem.getEnchantLevel() + 1;

	  // Modifier, depending on the quality of the item
	  // Decreases the chance of enchant
	  int qualityCap = 0;

	  ItemQuality quality = targetItem.getItemTemplate().getItemQuality();

	  switch (quality) {
		 case JUNK:
		 case COMMON:
			qualityCap = 5;
			break;
		 case RARE:
			qualityCap = 10;
			break;
		 case LEGEND:
			qualityCap = 15;
			break;
		 case UNIQUE:
			qualityCap = 20;
			break;
		 case EPIC:
			qualityCap = 25;
			break;
		 case MYTHIC:
			qualityCap = 30;
			break;
	  }

	  // Start value of success
	  float success = EnchantsConfig.ENCHANT_STONE;

	  // Extra success chance
	  // The greater the enchant stone level, the greater the
	  // level difference modifier
	  int levelDiff = enchantStoneLevel - targetItemLevel;
	  success += levelDiff > 0 ? levelDiff * 3f / qualityCap : 0;

	  // Level difference
	  // Can be negative, if the item quality too hight
	  // And the level difference too small
	  success += levelDiff - qualityCap;

	  // Enchant next level difficulty
	  // The greater item enchant level,
	  // the lower start success chance
	  success -= targetItem.getEnchantLevel() * qualityCap / (enchantitemLevel > 10 ? 4f : 5f);

	  // Supplement is used
	  if (supplementItem != null) {
		 // Amount of supplement items
		 int supplementUseCount = 1;
		 // Additional success rate for the supplement
		 ItemTemplate supplementTemplate = supplementItem.getItemTemplate();
		 float addSuccessRate = 0f;

		 EnchantItemAction action = supplementTemplate.getActions().getEnchantAction();
		 if (action != null) {
			if (action.isManastoneOnly())
			   return false;
			addSuccessRate = action.getChance() * 2;
		 }

		 action = enchantStone.getActions().getEnchantAction();
		 if (action != null)
			supplementUseCount = action.getCount();

		 // Beginning from the level 11 of the enchantment of the item,
		 // There will be 2 times more supplements required
		 if (enchantitemLevel > 10)
			supplementUseCount = supplementUseCount * 2;

		 // Check the required amount of the supplements
		 if (player.getInventory().getItemCountByItemId(supplementTemplate.getTemplateId()) < supplementUseCount)
			return false;

		 // Adjust addsuccessrate to rates in config
		 switch (parentItem.getItemTemplate().getItemQuality()) {
			case LEGEND:
			   addSuccessRate *= EnchantsConfig.LESSER_SUP;
			   break;
			case UNIQUE:
			   addSuccessRate *= EnchantsConfig.REGULAR_SUP;
			   break;
			case EPIC:
			   addSuccessRate *= EnchantsConfig.GREATER_SUP;
			   break;
		 }

		 // Add success rate of the supplement to the overall chance
		 success += addSuccessRate;

		 // Put supplements to wait for update
		 player.subtractSupplements(supplementUseCount, supplementTemplate.getTemplateId());
	  }

	  // The overall success chance can't be more, than 95
	  if (success >= 95)
		 success = 95;

	  boolean result = false;
	  float random = Rnd.get(1, 1000) / 10f;

	  // If the random number < or = overall success rate,
	  // The item will be successfully enchanted
	  if (random <= success)
		 result = true;

	  // For test purpose. To use by administrator
	  if (player.getAccessLevel() > 2)
		 PacketSendUtility.sendMessage(player, (result ? "Success" : "Fail") + " Rnd:" + random + " Luck:" + success);

	  return result;
   }

   public static void enchantItemAct(Player player, Item parentItem, Item targetItem, Item supplementItem,
		   int currentEnchant, boolean result) {
	  int addLevel = 1;
	  int rnd = Rnd.get(100); //crit modifier
	  if (rnd < 2)
		 addLevel = 3;
	  else if (rnd < 7)
		 addLevel = 2;
	  ItemQuality targetQuality = targetItem.getItemTemplate().getItemQuality();

	  if (!player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1)) {
		 AuditLogger.info(player, "Possible enchant hack, do not remove enchant stone.");
		 return;
	  }
	  //Decrease required supplements
	  player.updateSupplements();

	  // Items that are Fabled or Eternal can get up to +15.
	  if (result) {
		 switch (targetQuality) {
			case COMMON:
			case RARE:
			case LEGEND:
			   if (currentEnchant > EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1) {
				  currentEnchant = EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1;
				  AuditLogger.info(player, "Possible enchant hack, send fake packet for enchant up more posible.");
			   }
			   else if (currentEnchant == EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1) {
				  return;
			   }
			   else if (currentEnchant + addLevel <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1) {
				  currentEnchant += addLevel;
			   }
			   else if (((addLevel - 1) > 1) && ((currentEnchant + addLevel - 1) <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1)) {
				  currentEnchant += (addLevel - 1);
			   }
			   else 
				  currentEnchant += 1;
			   break;
			case UNIQUE:
			case EPIC:
			case MYTHIC:
			   if (currentEnchant > EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2) {
				  currentEnchant = EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2;
				  AuditLogger.info(player, "Possible enchant hack, send fake packet for enchant up more posible.");
			   }
			   else if (currentEnchant == EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2) {
				  return;
			   }
			   else if (currentEnchant + addLevel <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2) {
				  currentEnchant += addLevel;
			   }
			   else if (((addLevel - 1) > 1) && ((currentEnchant + addLevel - 1) <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2)) {
				  currentEnchant += (addLevel - 1);
			   }
			   else
				  currentEnchant += 1;
			   break;
			case JUNK:
			   return;
		 }
	  }
	  else {
		 // Retail: http://powerwiki.na.aiononline.com/aion/Patch+Notes:+1.9.0.1
		 // When socketing fails at +11~+15, the value falls back to +10.
		 if (currentEnchant > 10)
			currentEnchant = 10;
		 else if (currentEnchant > 0)
			currentEnchant -= 1;
	  }

	  targetItem.setEnchantLevel(currentEnchant);
	  if (targetItem.isEquipped())
		 player.getGameStats().updateStatsVisually();

	  ItemPacketService.updateItemAfterInfoChange(player, targetItem);

	  if (targetItem.isEquipped())
		 player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
	  else
		 player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);

	  if (result)
		 PacketSendUtility.sendPacket(player,
				 SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_SUCCEED(new DescriptionId(targetItem.getNameId())));
	  else
		 PacketSendUtility.sendPacket(player,
				 SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_FAILED(new DescriptionId(targetItem.getNameId())));
   }

   /**
    * @param player
    * @param parentItem the manastone
    * @param targetItem the item to socket
    * @param supplementItem
    * @param targetWeapon fusioned weapon
    */
   public static boolean socketManastone(Player player, Item parentItem, Item targetItem, Item supplementItem,
		   int targetWeapon) {

	  int targetItemLevel = 1;

	  // Fusioned weapon. Primary weapon level.
	  if (targetWeapon == 1)
		 targetItemLevel = targetItem.getItemTemplate().getLevel();
	  // Fusioned weapon. Secondary weapon level.
	  else
		 targetItemLevel = targetItem.getFusionedItemTemplate().getLevel();

	  int stoneLevel = parentItem.getItemTemplate().getLevel();
	  int slotLevel = (int) (10 * Math.ceil((targetItemLevel + 10) / 10d));
	  boolean result = false;

	  // Start value of success
	  float success = EnchantsConfig.MANA_STONE;

	  // The current amount of socketed stones
	  int stoneCount;

	  // Manastone level shouldn't be greater as 20 + item level
	  // Example: item level: 1 - 10. Manastone level should be <= 20
	  if (stoneLevel > slotLevel)
		 return false;

	  // Fusioned weapon. Primary weapon slots.
	  if (targetWeapon == 1)
		 // Count the inserted stones in the primary weapon
		 stoneCount = targetItem.getItemStones().size();
	  // Fusioned weapon. Secondary weapon slots.
	  else
		 // Count the inserted stones in the secondary weapon
		 stoneCount = targetItem.getFusionStones().size();

	  // Fusioned weapon. Primary weapon slots.
	  if (targetWeapon == 1) {
		 // Find all free slots in the primary weapon
		 if (stoneCount >= targetItem.getSockets(false)) {
			AuditLogger.info(player, "Manastone socket overload");
			return false;
		 }
	  }
	  // Fusioned weapon. Secondary weapon slots.
	  else if (!targetItem.hasFusionedItem() || stoneCount >= targetItem.getSockets(true)) {
		 // Find all free slots in the secondary weapon
		 AuditLogger.info(player, "Manastone socket overload");
		 return false;
	  }

	  // Stone quality modifier
	  success += parentItem.getItemTemplate().getItemQuality() == ItemQuality.COMMON ? 25f : 15f;

	  // Next socket difficulty modifier
	  float socketDiff = stoneCount * 1.25f + 1.75f;

	  // Level difference
	  success += (slotLevel - stoneLevel) / socketDiff;

	  // The supplement item is used
	  if (supplementItem != null) {
		 int supplementUseCount = 0;
		 ItemTemplate manastoneTemplate = parentItem.getItemTemplate();

		 int manastoneCount;
		 // Not fusioned
		 if (targetWeapon == 1)
			manastoneCount = targetItem.getItemStones().size() + 1;
		 // Fusioned
		 else
			manastoneCount = targetItem.getFusionStones().size() + 1;

		 // Additional success rate for the supplement
		 ItemTemplate supplementTemplate = supplementItem.getItemTemplate();
		 float addSuccessRate = 0f;

		 boolean isManastoneOnly = false;
		 EnchantItemAction action = manastoneTemplate.getActions().getEnchantAction();
		 if (action != null)
			supplementUseCount = action.getCount();

		 action = supplementTemplate.getActions().getEnchantAction();
		 if (action != null) {
			addSuccessRate = action.getChance();
			isManastoneOnly = action.isManastoneOnly();
		 }

		 // Adjust addsuccessrate to rates in config
		 switch (parentItem.getItemTemplate().getItemQuality()) {
			case LEGEND:
			   addSuccessRate *= EnchantsConfig.LESSER_SUP;
			   break;
			case UNIQUE:
			   addSuccessRate *= EnchantsConfig.REGULAR_SUP;
			   break;
			case EPIC:
			   addSuccessRate *= EnchantsConfig.GREATER_SUP;
			   break;
		 }

		 if (isManastoneOnly)
			supplementUseCount = 1;
		 else if (stoneCount > 0)
			supplementUseCount = supplementUseCount * manastoneCount;

		 if (player.getInventory().getItemCountByItemId(supplementTemplate.getTemplateId()) < supplementUseCount)
			return false;

		 // Add successRate
		 success += addSuccessRate;

		 // Put up supplements to wait for update
		 player.subtractSupplements(supplementUseCount, supplementTemplate.getTemplateId());
	  }

	  float random = Rnd.get(1, 1000) / 10f;

	  if (random <= success)
		 result = true;

	  // For test purpose. To use by administrator
	  if (player.getAccessLevel() > 2)
		 PacketSendUtility.sendMessage(player, (result ? "Success" : "Fail") + " Rnd:" + random + " Luck:" + success);

	  return result;
   }

   public static void socketManastoneAct(Player player, Item parentItem, Item targetItem, Item supplementItem,
		   int targetWeapon, boolean result) {

	  // Decrease required supplements
	  player.updateSupplements();

	  if (player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1) && result) {
		 PacketSendUtility.sendPacket(player,
				 SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_SUCCEED(new DescriptionId(targetItem.getNameId())));

		 if (targetWeapon == 1) {
			ManaStone manaStone = ItemSocketService.addManaStone(targetItem, parentItem.getItemTemplate().getTemplateId());
			if (targetItem.isEquipped()) {
			   ItemEquipmentListener.addStoneStats(targetItem, manaStone, player.getGameStats());
			   player.getGameStats().updateStatsAndSpeedVisually();
			}
		 }
		 else {
			ManaStone manaStone = ItemSocketService.addFusionStone(targetItem, parentItem.getItemTemplate().getTemplateId());
			if (targetItem.isEquipped()) {
			   ItemEquipmentListener.addStoneStats(targetItem, manaStone, player.getGameStats());
			   player.getGameStats().updateStatsAndSpeedVisually();
			}
		 }
	  }
	  else {
		 PacketSendUtility.sendPacket(player,
				 SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_FAILED(new DescriptionId(targetItem.getNameId())));
		 if (targetWeapon == 1) {
			Set<ManaStone> manaStones = targetItem.getItemStones();
			if (targetItem.isEquipped()) {
			   ItemEquipmentListener.removeStoneStats(manaStones, player.getGameStats());
			   player.getGameStats().updateStatsAndSpeedVisually();
			}
			ItemSocketService.removeAllManastone(player, targetItem);
		 }
		 else {
			Set<ManaStone> manaStones = targetItem.getFusionStones();

			if (targetItem.isEquipped()) {
			   ItemEquipmentListener.removeStoneStats(manaStones, player.getGameStats());
			   player.getGameStats().updateStatsAndSpeedVisually();
			}

			ItemSocketService.removeAllFusionStone(player, targetItem);
		 }
	  }

	  ItemPacketService.updateItemAfterInfoChange(player, targetItem);
   }

   /**
    * @param player
    * @param item
    */
   public static void onItemEquip(Player player, Item item) {
	  List<IStatFunction> modifiers = new ArrayList<IStatFunction>();
	  try {
		 if (item.getItemTemplate().isWeapon()) {
			switch (item.getItemTemplate().getWeaponType()) {
			   case BOOK_2H:
			   case ORB_2H:
			   case GUN_1H:
			   case CANNON_2H:
				 case HARP_2H:
				  modifiers.add(new StatEnchantFunction(item, StatEnum.BOOST_MAGICAL_SKILL));
				  modifiers.add(new StatEnchantFunction(item, StatEnum.MAGICAL_ATTACK));
				  break;
			   case MACE_1H:
			   case STAFF_2H:
				  modifiers.add(new StatEnchantFunction(item, StatEnum.BOOST_MAGICAL_SKILL));
			   case DAGGER_1H:
			   case BOW:
			   case POLEARM_2H:
			   case SWORD_1H:
			   case SWORD_2H:
				  if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask())
					 modifiers.add(new StatEnchantFunction(item, StatEnum.MAIN_HAND_POWER));
				  else
					 modifiers.add(new StatEnchantFunction(item, StatEnum.OFF_HAND_POWER));
			}
		 }
		 else if (item.getItemTemplate().isArmor()) {
			if (item.getItemTemplate().getArmorType() == ArmorType.SHIELD) {
			   modifiers.add(new StatEnchantFunction(item, StatEnum.DAMAGE_REDUCE));
			   modifiers.add(new StatEnchantFunction(item, StatEnum.BLOCK));
			}
			else {
			   modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_DEFENSE));
			   modifiers.add(new StatEnchantFunction(item, StatEnum.MAGICAL_DEFEND));
			   modifiers.add(new StatEnchantFunction(item, StatEnum.MAXHP));
			   modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_CRITICAL_RESIST));
			}
		 }
		 if (!modifiers.isEmpty())
			player.getGameStats().addEffect(item, modifiers);
	  }
	  catch (Exception ex) {
		 log.error("Error on item equip.", ex);
	  }
   }
}