package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.actions.CompositionAction;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.restrictions.RestrictionsManager;

/**
 * Created with IntelliJ IDEA.
 * User: pixfid
 * Date: 7/14/13
 * Time: 5:30 PM
 */
public class CM_COMPOSITE_STONES extends AionClientPacket {

    private int compinationToolItemObjectId;
    private int firstItemObjectId;
    private int secondItemObjectId;


    /**
     * Constructs new client packet instance. ByBuffer and ClientConnection should be later set manually, after using this
     * constructor.
     *
     * @param opcode     packet id
     * @param state      connection valid state
     * @param restStates rest of connection valid state (optional - if there are more than one)
     */
    public CM_COMPOSITE_STONES(int opcode, AionConnection.State state, AionConnection.State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        compinationToolItemObjectId = readD();
        firstItemObjectId = readD();
        secondItemObjectId = readD();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (player == null)
            return;

        if (player.isProtectionActive()) {
            player.getController().stopProtectionActiveTask();
        }

        if (player.isCasting()) {
            player.getController().cancelCurrentSkill();
        }

        Item tools = player.getInventory().getItemByObjId(compinationToolItemObjectId);
        if (tools == null)
            return;
        Item first = player.getInventory().getItemByObjId(firstItemObjectId);
        if (first == null)
            return;
        Item second = player.getInventory().getItemByObjId(secondItemObjectId);
        if (second == null)
            return;

        if (!RestrictionsManager.canUseItem(player, tools))
            return;

        CompositionAction action = new CompositionAction();

        if (!action.canAct(player, tools, first, second))
            return;

        action.act(player, tools, first, second);
    }
}
