package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.services.transfers.PlayerTransferService;

/**
 * @author KID
 */
public class CM_PTRANSFER_RESPONSE extends LsClientPacket {
	public CM_PTRANSFER_RESPONSE(int opCode) {
		super(opCode);
	}

	@Override
	protected void readImpl() {
		int actionId = this.readD();
		switch(actionId) 
		{
			case 20: //send info
				{
					int targetAccount = readD();
					int taskId = readD();
					String name = readS();
					String account = readS();
					int len = readD();
					byte[] db = this.readB(len);
					PlayerTransferService.getInstance().cloneCharacter(taskId, targetAccount, name, account, db);
				}
				break;
			case 21://ok
				{
					int taskId = readD();
					PlayerTransferService.getInstance().onOk(taskId);
				}
				break;
			case 22://error
				{
					int taskId = readD();
					String reason = readS();
					PlayerTransferService.getInstance().onError(taskId, reason);
				}
				break;
			case 23:
				{
					byte serverId = readSC();
					if(NetworkConfig.GAMESERVER_ID != serverId) {
						try {
							throw new Exception("Requesting player transfer for server id "+serverId+" but this is "+NetworkConfig.GAMESERVER_ID+" omgshit!");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						byte targetServerId = readSC();
						int account = readD();
						int targetAccount = readD();
						int playerId = readD();
						int taskId = readD();
						PlayerTransferService.getInstance().startTransfer(account, targetAccount, playerId, targetServerId, taskId);
					}
				}
				break;
		}
	}

	@Override
	protected void runImpl() {

	}
}
