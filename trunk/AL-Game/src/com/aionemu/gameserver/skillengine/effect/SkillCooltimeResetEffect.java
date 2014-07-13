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
package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rolandas, Luzien
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillCooltimeResetEffect")
public class SkillCooltimeResetEffect extends EffectTemplate {

	@XmlAttribute(name = "first_cd", required = true)
	protected int firstCd;

	@XmlAttribute(name = "last_cd", required = true)
	protected int lastCd;

	@Override
	public void applyEffect(Effect effect) {
	   Creature effector = effect.getEffector();
	   for (int i = firstCd; i <= lastCd; i++) {
		  long delay = effector.getSkillCoolDown(i) - System.currentTimeMillis();
		  if (delay <= 0)
			 continue;
		  if (delta > 0) //TODO: Percent of remaining CD or original cd?
			 delay -= delay * (delta/100);
		  else
			 delay -= value;
		  
		  effector.setSkillCoolDown(i, delay + System.currentTimeMillis());
	   }
	   if (effector instanceof Player)
		 PacketSendUtility.sendPacket( (Player) effector, new SM_SKILL_COOLDOWN(effector.getSkillCoolDowns()));
	}

}
