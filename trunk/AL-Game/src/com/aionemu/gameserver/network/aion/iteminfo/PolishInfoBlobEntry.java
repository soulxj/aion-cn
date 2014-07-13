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
package com.aionemu.gameserver.network.aion.iteminfo;

import com.aionemu.gameserver.model.items.IdianStone;
import java.nio.ByteBuffer;

import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * @author Rolandas
 */
public class PolishInfoBlobEntry extends ItemBlobEntry {

	PolishInfoBlobEntry() {
		super(ItemBlobType.POLISH_INFO);
	}

	@Override
	public void writeThisBlob(ByteBuffer buf) {
		// Idian charge value
		IdianStone stone = ownerItem.getIdianStone();
		writeD(buf, stone == null ? 0 : stone.getPolishCharge());
	}

	@Override
	public int getSize() {
		return 4;
	}

}
