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
package ai.worlds.inggison;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;


/**
 * @author Cheatkiller
 *
 */
@AIName("titanstarturtle")
public class TitanStarturtleAI2 extends AggressiveNpcAI2 {
	
	@Override
	protected void handleDied() {
		super.handleDied();
		spawn(getOwner().getWorldId(), 700545, 338.149f, 573.55f, 460, (byte) 0, 0, 1);
		PacketSendUtility.broadcastPacket(getOwner(), new SM_SYSTEM_MESSAGE(1400486));
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		Npc windStream = getPosition().getWorldMapInstance().getNpc(700545);
		if(windStream != null)
			windStream.getController().delete();
	}
}
