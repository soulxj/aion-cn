/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class CraftConfig {

	/**
	 * Enable craft skills unrestricted level-up
	 */
	@Property(key = "gameserver.craft.skills.unrestricted.levelup.enable", defaultValue = "false")
	public static boolean UNABLE_CRAFT_SKILLS_UNRESTRICTED_LEVELUP;

	@Property(key = "gameserver.craft.skills.delete.excess.enable", defaultValue = "false")
	public static boolean DELETE_EXCESS_CRAFT_ENABLE;

	/**
	 * Maximum number of expert skills a player can have
	 */
	@Property(key = "gameserver.craft.max.expert.skills", defaultValue = "2")
	public static int MAX_EXPERT_CRAFTING_SKILLS;

	/**
	 * Maximum number of master skills a player can have
	 */
	@Property(key = "gameserver.craft.max.master.skills", defaultValue = "1")
	public static int MAX_MASTER_CRAFTING_SKILLS;

	/**
	 * Chance to have a critical procraft (applied on first step)
	 */
	@Property(key = "gameserver.craft.critical.rate.regular", defaultValue = "15")
	public static int CRAFT_CRIT_RATE;
	@Property(key = "gameserver.craft.critical.rate.premium", defaultValue = "15")
	public static int PREMIUM_CRAFT_CRIT_RATE;
	@Property(key = "gameserver.craft.critical.rate.vip", defaultValue = "15")
	public static int VIP_CRAFT_CRIT_RATE;

	/**
	 * Chance to have a combo procraft (applied on second step)
	 */
	@Property(key = "gameserver.craft.combo.rate.regular", defaultValue = "25")
	public static int CRAFT_COMBO_RATE;
	@Property(key = "gameserver.craft.combo.rate.premium", defaultValue = "25")
	public static int PREMIUM_CRAFT_COMBO_RATE;
	@Property(key = "gameserver.craft.combo.rate.vip", defaultValue = "25")
	public static int VIP_CRAFT_COMBO_RATE;
}