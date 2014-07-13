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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.network.NioServer;
import com.aionemu.commons.network.ServerCfg;
import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.AEInfos;
import com.aionemu.gameserver.ai2.AI2Engine;
import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.configs.Config;
import com.aionemu.gameserver.configs.main.*;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.instance.InstanceEngine;
import com.aionemu.gameserver.services.instance.DredgionService2;
import com.aionemu.gameserver.model.GameEngine;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.house.MaintenanceTask;
import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.network.BannedMacManager;
import com.aionemu.gameserver.network.aion.GameConnectionFactoryImpl;
import com.aionemu.gameserver.network.chatserver.ChatServer;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.services.*;
import com.aionemu.gameserver.services.abyss.AbyssRankUpdateService;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.player.PlayerEventService;
import com.aionemu.gameserver.services.player.PlayerLimitService;
import com.aionemu.gameserver.services.reward.RewardService;
import com.aionemu.gameserver.services.transfers.PlayerTransferService;
import com.aionemu.gameserver.spawnengine.TemporarySpawnEngine;
import com.aionemu.gameserver.spawnengine.InstanceRiftSpawnManager;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.taskmanager.fromdb.TaskFromDBManager;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.utils.chathandlers.ChatProcessor;
import com.aionemu.gameserver.utils.cron.ThreadPoolManagerRunnableRunner;
import com.aionemu.gameserver.utils.gametime.DateTimeUtil;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.utils.javaagent.JavaAgentUtils;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;
import com.aionemu.gameserver.world.zone.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <tt>GameServer </tt> is the main class of the application and represents the whole game server.<br>
 * This class is also an entry point with main() method.
 *
 * @author -Nemesiss-
 * @author SoulKeeper
 * @author cura
 */
public class GameServer {

	private static final Logger log = LoggerFactory.getLogger(GameServer.class);

	//TODO remove all this shit
	private static int ELYOS_COUNT = 0;
	private static int ASMOS_COUNT = 0;
	private static double ELYOS_RATIO = 0.0;
	private static double ASMOS_RATIO = 0.0;
	private static final ReentrantLock lock = new ReentrantLock();

	private static void initalizeLoggger() {
		new File("./log/backup/").mkdirs();
		File[] files = new File("log").listFiles((dir, name) -> name.endsWith(".log"));

		if (files != null && files.length > 0) {
			byte[] buf = new byte[1024];
			try {
				String outFilename = "./log/backup/" + new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date()) + ".zip";
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
				out.setMethod(ZipOutputStream.DEFLATED);
				out.setLevel(Deflater.BEST_COMPRESSION);

				for (File logFile : files) {
					FileInputStream in = new FileInputStream(logFile);
					out.putNextEntry(new ZipEntry(logFile.getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
					logFile.delete();
				}
				out.close();
			} catch (IOException e) {
			}
		}
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure("config/slf4j-logback.xml");
		} catch (JoranException je) {
			throw new RuntimeException("Failed to configure loggers, shutting down...", je);
		}
	}

