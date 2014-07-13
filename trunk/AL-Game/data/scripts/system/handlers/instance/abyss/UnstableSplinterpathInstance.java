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
package instance.abyss;

import java.util.List;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;


/**
 * @author zhkchi
 * @reworked vlog, Luzien
 * @edit Cheatkiller
 */
@InstanceID(300600000)
public class UnstableSplinterpathInstance extends GeneralInstanceHandler {

	private int destroyedFragments;
	private int killedPazuzuWorms = 0;
	private boolean bossSpawned = false;

	@Override
	public void onDie(Npc npc) {
		final int npcId = npc.getNpcId();
		switch (npcId) {
			case 219942: // Pazuzu the Life Current
				spawnPazuzuHugeAetherFragment();
				spawnPazuzuGenesisTreasureBoxes();
				spawnPazuzuAbyssalTreasureBox();
				spawnPazuzusTreasureBox();
				break;
			case 219941: // Kaluva the Fourth Fragment
				spawnKaluvaHugeAetherFragment();
				spawnKaluvaGenesisTreasureBoxes();
				spawnKaluvaAbyssalTreasureBox();
				break;
			case 219939: //rukril 
			case 219940: //ebonsoul
				if (getNpc(npcId == 219940 ? 219939 : 219940) == null) {
					spawnDayshadeAetherFragment();
					spawnDayshadeGenesisTreasureBoxes();
					spawnDayshadeAbyssalTreasureChest();
				}
				else {
					sendMsg(npcId == 219939 ? 1400634 : 1400635); //Defeat Rukril/Ebonsoul in 1 min!
					ThreadPoolManager.getInstance().schedule(new Runnable() {

						@Override
						public void run() {

							if (getNpc(npcId == 219940 ? 219939 : 219940) != null) {
								switch (npcId) {
									case 219939:
										spawn(219940, 447.1937f, 683.72217f, 433.1805f, (byte) 108); // rukril
										break;
									case 219940:
										spawn(219939, 455.5502f, 702.09485f, 433.13727f, (byte) 108); // ebonsoul
										break;
								}
							}
						}

					}, 60000);
				}
				npc.getController().onDelete();
				break;
			case 284022:
				Npc ebonsoul = getNpc(219940);
				if (ebonsoul != null && !ebonsoul.getLifeStats().isAlreadyDead()) {
					if (MathUtil.isIn3dRange(npc, ebonsoul, 5)) {
						ebonsoul.getEffectController().removeEffect(19159);
						deleteNpcs(instance.getNpcs(281907));
						break;
					}
				}
				npc.getController().onDelete();
				break;
			case 284023:
				Npc rukril = getNpc(219939);
				if (rukril != null && !rukril.getLifeStats().isAlreadyDead()) {
					if (MathUtil.isIn3dRange(npc, rukril, 5)) {
						rukril.getEffectController().removeEffect(19266);
						deleteNpcs(instance.getNpcs(281908));
						break;
					}
				}
				npc.getController().onDelete();
				break;
			case 219951: // unstable Yamennes Painflare
			case 219943: // strengthened Yamennes Blindsight
				spawnYamennesGenesisTreasureBoxes();
				spawnYamennesAbyssalTreasureBox(npcId == 219951 ? 701579 : 701580);
				deleteNpcs(instance.getNpcs(219974));
				spawn(730317, 328.476f, 762.585f, 197.479f, (byte) 90); //Exit
				break;
			case 701588: // HugeAetherFragment
				destroyedFragments++;
				onFragmentKill();
				npc.getController().onDelete();
				break;

			case 284024:
				if (++killedPazuzuWorms == 4) {
					killedPazuzuWorms = 0;
					Npc pazuzu = getNpc(219942);
					if (pazuzu != null && !pazuzu.getLifeStats().isAlreadyDead()) {
						pazuzu.getEffectController().removeEffect(19145);
						pazuzu.getEffectController().removeEffect(19291);
					}
				}
				npc.getController().onDelete();
				break;
			case 219955:
			case 219967:
			case 219968:// Spawn Gate
				removeSummoned();
				npc.getController().onDelete();
				break;
		}
	}

	private boolean isSpawned(int npcId) {
		return !instance.getNpcs(npcId).isEmpty();
	}

	@Override
	public void onInstanceDestroy() {
		destroyedFragments = 0;
	}

