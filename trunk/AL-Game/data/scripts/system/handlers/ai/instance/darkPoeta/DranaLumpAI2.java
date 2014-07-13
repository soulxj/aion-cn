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
package ai.instance.darkPoeta;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;


/**
 * @author Ritsu
 *
 */

@AIName("dranalump")
public class DranaLumpAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleUseItemFinish(Player player)
	{
		//final Creature effected = getOwner().getEffected();
		//effected.getController().cancelCurrentSkill();
	//	effected.getMoveController().abortMove();
	//	effect.setAbnormal(AbnormalState.PARALYZE.getId());
		int skillId = 0;
		int level = 0;
		switch (getNpcId()) {
			case 281178: //Drana Lump.
				skillId = 18536; //Drana Break.
				level = 46;
				break;
		}
		SkillEngine.getInstance().getSkill(player, skillId, level, player).useSkill();
		getOwner().getController().onDelete();
	}
}
