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
package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

import com.aionemu.gameserver.model.templates.housing.HousingChair;
import com.aionemu.gameserver.model.templates.housing.HousingEmblem;
import com.aionemu.gameserver.model.templates.housing.HousingJukeBox;
import com.aionemu.gameserver.model.templates.housing.HousingMoveableItem;
import com.aionemu.gameserver.model.templates.housing.HousingMovieJukeBox;
import com.aionemu.gameserver.model.templates.housing.HousingNpc;
import com.aionemu.gameserver.model.templates.housing.HousingPassiveItem;
import com.aionemu.gameserver.model.templates.housing.HousingPicture;
import com.aionemu.gameserver.model.templates.housing.HousingPostbox;
import com.aionemu.gameserver.model.templates.housing.HousingStorage;
import com.aionemu.gameserver.model.templates.housing.HousingUseableItem;
import com.aionemu.gameserver.model.templates.housing.PlaceableHouseObject;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "housingObjects" })
@XmlRootElement(name = "housing_objects")
public class HousingObjectData {

	@XmlElements({ 
		@XmlElement(name = "postbox", type = HousingPostbox.class),
		@XmlElement(name = "use_item", type = HousingUseableItem.class),
		@XmlElement(name = "move_item", type = HousingMoveableItem.class),
		@XmlElement(name = "chair", type = HousingChair.class), 
		@XmlElement(name = "picture", type = HousingPicture.class),
		@XmlElement(name = "passive", type = HousingPassiveItem.class), 
		@XmlElement(name = "npc", type = HousingNpc.class),
		@XmlElement(name = "storage", type = HousingStorage.class),
		@XmlElement(name = "jukebox", type = HousingJukeBox.class),
		@XmlElement(name = "moviejukebox", type = HousingMovieJukeBox.class),
		@XmlElement(name = "emblem", type = HousingEmblem.class)})
	protected List<PlaceableHouseObject> housingObjects;

	@XmlTransient
	protected TIntObjectHashMap<PlaceableHouseObject> objectTemplatesById = new TIntObjectHashMap<PlaceableHouseObject>();

	void afterUnmarshal(Unmarshaller u, Object parent) {
		if (housingObjects == null)
			return;
		for (PlaceableHouseObject obj : housingObjects) {
			objectTemplatesById.put(obj.getTemplateId(), obj);
		}

		housingObjects.clear();
		housingObjects = null;
	}

	public int size() {
		return objectTemplatesById.size();
	}

	public PlaceableHouseObject getTemplateById(int templateId) {
		return objectTemplatesById.get(templateId);
	}

}
