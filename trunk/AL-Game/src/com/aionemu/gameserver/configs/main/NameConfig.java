/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;
import java.util.regex.Pattern;

/**
 * @author nrg
 */
public class NameConfig {

  /**
   * Enables custom names usage.
   */
  @Property(key = "gameserver.name.allow.custom", defaultValue = "false")
  public static boolean ALLOW_CUSTOM_NAMES;
  
	/**
	 * Character name pattern (checked when character is being created)
	 */
	@Property(key = "gameserver.name.characterpattern", defaultValue = "[a-zA-Z]{2,16}")
	public static Pattern CHAR_NAME_PATTERN;

	/**
	 * Forbidden word sequences Filters charname, miol, legion, chat
	 */
	@Property(key = "gameserver.name.forbidden.sequences", defaultValue = "")
	public static String NAME_SEQUENCE_FORBIDDEN;

	/**
	 * Enable client filter Filters charname, miol, legion, chat
	 */
	@Property(key = "gameserver.name.forbidden.enable.client", defaultValue = "true")
	public static boolean NAME_FORBIDDEN_ENABLE;

	/**
	 * Forbidden Charnames NOTE: Parsed out of aion 3.0 client Filters charname, miol, legion, chat
	 */
	@Property(key = "gameserver.name.forbidden.client", defaultValue = "")
	public static String NAME_FORBIDDEN_CLIENT;
}
