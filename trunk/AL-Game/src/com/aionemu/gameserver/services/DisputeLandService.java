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
package com.aionemu.gameserver.services;

import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DISPUTE_LAND;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.world.zone.ZoneAttributes;
import javolution.util.FastList;

/**
 * @author Source
 */
public class DisputeLandService {

	private boolean active;
	private FastList<Integer> worlds = new FastList<Integer>();
	private static final int chance = CustomConfig.DISPUTE_RND_CHANCE;
	private static final String rnd = CustomConfig.DISPUTE_RND_SCHEDULE;
	private static final String fxd = CustomConfig.DISPUTE_FXD_SCHEDULE;

	private DisputeLandService() {
	}

	public static DisputeLandService getInstance() {
		return DisputeLandServiceHolder.INSTANCE;
	}

	public void init() {
		if (!CustomConfig.DISPUTE_ENABLED) {
			return;
		}

		// Dispute worldId's
		worlds.add(600020000);
		worlds.add(600020001);
		worlds.add(600030000);

		CronService.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				setActive(chance > Rnd.get(100));

				if (isActive()) {
					// Disable after 30 mins
					ThreadPoolManager.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							setActive(false);
						}

					}, 1800 * 1000);
				}
			}

		}, rnd);

		CronService.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// Disable after 5 hours
				setActive(true);

				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						setActive(false);
					}

				}, 5 * 3600 * 1000);
			}

		}, fxd);
	}

	public boolean isActive() {
		if (!CustomConfig.DISPUTE_ENABLED) {
			return false;
		}

		return active;
	}

	public void setActive(boolean value) {
		active = value;
		syncState();
		broadcast();
	}

	private void syncState() {
		for (int world : worlds) {
			if (world == 600020001) {
				continue;
			}

			if (active) {
				World.getInstance().getWorldMap(world).setWorldOption(ZoneAttributes.PVP_ENABLED);
			}
			else {
				World.getInstance().getWorldMap(world).removeWorldOption(ZoneAttributes.PVP_ENABLED);
			}
		}
	}

	private void broadcast(Player player) {
		PacketSendUtility.sendPacket(player, new SM_DISPUTE_LAND(worlds, active));
	}

	private void broadcast() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				broadcast(player);
			}

		});
	}

	public void onLogin(Player player) {
		if (!CustomConfig.DISPUTE_ENABLED) {
			return;
		}

		broadcast(player);
	}

	private static class DisputeLandServiceHolder {

		private static final DisputeLandService INSTANCE = new DisputeLandService();
	}

}