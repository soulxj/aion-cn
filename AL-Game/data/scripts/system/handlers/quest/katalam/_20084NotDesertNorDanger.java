/*
 *	This file is part of aion-lightning <aion-lightning.com>.
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
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 */
public class _20084NotDesertNorDanger extends QuestHandler {

	private final static int questId = 20084;
	
	private final static int[] mobs = {230405, 230406};
	

	public _20084NotDesertNorDanger() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcIds = { 800553, 800554, 801153, 800555, 800565, 701538, 800545 };
		qe.registerQuestItem(182215234, questId);
		qe.registerQuestNpc(206285).addOnAtDistanceEvent(questId);
		qe.registerOnLevelUp(questId);
		for (int npcId : npcIds) {
			qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		}
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 20083);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();
		
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 800553) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 0) {
							return sendQuestDialog(env, 1011);
						}
						else if (qs.getQuestVarById(0) == 1) {
							return sendQuestDialog(env, 1352);
						}
						else if (qs.getQuestVarById(0) == 6) {
							return sendQuestDialog(env, 2034);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1);
					}
					case SETPRO4: {
						return defaultCloseDialog(env, 6, 7);
					}
					case CHECK_USER_HAS_QUEST_ITEM: {
						return checkQuestItems(env, 1, 2, false, 10000, 10001);
					}
				}
			}
			else if (targetId == 800554) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 7) {
							return sendQuestDialog(env, 2375);
						}
					}
					case SETPRO5: {
						giveQuestItem(env, 182215234, 1);
						return defaultCloseDialog(env, 7, 8); 
					}
				}
			}
			else if (targetId == 801153) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 9) {
							return sendQuestDialog(env, 3057);
						}
					}
					case SETPRO7: {
						return defaultCloseDialog(env, 9, 10); 
					}
				}
			}
			else if (targetId == 800555) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 10) {
							return sendQuestDialog(env, 3398);
						}
					}
					case SETPRO8: {
						giveQuestItem(env, 182215238, 1);
						return defaultCloseDialog(env, 10, 11); 
					}
				}
			}
			else if (targetId == 800565) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 12) {
							return sendQuestDialog(env, 4080);
						}
					}
					case SETPRO10: {
						return defaultCloseDialog(env, 12, 13); 
					}
				}
			}
			else if (targetId == 701538) { 
				switch (dialog) {
					case USE_OBJECT: {
						if (qs.getQuestVarById(0) == 13) {
							return sendQuestDialog(env, 4082);
						}
					}
					case SETPRO11: {
						giveQuestItem(env, 182215239, 1);
						return defaultCloseDialog(env, 13, 14, true, false); 
					}
				}
			}
		}
		else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800545) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 5);
				}
				else {
					removeQuestItem(env, 182215239, 1);
					removeQuestItem(env, 182215238, 1);
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		return defaultOnKillEvent(env, mobs, 2, 6, 0);
	}
	
	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getQuestVarById(0) == 8) {
			changeQuestStep(env, 8, 9, false);
			removeQuestItem(env, 182215234, 1);
		  return HandlerResult.SUCCESS;
		}
		return HandlerResult.FAILED;
	}
	
	@Override
	public boolean onAtDistanceEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (qs.getQuestVarById(0) == 11) {
				changeQuestStep(env, 11, 12, false);
	  		return true;
			}
  	}
		return false;
	}
}
