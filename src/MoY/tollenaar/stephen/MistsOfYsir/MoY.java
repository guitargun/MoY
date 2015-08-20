package MoY.tollenaar.stephen.MistsOfYsir;

import java.util.ArrayList;
import java.util.List;





import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import MoY.tollenaar.stephen.CEvents.ProgListener;
import MoY.tollenaar.stephen.CEvents.QuestProgListener;
import MoY.tollenaar.stephen.Commands.CommandsEvent;
import MoY.tollenaar.stephen.Commands.CommandsNPC;
import MoY.tollenaar.stephen.Commands.CommandsParty;
import MoY.tollenaar.stephen.Commands.CommandsPlayerinfo;
import MoY.tollenaar.stephen.Files.Filewriters;
import MoY.tollenaar.stephen.NPC.NPCNetworkManager;
import MoY.tollenaar.stephen.NPC.NPCHandler;
import MoY.tollenaar.stephen.PlayerInfo.Playerinfo;
import MoY.tollenaar.stephen.Quests.ProgressHarvest;
import MoY.tollenaar.stephen.Quests.ProgressKill;
import MoY.tollenaar.stephen.Quests.Quest;
import MoY.tollenaar.stephen.Quests.QuestChat;
import MoY.tollenaar.stephen.Quests.QuestClientSide;
import MoY.tollenaar.stephen.Quests.QuestEvent;
import MoY.tollenaar.stephen.Quests.QuestInvClick;
import MoY.tollenaar.stephen.Quests.QuestParticles;
import MoY.tollenaar.stephen.Quests.Questinteracts;
import MoY.tollenaar.stephen.Quests.QuestsServerSide;
import MoY.tollenaar.stephen.SkillsStuff.LevelSystems;
import MoY.tollenaar.stephen.SkillsStuff.Skillimprovement;
import MoY.tollenaar.stephen.Travel.Travel;
import MoY.tollenaar.stephen.Travel.TravelBoatEvent;
import MoY.tollenaar.stephen.Travel.TravelCartEvent;
import MoY.tollenaar.stephen.Travel.TravelDragonEvent;
import code.husky.mysql.MySQL;

public class MoY extends JavaPlugin {

	private MoY plugin;
	public QuestsServerSide questers;
	public Questinteracts qinteract;
	public QuestParticles qp;
	public DbStuff database;
	public Playerinfo playerinfo;
	public Travel tr;
	public RandomEvents re;
	public TravelBoatEvent boat;
	public TravelCartEvent cart;
	public TravelDragonEvent dragon;
	public QuestClientSide qqc;
	public Party party;
	public Quest q;
	public MobSpawns mob;
	public LevelSystems lvl;
	public Skillimprovement skill;
	public Filewriters fw;
	public QuestChat qc;
	public QuestInvClick qi;
	private List<QuestEvent> stortemp = new ArrayList<QuestEvent>();

	private NPCHandler handler;
	private NPCNetworkManager network;
	
	private FileConfiguration config;
	
	@Override
	public void onEnable() {
		config = this.getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		
		plugin = this;
	
		
		handler = new NPCHandler(this);
		if(!config.getBoolean("status")){
			handler.DublicateKiller();
			config.set("status", true);
			saveConfig();
		}
		network =  new NPCNetworkManager();
		
		playerinfo = new Playerinfo(this);
		
		q = new Quest(this);

		
		party = new Party(this);
		lvl = new LevelSystems(this);
		skill = new Skillimprovement(this);
		
		re = new RandomEvents(this);

		questers = new QuestsServerSide(this);
		
		fw = new Filewriters(this);
		for(QuestEvent in : stortemp){
			fw.SaveEvent(in);
		}
		stortemp.clear();
		
		qqc = new QuestClientSide(this);
		qinteract = new Questinteracts(this);

		boat = new TravelBoatEvent(this);
		cart = new TravelCartEvent(this);
		dragon = new TravelDragonEvent(this);
		tr = new Travel(this);

		qc = new QuestChat(this);
		qi = new QuestInvClick(this);
		qp = new QuestParticles(this);
		
		mob = new MobSpawns(this);

		database = new DbStuff(this);
		database.intvar();
		
		
		
		MySQL MySQl = database.MySQl;
		int poging = 0;
		while (MySQl.getConnection() == null) {
			MySQl.openConnection();
			if (MySQl.getConnection() == null) {
				poging++;
				getLogger()
						.info("Database connection lost. Reconection will be started");

			}
			if (poging == 2) {
				getLogger()
						.info("No Connection to Database. Plugin is deactivating. Reload server for database Connection");
				Bukkit.getPluginManager().disablePlugin(plugin);
				break;
			}

		}
		if (MySQl.getConnection() != null) {

			PluginManager pm = getServer().getPluginManager();

			pm.registerEvents(qinteract, this);

			pm.registerEvents(new Party(this), this);

			pm.registerEvents(playerinfo, this);
			pm.registerEvents(party, this);

			pm.registerEvents(lvl, this);
			pm.registerEvents(skill, this);
			pm.registerEvents(new ChatController(this), this);

			pm.registerEvents(tr, this);
			pm.registerEvents(re, this);

			pm.registerEvents(boat, this);
			pm.registerEvents(cart, this);
			pm.registerEvents(dragon, this);
			pm.registerEvents(mob, this);

			pm.registerEvents(qc, this);

			pm.registerEvents(new ProgressKill(this), this);
			pm.registerEvents(new ProgressHarvest(playerinfo), this);
			pm.registerEvents(new QuestProgListener(this), this);
			
			pm.registerEvents(new ProgListener(this), this);
			
			pm.registerEvents(handler, this);

			database.setcon(MySQl.getConnection());
			getLogger().info("Databse connection has succeed");
			database.TableCreate();
			database.closecon();
			database.loadall();
			
			config.set("status", false);
			saveConfig();
			
			getCommand("party").setExecutor(new CommandsParty(this));
			getCommand("skill").setExecutor(new CommandsPlayerinfo(this));
			getCommand("qnpc").setExecutor(new CommandsNPC(this));
			getCommand("lvl").setExecutor(new CommandsPlayerinfo(this));
			getCommand("event").setExecutor(new CommandsEvent(this));
			getCommand("harbor").setExecutor(new CommandsEvent(this));
			getCommand("trip").setExecutor(new CommandsEvent(this));
			getCommand("quest").setExecutor(new CommandsPlayerinfo(this));
			re.playerevent();
		}
	}

	@Override
	public void onDisable() {
		database.saveall();
		fw.saveall();
		re.cancelsced();
		getNPCHandler().onDisableEvent();
		config.set("status", true);
		saveConfig();
	}
	
	public void addStorage(QuestEvent event){
		stortemp.add(event);
	}
	
	public NPCHandler getNPCHandler(){
		return handler;
	}

	public NPCNetworkManager getNetwork() {
		return network;
	}


}
