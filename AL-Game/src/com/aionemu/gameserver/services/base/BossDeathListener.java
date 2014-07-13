/*
 * This file is part of archeage-emu <http://archeage-emu.com/>.
 * 
 * archeage-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * archeage-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with archeage-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services.base;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.BaseService;

/**
 *
 * @author Source
 */
@SuppressWarnings("rawtypes")
public class BossDeathListener extends OnDieEventCallback {

	private final Base<?> base;

	public BossDeathListener(Base base) {
		this.base = base;
	}

	@Override
	public void onBeforeDie(AbstractAI obj) {
		Creature killer = (Creature) base.getBoss().getAggroList().getMostDamage();

		if (killer.getRace().isPlayerRace())
			base.setRace(killer.getRace());
		else
			base.setRace(Race.NPC);

		BaseService.getInstance().capture(base.getId(), base.getRace());
	}

	@Override
	public void onAfterDie(AbstractAI obj) {
	}

}