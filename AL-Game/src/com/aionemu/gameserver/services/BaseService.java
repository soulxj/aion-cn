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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.base.BaseLocation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
import com.aionemu.gameserver.services.base.Base;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.Map;

import javolution.util.FastMap;

/**
 *
 * @author Source
 */
public class BaseService {

	private static final Logger log = LoggerFactory.getLogger(BaseService.class);
	private final Map<Integer, Base<?>> active = new FastMap<Integer, Base<?>>().shared();
	private Map<Integer, BaseLocation> bases;

	public void initRiftLocations() {
		log.info("Initializing bases...");
		bases = DataManager.BASE_DATA.getBaseLocations();
		log.info("Loaded " + bases.size() + " bases.");
	}

	public void initBases() {
		for (BaseLocation base : getBaseLocations().values()) {
			start(base.getId());
		}
	}

	public Map<Integer, BaseLocation> getBaseLocations() {
		return bases;
	}

	public BaseLocation getBaseLocation(int id) {
		return bases.get(id);
	}

	public void start(final int id) {
		final Base<?> base;

		synchronized (this) {
			if (active.containsKey(id)) {
				return;
			}
			base = new Base<BaseLocation>(getBaseLocation(id));
			active.put(id, base);
		}

		base.start();
	}

	public void stop(int id) {
		if (!isActive(id)) {
			log.info("Trying to stop not active base:" + id);
			return;
		}

		Base<?> base;
		synchronized (this) {
			base = active.remove(id);
		}

		if (base == null || base.isFinished()) {
			log.info("Trying to stop null or finished base:" + id);
			return;
		}

		base.stop();
		start(id);
	}

	public void capture(int id, Race race) {
		if (!isActive(id)) {
			log.info("Detecting not active base capture.");
			return;
		}

		getActiveBase(id).setRace(race);
		stop(id);
		broadcastUpdate(getBaseLocation(id));
	}

	public boolean isActive(int id) {
		return active.containsKey(id);
	}

	public Base<?> getActiveBase(int id) {
		return active.get(id);
	}

	public void onEnterBaseWorld(Player player) {
		for (BaseLocation baseLocation : getBaseLocations().values()) {
			if (baseLocation.getWorldId() == player.getWorldId() && isActive(baseLocation.getId())) {
				Base<?> base = getActiveBase(baseLocation.getId());
				PacketSendUtility.sendPacket(player, new SM_NPC_INFO(base.getFlag(), player));
			}
		}
	}

	public void broadcastUpdate(final BaseLocation baseLocation) {
		World.getInstance().getWorldMap(baseLocation.getWorldId()).getMainWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (isActive(baseLocation.getId())) {
					Base<?> base = getActiveBase(baseLocation.getId());
					PacketSendUtility.sendPacket(player, new SM_NPC_INFO(base.getFlag(), player));
				}
			}

		});
	}

	public static BaseService getInstance() {
		return BaseServiceHolder.INSTANCE;
	}

	private static class BaseServiceHolder {

		private static final BaseService INSTANCE = new BaseService();
	}

}