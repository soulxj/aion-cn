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
package quest.poeta;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author MrPoke
 */
public class _1205ANewSkill extends QuestHandler {

	private final static int questId = 1205;

	public _1205ANewSkill() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(203087).addOnTalkEvent(questId); // Warrior
		qe.registerQuestNpc(203088).addOnTalkEvent(questId); // Scout
		qe.registerQuestNpc(203089).addOnTalkEvent(questId); // Mage
		qe.registerQuestNpc(203090).addOnTalkEvent(questId); // Priest
		qe.registerQuestNpc(801210).addOnTalkEvent(questId); // Gunner
		qe.registerQuestNpc(801211).addOnTalkEvent(questId); // Bard
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		boolean lvlCheck = QuestService.checkLevelRequirement(questId, player.getCommonData().getLevel());
		if (!lvlCheck)
			return false;
		if (qs != null)
			return false;
		env.setQuestId(questId);
		if (QuestService.startQuest(env)) {
			qs = player.getQuestStateList().getQuestState(questId);
			qs.setStatus(QuestStatus.REWARD);
			PlayerClass playerClass = player.getPlayerClass();
			if (!playerClass.isStartingClass())
				playerClass = PlayerClass.getStartingClassFor(playerClass);
			switch (playerClass) {
				case WARRIOR:
					qs.setQuestVar(1);
					break;
				case SCOUT:
					qs.setQuestVar(2);
					break;
				case MAGE:
					qs.setQuestVar(3);
					break;
				case PRIEST:
					qs.setQuestVar(4);
					break;
				case ENGINEER:
					qs.setQuestVar(4);
					break;
				case ARTIST:
					qs.setQuestVar(4);
					break;
			}
			updateQuestStatus(env);
		}
		return true;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.REWARD)
			return false;

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		PlayerClass playerClass = PlayerClass.getStartingClassFor(player.getCommonData().getPlayerClass());
		switch (targetId) {
			case 203087:
				if (playerClass == PlayerClass.WARRIOR) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 1011);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 5);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
			case 203088:
				if (playerClass == PlayerClass.SCOUT) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 1352);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 6);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
			case 203089:
				if (playerClass == PlayerClass.MAGE) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 1693);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 7);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
			case 203090:
				if (playerClass == PlayerClass.PRIEST) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 2034);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 8);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
			case 801210:
				if (playerClass == PlayerClass.ENGINEER) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 2034);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 45);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
			case 801211:
				if (playerClass == PlayerClass.ARTIST) {
					if (env.getDialog() == DialogAction.USE_OBJECT)
						return sendQuestDialog(env, 2034);
					else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
						return sendQuestDialog(env, 46);
					else
						return this.sendQuestEndDialog(env);
				}
				return false;
		}

		return false;
	}
}
