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
package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Rolandas
 */
public class DecorateAction extends AbstractItemAction {

	@XmlAttribute(name = "id")
	private Integer partId;

	@Override
	public boolean canAct(Player player, Item parentItem, Item targetItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void act(Player player, Item parentItem, Item targetItem) {
		// TODO Auto-generated method stub

	}

	public int getTemplateId() {
		if (partId == null) // Addons missing in client
			return 0;
		return partId;
	}

}
