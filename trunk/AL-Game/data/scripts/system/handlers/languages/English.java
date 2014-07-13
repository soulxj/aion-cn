/*
 * This file is part of NextGenCore <Ver:3.7>.
 *
 *  NextGenCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InPanic-Core is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InPanic-Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package languages;

import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.Language;

/**
 * @author A7xatomic!
 */
 
public class English extends Language {

	public English() {
		super("en");
		addSupportedLanguage("en_EN");
		addTranslatedMessage(CustomMessageId.SERVER_REVISION, "Server Version : %-6s");
		addTranslatedMessage(CustomMessageId.WELCOME_PREMIUM, "Welcome PREMIUM Member to %s server.\nCopyright 2012 Ver:3.5.\nSERVER RATES:\nExp Rate: %d\nQuest Rate: %d\nDrop Rate: %d\n");
		addTranslatedMessage(CustomMessageId.WELCOME_REGULAR, "Welcome to %s server.\nCopyright Ver:3.5.\nSERVER RATES:\nExp Rate: %d\nQuest Rate: %d\nDrop Rate: %d\n");
		addTranslatedMessage(CustomMessageId.WELCOME_BASIC, "Bienvenidos a :");
		addTranslatedMessage(CustomMessageId.INFO1, "Nota: Publicidad de otro servidor esta prohibida!");
		addTranslatedMessage(CustomMessageId.INFO2, "Nota: El Staff jamas te preguntara por los datos de tu cuenta!");
		addTranslatedMessage(CustomMessageId.INFO3, "Warning: si se detecta uso de algun hack en el servidor se dara ban instantaneo.");
		addTranslatedMessage(CustomMessageId.INFO4, " El respeto en nuestro servidor es muy importante, cualquier falta al staff o usuario sera sancionado.");
	    addTranslatedMessage(CustomMessageId.ENDMESSAGE, "Diviertete en: ");
		addTranslatedMessage(CustomMessageId.ANNOUNCE_GM_CONNECTION, " just entered into Atreia!");
		addTranslatedMessage(CustomMessageId.ANNOUNCE_MEMBER_CONNECTION, "%s just entered into Atreia.");
		addTranslatedMessage(CustomMessageId.COMMAND_NOT_ENOUGH_RIGHTS, "You not have rights to use this command");
		addTranslatedMessage(CustomMessageId.PLAYER_NOT_ONLINE, "Player not online");
		addTranslatedMessage(CustomMessageId.INTEGER_PARAMETER_REQUIRED, "Integer parameter required");
		addTranslatedMessage(CustomMessageId.INTEGER_PARAMETERS_ONLY, "Integer parameters only");
		addTranslatedMessage(CustomMessageId.SOMETHING_WRONG_HAPPENED, "Somthing wrong happened");
		addTranslatedMessage(CustomMessageId.COMMAND_DISABLED, "Command disabled");
		addTranslatedMessage(CustomMessageId.COMMAND_ADD_SYNTAX, "Syntax: //add <player name> <itemid> [<amount>]");
		addTranslatedMessage(CustomMessageId.COMMAND_ADD_ADMIN_SUCCESS, "Item success added to player %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADD_PLAYER_SUCCESS, "Administrator %s give you %d item(s)");
		addTranslatedMessage(CustomMessageId.COMMAND_ADD_FAILURE, "Item %d not exist or cant be added %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDDROP_SYNTAX, "Syntax: //adddrop <npc id> <itemid> <min> <max> <chance>");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_SYNTAX, "Syntax: //addset <player name> <id set>");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_SET_DOES_NOT_EXISTS, "Set %d not exist");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_NOT_ENOUGH_SLOTS, "Not enough inventory %d slots to add this set");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_CANNOT_ADD_ITEM, "Item %d can not be added to the %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_ADMIN_SUCCESS, "Set %d added success %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_PLAYER_SUCCESS, "%s give you set");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_SYNTAX, "Syntax: //addskill <skill id> <skill lvl");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_ADMIN_SUCCESS, "Skill %d added success %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_PLAYER_SUCCESS, "%s give you skill");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_SYNTAX, "Syntax: //addtitle <title id> <player name> [special]");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_TITLE_INVALID, "Title must be from 1 to 50");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_CANNOT_ADD_TITLE_TO_ME, "Cannot add title %d self");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_CANNOT_ADD_TITLE_TO_PLAYER, "Cannot add title %d to %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_ADMIN_SUCCESS_ME, "Title %d added success self");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_ADMIN_SUCCESS, "You success added title %d to player %s");
		addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_PLAYER_SUCCESS, "%s give you title %d");
		addTranslatedMessage(CustomMessageId.COMMAND_SEND_SYNTAX, "Syntax: //send <file name>");
		addTranslatedMessage(CustomMessageId.COMMAND_SEND_MAPPING_NOT_FOUND, "%s not found");
		addTranslatedMessage(CustomMessageId.COMMAND_SEND_NO_PACKET, "Send no packet");
		addTranslatedMessage(CustomMessageId.CHANNEL_WORLD_DISABLED, "Channel %s disabled, use channel %s or %s based on your race");
		addTranslatedMessage(CustomMessageId.CHANNEL_ALL_DISABLED, "All channels disabled");
		addTranslatedMessage(CustomMessageId.CHANNEL_ALREADY_FIXED, "Your channel has been successfully installed %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_FIXED, "Installed channel %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_NOT_ALLOWED, "You cant use this channel");
		addTranslatedMessage(CustomMessageId.CHANNEL_FIXED_BOTH, "Installed channels %s and %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_UNFIX_HELP, "Write %s to come out of the channel"); // ;)
		addTranslatedMessage(CustomMessageId.CHANNEL_NOT_FIXED, "You are not installed on the channel");
		addTranslatedMessage(CustomMessageId.CHANNEL_FIXED_OTHER, "Your chat is not installed on this channel, but on %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_RELEASED, "You come out of the channel %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_RELEASED_BOTH, "You are out of %s and %s");
		addTranslatedMessage(CustomMessageId.CHANNEL_BAN_ENDED, "You can rejoin the channels");
		addTranslatedMessage(CustomMessageId.CHANNEL_BAN_ENDED_FOR, "Player %s may again join the channel");
		addTranslatedMessage(CustomMessageId.CHANNEL_BANNED, "You can not access the channel, because %s ban you because of: %s, left to unlock: %s");
		addTranslatedMessage(CustomMessageId.COMMAND_MISSING_SKILLS_STIGMAS_ADDED, "%d skill %d stigma given to you");
		addTranslatedMessage(CustomMessageId.COMMAND_MISSING_SKILLS_ADDED, "%d ability given to you");
		addTranslatedMessage(CustomMessageId.USER_COMMAND_DOES_NOT_EXIST, "This game command exists");
		addTranslatedMessage(CustomMessageId.COMMAND_XP_DISABLED, "Accrual XP disabled. Enter. xpon to unlock");
		addTranslatedMessage(CustomMessageId.COMMAND_XP_ALREADY_DISABLED, "Accrual XP disabled");
		addTranslatedMessage(CustomMessageId.COMMAND_XP_ENABLED, "Accrual XP enabled");
		addTranslatedMessage(CustomMessageId.COMMAND_XP_ALREADY_ENABLED, "Accrual XP already enabled");
		addTranslatedMessage(CustomMessageId.DREDGION_LEVEL_TOO_LOW, "Your current level is too low to enter the Dredgion.");
		addTranslatedMessage(CustomMessageId.DEFAULT_FINISH_MESSAGE, "Finish!");
		
		/**
		 * PvP Spree Service
		 */
		addTranslatedMessage(CustomMessageId.SPREE1, "Locura Sangrienta");
		addTranslatedMessage(CustomMessageId.SPREE2, "Baño de Sangre");
		addTranslatedMessage(CustomMessageId.SPREE3, "Genocidio");
		addTranslatedMessage(CustomMessageId.KILL_COUNT, "Muertes Seguidas: ");
		addTranslatedMessage(CustomMessageId.CUSTOM_MSG1, " de los ");
		addTranslatedMessage(CustomMessageId.MSG_SPREE1, " es una ");
		addTranslatedMessage(CustomMessageId.MSG_SPREE1_1, " !");
		addTranslatedMessage(CustomMessageId.MSG_SPREE2, " Realiza un ");
		addTranslatedMessage(CustomMessageId.MSG_SPREE2_1, " ! Detente !");
		addTranslatedMessage(CustomMessageId.MSG_SPREE3, " Realiza un Verdadero ");
		addTranslatedMessage(CustomMessageId.MSG_SPREE3_1, " ! Corre !");
		addTranslatedMessage(CustomMessageId.SPREE_END_MSG1, "La Racha de Kills de ");
		addTranslatedMessage(CustomMessageId.SPREE_END_MSG2, " Fue Terminada por ");
		addTranslatedMessage(CustomMessageId.SPREE_END_MSG3, " Despues de ");
		addTranslatedMessage(CustomMessageId.SPREE_END_MSG4, " Muertes Seguidas!");
		addTranslatedMessage(CustomMessageId.SPREE_MONSTER_MSG, "un monstruo");
		
		/**
		 * PvP Service
		 */
		addTranslatedMessage(CustomMessageId.ADV_WINNER_MSG, "[PvP System] You kill player ");
		addTranslatedMessage(CustomMessageId.ADV_LOSER_MSG, "[PvP System] You killed by ");
		addTranslatedMessage(CustomMessageId.PLAP_LOST1, "[PL-AP] You lost ");
		addTranslatedMessage(CustomMessageId.PLAP_LOST2, "% of your total ap");
		addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD1, "You have ");
		addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD2, " Toll earned.");
		addTranslatedMessage(CustomMessageId.PVP_NO_REWARD1, "You dont won anything for killing ");
		addTranslatedMessage(CustomMessageId.PVP_NO_REWARD2, " because you killed him too often!");
		addTranslatedMessage(CustomMessageId.EASY_MITHRIL_MSG, "[Mithril System] you have acquired: ");
		
		/**
		 * Reward Service Login Messages
		 */
		addTranslatedMessage(CustomMessageId.REWARD10, "You can. Start using a level to get 10 Features!");
	    addTranslatedMessage(CustomMessageId.REWARD30, "You can. Start to use a Level 30 Equipment get!");
	    addTranslatedMessage(CustomMessageId.REWARD40, "You can. Start using a level to get 40 Features!");
	    addTranslatedMessage(CustomMessageId.REWARD50, "You can. Start a level use 50 features to get!");
	    addTranslatedMessage(CustomMessageId.REWARD60, "You can. Start to use a Level 60 Features get!");
		
		/**
		 * Advanced PvP System
		 */
		addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE1, "Today PvP Map: Reshanta");
	    addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE2, "Today PvP Map: Tiamaranta");
		addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE3, "Today PvP Map: Tiamaranta Eye");
		addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE4, "Today PvP Map: Gelkmaros");
		addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE5, "Today PvP Map: Inggison");
		
		/**
		 * Asmo, Ely and World Channel
		 */
		addTranslatedMessage(CustomMessageId.ASMO_FAIL, "You are Elyos! You can not use this chat. Advantage. Ely <message> to post a new faction chat!");
		addTranslatedMessage(CustomMessageId.ELY_FAIL, "You are Asmo! You can not use this chat. Advantage. Asmo <message> to post a new faction chat!");
		
		/**
		 * Wedding related
		 */
		addTranslatedMessage(CustomMessageId.WEDDINGNO1, "You can not use this command during the fight!");
		addTranslatedMessage(CustomMessageId.WEDDINGNO2, "Wedding has not started!");
		addTranslatedMessage(CustomMessageId.WEDDINGNO3, "You refused to marry!");
		addTranslatedMessage(CustomMessageId.WEDDINGYES, "You have accepted the marriage!");
		
		/**
		 * Clean Command related
		 */
		addTranslatedMessage(CustomMessageId.CANNOTCLEAN, "You have to enter an Item ID, or post a link!");
		addTranslatedMessage(CustomMessageId.CANNOTCLEAN2, "You do not own this item!");
		addTranslatedMessage(CustomMessageId.SUCCESSCLEAN, "Item has been successfully removed from a cube!");
		
		/**
	     * Faction Chat (since 3.0 we don't use it more)
		 */
		addTranslatedMessage(CustomMessageId.INFOMESSAGE, "If you want to use the faction chat: ");
	    
	    /**
		 * Mission check command related
		 */
		addTranslatedMessage(CustomMessageId.SUCCESCHECKED, "Mission successfully checked!");
	    /**
		 * No Exp Command
		 */
		addTranslatedMessage(CustomMessageId.EPACTIVATED, "Your EP were re-activated!");
		addTranslatedMessage(CustomMessageId.ACTODE, "To disable your EP, use noexp.");
		addTranslatedMessage(CustomMessageId.EPDEACTIVATED, "Your EP were disabled!");
		addTranslatedMessage(CustomMessageId.DETOAC, "To activate your EP, use noexp.");
		
		/**
	     * Auto Quest Command
		 */
		addTranslatedMessage(CustomMessageId.WRONGQID, "Please enter a correct quest Id!");
		addTranslatedMessage(CustomMessageId.NOTSTARTED, "Quest could not be started!");
		addTranslatedMessage(CustomMessageId.NOTSUPPORT, "This quest is not supported by this command!");
		
		/**
		 * Quest Restart Command
		 */
		addTranslatedMessage(CustomMessageId.CANNOTRESTART, "] can not be restarted");
	    
	    /**
		 * Exchange Toll Command
		 */
		addTranslatedMessage(CustomMessageId.TOLLTOBIG, "You have too many Toll!");
		addTranslatedMessage(CustomMessageId.TOLOWAP, "You do not have enough AP!");
		addTranslatedMessage(CustomMessageId.TOLOWTOLL, "You do not have enough Toll!");
		addTranslatedMessage(CustomMessageId.WRONGTOLLNUM, "Something went wrong!");
		
		/**
	     * Cube Command
		 */
		addTranslatedMessage(CustomMessageId.CUBE_ALLREADY_EXPANDED, "Your cube is fully extended!");
		addTranslatedMessage(CustomMessageId.CUBE_SUCCESS_EXPAND, "Your cube is successfully expanded!");
		
		/**
		 * GMList Command
		 */
		addTranslatedMessage(CustomMessageId.ONE_GM_ONLINE, "Miembros del Staff online: ");
		addTranslatedMessage(CustomMessageId.MORE_GMS_ONLINE, "Los siguientes miembros del Staff estan online: ");
		addTranslatedMessage(CustomMessageId.NO_GM_ONLINE, "Ningun miembro del Staff online!");
		
		/**
	     * Go Command (PvP Command)
		 */
		addTranslatedMessage(CustomMessageId.NOT_USE_WHILE_FIGHT, "No puedes usar este comando en Batalla!");
		addTranslatedMessage(CustomMessageId.NOT_USE_ON_PVP_MAP, "No puedes usar este comando en un  PvP Map!");
		addTranslatedMessage(CustomMessageId.LEVEL_TOO_LOW, "Solo puedes usar este comando si eres level 55 o mas alto!");
		
		/**
		 * Paint Command
		 */
		addTranslatedMessage(CustomMessageId.WRONG_TARGET, "Please use a legal target!");
	 
	    /**
		 * Shiva Command
		 */
		addTranslatedMessage(CustomMessageId.ENCHANT_SUCCES, "All your items have been enchanted to: ");
		addTranslatedMessage(CustomMessageId.ENCHANT_INFO, "Info: This command all your enchanted items on <value>!");
		addTranslatedMessage(CustomMessageId.ENCHANT_SAMPLE, "For example, would enchant all your items to 15 (eq 15.)");
		
		/**
		 * Userinfo Command
		 */
		addTranslatedMessage(CustomMessageId.CANNOT_SPY_PLAYER, "You can not get information from other players!");
		
		/**
		 * Custom BossSpawnService
		 */
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_SPAWNED, " was spawned in ");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_KILLED, " was killed from ");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME1, "Awaked Dragon");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME2, "Shadowshift");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME3, "Flamestorm");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME4, "Thunderstorm");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME5, "Terra-Explosion");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME6, "The Canyonguard");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME7, "Padmarashka");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME8, "Sematariux");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME9, "Cedella");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME10, "Tarotran");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME11, "Raksha Boilheart");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_NAME12, "Big Red Orb");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_UNK, "Unknown NPC");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC1, "Beluslan");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC2, "Morheim");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC3, "Eltnen");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC4, "Heiron");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC5, "Inggison");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC6, "Gelkmaros");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC7, "Silentera Canyon");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC8, "Padmarashkas Cave");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC9, "Sarpan");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC10, "Tiamaranta");
		addTranslatedMessage(CustomMessageId.CUSTOM_BOSS_LOC_UNK, "");
		
		/**
		 * Battleground System
		 */
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE1, "You have earned %d battleground points.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE2, "You have lost %d battleground points.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE3, "The Battleground: %s is now ready to start. You will be teleported in 30 seconds.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE4, "seconds before starting ...");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE5, "You are now invisible.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE6, "You are now immortal.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE7, "The battleground is now open!");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE8, "The battleground will end in 30 seconds !");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE9, "Betting time has now ended.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE10, "The battle has now ended! Click the survey to show the rank board. If you are dead, use the spell Return and you will be teleported back.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE11, "You are now visible.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE12, "You are now mortal.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE13, "Do you want to join a battleground again?");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE14, "You are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE15, "Use your spell Return to leave the battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE16, "You are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUND_MESSAGE17, "Use the command .bg unregister to cancel your registration.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE1, "Only group leader can register group.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE2, "You are now registered for the battleground: %s");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE3, "Please wait while your team is in formation...");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE4, "All members can't join this battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE5, "You are too many to register in this battleground now.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE6, "You are now registered for the battleground:%s");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE7, "Please wait while your team is in formation...");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE8, "All members can't join this battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE9, "You are too many to register in this battleground now.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE10, "Some members are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE11, "You are now registered for the battleground: %s");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE12, "Please wait while your team is in formation...");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE13, "You are now registered to observe the battleground: %s");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE14, "Please wait until the battleground is full...");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE15, "No battleground available for you with your level and your battleground points.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE16, "Register to battlegrounds");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE016, "You can register for the following battlegrounds :");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE17, "No battleground available.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE18, "Register to battlegrounds");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDMANAGER_MESSAGE018, "You can register for the following battlegrounds :");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDHEALERCONTROLLER_1, "You were alone in the battleground, you have been teleported back.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDHEALERCONTROLLER_2, "You were alone in the battleground, you have been teleported back.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDAGENTCONTROLLER_1, "You are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDFLAGCONTROLLER_2, "unhandled case.");
		addTranslatedMessage(CustomMessageId.BATTLEGROUNDFLAGCONTROLLER_3, "Do you want to register in a battleground ?");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE0, "You cannot register for battlegrounds while you are in prison.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE1, "You are already in a battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE2, "Use your spell Return to leave the battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE3, "You are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE4, "Use the command .bg unregister to cancel your registration.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE5, "You are already registered in the Arena Team waiting list.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE6, "Use the command .arena to cancel your registration.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE7, "You are already registered in the Arena Team waiting list.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE8, "Use your spell Return to leave the Arena Team Battle.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE9, "You are already in a battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE10, "Use your spell Return to leave the battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE11, "You are already registered in a battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE12, "Use the command .bg unregister to cancel your registration.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE13, "You are playing in a battleground, not an observer.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE14, "You are now visible.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE15, "You are now mortal.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE16, "You have lost your bet of %d kinah.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE17, "You are not observing any battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE18, "You are not registered in any battleground or the battleground is over.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE19, "The exchange tool is not available.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE20, "The exchange rate is 1 BG point for 3 Abyss points.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE21, "To exchange some points, write .bg exchange <bg_points_number>");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE22, "You don't have enough BG points.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE23, "You have lost %d BG points.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE24, "Syntax error. Use .bg exchange <bg_points_number>.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE25, "You are not registered in any battleground.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE26, "Registration canceled.");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE27, ".bg register : register in a BG");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE28, ".bg observe : observe a battleground");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE29, ".bg unregister : unregister from the BG (before starting)");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE30, ".bg stop : stop observe and back to home");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE31, ".bg rank : see your rank during a BG");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE32, ".bg points : : to see your points");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE33, ".bet : bet on a faction during observe mode");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE34, "This command doesn't exist, use .bg help");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE35, "register");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE36, "observe");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE37, "stop");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE38, "rank");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE39, "stat");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE40, "exchange");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE41, "unregister");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE42, "help");
		addTranslatedMessage(CustomMessageId.COMMAND_BATTLEGROUND_MESSAGE43, "points");
		
		/**
		 * FFA System
		 */
		addTranslatedMessage(CustomMessageId.FFA_IS_ALREADY_IN_TEAM, "Please leave your team and try again.");
		addTranslatedMessage(CustomMessageId.FFA_IS_ALREADY_IN, "You're already on Anarchy Batleground");
		addTranslatedMessage(CustomMessageId.FFA_FROZEN_MESSAGE, "You're frozen for a while...");
		addTranslatedMessage(CustomMessageId.FFA_CURRENT_PLAYERS, "Players dentro :");
		addTranslatedMessage(CustomMessageId.FFA_USAGE, "Uso: .ffa enter, para entrar en la arena .leave, para salir de la arena.\n.ffa info, para saber cuantos estan en el Batleground ");
		addTranslatedMessage(CustomMessageId.FFA_YOU_KICKED_OUT, "You aren't any more in Anarchy Batleground!");
		addTranslatedMessage(CustomMessageId.FFA_YOUR_NOT_IN, "You're not member of Anarchy Batleground.");
		addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_1, "Entra al Batleground escribiendo .ffa enter ! ");
		addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_2, " Estan Dentro!.");
		addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_3, "Unete a nuestra Batleground! escribe .ffa enter");
		addTranslatedMessage(CustomMessageId.FFA_GHOST_KILL, "A ghost killed ");
		addTranslatedMessage(CustomMessageId.FFA_KILL_MESSAGE, " has slain ");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_1, " do his best!");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_2, " is on the trip!");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_3, " want more blood!");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_4, " is like a crazy monster!");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_5, " are you okay? ");
		addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_6, " fucking kills?");
		
		/**
		 * Exchange Command
		 */
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_ITEM, "You dont have enough from: ");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_AP, "You dont have enough ap, you only have: ");
		
		/**
		 * Medal Command
		 */
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_SILVER, "You dont have enough silver medals.");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_GOLD, "You dont have enough gold medals.");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_PLATIN, "You dont have enough platin medals.");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_MITHRIL, "You dont have enough mithril medals.");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_AP2, "You dont have enough ap, you need: ");
		addTranslatedMessage(CustomMessageId.EXCHANGE_SILVER, "You have exchange [item:186000031] to [item:186000030].");
		addTranslatedMessage(CustomMessageId.EXCHANGE_GOLD, "You have exchange [item:186000030] to [item:186000096].");
		addTranslatedMessage(CustomMessageId.EXCHANGE_PLATIN, "You have exchange [item:186000096] to [item:186000147].");
		addTranslatedMessage(CustomMessageId.EXCHANGE_MITHRIL, "You have exchange [item:186000147] to [item:186000223].");
		addTranslatedMessage(CustomMessageId.EX_SILVER_INFO, "\nSyntax: .medal silver - Exchange Silver to Gold.");
		addTranslatedMessage(CustomMessageId.EX_GOLD_INFO, "\nSyntax: .medal gold - Exchange Gold to Platin.");
		addTranslatedMessage(CustomMessageId.EX_PLATIN_INFO, "\nSyntax: .medal platinum - Exchnage Platin to Mithril.");
		addTranslatedMessage(CustomMessageId.EX_MITHRIL_INFO, "\nSyntax: .medal mithril - Exchange Mithril to Honorable Mithril.");
		
		/**
		 * Dimensional Vortex
		 */
		addTranslatedMessage(CustomMessageId.DIM_VORTEX_SPAWNED_ELYOS, "The Dimensional Vortex was opened for Elyos!");
		addTranslatedMessage(CustomMessageId.DIM_VORTEX_SPAWNED_ASMO, "The Dimensional Vortex was opened for Asmo!");
		addTranslatedMessage(CustomMessageId.DIM_VORTEX_DESPAWNED, "The Dimensional Assault finished!");
		
		/**
		 * Invasion Rift
		 */
		addTranslatedMessage(CustomMessageId.INVASION_RIFT_MIN_LEVEL, "Your level is too low to enter.");
		addTranslatedMessage(CustomMessageId.INVASION_RIFT_ELYOS, "A rift for Pandaemonium is open at Ingisson");
		addTranslatedMessage(CustomMessageId.INVASION_RIFT_ASMOS, "A rift for Sanctum is open at Gelkmaros");
		
		/**
		 * Legendary Raid Spawn Events
		 */
		addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_SPAWNED_ASMO, "[Legendary Raid Spawn Event] Ragnarok ha aparecido para Asmodians en Beluslan!");
		addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_SPAWNED_ELYOS, "[Legendary Raid Spawn Event] Omega ha aparecido para Elyos en Heiron!");
		addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_DESPAWNED_ASMO, "[Legendary Raid Spawn Event] Ragnarok ha desaparecido, nadie fue a matarlo!");
		addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_DESPAWNED_ELYOS, "[Legendary Raid Spawn Event] Omega ha desaparecido, nadie fue a matarlo!");
		
		/**
		 * HonorItems Command
		 */
		addTranslatedMessage(CustomMessageId.PLATE_ARMOR, "Plate Armor");
		addTranslatedMessage(CustomMessageId.LEATHER_ARMOR, "Leather Armor");
		addTranslatedMessage(CustomMessageId.CLOTH_ARMOR, "Cloth Armor");
		addTranslatedMessage(CustomMessageId.CHAIN_ARMOR, "Chain Armor");
		addTranslatedMessage(CustomMessageId.WEAPONS, "Weapons");
		addTranslatedMessage(CustomMessageId.PLATE_ARMOR_PRICES, "Plate Armor Prices");
		addTranslatedMessage(CustomMessageId.LEATHER_ARMOR_PRICES, "Leather Armor Prices");
		addTranslatedMessage(CustomMessageId.CLOTH_ARMOR_PRICES, "Cloth Armor Prices");
		addTranslatedMessage(CustomMessageId.CHAIN_ARMOR_PRICES, "Chain Armor Prices");
		addTranslatedMessage(CustomMessageId.WEAPONS_PRICES, "Weapons Prices");
		addTranslatedMessage(CustomMessageId.NOT_ENOUGH_MEDALS, "You dont have enough Medals, you need: ");
		addTranslatedMessage(CustomMessageId.PLATE_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 1");
		addTranslatedMessage(CustomMessageId.LEATHER_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 6");
		addTranslatedMessage(CustomMessageId.CLOTH_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 11");
		addTranslatedMessage(CustomMessageId.CHAIN_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 16");
		addTranslatedMessage(CustomMessageId.WEAPONS_USE_INFO, "Use .items and the equal ID (Example: .items 21");
		addTranslatedMessage(CustomMessageId.SUCCESSFULLY_TRADED, "You got successfully your Trade!");
	}
}
