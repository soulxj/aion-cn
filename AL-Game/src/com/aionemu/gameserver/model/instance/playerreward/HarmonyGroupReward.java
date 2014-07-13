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
package com.aionemu.gameserver.model.instance.playerreward;

import com.aionemu.gameserver.model.autogroup.AGPlayer;
import java.util.List;

/**
 *
 * @author xTz
 */
public class HarmonyGroupReward extends PvPArenaPlayerReward {

	private List<AGPlayer> players;
	public HarmonyGroupReward(Integer object, int timeBonus, byte buffId, List<AGPlayer> players) {
		super(object, timeBonus, buffId);
		this.players = players;
	}

	public List<AGPlayer> getAGPlayers() {
		return players;
	}

	public boolean containPlayer(Integer object) {
		for (AGPlayer agp : players) {
			if (agp.getObjectId().equals(object)) {
				return true;
			}
		}
		return false;
	}

	public AGPlayer getAGPlayer(Integer object) {
		for (AGPlayer agp : players) {
			if (agp.getObjectId().equals(object)) {
				return agp;
			}
		}
		return null;
	}
}
