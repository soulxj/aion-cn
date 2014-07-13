package quest.silentera_canyon;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Ritsu
 * 
 */
public class _30057JotunJaunt extends QuestHandler
{
	private final static int	questId	= 30057;

	public _30057JotunJaunt()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.registerQuestNpc(799381).addOnQuestStart(questId);
		qe.registerQuestNpc(799381).addOnTalkEvent(questId);
		qe.registerOnEnterZone(ZoneName.get("SILENTERA_WESTGATE_600010000"), questId);
	}

	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 0) {
				changeQuestStep(env, 0, 1, true);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();

		if(targetId == 799381)
		{
			if(qs == null || qs.getStatus() == QuestStatus.NONE)
			{
				if(dialog == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 4762);
				else
					return sendQuestStartDialog(env);
			}

			else if(qs != null && qs.getStatus() == QuestStatus.REWARD)
			{
				if(dialog == DialogAction.USE_OBJECT)
					return sendQuestDialog(env, 10002);
				else if(dialog == DialogAction.SELECT_QUEST_REWARD)
					return sendQuestDialog(env, 5);
				else 
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
