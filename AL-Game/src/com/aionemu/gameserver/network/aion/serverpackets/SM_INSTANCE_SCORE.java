/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * aion-emu is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * aion-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceScoreType;
import com.aionemu.gameserver.model.instance.instancereward.DarkPoetaReward;
import com.aionemu.gameserver.model.instance.instancereward.DredgionReward;
import com.aionemu.gameserver.model.instance.instancereward.HarmonyArenaReward;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.model.instance.instancereward.PvPArenaReward;
import com.aionemu.gameserver.model.instance.playerreward.CruciblePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.DredgionPlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.HarmonyGroupReward;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.PvPArenaPlayerReward;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.util.List;
import javolution.util.FastList;

/**
 * @author Dns, ginho1, nrg, xTz
 */
@SuppressWarnings("rawtypes")
public class SM_INSTANCE_SCORE extends AionServerPacket {

	private int type;
	private int mapId;
	private int instanceTime;
	private InstanceScoreType instanceScoreType;
	private InstanceReward instanceReward;
	private List<Player> players;
	private Integer object;

	public SM_INSTANCE_SCORE(int type, int instanceTime, InstanceReward instanceReward, Integer object) {
		this(instanceTime, instanceReward, null);
		this.type = type;
		this.object = object;
	}

	public SM_INSTANCE_SCORE(int instanceTime, InstanceReward instanceReward, List<Player> players) {
		this.mapId = instanceReward.getMapId();
		this.instanceTime = instanceTime;
		this.instanceReward = instanceReward;
		this.players = players;
		instanceScoreType = instanceReward.getInstanceScoreType();
	}

	public SM_INSTANCE_SCORE(InstanceReward instanceReward, InstanceScoreType instanceScoreType) {
		this.mapId = instanceReward.getMapId();
		this.instanceReward = instanceReward;
		this.instanceScoreType = instanceScoreType;
	}

