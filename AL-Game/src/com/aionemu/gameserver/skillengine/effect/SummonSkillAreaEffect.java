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
package com.aionemu.gameserver.skillengine.effect;

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.NpcObjectType;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonSkillAreaEffect")
public class SummonSkillAreaEffect extends SummonServantEffect {

	@Override
	public void applyEffect(Effect effect) {
		// should only be set if player has no target to avoid errors
		if (effect.getEffector().getTarget() == null)
			effect.getEffector().setTarget(effect.getEffector());
		float x = effect.getX();
		float y = effect.getY();
		float z = effect.getZ();
		if(x == 0 && y == 0){
			Creature effected = effect.getEffected();
			x = effected.getX();
			y = effected.getY();
			z = effected.getZ();
		}
		// fix for summon whirlwind
		// TODO revisit later and find better fix - kecimis
		int useTime = time;
		switch (effect.getSkillId()) {
			case 2291:
			case 2292:
			case 2293:
			case 2294:
				useTime = 7;
				break;
		}
		
		final Servant servant = spawnServant(effect, useTime, NpcObjectType.SKILLAREA, x, y, z);
		Future<?> task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				servant.getController().useSkill(skillId);
			}
		}, 0, 3000);
		servant.getController().addTask(TaskId.SKILL_USE, task);
	}
}
