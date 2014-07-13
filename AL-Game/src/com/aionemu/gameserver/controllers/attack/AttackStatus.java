/*
 * This file is part of aion-emu <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.controllers.attack;

/**
 * @author ATracer
 */
public enum AttackStatus {
	DODGE(0, true, false),
	OFFHAND_DODGE(1, true, false),
	PARRY(2, true, false),
	OFFHAND_PARRY(3, true, false),
	BLOCK(4, true, false),
	OFFHAND_BLOCK(5, true, false),
	RESIST(6),
	OFFHAND_RESIST(7),
	BUF(8), // ??
	OFFHAND_BUF(9),
	NORMALHIT(10),
	OFFHAND_NORMALHIT(11),
	CRITICAL_DODGE(-64, true, true),
	CRITICAL_PARRY(-624, true, true),
	CRITICAL_BLOCK(-604, true, true),
	PHYSICAL_CRITICAL_RESIST(-58, false, true),
	CRITICAL(-54, false, true),
	OFFHAND_CRITICAL_DODGE(-474, true, true),
	OFFHAND_CRITICAL_PARRY(-454, true, true),
	OFFHAND_CRITICAL_BLOCK(-434, true, true),
	OFFHAND_CRITICAL_RESIST(-41, false, true),
	OFFHAND_CRITICAL(-37, false, true);

	private final int type;
	private final boolean counterSkill;
	private final boolean isCritical;

	private AttackStatus(int type) {
		this(type, false, false);
	}
	
	private AttackStatus(int type, boolean counterSkill, boolean isCritical) {
		this.type = type;
		this.counterSkill = counterSkill;
		this.isCritical = isCritical;
	}

	public final int getId() {
		return type;
	}
	public final boolean isCounterSkill() {
		return counterSkill;
	}
	public final boolean isCritical() {
		return isCritical;
	}

	public static final AttackStatus getOffHandStats(AttackStatus mainHandStatus) {
		switch (mainHandStatus) {
			case DODGE:
				return OFFHAND_DODGE;
			case PARRY:
				return OFFHAND_PARRY;
			case BLOCK:
				return OFFHAND_BLOCK;
			case RESIST:
				return OFFHAND_RESIST;
			case BUF:
				return OFFHAND_BUF;
			case NORMALHIT:
				return OFFHAND_NORMALHIT;
			case CRITICAL:
				return OFFHAND_CRITICAL;
			case CRITICAL_DODGE:
				return OFFHAND_CRITICAL_DODGE;
			case CRITICAL_PARRY:
				return OFFHAND_CRITICAL_PARRY;
			case CRITICAL_BLOCK:
				return OFFHAND_CRITICAL_BLOCK;
			case PHYSICAL_CRITICAL_RESIST:
				return OFFHAND_CRITICAL_RESIST;
		}
		throw new IllegalArgumentException("Invalid mainHandStatus " + mainHandStatus);
	}
	
	public static final AttackStatus getBaseStatus(AttackStatus status) {
		switch (status) {
			case DODGE:
			case CRITICAL_DODGE:
			case OFFHAND_DODGE:
			case OFFHAND_CRITICAL_DODGE:
				return AttackStatus.DODGE;
			case PARRY:
			case CRITICAL_PARRY:
			case OFFHAND_PARRY:
			case OFFHAND_CRITICAL_PARRY:
				return AttackStatus.PARRY;
			case BLOCK:
			case CRITICAL_BLOCK:
			case OFFHAND_BLOCK:
			case OFFHAND_CRITICAL_BLOCK:
				return AttackStatus.BLOCK;
			default:
				return status;
		}
	}
	
	public static final AttackStatus getCriticalStatusFor(AttackStatus status) {
		switch(status) {
			case DODGE:
				return AttackStatus.CRITICAL_DODGE;
			case OFFHAND_DODGE:
				return AttackStatus.OFFHAND_CRITICAL_DODGE;
			case PARRY:
				return AttackStatus.CRITICAL_PARRY;
			case OFFHAND_PARRY:
				return AttackStatus.OFFHAND_CRITICAL_PARRY;
			case BLOCK:
				return AttackStatus.CRITICAL_BLOCK;
			case OFFHAND_BLOCK:
				return AttackStatus.OFFHAND_CRITICAL_BLOCK;
			case NORMALHIT:
				return AttackStatus.CRITICAL;
			case OFFHAND_NORMALHIT:
				return AttackStatus.OFFHAND_CRITICAL;
		default:
			return status;
		}
	}
}
