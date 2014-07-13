/*
 * This file is part of aion-lightning <aion-lightning.org>
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.raksang;

import ai.GeneralNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author xTz
 */
@AIName("raksha_sealing_wall")
public class RakshaSealingWallAI2 extends GeneralNpcAI2 {

	private AtomicBoolean startedEvent = new AtomicBoolean(false);

	@Override
	public boolean canThink() {
		return false;
	}

	@Override
	protected void handleCreatureMoved(Creature creature) {
		if (creature instanceof Player) {
			final Player player = (Player) creature;
			if (MathUtil.getDistance(getOwner(), player) <= 35) {
				if (startedEvent.compareAndSet(false, true)) {
					WorldMapInstance instance = getPosition().getWorldMapInstance();
					Npc sharik = instance.getNpc(217425);
					Npc flamelord = instance.getNpc(217451);
					Npc sealguard = instance.getNpc(217456);
					int bossId;
					if ((sharik == null || NpcActions.isAlreadyDead(sharik))
							&& (flamelord == null || NpcActions.isAlreadyDead(flamelord))
							&& (sealguard == null || NpcActions.isAlreadyDead(sealguard))) {
						bossId = 217475;
					}
					else {
						bossId = 217647;
					}
					spawn(bossId, 1063.08f, 903.13f, 138.744f, (byte) 29);
					AI2Actions.deleteOwner(this);
				}
			}
		}
	}

}