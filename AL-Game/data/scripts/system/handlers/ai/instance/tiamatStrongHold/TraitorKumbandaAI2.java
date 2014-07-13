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
package ai.instance.tiamatStrongHold;

import java.util.List;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.WorldMapInstance;


/**
 * @author Cheatkiller
 *
 */
@AIName("traitorkumbanda")
public class TraitorKumbandaAI2 extends AggressiveNpcAI2 {

	private boolean isFinalBuff;
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (Rnd.get(1, 100) < 5) {
			spawnTimeAccelerator();
			spawnKumbandaGhost();
		}
		if(!isFinalBuff && getOwner().getLifeStats().getHpPercentage() <= 5) {
			isFinalBuff = true;
			AI2Actions.useSkill(this, 20942);
		}
	}

	private void spawnTimeAccelerator() {
		if (getPosition().getWorldMapInstance().getNpc(283221) == null) {
			SkillEngine.getInstance().getSkill(getOwner(), 20726, 55, getOwner()).useNoAnimationSkill();
			spawn(283221, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
			rndSpawn(283221, 6);
		}
	}
	
	private void spawnKumbandaGhost() {
		if (getPosition().getWorldMapInstance().getNpc(283220) == null && getOwner().getLifeStats().getHpPercentage() <= 50) {
			spawn(283220, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
		}
	}
	
	private void deleteNpcs(List<Npc> npcs) {
		for (Npc npc : npcs) {
			if (npc != null) {
				npc.getController().onDelete();
			}
		}
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		WorldMapInstance instance = getPosition().getWorldMapInstance();
		deleteNpcs(instance.getNpcs(283221));
		deleteNpcs(instance.getNpcs(283223));
	}
	
	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		isFinalBuff = false;
	}
	
	private void rndSpawn(int npcId, int count) {
		for (int i = 0; i < count; i++) {
			SpawnTemplate template = rndSpawnInRange(npcId, Rnd.get(10, 20));
			SpawnEngine.spawnObject(template, getPosition().getInstanceId());
		}
	}
	
	private SpawnTemplate rndSpawnInRange(int npcId, int dist) {
		float direction = Rnd.get(0, 199) / 100f;
		float x1 = (float) (Math.cos(Math.PI * direction) * dist);
		float y1 = (float) (Math.sin(Math.PI * direction) * dist);
		return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x1, getPosition().getY()
				+ y1, getPosition().getZ(), getPosition().getHeading());
	}
}
