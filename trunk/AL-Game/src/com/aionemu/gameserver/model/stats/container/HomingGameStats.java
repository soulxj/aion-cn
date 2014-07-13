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
package com.aionemu.gameserver.model.stats.container;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Homing;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;


/**
 * @author Cheatkiller
 *
 */
public class HomingGameStats extends SummonedObjectGameStats {

	public HomingGameStats(Npc owner) {
		super(owner);
	}
	
	
	@Override
	public Stat2 getStat(StatEnum statEnum, int base) {
		Stat2 stat = super.getStat(statEnum, base);
		if (owner.getMaster() == null)
			return stat;
		switch (statEnum) {
			case MAGICAL_ATTACK:
				stat.setBonusRate(0.2f);
				return owner.getMaster().getGameStats().getItemStatBoost(statEnum, stat);

		}
		return stat;
	}

	@Override
	public Stat2 getMAttack() {
		Homing homing = (Homing) owner;
		int power = homing.getObjectTemplate().getStatsTemplate().getPower();
		SkillTemplate skill = DataManager.SKILL_DATA.getSkillTemplate(homing.getSkillId());
		int skillLvl = skill.getLvl();
		switch (skillLvl) {
			case 3:
				if(homing.getName().equals("stone energy"))
					power = 316;
				if(homing.getName().equals("water energy"))
					power = 362;
				break;
			case 4:
				if(homing.getName().equals("cyclone servant"))
					power = 1166;
				if(homing.getName().equals("fire energy"))
					power = 313;
				if(homing.getName().equals("wind servant"))
					power = 373;
				break;
			case 5:
				if(homing.getName().equals("cyclone servant"))
					power = 1221;
				break;
			case 6:
				if(homing.getName().equals("cyclone servant"))
					power = 1283;
				break;
		}
		return getStat(StatEnum.MAGICAL_ATTACK, power);
	}

}