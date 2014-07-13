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
package quest.abyss_entry;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * Meet Kvasir (204053) and choose your mission.<br>
 * -- Choice 2 --<br>
 * Talk with Aegir (204301).<br>
 * Get the Golden Helmet of Urgasch (182207037) (Statue of Urgasch, 700268) and take it to Aegir.<br>
 * -- Choice 1 --<br>
 * Talk with Aegir.<br>
 * Defeat the fierce creatures of Morheim:<br>
 * Guzzling Kurin (212396) (3),<br>
 * Klaw Scouter (212611) (3),<br>
 * Dark Lake Spirit (212408) (3).<br>
 * Report the result to Aegir.<br>
 * -- Choice 0 --<br>
 * Talk with Garm (204089).<br>
 * Defeat Spirit of Underground Arena in the Triniel Underground Arena(10):<br>
 * Warrior Spirit (213583, 290048, 211987, 290047, 290050, 211986, 290049),<br>
 * Mage Spirit (213584, 211982).<br>
 * You succeeded! Talk with Garm.<br>
 * You failed! Talk with Garm again.<br>
 * 
 * @author Hellboy aion4Free
 * @modified Gigi
 * @reworked vlog Please don't touch commented code. There is no more dialog to choose a mission. But maybe there will
 *           be one.
 */
public class _2947FollowingThrough extends QuestHandler {

	private final static int questId = 2947;
	private int choice = 0;

	public _2947FollowingThrough() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcs = { 204053, 204301, 204089, 700268 };
		int[] mobs = { 212396, 212611, 212408, 213583, 290048, 211987, 290047, 290050, 211986, 290049, 213584, 211982 };
		qe.registerOnLevelUp(questId);
		qe.registerOnQuestTimerEnd(questId);
		qe.registerOnEnterWorld(questId);
		qe.registerOnMovieEndQuest(168, questId);
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;
		int var = qs.getQuestVarById(0);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 204053: { // Kvasir
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							}
							else if (var == 4) {
								return sendQuestDialog(env, 1019);
							}
							// else if (var == 1 || var == 4 || var == 9) {
							// return sendQuestDialog(env, 2375); // to have a possibility to change the choice
							// }
						}
						// case SETPRO11: {
						// choice = 0;
						// return defaultCloseDialog(env, 0, 1); // 1
						// }
						case SETPRO12: {
							choice = 1;
							if (var == 0) {
								return defaultCloseDialog(env, 0, 4); // 4
							}
							else if (var == 4) {
								return defaultCloseDialog(env, 4, 4);
							}
						}
						// case SETPRO13: {
						// choice = 2;
						// return defaultCloseDialog(env, 0, 9); // 9
						// }
						case FINISH_DIALOG: {
							if (var == 0) {
								return defaultCloseDialog(env, 0, 0);
							}
						}
					}
					break;
				}
				case 204301: { // Aegir
					switch (dialog) {
						case USE_OBJECT: {
							// if (var == 1) {
							// return sendQuestDialog(env, 1352);
							// }
							// else if (var == 9) {
							// return sendQuestDialog(env, 4080);
							// }
							// else if (var == 3 && choice == 1) {
							// return sendQuestDialog(env, 3398);
							// }
							if (var == 7) {
								return sendQuestDialog(env, 3739);
							}
						}
						// case SETPRO2: {
						// return defaultCloseDialog(env, 1, 2); // 2
						// }
						case SELECT_QUEST_REWARD: {
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 6);
							// else if (var == 3) {
							// return defaultCloseDialog(env, 3, 3, true, true); // reward
							// }
						}
						// case SELECT_ACTION_4081: {
						// return checkQuestItems(env, 9, 9, true, 7, 4081); // reward
						// }
						// case FINISH_DIALOG: {
						// return defaultCloseDialog(env, 9, 9);
						// }
					}
					break;
				}
				case 204089: { // Garm
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 4) {
								return sendQuestDialog(env, 1693);
							}
							else if (qs.getQuestVarById(4) == 10) {
								return sendQuestDialog(env, 2034);
							}
							// else if (var == 6) { doesn't work :(
							// return sendQuestDialog(env, 1779);
							// }
						}
						case SETPRO3: {
							return defaultCloseDialog(env, 4, 5); // 5
						}
						case SETPRO4: {
							qs.setQuestVar(7);
							updateQuestStatus(env);
							return defaultCloseDialog(env, 7, 7); // 7
						}
					}
					break;
				}
				case 700268: { // Statue of Urgasch
					if (var == 9 && dialog == DialogAction.USE_OBJECT) {
						return true; // loot
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			switch (targetId) {
				case 204301: { // Aegir
					return sendQuestEndDialog(env, choice);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 2) {
				int var1 = qs.getQuestVarById(1);
				int var2 = qs.getQuestVarById(2);
				int var3 = qs.getQuestVarById(3);
				switch (env.getTargetId()) {
					case 212396: { // Guzzling Kurin
						if (var2 == 3 && var3 == 3) {
							qs.setQuestVar(3); // 3
							updateQuestStatus(env);
							playQuestMovie(env, 168);
							return true;
						}
						changeQuestStep(env, var1, var1 + 1, false, 1); // 1: 1 - 3
					}
					case 212611: { // Klaw Scouter
						if (var1 == 3 && var3 == 3) {
							qs.setQuestVar(3); // 3
							updateQuestStatus(env);
							playQuestMovie(env, 168);
							return true;
						}
						changeQuestStep(env, var2, var2 + 1, false, 2); // 2: 1 - 3
					}
					case 212408: { // Dark Lake Spirit
						if (var1 == 3 && var2 == 3) {
							qs.setQuestVar(3); // 3
							updateQuestStatus(env);
							return true;
						}
						changeQuestStep(env, var3, var3 + 1, false, 3); // 3: 1 - 3
					}
				}
			}
			else if (var == 5) {
				int var4 = qs.getQuestVarById(4);
				int[] mobs = { 213583, 290048, 211987, 290047, 290050, 211986, 290049, 213584, 211982 };
				if (var4 < 9) {
					return defaultOnKillEvent(env, mobs, 0, 9, 4); // 4: 1 - 9
				}
				else if (var4 == 9) {
					defaultOnKillEvent(env, mobs, 9, 10, 4); // 4: 10
					QuestService.questTimerEnd(env);
					playQuestMovie(env, 168);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onQuestTimerEndEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var4 = qs.getQuestVarById(4);
			if (var4 != 10) {
				qs.setQuestVar(4);
				updateQuestStatus(env);
				TeleportService2.teleportTo(player, 120010000, 1006.1f, 1526, 222.2f, (byte) 90);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			int var4 = qs.getQuestVars().getVarById(4);
			if (var == 5 && var4 != 10) {
				if (player.getWorldId() != 320090000) {
					QuestService.questTimerEnd(env);
					qs.setQuestVar(4);
					updateQuestStatus(env);
					return true;
				}
				else {
					playQuestMovie(env, 167);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (movieId == 168) {
				TeleportService2.teleportTo(player, 120010000, 1006.1f, 1526, 222.2f, (byte) 90);
				return true;
			}
			else if (movieId == 167) {
				QuestService.questTimerStart(env, 240);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 2946);
	}
}
