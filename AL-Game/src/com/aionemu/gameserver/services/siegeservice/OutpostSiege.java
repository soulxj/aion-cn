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
package com.aionemu.gameserver.services.siegeservice;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.OutpostLocation;
import com.aionemu.gameserver.model.siege.SiegeModType;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.player.PlayerService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import java.util.Map;

/**
 * @author SoulKeeper
 */
public class OutpostSiege extends Siege<OutpostLocation> {

	public OutpostSiege(OutpostLocation siegeLocation) {
		super(siegeLocation);
	}

	@Override
	protected void onSiegeStart() {
		SiegeService.getInstance().deSpawnNpcs(getSiegeLocationId());

		getSiegeLocation().setVulnerable(true);

		SiegeService.getInstance().spawnNpcs(getSiegeLocationId(), getSiegeLocation().getRace(), SiegeModType.SIEGE);
		initSiegeBoss();

		// TODO: Refactor me
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {

			@Override
			public void visit(Player player) {
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(getSiegeLocationId() == 2111 ? 1400317 : 1400318));
			}

		});

		broadcastUpdate(getSiegeLocation());
	}

	@Override
	protected void onSiegeFinish() {
		getSiegeLocation().setVulnerable(false);
		unregisterSiegeBossListeners();

		// TODO: Refactor messages
		if (isBossKilled())
			onCapture();
		else {
			World.getInstance().doOnAllPlayers(new Visitor<Player>() {

				@Override
				public void visit(Player player) {
					PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(getSiegeLocationId() == 2111 ? 1400319 : 1400320));
				}

			});
		}

		broadcastUpdate(getSiegeLocation());
	}

	private void onCapture() {
		SiegeRaceCounter winnerCounter = getSiegeCounter().getWinnerRaceCounter();
		Map<Integer, Long> topPlayerDamages = winnerCounter.getPlayerDamageCounter();
		if (!topPlayerDamages.isEmpty()) {

			// prepare top player
			Integer topPlayer = topPlayerDamages.keySet().iterator().next();
			final String topPlayerName = PlayerService.getPlayerName(topPlayer);
			// Prepare message for sending to all players
			int messageId = getSiegeLocationId() == 2111 ? 1400324 : 1400323;
			final Race race = winnerCounter.getSiegeRace() == SiegeRace.ELYOS ? Race.ELYOS : Race.ASMODIANS;
			final AionServerPacket asp = new SM_SYSTEM_MESSAGE(messageId, race, topPlayerName);

			// send packet for all players
			World.getInstance().doOnAllPlayers(new Visitor<Player>() {

				@Override
				public void visit(Player player) {
					PacketSendUtility.sendPacket(player, asp);
					if (player.getRace().equals(race)) {
						SkillEngine.getInstance().applyEffectDirectly(race == Race.ELYOS ? 12120 : 12119, player, player, 0);
					}
				}

			});
		}
	}

	@Override
	public boolean isEndless() {
		return false;
	}

	@Override
	public void addAbyssPoints(Player player, int abysPoints) {
		// No need to control AP
	}

}