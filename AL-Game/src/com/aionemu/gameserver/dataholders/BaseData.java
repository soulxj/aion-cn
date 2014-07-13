/*
 * This file is part of aion-lightning <aion-lightning.org>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.base.BaseLocation;
import com.aionemu.gameserver.model.templates.base.BaseTemplate;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javolution.util.FastMap;

/**
 *
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "base_locations")
public class BaseData {

	@XmlElement(name = "base_location")
	private List<BaseTemplate> baseTemplates;
	@XmlTransient
	private FastMap<Integer, BaseLocation> base = new FastMap<Integer, BaseLocation>();

	void afterUnmarshal(Unmarshaller u, Object parent) {
		for (BaseTemplate template : baseTemplates) {
			base.put(template.getId(), new BaseLocation(template));
		}
	}

	public int size() {
		return base.size();
	}

	public FastMap<Integer, BaseLocation> getBaseLocations() {
		return base;
	}

}