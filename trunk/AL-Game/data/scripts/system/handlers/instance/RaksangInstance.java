/*
 * This file is part of aion-lightning <aion-lightning.org>
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package instance;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.Map;

/**
 * @author xTz
 */
@InstanceID(300310000)
public class RaksangInstance extends GeneralInstanceHandler {

	private Map<Integer, StaticDoor> doors;
	private int generatorKilled;
	private int ashulagenKilled;
	private int gargoyleKilled;
	private int rakshaHelpersKilled;
	private boolean isInstanceDestroyed;

	@Override
	public void onDie(Npc npc) {
		switch (npc.getNpcId()) {
			case 730453:
			case 730454:
			case 730455:
			case 730456:
				generatorKilled ++;
				if (generatorKilled == 1) {
					sendMsg(1401133);
					doors.get(87).setOpen(true);
				}
				else if (generatorKilled == 2) {
					sendMsg(1401133);
					doors.get(167).setOpen(true);
				}
				else if (generatorKilled == 3) {
					sendMsg(1401133);
					doors.get(114).setOpen(true);
				}
				else if (generatorKilled == 4) {
					sendMsg(1401134);
					doors.get(165).setOpen(true);
				}
				despawnNpc(npc);
				break;
			case 217399:
			case 217400:
				isDeadKerops();
				break;
			case 217392:
				doors.get(103).setOpen(true);
				break;
			case 217469:
				doors.get(107).setOpen(true);
				break;
			case 217471:
			case 217472:
				gargoyleKilled++;
				if (gargoyleKilled == 2) {
					Npc magic = instance.getNpc(217473);
					if (magic != null) {
						sendMsg(1401159);
						magic.getEffectController().removeEffect(19126);
					}
				}
				despawnNpc(npc);
				break;
			case 217473:
				despawnNpc(npc);
				final Npc dust = (Npc) spawn(701075, 1068.630f, 967.205f, 138.785f, (byte) 0, 323);
				doors.get(105).setOpen(true);
				ThreadPoolManager.getInstance().schedule(new Runnable() {

					@Override
					public void run() {
						if (!isInstanceDestroyed && dust != null && !NpcActions.isAlreadyDead(dust)) {
							NpcActions.delete(dust);
						}
					}

				}, 4000);
				break;
			case 217455:
				ashulagenKilled ++;
				if (ashulagenKilled == 1 || ashulagenKilled == 2 || ashulagenKilled == 3) {
					sendMsg(1401160);
				}
				else if (ashulagenKilled == 4) {
					spawn(217456, 615.081f, 640.660f, 524.195f, (byte) 0);
					sendMsg(1401135);
				}
				break;
			case 217425:
			case 217451:
			case 217456:
				rakshaHelpersKilled ++;
				if (rakshaHelpersKilled < 3) {
					sendMsg(1401161);
				}
				else if (rakshaHelpersKilled == 3) {
					sendMsg(1401162);
				}
				break;
			case 217647:
			case 217475:
				rakshaHelpersKilled = 4;
				break;
		}
	}

	@Override
	public void onInstanceDestroy() {
		isInstanceDestroyed = true;
		doors.clear();
	}

	@Override
	public void onInstanceCreate(WorldMapInstance instance) {
		super.onInstanceCreate(instance);
		doors = instance.getDoors();
		Npc melkennis = getNpc(217392);
		SkillEngine.getInstance().getSkill(melkennis, 19126, 60, melkennis).useNoAnimationSkill();
	}

	private void despawnNpc(Npc npc) {
		if (npc != null) {
			npc.getController().onDelete();
		}
	}
	
	private boolean isDeadKerops() {
		Npc kerop1 = getNpc(217399);
		Npc kerop2 = getNpc(217400);
		if (isDead(kerop1) && isDead(kerop2)) {
			Npc melkennis = getNpc(217392);
			if (melkennis != null)
				melkennis.getEffectController().removeEffect(19126);
			return true;
		}
		return false;
	}
	
	private boolean isDead(Npc npc) {
		return (npc == null || npc.getLifeStats().isAlreadyDead());
	}

	@Override
	public boolean onDie(final Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0
				: lastAttacker.getObjectId()), true);

		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}

}