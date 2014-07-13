/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.configs;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.commons.configs.CommonsConfig;
import com.aionemu.commons.configs.DatabaseConfig;
import com.aionemu.commons.configuration.ConfigurableProcessor;
import com.aionemu.commons.utils.PropertiesUtils;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.administration.DeveloperConfig;
import com.aionemu.gameserver.configs.main.*;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.utils.Util;

/**
 * @author Nemesiss, SoulKeeper
 */
public class Config {

	protected static final Logger log = LoggerFactory.getLogger(Config.class);

	/**
	 * Initialize all configs in com.aionemu.gameserver.configs package
	 */
	public static void load() {
		try {
			Properties myProps = null;
			try {
				log.info("Loading: mygs.properties");
				myProps = PropertiesUtils.load("./config/mygs.properties");
			}
			catch (Exception e) {
				log.info("No override properties found");
			}

			// Administration
			Util.printSection("Administration");
			String administration = "./config/administration";

			Properties[] adminProps = PropertiesUtils.loadAllFromDirectory(administration);
			PropertiesUtils.overrideProperties(adminProps, myProps);

			ConfigurableProcessor.process(AdminConfig.class, adminProps);
			log.info("Loading: " + administration + "/admin.properties");

			ConfigurableProcessor.process(DeveloperConfig.class, adminProps);
			log.info("Loading: " + administration + "/developer.properties");

			// Main
			Util.printSection("Main");
			String main = "./config/main";

			Properties[] mainProps = PropertiesUtils.loadAllFromDirectory(main);
			PropertiesUtils.overrideProperties(mainProps, myProps);

			ConfigurableProcessor.process(AIConfig.class, mainProps);
			log.info("Loading: " + main + "/ai.properties");

			ConfigurableProcessor.process(AutoGroupConfig.class, mainProps);
			log.info("Loading: " + main + "/autogroup.properties");

			ConfigurableProcessor.process(CommonsConfig.class, mainProps);
			log.info("Loading: " + main + "/commons.properties");

			ConfigurableProcessor.process(CacheConfig.class, mainProps);
			log.info("Loading: " + main + "/cache.properties");
                        
			ConfigurableProcessor.process(CleaningConfig.class, mainProps);
			log.info("Loading: " + main + "/cleaning.properties");

			ConfigurableProcessor.process(CraftConfig.class, mainProps);
			log.info("Loading: " + main + "/craft.properties");

			ConfigurableProcessor.process(CustomConfig.class, mainProps);
			log.info("Loading: " + main + "/custom.properties");

			ConfigurableProcessor.process(DropConfig.class, mainProps);
			log.info("Loading: " + main + "/drop.properties");

			ConfigurableProcessor.process(EnchantsConfig.class, mainProps);
			log.info("Loading: " + main + "/enchants.properties");

			ConfigurableProcessor.process(EventsConfig.class, mainProps);
			log.info("Loading: " + main + "/events.properties");

			ConfigurableProcessor.process(FallDamageConfig.class, mainProps);
			log.info("Loading: " + main + "/falldamage.properties");

			ConfigurableProcessor.process(GSConfig.class, mainProps);
			log.info("Loading: " + main + "/gameserver.properties");

			ConfigurableProcessor.process(GeoDataConfig.class, mainProps);
			log.info("Loading: " + main + "/geodata.properties");

			ConfigurableProcessor.process(GroupConfig.class, mainProps);
			log.info("Loading: " + main + "/group.properties");

			ConfigurableProcessor.process(HousingConfig.class, mainProps);
			log.info("Loading: " + main + "/housing.properties");

			ConfigurableProcessor.process(HTMLConfig.class, mainProps);
			log.info("Loading: " + main + "/html.properties");

			ConfigurableProcessor.process(InGameShopConfig.class, mainProps);
			log.info("Loading: " + main + "/ingameshop.properties");

			ConfigurableProcessor.process(LegionConfig.class, mainProps);
			log.info("Loading: " + main + "/legion.properties");

			ConfigurableProcessor.process(LoggingConfig.class, mainProps);
			log.info("Loading: " + main + "/logging.properties");

			ConfigurableProcessor.process(MembershipConfig.class, mainProps);
			log.info("Loading: " + main + "/membership.properties");

			ConfigurableProcessor.process(NameConfig.class, mainProps);
			log.info("Loading: " + main + "/name.properties");

			ConfigurableProcessor.process(PeriodicSaveConfig.class, mainProps);
			log.info("Loading: " + main + "/periodicsave.properties");

			ConfigurableProcessor.process(PlayerTransferConfig.class, mainProps);
			log.info("Loading: " + main + "/playertransfer.properties");

			ConfigurableProcessor.process(PricesConfig.class, mainProps);
			log.info("Loading: " + main + "/prices.properties");

			ConfigurableProcessor.process(PunishmentConfig.class, mainProps);
			log.info("Loading: " + main + "/punishment.properties");

			ConfigurableProcessor.process(RankingConfig.class, mainProps);
			log.info("Loading: " + main + "/ranking.properties");

			ConfigurableProcessor.process(RateConfig.class, mainProps);
			log.info("Loading: " + main + "/rates.properties");
			
			ConfigurableProcessor.process(SecurityConfig.class, mainProps);
			log.info("Loading: " + main + "/security.properties");

			ConfigurableProcessor.process(ShutdownConfig.class, mainProps);
			log.info("Loading: " + main + "/shutdown.properties");

			ConfigurableProcessor.process(SiegeConfig.class, mainProps);
			log.info("Loading: " + main + "/siege.properties");

			ConfigurableProcessor.process(ThreadConfig.class, mainProps);
			log.info("Loading: " + main + "/thread.properties");

			ConfigurableProcessor.process(WeddingsConfig.class, mainProps);
			log.info("Loading: " + main + "/weddings.properties");

			ConfigurableProcessor.process(WorldConfig.class, mainProps);
			log.info("Loading: " + main + "/world.properties");

			// Network
			Util.printSection("Network");
			String network = "./config/network";

			Properties[] networkProps = PropertiesUtils.loadAllFromDirectory(network);
			PropertiesUtils.overrideProperties(networkProps, myProps);

			log.info("Loading: " + network + "/database.properties");
			ConfigurableProcessor.process(DatabaseConfig.class, networkProps);

			log.info("Loading: " + network + "/network.properties");
			ConfigurableProcessor.process(NetworkConfig.class, networkProps);

		}
		catch (Exception e) {
			log.error("Can't load gameserver configuration: ", e);
			throw new Error("Can't load gameserver configuration: ", e);
		}

		IPConfig.load();
	}
	
