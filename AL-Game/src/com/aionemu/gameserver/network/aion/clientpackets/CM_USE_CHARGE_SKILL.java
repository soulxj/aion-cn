/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.skillengine.model.ChargedSkill;
import com.aionemu.gameserver.skillengine.model.Skill;

/**
 * @author Cheatkiller
 */
public class CM_USE_CHARGE_SKILL extends AionClientPacket {

	public CM_USE_CHARGE_SKILL(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	
	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		Skill chargeCastingSkill = player.getCastingSkill();
		if (chargeCastingSkill == null) {
			return;
		}
		long time = System.currentTimeMillis() - chargeCastingSkill.getCastStartTime();
		int i = 0;
		for (ChargedSkill skill : chargeCastingSkill.getChargeSkillList()) {
			if (time > skill.getTime()) {
				i++;
				time -= skill.getTime();
				continue;
			}
		}
		player.getController().useChargeSkill(chargeCastingSkill.getChargeSkillList().get(i).getId(), 1);
		chargeCastingSkill.cancelCast();
		chargeCastingSkill.getChargeSkillList().clear();
	}


	@Override
	protected void readImpl() {
		
	}
}
