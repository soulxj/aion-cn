package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * Talk with Jebal (278002). Talk with Lakadi (278019). Talk with Glati (278088). Locate the Captured Asmodian Prisoner
 * (253626). Escort Captured Asmodian Prisoner to the Magic Ward (1273, 1494, 1538). Report back to Lakadi.
 * 
 * @author MetaWind
 * @modified kale
 * @reworked vlog
 */
public class _2073CapturedComrades extends QuestHandler {

	private final static int questId = 2073;
	private final static int[] npcs = { 278002, 278019, 278088, 253626 };

	public _2073CapturedComrades() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerOnLogOut(questId);
		qe.registerAddOnReachTargetEvent(questId);
		qe.registerAddOnLostTargetEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;
		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 278002: { // Jebal
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 0)
								return sendQuestDialog(env, 1011);
						case SETPRO1:
							return defaultCloseDialog(env, 0, 1); // 1
					}
					break;
				}
				case 278019: { // Lakadi
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 1)
								return sendQuestDialog(env, 1352);
						case SETPRO2:
							return defaultCloseDialog(env, 1, 2); // 2
					}
					break;
				}
				case 278088: { // Glati
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 2)
								return sendQuestDialog(env, 1693);
						case SETPRO3:
							return defaultCloseDialog(env, 2, 3); // 3
					}
					break;
				}
				case 253626: { // Captured Asmodian Prisoner
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 3) {
								return sendQuestDialog(env, 2034);
							}
						case SELECT_ACTION_2035: {
							playQuestMovie(env, 294);
							return sendQuestDialog(env, 2035);
						}
						case SETPRO4: {
							return defaultStartFollowEvent(env, (Npc) env.getVisibleObject(),  1295.0565f, 1499.0419f, 1571.1864f, 3, 4); // 4
						}
					}
					break;
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 278019) { // Lakadi
				if (env.getDialog() == DialogAction.USE_OBJECT)
					return sendQuestDialog(env, 10002);
				else
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
	
	
	@Override
	public boolean onLogOutEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 4) {
				changeQuestStep(env, 4, 3, false);
			}
		}
		return false;
	}

	@Override
	public boolean onNpcReachTargetEvent(QuestEnv env) {
		return defaultFollowEndEvent(env, 4, 4, true, 290); // reward
	}

	@Override
	public boolean onNpcLostTargetEvent(QuestEnv env) {
		return defaultFollowEndEvent(env, 4, 3, false); // 3
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 2701, true);
	}
}