	/**
	 * Launching method for GameServer
	 *
	 * @param args arguments, not used
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		final GameEngine[] parallelEngines = new GameEngine[] {
			QuestEngine.getInstance(), InstanceEngine.getInstance(),
			AI2Engine.getInstance(), ChatProcessor.getInstance()
		};
		
		final CountDownLatch progressLatch = new CountDownLatch(parallelEngines.length);
		
		initalizeLoggger();
		initUtilityServicesAndConfig();
		DataManager.getInstance();
		Util.printSection("IDFactory");
		IDFactory.getInstance();
		
		Util.printSection("Zone");
		ZoneService.getInstance().load(null);

		Util.printSection("Geodata");
		GeoService.getInstance().initializeGeo();
		// ZoneService.getInstance().saveMaterialZones();
		System.gc();

		Util.printSection("World");
		World.getInstance();
		
		Util.printSection("Drops");
		DropRegistrationService.getInstance();

		GameServer gs = new GameServer();
		// Set all players is offline
		DAOManager.getDAO(PlayerDAO.class).setPlayersOffline(false);
		DatabaseCleaningService.getInstance();

		BannedMacManager.getInstance();

		for (int i = 0; i < parallelEngines.length; i++) {
			final int index = i;
			ThreadPoolManager.getInstance().execute(() -> parallelEngines[index].load(progressLatch));
		}
		
		try {
			progressLatch.await();
		}
		catch (InterruptedException e1) {
		}

		// This is loading only siege location data
		// No Siege schedule or spawns
		Util.printSection("Location Data");
		BaseService.getInstance().initRiftLocations();
		SiegeService.getInstance().initSiegeLocations();
		VortexService.getInstance().initVortexLocations();
		RiftService.getInstance().initRiftLocations();

		Util.printSection("Spawns");
		SpawnEngine.spawnAll();
		RiftService.getInstance().initRifts();
		InstanceRiftSpawnManager.spawnAll();
		TemporarySpawnEngine.spawnAll();
		if (SiegeConfig.SIEGE_ENABLED)
			ShieldService.getInstance().spawnAll();

		Util.printSection("Limits");
		LimitedItemTradeService.getInstance().start();
		if (CustomConfig.LIMITS_ENABLED)
			PlayerLimitService.getInstance().scheduleUpdate();
		GameTimeManager.startClock();
		
		// Init Sieges... It's separated due to spawn engine.
		// It should not spawn siege NPCs
		Util.printSection("Siege Schedule initialization");
		SiegeService.getInstance().initSieges();

		Util.printSection("World Bases initialization");
		BaseService.getInstance().initBases();

		Util.printSection("Serial Killers initialization");
		SerialKillerService.getInstance().initSerialKillers();

		Util.printSection("Dispute Lands initialization");
		DisputeLandService.getInstance().init();

		Util.printSection("TaskManagers");
		PacketBroadcaster.getInstance();

		GameTimeService.getInstance();
		AnnouncementService.getInstance();
		DebugService.getInstance();
		WeatherService.getInstance();
		BrokerService.getInstance();
		Influence.getInstance();
		ExchangeService.getInstance();
		PeriodicSaveService.getInstance();
		PetitionService.getInstance();

		if (AIConfig.SHOUTS_ENABLE)
			NpcShoutsService.getInstance();
		InstanceService.load();

		FlyRingService.getInstance();
		LanguageHandler.getInstance();
		if (!GeoDataConfig.GEO_MATERIALS_ENABLE)
			CuringZoneService.getInstance();
		RoadService.getInstance();
		HTMLCache.getInstance();
		AbyssRankUpdateService.getInstance().scheduleUpdate();
		TaskFromDBManager.getInstance();
		if (AutoGroupConfig.AUTO_GROUP_ENABLE && AutoGroupConfig.DREDGION2_ENABLE) {
			Util.printSection("Dredgion");
			DredgionService2.getInstance().start();
		}
		if (CustomConfig.ENABLE_REWARD_SERVICE)
			RewardService.getInstance();
		if (EventsConfig.EVENT_ENABLED)
			PlayerEventService.getInstance();
		if (EventsConfig.ENABLE_EVENT_SERVICE)
			EventService.getInstance().start();
		if (WeddingsConfig.WEDDINGS_ENABLE)
			WeddingService.getInstance();

		AdminService.getInstance();
		PlayerTransferService.getInstance();
		HousingBidService.getInstance().start();
		MaintenanceTask.getInstance();
		TownService.getInstance();
		ChallengeTaskService.getInstance();

		Util.printSection("System");
		AEVersions.printFullVersionInfo();
		System.gc();
		AEInfos.printAllInfos();

		Util.printSection("GameServerLog");
		log.info("AL Game Server started in " + (System.currentTimeMillis() - start) / 1000 + " seconds.");

		gs.startServers();

		Runtime.getRuntime().addShutdownHook(ShutdownHook.getInstance());

		if (GSConfig.ENABLE_RATIO_LIMITATION) {
			addStartupHook(() -> {
                lock.lock();
                try {
                    ASMOS_COUNT = DAOManager.getDAO(PlayerDAO.class).getCharacterCountForRace(Race.ASMODIANS);
                    ELYOS_COUNT = DAOManager.getDAO(PlayerDAO.class).getCharacterCountForRace(Race.ELYOS);
                    computeRatios();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
                displayRatios(false);
            });
		}

		onStartup();
	}

	/**
	 * Starts servers for connection with aion client and login\chat server.
	 */
	private void startServers() {
		Util.printSection("Starting Network");
		NioServer nioServer = new NioServer(NetworkConfig.NIO_READ_WRITE_THREADS, new ServerCfg(NetworkConfig.GAME_BIND_ADDRESS, NetworkConfig.GAME_PORT, "Game Connections", new GameConnectionFactoryImpl()));

		LoginServer ls = LoginServer.getInstance();
		ChatServer cs = ChatServer.getInstance();

		ls.setNioServer(nioServer);
		cs.setNioServer(nioServer);

		// Nio must go first
		nioServer.connect();
		ls.connect();

		if (GSConfig.ENABLE_CHAT_SERVER)
			cs.connect();
	}

