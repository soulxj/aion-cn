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
package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.LeftHandSlot;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;


/**
 * @author Cheatkiller
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LeftHandCondition")
public class LeftHandCondition extends Condition {
	
	@XmlAttribute(name = "type")
	private LeftHandSlot type;


	@Override
	public boolean validate(Skill env) {
		if (env.getEffector() instanceof Player) {
			Player player = (Player) env.getEffector();
			switch (type) {
				case DUAL: {
					if (player.getEquipment().getOffHandWeapon() != null && player.getEquipment().getOffHandWeapon().getItemTemplate().isWeapon())
						return true;
					else {
						PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_NEED_DUAL_WEAPON);
						return false;
					}
				}
				case SHIELD: {
					if (player.getEquipment().isShieldEquipped())
						return true;
					else {
						PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_NEED_SHIELD);
						return false;
					}
				}
			}
		}
		return false;
	}
}