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
package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.ingameshop.IGItem;
import java.util.List;
import javolution.util.FastMap;

/**
 * @author xTz, KID
 */
public abstract class InGameShopDAO implements DAO {

	public abstract boolean deleteIngameShopItem(int itemId, byte category, byte subCategory, int list);

	public abstract FastMap<Byte, List<IGItem>> loadInGameShopItems();

	public abstract void saveIngameShopItem(int objectId, int itemId, long itemCount, long itemPrice, byte category, byte subCategory, int list, int salesRanking,
			byte itemType, byte gift, String titleDescription, String description);

	public abstract boolean increaseSales(int object, int current);

	@Override
	public String getClassName() {
		return InGameShopDAO.class.getName();
	}
}
