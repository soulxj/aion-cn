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

import java.util.List;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.LetterType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.mail.SystemMailService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import org.joda.time.DateTime;
/**
 * @author Bobobear
 *
 */
 
@AIName("rvr_boss")
public class RvrBossAI2 extends AggressiveNpcAI2 {

   @Override
   protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		//add player to event list for additional reward
		if (creature instanceof Player && (int)getPosition().getMapId() == 600010000) {
			SiegeService.getInstance().checkRvrPlayerOnEvent((Player)creature);
		}
		//TODO Spawn defensive guards (only for bosses in silentera Canyon)
	}

   @Override
   protected void handleDied() {
		super.handleDied();
		despawnEnemyBoss();
		scheduleRespawn();
		performPartecipationReward();
   }

 	//despawn enemy boss (only for silentera)
	private void despawnEnemyBoss() {
		if ((int)getPosition().getMapId() == 600010000) {
			WorldMapInstance instance = getPosition().getWorldMapInstance();
			deleteNpcs(instance.getNpcs(getNpcId() == 220948 ? 220949 : 220948 ));
		}
	}
	
	//schedule respawn of both silentera bosses (bosses in other maps must not respawn)
	private void scheduleRespawn() {
		if ((int)getPosition().getMapId() == 600010000) {
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					spawn(220948, 658.7087f, 795.21857f, 293.14087f, (byte) 7);
					spawn(220949, 657.95105f, 737.5624f, 293.19818f, (byte) 0);
				}
			}, 43200000);
		}
	}

	private void deleteNpcs(List<Npc> npcs) {
		for (Npc npc : npcs) {
			if (npc != null)
				npc.getController().onDelete();
		}
	}

	private void performPartecipationReward() {
		if ((int)getPosition().getMapId() == 600010000) {
			DateTime now = DateTime.now();
			int hour = now.getHourOfDay();
			if (hour >= 19 && hour <= 23) {
				List<Player> eventPlayerList = SiegeService.getInstance().getRvrPlayersOnEvent();
				for (Player rewardedPlayer: eventPlayerList) {
					SystemMailService.getInstance().sendMail("EventService", rewardedPlayer.getName(), "EventReward", "Medal",
						186000147, 1, 0, LetterType.NORMAL);
				}
			}
			SiegeService.getInstance().clearRvrPlayersOnEvent();
		}
	}
	
	@Override
	protected void handleDespawned() {
	  super.handleDespawned();
	}
}