	@Override
	public void handleUseItemFinish(Player player, Npc npc) {
		if (npc.getNpcId() == 700957) {
			QuestEnv env = new QuestEnv(npc, player, 0, 0);
			QuestEngine.getInstance().onDialog(env);
			if (!isSpawned(219951) && !isSpawned(219943) && !bossSpawned) { // No bosses spawned
				if (!isSpawned(700957) && destroyedFragments == 3) { // No Huge Aether Fragments spawned (all destroyed)
					sendMsg(1400732);
					spawn(219943, 329.70886f, 733.8744f, 197.60938f, (byte) 0);
				}
				else {
					sendMsg(1400731);
					spawn(219951, 329.70886f, 733.8744f, 197.60938f, (byte) 0);
				}
				bossSpawned = true;
			}
		}
	}

	@Override
	public boolean onDie(final Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0
			: lastAttacker.getObjectId()), true);

		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}

	private void spawnPazuzuHugeAetherFragment() {
		spawn(701588, 669.576f, 335.135f, 465.895f, (byte) 0);
	}

	private void spawnPazuzuGenesisTreasureBoxes() {
		spawn(701576, 651.53204f, 357.085f, 466.8837f, (byte) 66);
		spawn(701576, 647.00446f, 357.2484f, 466.14117f, (byte) 0);
		spawn(701576, 653.8384f, 360.39508f, 466.8837f, (byte) 100);
	}

	private void spawnPazuzuAbyssalTreasureBox() {
		spawn(701575, 649.24286f, 361.33755f, 467.89145f, (byte) 33);
	}

	private void spawnPazuzusTreasureBox() {
		if (Rnd.get(0, 100) >= 80) { // 20% chance, not retail
			spawn(700861, 649.243f, 362.338f, 466.0451f, (byte) 0);
		}
	}

	private void spawnKaluvaHugeAetherFragment() {
		spawn(701588, 633.7498f, 557.8822f, 424.99347f, (byte) 6);
	}

	private void spawnKaluvaGenesisTreasureBoxes() {
		spawn(701576, 601.2931f, 584.66705f, 424.2829f, (byte) 6);
		spawn(701576, 597.2156f, 583.95416f, 424.2829f, (byte) 66);
		spawn(701576, 602.9586f, 589.2678f, 424.2829f, (byte) 100);
	}

	private void spawnKaluvaAbyssalTreasureBox() {
		spawn(701577, 598.82776f, 588.25946f, 424.29065f, (byte) 113);
	}

	private void spawnDayshadeAetherFragment() {
		spawn(701588, 452.89706f, 692.36084f, 433.96838f, (byte) 6);
	}

	private void spawnDayshadeGenesisTreasureBoxes() {
		spawn(701576, 408.10938f, 650.9015f, 439.28332f, (byte) 66);
		spawn(701576, 402.40375f, 655.55237f, 439.26288f, (byte) 33);
		spawn(701576, 406.74445f, 655.5914f, 439.2548f, (byte) 100);
	}

	private void spawnDayshadeAbyssalTreasureChest() {
		sendMsg(1400636); //A Treasure Box Appeared
		spawn(701578, 404.891f, 650.2943f, 439.2548f, (byte) 130);
	}

	private void spawnYamennesGenesisTreasureBoxes() {
		spawn(701576, 326.978f, 729.8414f, 198.46796f, (byte) 16);
		spawn(701576, 326.5296f, 735.13324f, 198.46796f, (byte) 66);
		spawn(701576, 329.8462f, 738.41095f, 198.46796f, (byte) 3);
	}

	private void spawnYamennesAbyssalTreasureBox(int npcId) {
		spawn(npcId, 330.891f, 733.2943f, 198.55286f, (byte) 113);
	}

	private void deleteNpcs(List<Npc> npcs) {
		for (Npc npc : npcs) {
			if (npc != null) {
				npc.getController().onDelete();
			}
		}
	}

	private void removeSummoned(){
		Npc gate1 = getNpc(219955);
		Npc gate2 = getNpc(219967);
		Npc gate3 = getNpc(219968);
		if((gate1 == null || gate1.getLifeStats().isAlreadyDead())
			&& (gate2 == null || gate2.getLifeStats().isAlreadyDead()) 
			&& (gate3 == null || gate3.getLifeStats().isAlreadyDead())){
			deleteNpcs(instance.getNpcs(219953));// Summoned Orkanimum
			deleteNpcs(instance.getNpcs(219954));// Summoned Lapilima
		}
	}

	private void onFragmentKill() {
		switch (destroyedFragments) {
			case 1:
				// The destruction of the Huge Aether Fragment has destabilized the artifact!
				sendMsg(1400689);
				break;
			case 2:
				// The destruction of the Huge Aether Fragment has put the artifact protector on alert!
				sendMsg(1400690);
				break;
			case 3:
				// The destruction of the Huge Aether Fragment has caused abnormality on the artifact. The artifact protector is
				// furious!
				sendMsg(1400691);
				break;
		}
	}
}
