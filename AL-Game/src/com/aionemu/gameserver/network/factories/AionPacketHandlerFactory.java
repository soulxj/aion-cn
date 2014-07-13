/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionPacketHandler;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.clientpackets.*;

/**
 * This factory is responsible for creating {@link AionPacketHandler} object. It also initializes created handler with a
 * set of packet prototypes.<br>
 * Object of this classes uses <tt>Injector</tt> for injecting dependencies into prototype objects.<br>
 * <br>
 * 
 * @author Luno
 */
public class AionPacketHandlerFactory {

	private AionPacketHandler handler;

	public static AionPacketHandlerFactory getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Creates new instance of <tt>AionPacketHandlerFactory</tt><br>
	 */
	public AionPacketHandlerFactory() {
		handler = new AionPacketHandler();
		
		// ////////////////////// 4.0 ///////////////////////
		addPacket(new CM_UI_SETTINGS(0xAD, State.IN_GAME));		
		addPacket(new CM_MOTION(0x2EE, State.IN_GAME));
		addPacket(new CM_WINDSTREAM(0x119, State.IN_GAME));
		addPacket(new CM_STOP_TRAINING(0x2EB, State.IN_GAME));
		addPacket(new CM_REVIVE(0xD8, State.IN_GAME));			
		addPacket(new CM_DUEL_REQUEST(0x135, State.IN_GAME));		
		addPacket(new CM_CRAFT(0x120, State.IN_GAME));
		addPacket(new CM_QUESTION_RESPONSE(0xF5, State.IN_GAME));	
		addPacket(new CM_SHOW_FRIENDLIST(0x1B9, State.IN_GAME));
		addPacket(new CM_OPEN_STATICDOOR(0xFE, State.IN_GAME));
		addPacket(new CM_SPLIT_ITEM(0x170, State.IN_GAME));	
		addPacket(new CM_CUSTOM_SETTINGS(0xA3, State.IN_GAME));	
		addPacket(new CM_PLAY_MOVIE_END(0x114, State.IN_GAME));
		addPacket(new CM_LEVEL_READY(0xAC, State.IN_GAME));
		addPacket(new CM_ENTER_WORLD(0xAF, State.AUTHED));
		addPacket(new CM_PING(0xC3, State.AUTHED, State.IN_GAME));
		addPacket(new CM_VERSION_CHECK(0xC7, State.CONNECTED));
		addPacket(new CM_TIME_CHECK(0xD5, State.CONNECTED, State.AUTHED, State.IN_GAME));
		addPacket(new CM_LEGION_SEND_EMBLEM_INFO(0xD7, State.IN_GAME));
		addPacket(new CM_QUIT(0xDA, State.AUTHED, State.IN_GAME));
		addPacket(new CM_L2AUTH_LOGIN_CHECK(0x128, State.CONNECTED));
		addPacket(new CM_CHARACTER_LIST(0x129, State.AUTHED));
		addPacket(new CM_FRIEND_STATUS(0x14D, State.IN_GAME));
		addPacket(new CM_CREATE_CHARACTER(0x17E, State.AUTHED));
		addPacket(new CM_MAC_ADDRESS(0x190, State.CONNECTED, State.AUTHED, State.IN_GAME));
		addPacket(new CM_CHARACTER_PASSKEY(0x195, State.AUTHED));
		addPacket(new CM_MAY_LOGIN_INTO_GAME(0x19D, State.AUTHED));
		addPacket(new CM_MOVE(0xF7, State.IN_GAME));
		addPacket(new CM_CASTSPELL(0xE4, State.IN_GAME));
		addPacket(new CM_CHAT_MESSAGE_PUBLIC(0xF2, State.IN_GAME));
		addPacket(new CM_EMOTION(0xC2, State.IN_GAME));
		addPacket(new CM_TITLE_SET(0x122, State.IN_GAME));
		addPacket(new CM_DELETE_ITEM(0x10B, State.IN_GAME));
		addPacket(new CM_SHOW_MAP(0x19B, State.IN_GAME));
		addPacket(new CM_CHAT_MESSAGE_WHISPER(0xF3, State.IN_GAME));
		addPacket(new CM_PING_REQUEST(0x10E, State.IN_GAME));
		addPacket(new CM_QUEST_SHARE(0x17B, State.IN_GAME));
		addPacket(new CM_PLAYER_SEARCH(0x166, State.IN_GAME));
		addPacket(new CM_DELETE_QUEST(0x117, State.IN_GAME));
		addPacket(new CM_ABYSS_RANKING_PLAYERS(0x193, State.IN_GAME));
		addPacket(new CM_ABYSS_RANKING_LEGIONS(0x109, State.IN_GAME));
		addPacket(new CM_PRIVATE_STORE(0x15E, State.IN_GAME));
		addPacket(new CM_USE_ITEM(0xF8, State.IN_GAME));
		addPacket(new CM_TARGET_SELECT(0xE6, State.IN_GAME));
		addPacket(new CM_SHOW_DIALOG(0xCB, State.IN_GAME));
		addPacket(new CM_CHECK_NICKNAME(0x174, State.AUTHED));
		addPacket(new CM_PRIVATE_STORE_NAME(0x15F, State.IN_GAME));
		addPacket(new CM_DELETE_CHARACTER(0x17F, State.AUTHED));
		addPacket(new CM_RESTORE_CHARACTER(0x17C, State.AUTHED));
		addPacket(new CM_MOVE_ITEM(0x173, State.IN_GAME));
		addPacket(new CM_MACRO_CREATE(0x176, State.IN_GAME));
		addPacket(new CM_MACRO_DELETE(0x177, State.IN_GAME));
		addPacket(new CM_FRIEND_ADD(0x136, State.IN_GAME));
		addPacket(new CM_GATHER(0xAA, State.IN_GAME));
		addPacket(new CM_INSTANCE_INFO(0x187, State.IN_GAME));
		addPacket(new CM_CLIENT_COMMAND_ROLL(0x102, State.IN_GAME));
		addPacket(new CM_START_LOOT(0x17D, State.IN_GAME));
		addPacket(new CM_LEGION_MODIFY_EMBLEM(0x112, State.IN_GAME));
		addPacket(new CM_CLOSE_DIALOG(0xC8, State.IN_GAME));
		addPacket(new CM_DIALOG_SELECT(0xC9, State.IN_GAME));
		addPacket(new CM_SET_NOTE(0x11D, State.IN_GAME));
		addPacket(new CM_FIND_GROUP(0x2E0, State.IN_GAME)); // TODO its sent also on AUTHED state
		addPacket(new CM_BUY_ITEM(0xCA, State.IN_GAME));
		addPacket(new CM_IN_GAME_SHOP_INFO(0x184, State.IN_GAME));
		addPacket(new CM_EQUIP_ITEM(0xF9, State.IN_GAME));
		addPacket(new CM_TELEPORT_SELECT(0x12B, State.IN_GAME));
		addPacket(new CM_LOOT_ITEM(0x172, State.IN_GAME));
		addPacket(new CM_QUESTIONNAIRE(0x154, State.IN_GAME));
		addPacket(new CM_ATTACK(0xE7, State.IN_GAME));
		addPacket(new CM_AUTO_GROUP(0x16F, State.IN_GAME));
		addPacket(new CM_PET(0xA9, State.IN_GAME));
		addPacket(new CM_BLOCK_ADD(0x179, State.IN_GAME));
		addPacket(new CM_FRIEND_DEL(0x137, State.IN_GAME));
		addPacket(new CM_PET_EMOTE(0xA8, State.IN_GAME));
		addPacket(new CM_LEGION(0xC0, State.IN_GAME));
		addPacket(new CM_CHAT_AUTH(0x141, State.IN_GAME));
		addPacket(new CM_MARK_FRIENDLIST(0x101, State.IN_GAME));//Thx Rolandas
		addPacket(new CM_BLOCK_DEL(0x14E, State.IN_GAME));
		addPacket(new CM_SUMMON_MOVE(0x16C, State.IN_GAME));
		addPacket(new CM_SUMMON_COMMAND(0x15C, State.IN_GAME));
		addPacket(new CM_SUMMON_EMOTION(0x16D, State.IN_GAME));
		addPacket(new CM_SUMMON_ATTACK(0x162, State.IN_GAME));
		addPacket(new CM_SUMMON_CASTSPELL(0x160, State.IN_GAME));
		addPacket(new CM_LEGION_SEND_EMBLEM(0xF6, State.IN_GAME));
		addPacket(new CM_CHALLENGE_LIST(0x18F, State.IN_GAME));
		addPacket(new CM_GM_BOOKMARK(0xCC, State.IN_GAME));
		addPacket(new CM_SEND_MAIL(0x15B, State.IN_GAME));
		addPacket(new CM_READ_MAIL(0x159, State.IN_GAME));
		addPacket(new CM_READ_EXPRESS_MAIL(0x165, State.IN_GAME));
		addPacket(new CM_DELETE_MAIL(0x12C, State.IN_GAME));
		addPacket(new CM_GET_MAIL_ATTACHMENT(0x12F, State.IN_GAME));
		addPacket(new CM_MOVE_IN_AIR(0xF4, State.IN_GAME));
		addPacket(new CM_VIEW_PLAYER_DETAILS(0x13B, State.IN_GAME));
		addPacket(new CM_INVITE_TO_GROUP(0x124, State.IN_GAME));
		addPacket(new CM_EXCHANGE_REQUEST(0x106, State.IN_GAME));
		addPacket(new CM_TELEPORT_DONE(0xD6, State.IN_GAME));
		addPacket(new CM_LEGION_WH_KINAH(0x2E3, State.IN_GAME));
		addPacket(new CM_CHARACTER_EDIT(0xAE, State.AUTHED));
		addPacket(new CM_EXCHANGE_ADD_ITEM(0x107, State.IN_GAME));
		addPacket(new CM_EXCHANGE_ADD_KINAH(0x105, State.IN_GAME));
		addPacket(new CM_EXCHANGE_LOCK(0x11A, State.IN_GAME));
		addPacket(new CM_EXCHANGE_CANCEL(0x118, State.IN_GAME));
		addPacket(new CM_EXCHANGE_OK(0x11B, State.IN_GAME));
		addPacket(new CM_GROUP_DISTRIBUTION(0x103, State.IN_GAME));
		addPacket(new CM_DISTRIBUTION_SETTINGS(0x19C, State.IN_GAME));
		addPacket(new CM_PLAYER_STATUS_INFO(0x127, State.IN_GAME));
		addPacket(new CM_SHOW_BRAND(0x148, State.IN_GAME));
		addPacket(new CM_GROUP_LOOT(0x19F, State.IN_GAME));
		addPacket(new CM_GROUP_DATA_EXCHANGE(0x116, State.IN_GAME));
		addPacket(new CM_MANASTONE(0x2ED, State.IN_GAME));
		addPacket(new CM_GODSTONE_SOCKET(0x132, State.IN_GAME));
		addPacket(new CM_FUSION_WEAPONS(0x161, State.IN_GAME));
		addPacket(new CM_ITEM_REMODEL(0x13D, State.IN_GAME));
		addPacket(new CM_TOGGLE_SKILL_DEACTIVATE(0xE5, State.IN_GAME));
		addPacket(new CM_HOUSE_TELEPORT_BACK(0x126, State.IN_GAME));
		addPacket(new CM_OBJECT_SEARCH(0xA2, State.IN_GAME));
		addPacket(new CM_HOUSE_OPEN_DOOR(0x1A5, State.IN_GAME));
		addPacket(new CM_HOUSE_SCRIPT(0xF1, State.IN_GAME));
		addPacket(new CM_HOUSE_TELEPORT(0x1B1, State.IN_GAME));
		addPacket(new CM_HOUSE_EDIT(0x115, State.IN_GAME));
		addPacket(new CM_USE_HOUSE_OBJECT(0x1A7, State.IN_GAME));
		addPacket(new CM_HOUSE_SETTINGS(0x2EC, State.IN_GAME));
		addPacket(new CM_HOUSE_KICK(0x2EF, State.IN_GAME));
		addPacket(new CM_GET_HOUSE_BIDS(0x1BD, State.IN_GAME));
		addPacket(new CM_HOUSE_PAY_RENT(0x1A6, State.IN_GAME));
		addPacket(new CM_REGISTER_HOUSE(0x1B2, State.IN_GAME));
		addPacket(new CM_HOUSE_DECORATE(0x2E2, State.IN_GAME));
		addPacket(new CM_RELEASE_OBJECT(0x1A4, State.IN_GAME));
		addPacket(new CM_PLACE_BID(0x1B0, State.IN_GAME));
		addPacket(new CM_RECIPE_DELETE(0x13C, State.IN_GAME));
		addPacket(new CM_BROKER_SETTLE_LIST(0x144, State.IN_GAME));
		addPacket(new CM_REGISTER_BROKER_ITEM(0x146, State.IN_GAME));
		addPacket(new CM_BROKER_CANCEL_REGISTERED(0x147, State.IN_GAME));
		addPacket(new CM_BROKER_REGISTERED(0x150, State.IN_GAME));
		addPacket(new CM_BUY_BROKER_ITEM(0x151, State.IN_GAME));
		addPacket(new CM_BROKER_LIST(0x152, State.IN_GAME));
		addPacket(new CM_BROKER_SEARCH(0x153, State.IN_GAME));
		addPacket(new CM_REMOVE_ALTERED_STATE(0xFA, State.IN_GAME));
		addPacket(new CM_MAY_QUIT(0xDB, State.AUTHED, State.IN_GAME));
		addPacket(new CM_REPORT_PLAYER(0x186, State.IN_GAME));
		addPacket(new CM_PLAYER_LISTENER(0xCF, State.IN_GAME));		
		addPacket(new CM_REPLACE_ITEM(0x175, State.IN_GAME));		
		addPacket(new CM_LEGION_TABS(0x11E, State.IN_GAME));
		addPacket(new CM_BONUS_TITLE(0x18C, State.IN_GAME));
		addPacket(new CM_BUY_TRADE_IN_TRADE(0x13F, State.IN_GAME));
		addPacket(new CM_BREAK_WEAPONS(0x196, State.IN_GAME));
		addPacket(new CM_CHARGE_ITEM(0x2E1, State.IN_GAME));
		addPacket(new CM_USE_CHARGE_SKILL(0x18D, State.IN_GAME));
		addPacket(new CM_RECONNECT_AUTH(0x19E, State.AUTHED));
		addPacket(new CM_SHOW_BLOCKLIST(0x171, State.IN_GAME));
		addPacket(new CM_BLOCK_SET_REASON(0x14A, State.IN_GAME));
		addPacket(new CM_LEGION_UPLOAD_INFO(0x167, State.IN_GAME));
		addPacket(new CM_LEGION_UPLOAD_EMBLEM(0x164, State.IN_GAME));
		addPacket(new CM_BROKER_SETTLE_ACCOUNT(0x145, State.IN_GAME));
		addPacket(new CM_INSTANCE_LEAVE(0xC1, State.IN_GAME));
		addPacket(new CM_APPEARANCE(0x198, State.IN_GAME));
		addPacket(new CM_CAPTCHA(0xA1, State.IN_GAME));
		addPacket(new CM_CHANGE_CHANNEL(0x143, State.IN_GAME));
		addPacket(new CM_CHAT_PLAYER_INFO(0xCE, State.IN_GAME));
		addPacket(new CM_CHAT_GROUP_INFO(0x110, State.IN_GAME));
        addPacket(new CM_COMPOSITE_STONES(0x197, State.IN_GAME));


		// ////////////////////// 3.0 ///////////////////////
		/*
		 * addPacket(new CM_UNK(0x136, State.IN_GAME)); 
		 */
		// ////////////////2.6/////////////////////
		/*
		 * This packets possible is removed 4-ever 
		 * addPacket(new CM_PETITION(0x89, State.IN_GAME)); // 2.6
		 */
	}

	public AionPacketHandler getPacketHandler() {
		return handler;
	}

	private void addPacket(AionClientPacket prototype) {
		handler.addPacketPrototype(prototype);
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder {

		protected static final AionPacketHandlerFactory instance = new AionPacketHandlerFactory();
	}
}
