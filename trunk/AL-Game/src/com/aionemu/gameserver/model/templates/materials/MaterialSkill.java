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
package com.aionemu.gameserver.model.templates.materials;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaterialSkill")
public class MaterialSkill {

	@XmlAttribute
	protected MaterialActTime time;

	@XmlAttribute(required = true)
	protected float frequency;

	@XmlAttribute
	protected MaterialTarget target;

	@XmlAttribute(required = true)
	protected int level;

	@XmlAttribute(required = true)
	protected int id;

	public MaterialActTime getTime() {
		return time;
	}

	public float getFrequency() {
		return frequency;
	}

	public MaterialTarget getTarget() {
		if (target == null)
			return MaterialTarget.ALL;
		return target;
	}

	public int getSkillLevel() {
		return level;
	}

	public int getId() {
		return id;
	}

}
