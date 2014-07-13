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
package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.CraftLearnAction;
import com.aionemu.gameserver.model.templates.item.actions.ItemActions;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author xTz
 */
public class BrokerRecipeFilter extends BrokerFilter {

	private int craftSkillId;
	private int[] masks;

	/**
	 * @param masks
	 */
	public BrokerRecipeFilter(int craftSkillId, int... masks) {
		this.craftSkillId = craftSkillId;
		this.masks = masks;
	}

	@Override
	public boolean accept(ItemTemplate template) {
		ItemActions actions = template.getActions();
		if (actions != null) {
			CraftLearnAction craftAction = actions.getCraftLearnAction();
			if (craftAction != null) {
				int id = craftAction.getRecipeId();
				RecipeTemplate recipeTemplate = DataManager.RECIPE_DATA.getRecipeTemplateById(id);
				if (recipeTemplate != null && recipeTemplate.getSkillid() == craftSkillId) {
					return ArrayUtils.contains(masks, template.getTemplateId() / 100000);
				}
			}
		}
		return false;
	}

}