	/**
	 * Reload all configs in com.aionemu.gameserver.configs package
	 */
	public static void reload() {
		try {
			Properties myProps = null;
			try {
				log.info("Loading: mygs.properties");
				myProps = PropertiesUtils.load("./config/mygs.properties");
			}
			catch (Exception e) {
				log.info("No override properties found");
			}

			// Administration
			String administration = "./config/administration";

			Properties[] adminProps = PropertiesUtils.loadAllFromDirectory(administration);
			PropertiesUtils.overrideProperties(adminProps, myProps);

			ConfigurableProcessor.process(AdminConfig.class, adminProps);
			log.info("Reload: " + administration + "/admin.properties");

			ConfigurableProcessor.process(DeveloperConfig.class, adminProps);
			log.info("Reload: " + administration + "/developer.properties");

			// Main
			String main = "./config/main";

			Properties[] mainProps = PropertiesUtils.loadAllFromDirectory(main);
			PropertiesUtils.overrideProperties(mainProps, myProps);

			ConfigurableProcessor.process(AIConfig.class, mainProps);
			log.info("Reload: " + main + "/ai.properties");
			
			ConfigurableProcessor.process(AutoGroupConfig.class, mainProps);
			log.info("Loading: " + main + "/autogroup.properties");

			ConfigurableProcessor.process(CommonsConfig.class, mainProps);
			log.info("Reload: " + main + "/commons.properties");

			ConfigurableProcessor.process(CacheConfig.class, mainProps);
			log.info("Reload: " + main + "/cache.properties");

			ConfigurableProcessor.process(CraftConfig.class, mainProps);
			log.info("Reload: " + main + "/craft.properties");

			ConfigurableProcessor.process(CustomConfig.class, mainProps);
			log.info("Reload: " + main + "/custom.properties");

			ConfigurableProcessor.process(DropConfig.class, mainProps);
			log.info("Reload: " + main + "/drop.properties");

			ConfigurableProcessor.process(EnchantsConfig.class, mainProps);
			log.info("Reload: " + main + "/enchants.properties");

			ConfigurableProcessor.process(EventsConfig.class, mainProps);
			log.info("Reload: " + main + "/events.properties");

			ConfigurableProcessor.process(FallDamageConfig.class, mainProps);
			log.info("Reload: " + main + "/falldamage.properties");

			ConfigurableProcessor.process(GSConfig.class, mainProps);
			log.info("Reload: " + main + "/gameserver.properties");

			ConfigurableProcessor.process(GeoDataConfig.class, mainProps);
			log.info("Reload: " + main + "/geodata.properties");

			ConfigurableProcessor.process(GroupConfig.class, mainProps);
			log.info("Reload: " + main + "/group.properties");

			ConfigurableProcessor.process(HousingConfig.class, mainProps);
			log.info("Reload: " + main + "/housing.properties");

			ConfigurableProcessor.process(HTMLConfig.class, mainProps);
			log.info("Reload: " + main + "/html.properties");

			ConfigurableProcessor.process(InGameShopConfig.class, mainProps);
			log.info("Reload: " + main + "/ingameshop.properties");

			ConfigurableProcessor.process(LegionConfig.class, mainProps);
			log.info("Reload: " + main + "/legion.properties");

			ConfigurableProcessor.process(LoggingConfig.class, mainProps);
			log.info("Reload: " + main + "/logging.properties");

			ConfigurableProcessor.process(MembershipConfig.class, mainProps);
			log.info("Reload: " + main + "/membership.properties");

			ConfigurableProcessor.process(NameConfig.class, mainProps);
			log.info("Reload: " + main + "/name.properties");

			ConfigurableProcessor.process(PeriodicSaveConfig.class, mainProps);
			log.info("Reload: " + main + "/periodicsave.properties");

			ConfigurableProcessor.process(PlayerTransferConfig.class, mainProps);
			log.info("Reload: " + main + "/playertransfer.properties");

			ConfigurableProcessor.process(PricesConfig.class, mainProps);
			log.info("Reload: " + main + "/prices.properties");

			ConfigurableProcessor.process(PunishmentConfig.class, mainProps);
			log.info("Reload: " + main + "/punishment.properties");

			ConfigurableProcessor.process(RankingConfig.class, mainProps);
			log.info("Reload: " + main + "/ranking.properties");

			ConfigurableProcessor.process(RateConfig.class, mainProps);
			log.info("Reload: " + main + "/rates.properties");
			
			ConfigurableProcessor.process(SecurityConfig.class, mainProps);
			log.info("Reload: " + main + "/security.properties");

			ConfigurableProcessor.process(ShutdownConfig.class, mainProps);
			log.info("Reload: " + main + "/shutdown.properties");

			ConfigurableProcessor.process(SiegeConfig.class, mainProps);
			log.info("Reload: " + main + "/siege.properties");

			ConfigurableProcessor.process(ThreadConfig.class, mainProps);
			log.info("Reload: " + main + "/thread.properties");

			ConfigurableProcessor.process(WeddingsConfig.class, mainProps);
			log.info("Reload: " + main + "/weddings.properties");

			ConfigurableProcessor.process(WorldConfig.class, mainProps);
			log.info("Reload: " + main + "/world.properties");
		}
		catch (Exception e) {
			log.error("Can't reload configuration: ", e);
			throw new Error("Can't reload configuration: ", e);
		}
	}
}
