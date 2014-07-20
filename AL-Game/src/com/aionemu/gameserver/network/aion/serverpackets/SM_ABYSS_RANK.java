/*
 * This file is part of aion-unique <www.aion-unique.org>.
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
package com.aionemu.gameserver.network.aion.serverpackets;


import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

/**
 * @author Nemiroff Date: 25.01.2010
 */
public class SM_ABYSS_RANK extends AionServerPacket {

	private AbyssRank rank;
	private int currentRankId;

	public SM_ABYSS_RANK(AbyssRank rank) {
		this.rank = rank;
		this.currentRankId = rank.getRank().getId();
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeQ(rank.getAp()); // curAP
        writeD(0);//rank.getFame()); // current Fame points 4.5
		writeD(currentRankId); // curRank
		writeD(rank.getTopRanking()); // curRating

		int nextRankId = currentRankId < AbyssRankEnum.values().length ? currentRankId + 1 : currentRankId;
		writeD(100 * rank.getAp() / AbyssRankEnum.getRankById(nextRankId).getRequired()); // exp %

		writeD(rank.getAllKill()); // allKill
		writeD(rank.getMaxRank()); // maxRank

        writeH(rank.getDailyKill()); // dayKill
        writeH(0);
        writeD(rank.getDailyAP());
        writeD(0);

        writeD(0);//rank.getDailyFame()); // 4.5 New

        writeD(rank.getWeeklyKill()); // weekKill
        writeQ(rank.getWeeklyAP()); // weekAP

        writeD(0);//rank.getWeeklyFame()); // 4.5 New

        writeD(rank.getLastKill()); // laterKill
        writeQ(rank.getLastAP()); // laterAP

        writeD(0);//rank.getLastFame()); // 4.5 New

        writeC(0x00); // unk

	}
}