	/**
	 * Initialize all helper services, that are not directly related to aion gs, which includes:
	 * <ul>
	 * <li>Logging</li>
	 * <li>Database factory</li>
	 * <li>Thread pool</li>
	 * </ul>
	 * This method also initializes {@link Config}
	 */
	private static void initUtilityServicesAndConfig() {
		// Set default uncaught exception handler
		Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());

		// make sure that callback code was initialized
		//if (JavaAgentUtils.isConfigured())
		//	log.info("JavaAgent [Callback Support] is configured.");

		// Initialize cron service
		CronService.initSingleton(ThreadPoolManagerRunnableRunner.class);

		// init config
		Config.load();
		// DateTime zone override from configs
		DateTimeUtil.init();
		// Second should be database factory
		Util.printSection("DataBase");
		DatabaseFactory.init();
		// Initialize DAOs
		DAOManager.init();
		// Initialize thread pools
		Util.printSection("Threads");
		ThreadConfig.load();
		ThreadPoolManager.getInstance();
	}

	private static Set<StartupHook> startUpHooks = new HashSet<StartupHook>();

	public synchronized static void addStartupHook(StartupHook hook) {
		if (startUpHooks != null)
			startUpHooks.add(hook);
		else
			hook.onStartup();
	}

	private synchronized static void onStartup() {
		final Set<StartupHook> startupHooks = startUpHooks;

		startUpHooks = null;

		for (StartupHook hook : startupHooks)
			hook.onStartup();
	}

	public interface StartupHook {

		public void onStartup();
	}

	/**
	 * @param race
	 * @param i
	 */
	public static void updateRatio(Race race, int i) {
		lock.lock();
		try {
			switch (race) {
				case ASMODIANS:
					GameServer.ASMOS_COUNT += i;
					break;
				case ELYOS:
					GameServer.ELYOS_COUNT += i;
					break;
				default:
					break;
			}

			computeRatios();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}

		displayRatios(true);
	}

	private static void computeRatios() {
		if ((GameServer.ASMOS_COUNT <= GSConfig.RATIO_MIN_CHARACTERS_COUNT) && (GameServer.ELYOS_COUNT <= GSConfig.RATIO_MIN_CHARACTERS_COUNT)) {
			GameServer.ASMOS_RATIO = GameServer.ELYOS_RATIO = 50.0;
		} else {
			GameServer.ASMOS_RATIO = GameServer.ASMOS_COUNT * 100.0 / (GameServer.ASMOS_COUNT + GameServer.ELYOS_COUNT);
			GameServer.ELYOS_RATIO = GameServer.ELYOS_COUNT * 100.0 / (GameServer.ASMOS_COUNT + GameServer.ELYOS_COUNT);
		}
	}

	private static void displayRatios(boolean updated) {
		log.info("FACTIONS RATIO " + (updated ? "UPDATED " : "") + ": E " + String.format("%.1f", GameServer.ELYOS_RATIO)
				+ " % / A " + String.format("%.1f", GameServer.ASMOS_RATIO) + " %");
	}

	public static double getRatiosFor(Race race) {
		switch (race) {
			case ASMODIANS:
				return GameServer.ASMOS_RATIO;
			case ELYOS:
				return GameServer.ELYOS_RATIO;
			default:
				return 0.0;
		}
	}

	public static int getCountFor(Race race) {
		switch (race) {
			case ASMODIANS:
				return GameServer.ASMOS_COUNT;
			case ELYOS:
				return GameServer.ELYOS_COUNT;
			default:
				return 0;
		}
	}

}