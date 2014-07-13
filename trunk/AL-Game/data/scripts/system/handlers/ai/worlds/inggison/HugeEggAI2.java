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
package ai.worlds.inggison;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

import ai.GeneralNpcAI2;


/**
 * @author Cheatkiller
 *
 */
@AIName("hugeegg")
public class HugeEggAI2 extends GeneralNpcAI2 {
	
	@Override
	public boolean canThink() {
		return false;
	}
	
	@Override
	public int modifyDamage(int damage) {
		return 1;
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		if(Rnd.get(0, 100) < 50) {
			spawn(217097, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
			AI2Actions.deleteOwner(this);
		}
	}
}
