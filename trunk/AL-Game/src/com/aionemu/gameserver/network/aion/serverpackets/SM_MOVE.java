/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * aion-emu is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * aion-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.controllers.movement.MoveController;
import com.aionemu.gameserver.controllers.movement.MovementMask;
import com.aionemu.gameserver.controllers.movement.PlayableMoveController;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * This packet is displaying movement of players etc.
 *
 * @author -Nemesiss-
 */
public class SM_MOVE extends AionServerPacket {

	/**
	 * Object that is moving.
	 */
	private Creature creature;

	public SM_MOVE(Creature creature) {
		this.creature = creature;
	}

	@Override
	protected void writeImpl(AionConnection client) {
		MoveController moveData = creature.getMoveController();
		writeD(creature.getObjectId());
		writeF(creature.getX());
		writeF(creature.getY());
		writeF(creature.getZ());
		writeC(creature.getHeading());

		writeC(moveData.getMovementMask());

		if (moveData instanceof PlayableMoveController) {
			PlayableMoveController<?> playermoveData = (PlayableMoveController<?>) moveData;
			if ((moveData.getMovementMask() & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
				if ((moveData.getMovementMask() & MovementMask.MOUSE) == 0) {
					writeF(playermoveData.vectorX);
					writeF(playermoveData.vectorY);
					writeF(playermoveData.vectorZ);
				}
				else {
					writeF(moveData.getTargetX2());
					writeF(moveData.getTargetY2());
					writeF(moveData.getTargetZ2());
				}
			}
			if ((moveData.getMovementMask() & MovementMask.GLIDE) == MovementMask.GLIDE) {
				writeC(playermoveData.glideFlag);
			}
			if ((moveData.getMovementMask() & MovementMask.VEHICLE) == MovementMask.VEHICLE) {
				writeD(playermoveData.unk1);
				writeD(playermoveData.unk2);
				writeF(playermoveData.vectorX);
				writeF(playermoveData.vectorY);
				writeF(playermoveData.vectorZ);
			}
		}
		else {
			if ((moveData.getMovementMask() & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
				writeF(moveData.getTargetX2());
				writeF(moveData.getTargetY2());
				writeF(moveData.getTargetZ2());
			}
		}
	}

}