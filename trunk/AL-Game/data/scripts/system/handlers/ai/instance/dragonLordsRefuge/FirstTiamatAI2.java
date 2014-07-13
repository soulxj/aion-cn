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
package ai.instance.dragonLordsRefuge;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;

import com.aionemu.gameserver.ai2.AIName;


/**
 * @author Cheatkiller
 *
 */
@AIName("firsttiamat")
public class FirstTiamatAI2 extends AggressiveNpcAI2 {
	
	@Override
	protected void handleDeactivate() {
	}
	
	@Override
	protected void handleSpawned() {
	   super.handleSpawned();
	   if (getNpcId() == 219360)
		  AI2Actions.useSkill(this, 20917);
	}
}

