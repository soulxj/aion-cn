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
package instance.abyss;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.flyring.FlyRing;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.flyring.FlyRingTemplate;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.List;

/**
 * @author xTz
 */
@InstanceID(300080000)
public class LeftWingChamberInstance extends GeneralInstanceHandler {

	private boolean isStartTimer = false;
	private long startTime;
	private boolean isInstanceDestroyed = false;
	private Race instanceRace;

	@Override
	public void onInstanceCreate(WorldMapInstance instance) {
		super.onInstanceCreate(instance);
		spawnRings();
		spawnGoldChest();
		//spawn Treasurer Nabatma (pool=1)
		switch ((int)Rnd.get(1, 2)) {
			case 1:
				spawn(215424, 502.1326f, 502.89673f, 352.94437f, (byte) 20);
				break;
			case 2:
				spawn(215424, 508.71814f, 660.93994f, 352.94638f, (byte) 60);
				break;
		}
	}

	private void spawnRings() {
		FlyRing f1 = new FlyRing(new FlyRingTemplate("LEFT_WING_1", mapId,
				new Point3D(576.2102, 585.4146, 353.90677),
				new Point3D(576.2102, 585.4146, 359.90677),
				new Point3D(575.18384, 596.36664, 353.90677), 10), instanceId);
		f1.spawn();
	}

	@Override
	public boolean onPassFlyingRing(Player player, String flyingRing) {
		if (flyingRing.equals("LEFT_WING_1")) {
			if (!isStartTimer) {
				PacketSendUtility.sendPacket(player, STR_MSG_INSTANCE_START_IDABRE);
				isStartTimer = true;
				startTime = System.currentTimeMillis();
				PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900));
				ThreadPoolManager.getInstance().schedule(new Runnable() {

					@Override
					public void run() {
						despawnNpcs(getNpcs(700466));
						despawnNpcs(getNpcs(701481));
						despawnNpcs(getNpcs(701486));
					}

				}, 900000);
			}
		}
		return false;
	}

	@Override
	public void onEnterInstance(Player player) {
		if (isStartTimer) {
			long time = System.currentTimeMillis() - startTime;
			if (time < 900000) {
				PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900 - (int) time / 1000));
			}
		}

	}

	private List<Npc> getNpcs(int npcId) {
		if (!isInstanceDestroyed) {
			return instance.getNpcs(npcId);
		}
		return null;
	}

	private void despawnNpcs(List<Npc> npcs) {
		for (Npc npc : npcs) {
			npc.getController().onDelete();
		}
	}
	
	private void spawnGoldChest() {
		final int chestId = instanceRace == Race.ELYOS ? 701481 : 701486;
		spawn(chestId, 496.87f, 664.07f, 352.94f, (byte) 90);
	}

	@Override
	public void onInstanceDestroy() {
		isInstanceDestroyed = true;
	}
}
