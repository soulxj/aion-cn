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
package com.aionemu.gameserver.skillengine.model;

import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeSkill", propOrder = { "skills" })
public class ChargeSkillEntry {

	@XmlElement(name = "skill", required = true)
	protected List<ChargedSkill> skills;

	@XmlAttribute(required = true)
	protected int id;

	@XmlAttribute(name = "min_time", required = true)
	protected int minTime;

	public List<ChargedSkill> getSkills() {
		return skills;
	}

	/**
	 * Gets the value of the minTime property.
	 */
	public int getMinTime() {
		return minTime;
	}

	/**
	 * Gets the value of the id property.
	 */
	public int getId() {
		return id;
	}
}
