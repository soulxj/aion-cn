/*
 * This file is part of aion-lightning <aion-lightning.org>.
 * 
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.templates.curingzones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CuringTemplate")
public class CuringTemplate {

	@XmlAttribute(name = "map_id")
	protected int mapId;
	@XmlAttribute(name = "x")
	protected float x;
	@XmlAttribute(name = "y")
	protected float y;
	@XmlAttribute(name = "z")
	protected float z;
	@XmlAttribute(name = "range")
	protected float range;

	/**
	 * Gets the value of the mapId property.
	 *
	 * @return possible object is
	 *     {@link Integer }
	 *
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * Sets the value of the mapId property.
	 *
	 * @param value allowed object is
	 *     {@link Integer }
	 *
	 */
	public void setMapId(int value) {
		this.mapId = value;
	}

	/**
	 * Gets the value of the x property.
	 *
	 * @return possible object is
	 *     {@link Float }
	 *
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the x property.
	 *
	 * @param value allowed object is
	 *     {@link Float }
	 *
	 */
	public void setX(float value) {
		this.x = value;
	}

	/**
	 * Gets the value of the y property.
	 *
	 * @return possible object is
	 *     {@link Float }
	 *
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the y property.
	 *
	 * @param value allowed object is
	 *     {@link Float }
	 *
	 */
	public void setY(float value) {
		this.y = value;
	}

	/**
	 * Gets the value of the z property.
	 *
	 * @return possible object is
	 *     {@link Float }
	 *
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the z property.
	 *
	 * @param value allowed object is
	 *     {@link Float }
	 *
	 */
	public void setZ(float value) {
		this.z = value;
	}

	public float getRange() {
		return range;
	}
}