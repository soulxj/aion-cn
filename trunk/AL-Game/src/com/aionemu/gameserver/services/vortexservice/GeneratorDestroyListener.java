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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services.vortexservice;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.aionemu.gameserver.services.VortexService;

/**
 * @author Source
 */
@SuppressWarnings("rawtypes")
public class GeneratorDestroyListener extends OnDieEventCallback {

	private final DimensionalVortex<?> vortex;

	public GeneratorDestroyListener(DimensionalVortex vortex) {
		this.vortex = vortex;
	}

	@Override
	public void onBeforeDie(AbstractAI obj) {
	}

	@Override
	public void onAfterDie(AbstractAI obj) {
		vortex.setGeneratorDestroyed(true);
		VortexService.getInstance().stopInvasion(vortex.getVortexLocationId());
	}

}