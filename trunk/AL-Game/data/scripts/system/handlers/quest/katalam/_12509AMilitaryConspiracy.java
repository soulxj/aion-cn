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
package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * 
 * @author Ritsu
 */
public class _12509AMilitaryConspiracy extends QuestHandler 
{

	private static final int questId = 12509;

	public _12509AMilitaryConspiracy() 
	{
		super(questId);
	}

	@Override
	public void register() 
	{
		qe.registerQuestNpc(801028).addOnQuestStart(questId);
		qe.registerQuestNpc(801018).addOnTalkEvent(questId);
		qe.registerQuestNpc(701735).addOnKillEvent(questId);
		qe.registerQuestItem(182213307, questId);
	}

	@Override
	public boolean onKillEvent(QuestEnv env) 
	{
		return defaultOnKillEvent(env, 701735, 0, 2);
	}

	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item)
	{
		final Player player = env.getPlayer();
		final int id = item.getItemTemplate().getTemplateId();
		final int itemObjId = item.getObjectId();

		if (id != 182213307)
			return HandlerResult.UNKNOWN;
		PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
			0), true);
		ThreadPoolManager.getInstance().schedule(new Runnable() 
		{

			@Override
			public void run() 
			{
				PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
					1, 0), true);
				changeQuestStep(env, 3, 3, true);
			}
		}, 3000);
		return HandlerResult.SUCCESS;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if (targetId == 801028) 
			{
				if (dialog == DialogAction.QUEST_ACCEPT_SIMPLE)
					return sendQuestDialog(env, 1011);
				else 
					return sendQuestStartDialog(env);
			}
		}

		if (qs == null)
			return false;

		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 801018)
			{
				if (dialog == DialogAction.USE_OBJECT)
					return sendQuestDialog(env, 10002);
				else 
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
