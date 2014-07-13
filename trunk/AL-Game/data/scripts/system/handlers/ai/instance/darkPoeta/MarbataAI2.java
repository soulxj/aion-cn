/*
 * This file is part of aion-lightning <aion-lightning.org>.
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.darkPoeta;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @author xTz
 */
@AIName("marbata")
public class MarbataAI2 extends AggressiveNpcAI2 {

	private boolean isStart;

	@Override
	protected void handleCreatureAggro(Creature creature) {
		super.handleCreatureAggro(creature);
		if (!isStart) {
			isStart = true;
			buff();
		}
	}

	private void buff() {
		AI2Actions.useSkill(this, 18556);
		AI2Actions.useSkill(this, 18110);
	}

	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		isStart = false;
		getEffectController().removeEffect(18556);
		getEffectController().removeEffect(18110);
	}
}
