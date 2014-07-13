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

public class EnchantsConfig {

	/**
	 * Supplement Additional Rates
	 */
	@Property(key = "gameserver.supplement.lesser", defaultValue = "1.0")
	public static float LESSER_SUP;
	@Property(key = "gameserver.supplement.regular", defaultValue = "1.0")
	public static float REGULAR_SUP;
	@Property(key = "gameserver.supplement.greater", defaultValue = "1.0")
	public static float GREATER_SUP;

	
	/**
	 * Max enchant level
	 */
	@Property(key = "gameserver.enchant.type1", defaultValue = "10")
	public static int ENCHANT_MAX_LEVEL_TYPE1;
	@Property(key = "gameserver.enchant.type2", defaultValue = "15")
	public static int ENCHANT_MAX_LEVEL_TYPE2;

	/**
	 * ManaStone Rates
	 */
	@Property(key = "gameserver.base.manastone", defaultValue = "50")
	public static float MANA_STONE;
	@Property(key = "gameserver.base.enchant", defaultValue = "60")
	public static float ENCHANT_STONE;
	
	@Property(key = "gameserver.manastone.clean", defaultValue = "false")
	public static boolean CLEAN_STONE;
}
