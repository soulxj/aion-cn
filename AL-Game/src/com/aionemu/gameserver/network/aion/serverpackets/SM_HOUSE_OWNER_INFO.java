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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.sql.Timestamp;

import com.aionemu.gameserver.model.gameobjects.player.PlayerHouseOwnerFlags;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.house.MaintenanceTask;
import com.aionemu.gameserver.model.town.Town;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.TownService;
import org.joda.time.DateTime;

/**
 * @author Rolandas
 */
public class SM_HOUSE_OWNER_INFO extends AionServerPacket {

	private Player player;
	private House activeHouse;

	public SM_HOUSE_OWNER_INFO(Player player, House activeHouse) {
		this.player = player;
		this.activeHouse = activeHouse;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		if (activeHouse == null) {
			writeD(0);
			writeD(player.isBuildingInState(PlayerHouseOwnerFlags.BUY_STUDIO_ALLOWED) ? 355000 : 0); // studio building id
		}
		else {
			writeD(activeHouse.getAddress().getId());
			writeD(activeHouse.getBuilding().getId());
		}
		writeC(player.getBuildingOwnerStates());
		int townLevel = 1;
		if (activeHouse != null && activeHouse.getAddress().getTownId() != 0) {
			Town town = TownService.getInstance().getTownById(activeHouse.getAddress().getTownId());
			townLevel = town.getLevel();
		}
		writeC(townLevel);
		// Maintenance bill weeks left ?, if 0 maintenance date is in red
		if (activeHouse == null || !activeHouse.isFeePaid()) {
			writeC(0);
		}
		else {
			Timestamp nextPay = activeHouse.getNextPay();
			float diff;
			if (nextPay == null) {
				// See MaintenanceTask.updateMaintainedHouses()
				// all just obtained houses have fee paid true and time is null;
				// means they should pay next week
				diff = MaintenanceTask.getInstance().getPeriod();
			}
			else {
				long paytime = activeHouse.getNextPay().getTime();
				diff = paytime - ((long) MaintenanceTask.getInstance().getRunTime() * 1000);
			}
			if (diff < 0) {
				writeC(0);
			}
			else {
				int weeks = (int) (Math.round(diff / MaintenanceTask.getInstance().getPeriod()));
				if (DateTime.now().getDayOfWeek() != 7) // Hack for auction Day, client counts sunday to new week
					weeks++;
				writeC(weeks);
			}
		}
		writeH(0); // unk

		// Second house info ?
		writeD(0);
		writeD(0);
		writeC(0);
		writeC(0);
		writeC(0); // 3.5
		writeH(0); // 3.5 unk

	}
}
