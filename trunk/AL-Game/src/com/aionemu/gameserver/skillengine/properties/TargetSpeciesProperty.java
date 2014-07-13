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
package com.aionemu.gameserver.skillengine.properties;


import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import java.util.Iterator;
import java.util.List;

/**
 * @author Luzien
 */
public class TargetSpeciesProperty {

	/**
	 * @param skill
	 * @param properties
	 * @return
	 */
	public static boolean set(final Skill skill, Properties properties) {
		TargetSpeciesAttribute value = properties.getTargetSpecies();

		final List<Creature> effectedList = skill.getEffectedList();

		switch (value) {
			case NPC:
				for (Iterator<Creature> iter = effectedList.iterator(); iter.hasNext();) {
					Creature nextEffected = iter.next();

					if (nextEffected instanceof Npc)
						continue;

					iter.remove();
				}
				break;
			case PC:
				for (Iterator<Creature> iter = effectedList.iterator(); iter.hasNext();) {
					Creature nextEffected = iter.next();

					if (nextEffected instanceof Player)
						continue;

					iter.remove();
				}
				break;
		}
		return true;
	}
}
