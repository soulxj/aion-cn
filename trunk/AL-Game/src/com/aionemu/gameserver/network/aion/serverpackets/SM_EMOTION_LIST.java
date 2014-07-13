/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;


import java.util.Collection;

import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.model.gameobjects.player.emotion.Emotion;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class SM_EMOTION_LIST extends AionServerPacket {

	byte action;
	Collection<Emotion> emotions;

	/**
	 * @param action
	 * @ Editor: eXs Emu 4.3 -Reverse-
	 */
	public SM_EMOTION_LIST(byte action, Collection<Emotion> emotions) {
		this.action = action;
		this.emotions = emotions;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeC(action);
		if (con.getActivePlayer().havePermission(MembershipConfig.EMOTIONS_ALL)) {
			writeH(76); // 4.5 writeH(85);
			for (int i = 0; i < 69; i++) { // 4.5 for (int i = 0; i < 85; i++)
				writeH(76 + i);
				writeD(0x00);
			}
		}
		else if (emotions == null || emotions.isEmpty()) {
			writeH(0);
		}
		else {
			writeH(emotions.size());
			for (Emotion emotion : emotions) {
				writeH(emotion.getId());
				writeD(emotion.getRemainingTime());//remaining time
			}
		}
	}
}
