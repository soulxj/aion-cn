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
package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.HouseBidEntry;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_BIDS;
import com.aionemu.gameserver.services.HousingBidService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.collections.ListSplitter;

/**
 * @author Rolandas
 */
public class CM_GET_HOUSE_BIDS extends AionClientPacket {

	public CM_GET_HOUSE_BIDS(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		// No data
	}

	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		HouseBidEntry playerBid = HousingBidService.getInstance().getLastPlayerBid(player.getObjectId());

		// TODO: [RR] check player bids placement in sniffs, it's just a guess ;)
		List<HouseBidEntry> houseBids = HousingBidService.getInstance().getHouseBidEntries(player.getRace());
		ListSplitter<HouseBidEntry> splitter = new ListSplitter<HouseBidEntry>(houseBids, 181);
		while (!splitter.isLast()) {
			List<HouseBidEntry> packetBids = splitter.getNext();
			HouseBidEntry playerData = splitter.isLast() ? playerBid : null;
			PacketSendUtility.sendPacket(player, new SM_HOUSE_BIDS(splitter.isFirst(), splitter.isLast(), playerData, packetBids));
		}
	}

}
