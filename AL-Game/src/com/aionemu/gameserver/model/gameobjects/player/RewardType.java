/*
 * This file is part of aion-lightning <aion-lightning.org>.
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

package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author antness
 */
public enum RewardType {
	AP_PLAYER {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getApPlayerGainRate() * statRate);
		}
	},
	AP_NPC {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getApNpcRate() * statRate);
		}
	},
	HUNTING {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_HUNTING_XP_RATE, 100).getCurrent() / 100f;
			long legionOnlineBonus = 0;
			if(player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= 10) {
				legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
			}
			return (long) (reward * player.getRates().getXpRate() * statRate + legionOnlineBonus);
		}
	},
	GROUP_HUNTING {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_GROUP_HUNTING_XP_RATE, 100).getCurrent() / 100f;
			long legionOnlineBonus = 0;
			if(player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= 10) {
				legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
			}
			return (long) (reward * player.getRates().getGroupXpRate() * statRate + legionOnlineBonus);
		}
	},
	PVP_KILL {

		@Override
		public long calcReward(Player player, long reward) {
			return (reward);
		}
	},
	QUEST {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
			return (long) (reward * player.getRates().getQuestXpRate() * statRate);
		}
	},
	CRAFTING {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_CRAFTING_XP_RATE, 100).getCurrent() / 100f;
			long legionOnlineBonus = 0;
			if(player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= 10) {
				legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
			}
			return (long) (reward * player.getRates().getCraftingXPRate() * statRate + legionOnlineBonus);
		}
	},
	GATHERING {

		@Override
		public long calcReward(Player player, long reward) {
			float statRate = player.getGameStats().getStat(StatEnum.BOOST_GATHERING_XP_RATE, 100).getCurrent() / 100f;
			long legionOnlineBonus = 0;
			if(player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= 10) {
				legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
			}
			return (long) (reward * player.getRates().getGatheringXPRate() * statRate + legionOnlineBonus);
		}
	};

	public abstract long calcReward(Player player, long reward);
}
