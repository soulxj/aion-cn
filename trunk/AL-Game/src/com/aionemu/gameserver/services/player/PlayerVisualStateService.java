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
package com.aionemu.gameserver.services.player;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
public class PlayerVisualStateService {

	public static void hideValidate(final Player hiden) {
		hiden.getKnownList().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player observer) {
				boolean canSee = observer.canSee(hiden);
				boolean isSee = observer.isSeePlayer(hiden);

				if (canSee && !isSee)
					observer.getKnownList().addVisualObject(hiden);
				else if (!canSee && isSee)
					observer.getKnownList().delVisualObject(hiden, false);
			}

		});
	}

	public static void seeValidate(final Player search) {
		search.getKnownList().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player hide) {
				boolean canSee = search.canSee(hide);
				boolean isSee = search.isSeePlayer(hide);

				if (canSee && !isSee)
					search.getKnownList().addVisualObject(hide);
				else if (!canSee && isSee)
					search.getKnownList().delVisualObject(hide, false);
			}

		});
	}

}