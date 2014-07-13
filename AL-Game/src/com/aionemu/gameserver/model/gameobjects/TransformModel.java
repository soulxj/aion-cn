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
package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.model.TribeClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.TransformType;

/**
 * @author Rolandas
 */
public class TransformModel {

	private int modelId;
	private int originalModelId;
	private TransformType originalType;
	private TransformType transformType;
	private int panelId;
	private boolean isActive = false;
	private TribeClass transformTribe;
	private TribeClass overrideTribe;

	public TransformModel(Creature creature) {
		if (creature instanceof Player) {
			this.originalType = TransformType.PC;
		}
		else {
			this.originalType = TransformType.NONE;
		}
		this.originalModelId = creature.getObjectTemplate().getTemplateId();
		this.transformType = TransformType.NONE;
	}

	/**
	 * @return the modelId
	 */
	public int getModelId() {
		if (isActive && modelId > 0)
			return modelId;
		return originalModelId;
	}

	public void setModelId(int modelId) {
		if (modelId == 0 || modelId == originalModelId) {
			modelId = originalModelId;
			isActive = false;
		}
		else {
			this.modelId = modelId;
			isActive = true;
		}
	}

	/**
	 * @return the type
	 */
	public TransformType getType() {
		if (isActive)
			return transformType;
		return originalType;
	}

	public void setTransformType(TransformType transformType) {
		this.transformType = transformType;
	}

	/**
	 * @return the panelId
	 */
	public int getPanelId() {
		if (isActive)
			return panelId;
		return 0;
	}

	public void setPanelId(int id) {
		this.panelId = id;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the transformTribe
	 */
	public TribeClass getTribe() {
		if (isActive && transformTribe != null)
			return transformTribe;
		return overrideTribe;
	}

	/**
	 * @param transformTribe the transformTribe to set
	 */
	public void setTribe(TribeClass transformTribe, boolean override) {
		if (override)
			this.overrideTribe = transformTribe;
		else
			this.transformTribe = transformTribe;
	}

}