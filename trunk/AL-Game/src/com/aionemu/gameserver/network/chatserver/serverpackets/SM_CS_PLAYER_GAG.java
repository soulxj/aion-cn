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
package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;


/**
 * @author ViAl
 *
 */
public class SM_CS_PLAYER_GAG extends CsServerPacket {

	private int playerId;
	private long gagTime;

	public SM_CS_PLAYER_GAG(int playerId, long gagTime) {
		super(0x03);
		this.playerId = playerId;
		this.gagTime = gagTime;
	}

	@Override
	protected void writeImpl(ChatServerConnection con) {
		writeD(playerId);
		writeQ(gagTime);
	}
}