	public SM_INSTANCE_SCORE(InstanceReward instanceReward) {
		this.mapId = instanceReward.getMapId();
		this.instanceReward = instanceReward;
		this.instanceScoreType = instanceReward.getInstanceScoreType();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void writeImpl(AionConnection con) {
		int playerCount = 0;
		Player owner = con.getActivePlayer();
		Integer ownerObject = owner.getObjectId();
		writeD(mapId);
		writeD(instanceTime);
		writeD(instanceScoreType.getId());
		switch (mapId) {
			case 300570000:
			case 300450000:
				HarmonyArenaReward harmonyArena = (HarmonyArenaReward) instanceReward;
				if (object == null) {
					object = ownerObject;
				}
				HarmonyGroupReward harmonyGroupReward = harmonyArena.getHarmonyGroupReward(object);
				writeC(type);
				switch (type) {
					case 2:
						writeD(0);
						writeD(harmonyArena.getRound());
						break;
					case 3:
						writeD(harmonyGroupReward.getOwner() - 1);
						writeS(harmonyGroupReward.getAGPlayer(object).getName(), 52); // playerName
						writeD(harmonyGroupReward.getOwner()); // groupObj
						writeD(object); // memberObj
						break;
					case 4:
						writeD(harmonyArena.getPlayerReward(object).getRemaningTime()); // buffTime
						writeD(0);
						writeD(0);
						writeD(object); // memberObj
						break;
					case 5:
						writeD(harmonyGroupReward.getBasicAP()); // basicRewardAp
						writeD(harmonyGroupReward.getScoreAP()); // scoreRewardAp
						writeD(harmonyGroupReward.getRankingAP()); // rankingRewardAp
						writeD(186000137); // Courage Insignia
						writeD(harmonyGroupReward.getBasicCourage()); // basicRewardCourageIn
						writeD(harmonyGroupReward.getScoreCourage()); // scoreRewardCourageIn
						writeD(harmonyGroupReward.getRankingCourage()); // rankingRewardCourageIn
						if (harmonyGroupReward.getGloryTicket() != 0) {
							writeD(186000185); // 186000185
							writeD(harmonyGroupReward.getGloryTicket());
						}
						else {
							writeD(0);
							writeD(0);
						}
						writeD(0);
						writeD(0);
						writeD(0);
						writeD(0);
						writeD(0);
						writeD(0);
						writeD((int) harmonyGroupReward.getParticipation() * 100); // progressType
						writeD(harmonyGroupReward.getPoints()); // score
						break;
					case 6:
						writeD(3);
						writeD(harmonyArena.getCapPoints()); // capPoints
						writeD(3); // possible rounds
						writeD(1);
						writeD(harmonyArena.getBuffId());
						writeD(2);
						writeD(0);
						writeD(harmonyArena.getRound()); // round
						FastList<HarmonyGroupReward> groups = harmonyArena.getHarmonyGroupInside();
						writeC(groups.size()); // size
						for (HarmonyGroupReward group : groups) {
							writeC(harmonyArena.getRank(group.getPoints()));
							writeD(group.getPvPKills());
							writeD(group.getPoints()); // groupScore
							writeD(group.getOwner()); // groupObj
							FastList<Player> members = harmonyArena.getPlayersInside(group);
							writeC(members.size());
							int i = 0;
							for (Player p : members) {
								PvPArenaPlayerReward rewardedPlayer = harmonyArena.getPlayerReward(p.getObjectId());
								writeD(0);
								writeD(rewardedPlayer.getRemaningTime()); // buffTime
								writeD(0);
								writeC(group.getOwner() - 1); // groupId
								writeC(i); // memberNr
								writeH(0);
								writeS(p.getName(), 52); // playerName
								writeD(p.getObjectId()); // memberObj
								i++;
							}
						}
						break;
					case 10:
						writeC(harmonyArena.getRank(harmonyGroupReward.getPoints()));
						writeD(harmonyGroupReward.getPvPKills()); // kills
						writeD(harmonyGroupReward.getPoints()); // groupScore
						writeD(harmonyGroupReward.getOwner()); // groupObj
						break;
				}
				break;
			case 300110000:
			case 300210000:
			case 300440000:
				fillTableWithGroup(Race.ELYOS);
				fillTableWithGroup(Race.ASMODIANS);
				DredgionReward dredgionReward = (DredgionReward) instanceReward;
				int elyosScore = dredgionReward.getPointsByRace(Race.ELYOS).intValue();
				int asmosScore = dredgionReward.getPointsByRace(Race.ASMODIANS).intValue();
				writeD(instanceScoreType.isEndProgress() ? (asmosScore > elyosScore ? 1 : 0) : 255);
				writeD(elyosScore);
				writeD(asmosScore);
				writeH(0); // [3.5]
				for (DredgionReward.DredgionRooms dredgionRoom : dredgionReward.getDredgionRooms()) {
					writeC(dredgionRoom.getState());
				}
				break;
			case 300320000:
			case 300300000:
				for (CruciblePlayerReward playerReward : (FastList<CruciblePlayerReward>) instanceReward.getInstanceRewards()) {
					writeD(playerReward.getOwner()); // obj
					writeD(playerReward.getPoints()); // points
					writeD(instanceScoreType.isEndProgress() ? 3 : 1);
					writeD(playerReward.getInsignia());
					playerCount++;
				}
				if (playerCount < 6) {
					writeB(new byte[16 * (6 - playerCount)]); // spaces
				}
				break;
			case 300040000:
				DarkPoetaReward dpr = (DarkPoetaReward) instanceReward;
				writeD(dpr.getPoints());
				writeD(dpr.getNpcKills());
				writeD(dpr.getGatherCollections()); // gathers
				writeD(dpr.getRank()); // 7 for none, 8 for F, 5 for D, 4 C, 3 B, 2 A, 1 S
				break;
			case 300350000:
			case 300360000:
			case 300420000:
			case 300430000:
			case 300550000:
				PvPArenaReward arenaReward = (PvPArenaReward) instanceReward;
				PvPArenaPlayerReward rewardedPlayer = arenaReward.getPlayerReward(ownerObject);
				int rank, points;
				boolean isRewarded = arenaReward.isRewarded();
				for (Player player : players) {
					InstancePlayerReward reward = arenaReward.getPlayerReward(player.getObjectId());
					PvPArenaPlayerReward playerReward = (PvPArenaPlayerReward) reward;
					points = playerReward.getPoints();
					rank = arenaReward.getRank(playerReward.getScorePoints());
					writeD(playerReward.getOwner()); // obj
					writeD(playerReward.getPvPKills()); // kills
					writeD(isRewarded ? points + playerReward.getTimeBonus() : points); // points
					writeD(0); // unk
					writeC(0); // unk
					writeC(player.getPlayerClass().getClassId()); // class id
					writeC(1); // unk
					writeC(rank); // top position
					writeD(playerReward.getRemaningTime()); // instance buff time
					writeD(isRewarded ? playerReward.getTimeBonus() : 0); // time bonus
					writeD(0); // unk
					writeD(0); // unk
					writeH(isRewarded ? (short)(playerReward.getParticipation() * 100) : 0); // participation
					writeS(player.getName(), 54); // playerName
					playerCount++;
				}
				if (playerCount < 12) {
					writeB(new byte[92 * (12 - playerCount)]); // spaces
				}
				if (isRewarded && arenaReward.canRewarded() && rewardedPlayer != null) {
					writeD(rewardedPlayer.getBasicAP()); // basicRewardAp
					writeD(rewardedPlayer.getRankingAP()); // rankingRewardAp
					writeD(rewardedPlayer.getScoreAP()); // scoreRewardAp
					if (mapId == 300550000) {
						writeB(new byte[32]);
						if (rewardedPlayer.getMithrilMedal() != 0) {
							writeD(186000147); // 186000147
							writeD(rewardedPlayer.getMithrilMedal()); // mithril medal
						}
						else if (rewardedPlayer.getPlatinumMedal() != 0) {
							writeD(186000096); // 186000096
							writeD(rewardedPlayer.getPlatinumMedal()); // platinum medal
						}
						else if (rewardedPlayer.getLifeSerum() != 0) {
							writeD(162000077); // 162000077
							writeD(rewardedPlayer.getLifeSerum()); // life serum
						}
						else {
							writeD(0);
							writeD(0); 
						}
						if (rewardedPlayer.getGloriousInsignia() != 0) {
							writeD(182213259); // 182213259
							writeD(rewardedPlayer.getGloriousInsignia()); // glorious insignia
						}
						else {
							writeD(0);
							writeD(0); 
						}
					}
					else {
						writeD(186000130); // 186000130
						writeD(rewardedPlayer.getBasicCrucible()); // basicRewardCrucibleIn
						writeD(rewardedPlayer.getScoreCrucible()); // scoreRewardCrucibleIn
						writeD(rewardedPlayer.getRankingCrucible()); // rankingRewardCrucibleIn
						writeD(186000137); // 186000137
						writeD(rewardedPlayer.getBasicCourage()); // basicRewardCourageIn
						writeD(rewardedPlayer.getScoreCourage()); // scoreRewardCourageIn
						writeD(rewardedPlayer.getRankingCourage()); // rankingRewardCourageIn
						if (rewardedPlayer.getOpportunity() != 0) {
							writeD(186000165); // 186000165
							writeD(rewardedPlayer.getOpportunity()); // opportunity token
						}
						else if (rewardedPlayer.getGloryTicket() != 0) {
							writeD(186000185); // 186000185
							writeD(rewardedPlayer.getGloryTicket()); // glory ticket
						}
						else {
							writeD(0);
							writeD(0);
						}
						writeD(0);
						writeD(0); 
					}
				}
				else {
					writeB(new byte[60]);
				}
				writeD(arenaReward.getBuffId()); // instance buff id
				writeD(0); // unk
				writeD(arenaReward.getRound()); // round
				writeD(arenaReward.getCapPoints()); // cap points
				writeD(3); // possible rounds
				writeD(0); // unk
				break;
		}
	}

	private void fillTableWithGroup(Race race) {
		int count = 0;
		DredgionReward dredgionReward = (DredgionReward) instanceReward;
		for (Player player : players) {
			if (!race.equals(player.getRace())) {
				continue;
			}
			InstancePlayerReward playerReward = dredgionReward.getPlayerReward(player.getObjectId());
			DredgionPlayerReward dpr = (DredgionPlayerReward) playerReward;
			writeD(playerReward.getOwner()); // playerObjectId
			writeD(player.getAbyssRank().getRank().getId()); // playerRank
			writeD(dpr.getPvPKills()); // pvpKills
			writeD(dpr.getMonsterKills()); // monsterKills
			writeD(dpr.getZoneCaptured()); // captured
			writeD(dpr.getPoints()); // playerScore

			if (instanceScoreType.isEndProgress()) {
				boolean winner = race.equals(dredgionReward.getWinningRace());
				writeD((winner ? dredgionReward.getWinnerPoints() : dredgionReward.getLooserPoints()) + (int) (dpr.getPoints() * 1.6f)); // apBonus1
				writeD((winner ? dredgionReward.getWinnerPoints() : dredgionReward.getLooserPoints())); // apBonus2
			}
			else {
				writeB(new byte[8]);
			}

			writeC(player.getPlayerClass().getClassId()); // playerClass
			writeC(0); // unk
			writeS(player.getName(), 54); // playerName
			count++;
		}
		if (count < 6) {
			writeB(new byte[88 * (6 - count)]); // spaces
		}
	}

}