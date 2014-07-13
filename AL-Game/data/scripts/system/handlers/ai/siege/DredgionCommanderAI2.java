/*
 * This file is part of aion-lightning <aion-lightning.org>.
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
package ai.siege;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/*
 * @author Luzien
 */
@AIName("dredgionCommander")
public class DredgionCommanderAI2 extends SiegeNpcAI2 {
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		scheduleOneShot();
	}
	
	private int getSkill() {
		switch (getNpcId()) {
			case 276649:
				return 17572;
			case 276871:
			case 276872:
				return 18411;
			case 258236:
				return 18428;
			default:
				return 0;
		}
	}
	private void scheduleOneShot() {
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				if(getSkill() != 0) {
					if (getTarget() instanceof Npc) {
						Npc target = (Npc) getTarget();
						Race race = target.getRace();
						if ((race.equals(Race.GCHIEF_DARK) || race.equals(Race.GCHIEF_LIGHT)) && !target.getLifeStats().isAlreadyDead()) {
							AI2Actions.useSkill(DredgionCommanderAI2.this, getSkill());
							getAggroList().addHate(target, 10000);
						}
					}
					scheduleOneShot();
				}
			}
		}, 45 * 1000);
	}
}