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
package quest.siels_spear;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;


/**
 * @author Cheatkiller
 *
 */
public class _21522The_Shadow_Spear extends QuestHandler {
	private final static int questId = 21522;
	
	public _21522The_Shadow_Spear(){
		super(questId);
	}
		@Override
		public void register() {
			qe.registerQuestNpc(205579).addOnQuestStart(questId);
			qe.registerQuestNpc(205579).addOnTalkEvent(questId);
			qe.registerQuestNpc(205585).addOnTalkEvent(questId);
		}
		
		@Override
		public boolean onDialogEvent(QuestEnv env) {
			Player player = env.getPlayer();
			int targetId = env.getTargetId();
			DialogAction dialog = env.getDialog();
			QuestState qs = player.getQuestStateList().getQuestState(questId);
			
			if (qs == null || qs.getStatus() == QuestStatus.NONE) {
				if (targetId == 205579) {
					if (dialog == DialogAction.QUEST_SELECT) {
						return sendQuestDialog(env, 1011);
					}
					else {
						return sendQuestStartDialog(env);
					}
				}
			}
			else if (qs != null && qs.getStatus() == QuestStatus.START) {
				int var = qs.getQuestVarById(0);
				switch (targetId) {
					case 205585: { 
						switch (dialog) {
							case QUEST_SELECT: {
								if (var == 0) {
									return sendQuestDialog(env, 1352);
								}
							}
							case SETPRO1: {
								qs.setQuestVar(1);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return closeDialogWindow(env);
							}
						}
						break;
					}
				}
			}
			else if (qs.getStatus() == QuestStatus.REWARD) {
				if (targetId == 205579) {
					if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 5);
					else
						return sendQuestEndDialog(env);
				}
			}
			return false;
		}

	}
