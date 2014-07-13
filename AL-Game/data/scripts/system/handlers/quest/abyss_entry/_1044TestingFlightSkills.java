/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique. If not, see <http://www.gnu.org/licenses/>.
 */
package quest.abyss_entry;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * Speak to Telemachus (203901). Talk with Daedalus (203930) about your flight test. Go through all the rings within the
 * time limit. Talk with Daedalus again. Report to Telemachus.
 * 
 * @author Hellboy, aion4Free, Hilgert
 * @reworked vlog
 */
public class _1044TestingFlightSkills extends QuestHandler {

	private final static int questId = 1044;
	private String[] rings = { "ELTNEN_FORTRESS_210020000_1", "ELTNEN_FORTRESS_210020000_2",
		"ELTNEN_FORTRESS_210020000_3", "ELTNEN_FORTRESS_210020000_4", "ELTNEN_FORTRESS_210020000_5",
		"ELTNEN_FORTRESS_210020000_6" };

	public _1044TestingFlightSkills() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(203901).addOnTalkEvent(questId);
		qe.registerQuestNpc(203930).addOnTalkEvent(questId);
		for (String ring : rings) {
			qe.registerOnPassFlyingRings(ring, questId);
		}
		qe.registerOnQuestTimerEnd(questId);
		qe.registerOnDie(questId);
		qe.registerOnEnterWorld(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();
		if (qs == null)
			return false;
		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 203901: { // Telemachus
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							}
						}
						case SETPRO1: {
							return defaultCloseDialog(env, 0, 1); // 1
						}
						case FINISH_DIALOG: {
							return defaultCloseDialog(env, 0, 0);
						}
					}
					break;
				}
				case 203930: { // Daedalus
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 1) {
								return sendQuestDialog(env, 1352);
							}
							else if (var == 8) {
								return sendQuestDialog(env, 1693);
							}
							else if (var == 9) {
								return sendQuestDialog(env, 3057);
							}
						}
						case SETPRO2: {
							if (var == 1) {
								QuestService.questTimerStart(env, 150);
								return defaultCloseDialog(env, 1, 2); // 2
							}
							else if (var == 9) {
								QuestService.questTimerStart(env, 150);
								return defaultCloseDialog(env, 9, 2); // 2
							}
						}
						case SET_SUCCEED: {
							if (var == 8) {
								qs.setQuestVar(9);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestSelectionDialog(env);
							}
						}
						case FINISH_DIALOG: {
							return sendQuestSelectionDialog(env);
						}
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203901) { // Telemachus
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				}
				else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 1922);
	}

	@Override
	public boolean onPassFlyingRingEvent(QuestEnv env, String flyingRing) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (rings[0].equals(flyingRing)) {
				changeQuestStep(env, 2, 3, false); // 3
				return true;
			}
			else if (rings[1].equals(flyingRing)) {
				changeQuestStep(env, 3, 4, false); // 4
				return true;
			}
			else if (rings[2].equals(flyingRing)) {
				changeQuestStep(env, 4, 5, false); // 5
				return true;
			}
			else if (rings[3].equals(flyingRing)) {
				changeQuestStep(env, 5, 6, false); // 6
				return true;
			}
			else if (rings[4].equals(flyingRing)) {
				changeQuestStep(env, 6, 7, false); // 7
				return true;
			}
			else if (rings[5].equals(flyingRing)) {
				changeQuestStep(env, 7, 8, false); // 8
				QuestService.questTimerEnd(env);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onQuestTimerEndEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var > 1 && var < 8) {
				changeQuestStep(env, var, 9, false); // 9
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onDieEvent(QuestEnv env) {
		QuestService.questTimerEnd(env);
		return this.onQuestTimerEndEvent(env);
	}

	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		QuestService.questTimerEnd(env);
		return this.onQuestTimerEndEvent(env);
	}
}
