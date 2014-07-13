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
package com.aionemu.gameserver.model;

/**
 *
 * @author xTz
 */
public enum TeleportAnimation {

	NO_ANIMATION(0, 0),
	BEAM_ANIMATION(1, 3),
	JUMP_AIMATION(3, 10),
	JUMP_AIMATION_2(4, 10),
	JUMP_AIMATION_3(8, 3);

	private int startAnimation;
	private int endAnimation;

	TeleportAnimation(int startAnimation, int endAnimation) {
		this.startAnimation = startAnimation;
		this.endAnimation = endAnimation;
	}

	public int getStartAnimationId() {
		return startAnimation;
	}

	public int getEndAnimationId() {
		return endAnimation;
	}

	public boolean isNoAnimation() {
		return this.getStartAnimationId() == 0;
	}

}