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
package ai.events;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SiegeService;
import org.joda.time.DateTime;

/**
 * @author Bobobear
 *
 */
 
@AIName("rvr_guard")
public class RvrGuardAI2 extends AggressiveNpcAI2 {

   @Override
   protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		//add player to event list for additional reward
		if (creature instanceof Player && (int)getPosition().getMapId() == 600010000) {
			DateTime now = DateTime.now();
			int hour = now.getHourOfDay();
			if (hour >= 19 && hour <= 23) {
				Npc bossAsmo = getPosition().getWorldMapInstance().getNpc(220948);
				Npc bossElyos = getPosition().getWorldMapInstance().getNpc(220949);
				if (bossAsmo != null && bossElyos != null) {
					SiegeService.getInstance().checkRvrPlayerOnEvent((Player)creature);
				}
			}
		}
	}
}