/*
 * This file is part of aion-unique <aion-unique.org>.
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
package com.aionemu.gameserver.utils.stats;

import com.aionemu.gameserver.configs.main.RateConfig;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author ATracer
 * @author Sarynth
 * @author Imaginary
 * @author aion eXs emu - Reverse -
 */
@XmlEnum
public enum AbyssRankEnum {
	GRADE9_SOLDIER(1, 132, 24, 0, 0, 1802431), // 4.5 +Abyss point 132 > 300
	GRADE8_SOLDIER(2, 185, 37, 1200, 0, 1802433), // 4.5 +Abyss point 185 > 345
	GRADE7_SOLDIER(3, 259, 58, 4220, 0, 1802435), // 4.5 +Abyss point 259 > 396
	GRADE6_SOLDIER(4, 362, 91, 10990, 0, 1802437), // 4.5 +Abyss point 362 > 455
	GRADE5_SOLDIER(5, 507, 143, 23500, 0, 1802439), // 4.5 +Abyss point 507 > 523
	GRADE4_SOLDIER(6, 710, 225, 42780, 0, 1802441), // 4.5 +Abyss point 701 > 601
	GRADE3_SOLDIER(7, 993, 356, 69700, 0, 1802443), // 4.5 +Abyss point 993 > 721
	GRADE2_SOLDIER(8, 1390, 561, 105600, 0, 1802445), // 4.5 +Abyss point 1390 > 865
	GRADE1_SOLDIER(9, 1947, 887, 150800, 0, 1802447), // 4.5 +Abyss point 1947 > 1038
	STAR1_OFFICER(10, 2336, 1428, 214100, 1000, 1802449), // 4.5 +Abyss point 2336 > 1557 / -Abyss point 1428 > 467
	STAR2_OFFICER(11, 2688, 1941, 278700, 700, 1802451), // 4.5 +Abyss point 2688 > 1868 / -Abyss point 1941 > 560
	STAR3_OFFICER(12, 3091, 2524, 344500, 500, 1802453), // 4.5 +Abyss point 3091 > 2148 / -Abyss point 2524 > 644
	STAR4_OFFICER(13, 3555, 3155, 411700, 300, 1802455), // 4.5 +Abyss point 3555 > 2470 / -Abyss point 3155 > 741
	STAR5_OFFICER(14, 4799, 5048, 488200, 100, 1802457), // 4.5 +Abyss point 4799 > 3705 / -Abyss point 5048 > 1482
	GENERAL(15, 5945, 5704, 565400, 30, 1802459), // 4.5 +Abyss point 5945 > 4075 / -Abyss point 5704 > 1630
	GREAT_GENERAL(16, 6420, 6389, 643200, 10, 1802461), // 4.5 +Abyss point 6420 > 4482 / -Abyss point 6389 > 1792
	COMMANDER(17, 6934, 7155, 721600, 3, 1802463), // 4.5 +Abyss point 6934 > 4930 / -Abyss point 7155 > 1972
	SUPREME_COMMANDER(18, 7489, 8014, 800700, 1, 1802465); // 4.5 +Abyss point 7489 > 5916 / -Abyss point 8014 > 2366

	private int id;
	private int pointsGained;
	private int pointsLost;
	private int required;
	private int quota;
	private int descriptionId;

	/**
	 * @param id
	 * @param pointsGained
	 * @param pointsLost
	 * @param required
	 * @param quota
	 */
	private AbyssRankEnum(int id, int pointsGained, int pointsLost, int required, int quota, int descriptionId) {
		this.id = id;
		this.pointsGained = pointsGained;
		this.pointsLost = pointsLost;
		this.required = required * RateConfig.ABYSS_RANK_RATE;
		this.quota = quota;
		this.descriptionId = descriptionId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the pointsLost
	 */
	public int getPointsLost() {
		return pointsLost;
	}

	/**
	 * @return the pointsGained
	 */
	public int getPointsGained() {
		return pointsGained;
	}

	/**
	 * @return AP required for Rank
	 */
	public int getRequired() {
		return required;
	}

	/**
	 * @return The quota is the maximum number of allowed player to have the rank
	 */
	public int getQuota() {
		return quota;
	}
	
	public int getDescriptionId() {
		return descriptionId;
	}

	public static DescriptionId getRankDescriptionId(Player player){
		int pRankId = player.getAbyssRank().getRank().getId();
		for (AbyssRankEnum rank : values()) {
			if (rank.getId() == pRankId) {
				int descId = rank.getDescriptionId();
				return (player.getRace() == Race.ELYOS) ? new DescriptionId(descId) : new DescriptionId(descId + 36);
			}
		}
		throw new IllegalArgumentException("No rank Description Id found for player: " + player);
	}

	/**
	 * @param id
	 * @return The abyss rank enum by his id
	 */
	public static AbyssRankEnum getRankById(int id) {
		for (AbyssRankEnum rank : values()) {
			if (rank.getId() == id)
				return rank;
		}
		throw new IllegalArgumentException("Invalid abyss rank provided" + id);
	}

	/**
	 * @param ap
	 * @return The abyss rank enum for his needed ap
	 */
	public static AbyssRankEnum getRankForAp(int ap) {
		AbyssRankEnum r = AbyssRankEnum.GRADE9_SOLDIER;
		for (AbyssRankEnum rank : values()) {
			if (rank.getRequired() <= ap)
				r = rank;
			else
				break;
		}
		return r;
	}
}
