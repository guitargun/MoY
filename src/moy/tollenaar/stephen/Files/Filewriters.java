package moy.tollenaar.stephen.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import moy.tollenaar.stephen.MistsOfYsir.MoY;
import moy.tollenaar.stephen.NPC.NPCEntity;
import moy.tollenaar.stephen.PlayerInfo.Playerinfo;
import moy.tollenaar.stephen.PlayerInfo.Playerstats;
import moy.tollenaar.stephen.Quests.QuestEvent;
import moy.tollenaar.stephen.Quests.QuestHarvest;
import moy.tollenaar.stephen.Quests.QuestKill;
import moy.tollenaar.stephen.Quests.QuestTalkto;
import moy.tollenaar.stephen.Quests.QuestsServerSide;
import moy.tollenaar.stephen.SkillsStuff.FurnaceStorage;
import moy.tollenaar.stephen.Speech.SpeechNode;
import moy.tollenaar.stephen.Speech.SpeechTrait;

public class Filewriters {
	private MoY plugin;
	private QuestsServerSide quest;
	private Playerinfo playerinfo;
	private LibraryWriters libwriter;
	
	private File dummymessages;

	private File TravelBoatnearmis;
	private File TravelCartnearmis;
	private File TravelBoatmis;
	private File TravelCartmis;

	private File TravelBoatleaving;
	private File TravelCartleaving;
	private File TravelDragonleaving;

	private File TravelBoatlogout;
	private File TravelCartlogout;
	private File TravelDragonlogout;

	private File utilitiesyml;
	public FileConfiguration utilitiesconfig;
	private File desserter;

	private File quests;

	private File harvest;
	private File kill;
	private File event;
	private File talkto;

	private File furnaces;

	private File players;

	private File rewards;

	private File library;

	private File speechTraits;

	public void filecheck() {
		File direct = new File(plugin.getDataFolder(), "messages");
		if (!direct.exists()) {
			direct.mkdir();
		}
		library = new File(plugin.getDataFolder(), "library");
		if (!library.exists()) {
			library.mkdir();
		}
		this.libwriter = new LibraryWriters(library);
		
		speechTraits = new File(plugin.getDataFolder(), "speechTraits");
		if (!speechTraits.exists()) {
			speechTraits.mkdir();
		}

		quests = new File(plugin.getDataFolder(), "quests");
		if (!quests.exists()) {
			quests.mkdir();
		}
		rewards = new File(plugin.getDataFolder(), "rewards.txt");
		if (!rewards.exists()) {
			try {
				rewards.createNewFile();
				PrintWriter w = new PrintWriter(rewards);
				w.println("skill");
				w.println("eco");
				w.println("give");
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		players = new File(plugin.getDataFolder(), "players");
		if (!players.exists()) {
			players.mkdir();
		}

		harvest = new File(quests, "harvest");
		if (!harvest.exists()) {
			harvest.mkdir();
		}
		kill = new File(quests, "kill");
		if (!kill.exists()) {
			kill.mkdir();
		}
		event = new File(quests, "event");
		if (!event.exists()) {
			event.mkdir();
		}
		talkto = new File(quests, "talkto");
		if (!talkto.exists()) {
			talkto.mkdir();
		}

		desserter = new File(plugin.getDataFolder(), "desserters");
		if (!desserter.exists()) {
			desserter.mkdir();
		}

		dummymessages = new File(direct, "dummymessages.txt");
		if (!dummymessages.exists()) {
			try {
				dummymessages.createNewFile();
				defaultdummymessage(dummymessages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		furnaces = new File(plugin.getDataFolder(), "furnaces");
		if (!furnaces.exists()) {
			furnaces.mkdir();
		}

		TravelBoatnearmis = new File(direct, "TravelBoatAlmostThere.txt");
		if (!TravelBoatnearmis.exists()) {
			try {
				TravelBoatnearmis.createNewFile();
				defaultboatalmostthere(TravelBoatnearmis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelCartnearmis = new File(direct, "TravelCartAlmostThere.txt");
		if (!TravelCartnearmis.exists()) {
			try {
				TravelCartnearmis.createNewFile();
				defaultcartalmostthere(TravelCartnearmis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelBoatmis = new File(direct, "TravelBoatMis.txt");
		if (!TravelBoatmis.exists()) {
			try {
				TravelBoatmis.createNewFile();
				defaultboatmiss(TravelBoatmis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelCartmis = new File(direct, "TravelCartMis.txt");
		if (!TravelCartmis.exists()) {
			try {
				TravelCartmis.createNewFile();
				defaultcartmassmiss(TravelCartmis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelBoatleaving = new File(direct, "TravelBoatLeaving.txt");
		if (!TravelBoatleaving.exists()) {
			try {
				TravelBoatleaving.createNewFile();
				defaultboatleavin(TravelBoatleaving);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelCartleaving = new File(direct, "TravelCartLeaving.txt");
		if (!TravelCartleaving.exists()) {
			try {
				TravelCartleaving.createNewFile();
				defautlcartleaving(TravelCartleaving);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		TravelDragonleaving = new File(direct, "TravelDragonLeaving.txt");
		if (!TravelDragonleaving.exists()) {
			try {
				TravelDragonleaving.createNewFile();
				defaultdragonleaving(TravelDragonleaving);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelBoatlogout = new File(direct, "TravelBoatLogout.txt");
		if (!TravelBoatlogout.exists()) {
			try {
				TravelBoatlogout.createNewFile();
				defaultboatlogout(TravelBoatlogout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelCartlogout = new File(direct, "TravelCartLogout.txt");
		if (!TravelCartlogout.exists()) {
			try {
				TravelCartlogout.createNewFile();
				defaultcartlogout(TravelCartlogout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TravelDragonlogout = new File(direct, "TravelDragonLogout.txt");
		if (!TravelDragonlogout.exists()) {
			try {
				TravelDragonlogout.createNewFile();
				defaultdragonlogout(TravelDragonlogout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		utilitiesyml = new File(direct, "Utilities.yml");
		if (!utilitiesyml.exists()) {
			try {
				utilitiesyml.createNewFile();
				utilitiesconfig = YamlConfiguration
						.loadConfiguration(utilitiesyml);
				defaultymlvalues(utilitiesconfig);
				saveyml(utilitiesconfig, utilitiesyml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			utilitiesconfig = YamlConfiguration.loadConfiguration(utilitiesyml);
			saveyml(utilitiesconfig, utilitiesyml);
		}
	}

	// public getters
	public String GetUtilityLine(String line) {
		return utilitiesconfig.getString(line);
	}

	public List<String> getRewards() {
		List<String> l = new ArrayList<String>();
		try {
			Scanner in = new Scanner(rewards);
			while (in.hasNextLine()) {
				l.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return l;
	}

	public void saveplayer(Playerstats p) {
		File f = new File(players, p.getPlayeruuid().toString() + ".dat");
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(f));
			out.writeObject(p);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveplayers() {
		for (UUID player : playerinfo.getplayers()) {
			Playerstats p = playerinfo.getplayer(player);
			File f = new File(players, player.toString() + ".dat");
			try {
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(f));
				out.writeObject(p);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private int getUniqueID(UUID npcuuid) {
		for (int key : quest.uniquenpcid.keySet()) {
			if (quest.uniquenpcid.get(key).equals(npcuuid)) {
				return key;
			}
		}
		throw new NullPointerException("NPC IS NON EXISTING");
	}

	public void saveTraits(UUID npcuuid) {
		NPCEntity npc = plugin.getNPCHandler().getNPCByUUID(npcuuid);
		int id = getUniqueID(npcuuid);
		File file = new File(speechTraits, Integer.toString(id) + ".yml");
		if (!file.exists() && npc.getNodes().size() > 0) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (file.exists() & npc.getNodes().size() == 0) {
			file.delete();
			return;
		}
		if (npc.getNodes().size() > 0) {
			FileConfiguration config = YamlConfiguration
					.loadConfiguration(file);
			if (npc.getNodes().size() > 0) {
				for (int node : npc.getNodes()) {
					SpeechNode n = SpeechNode.getNode(node);
					String path = Integer.toString(node);
					config.set(path + ".Response", n.getResponse());
					config.set(path + ".ID", n.getId());
					for (int traits : n.getTraits()) {
						SpeechTrait t = SpeechTrait.getSpeech(traits);
						String traitpath = path;
						traitpath += ".SpeechTrait." + traits;
						config.set(traitpath + ".Message", t.getMessage());
						config.set(traitpath + ".Depth", t.getDepth());
					}
				}
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void loadTraits(UUID npcuuid) {
		int id = getUniqueID(npcuuid);
		File file = new File(speechTraits, Integer.toString(id) + ".yml");
		if (file.exists()) {
			FileConfiguration config = YamlConfiguration
					.loadConfiguration(file);
			for (String in : config.getKeys(false)) {
				int node = Integer.parseInt(in);
				SpeechNode n = new SpeechNode(node, plugin);
				n.setResponse(config.getString(in + ".Response"));
				for (String traits : config.getConfigurationSection(
						in + ".SpeechTrait").getKeys(false)) {
					int trait = Integer.parseInt(traits);
					SpeechTrait t = new SpeechTrait(trait);
					t.setDepth(config.getInt(in + ".SpeechTrait." + traits
							+ ".Depth"));
					t.setMessage(config.getString(in + ".SpeechTrait." + traits
							+ ".Message"));
					n.addTrait(t);
				}
				plugin.getNPCHandler().getNPCByUUID(npcuuid).addNode(n.getId());
			}

		}
	}

	public boolean loadplayer(UUID playeruuid) {
		for (File file : players.listFiles()) {
			if (file.getName().replace(".dat", "")
					.equals(playeruuid.toString())) {
				try {
					ObjectInputStream in = new ObjectInputStream(
							new FileInputStream(file));
					Playerstats p = (Playerstats) in.readObject();
					if (playerinfo.getplayer(p.getPlayeruuid()) == null) {
						playerinfo.loadplayer(p);
					}
					in.close();
					return true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void loadplayers() {
		for (File file : players.listFiles()) {
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(file));
				Playerstats p = (Playerstats) in.readObject();
				if (playerinfo.getplayer(p.getPlayeruuid()) == null) {
					playerinfo.loadplayer(p);
				}
				for (String type : p.getactivetype()) {
					for (int number : p.getactives(type)) {
						if (!type.equals("talkto")) {
							quest.addProgress(p.getPlayeruuid(), type, number);
						}
					}
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	public void AddDesserter(String playeruuid, String message) {
		File temp = new File(desserter, playeruuid + ".txt");
		try {
			temp.createNewFile();
			FileWriter wr = new FileWriter(temp);
			wr.write(message);
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// stuff for the YML
	public void saveyml(FileConfiguration custom, File ymlFile) {
		try {
			custom.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void defaultymlvalues(FileConfiguration config) {
		config.options()
				.header("All parts with a %escapethingy% has to be in that sentence. Don't add more of these and don't change the sign of these. You can place them in a different order.");
		config.set("DragonNotEnoughMoney",
				"Sorry you don't have enough money to board this dragon coach.");
		config.set("TravelEvent", "There is trouble ahead. Prepare yourself.");
		config.set("TravelArrive", "You have arrived at your destination.");
		config.set("AlmostLeaving",
				"The %type% is about to leave. Hurry you only have %time% left.");
		config.set(
				"Boarding",
				"You board the %type%. And a friendly crewmember lets you know that it leaves in %time% %min% and %sec% seconds.");

		config.set(
				"QuestComplete",
				"You have completed the quest %questname%. Go back to %npc% to claim your reward.");
		config.set(
				"HarvestProg",
				"The quest %name% has made progress. You have mined %count% of the block %block%.");
		config.set("KillProg",
				"The quest %name% has made progress. You have killed %count%  %monster%'s.");

		config.set("Ribesalspawn", "Prepare to be annihilated.");
		config.set("Ribesalwin", "You have been annihilated.");
		config.set("RibesalWrong", "Don't bother me. You are not the victim.");
		config.set("Randomgift", "You have been a good boy, take this.");

		config.set("Partylvlup",
				"You have gained 1 level thanks to %playername%. You are now level %lvl%.");
		config.set("Partyname.tolong",
				"This partyname is too long use less than 50 characters.");
		config.set("Partyname.taken", "This partyname is already taken.");
		config.set("Partyjoin", "%member% has joined the party.");
		config.set("Partyinvite.victim",
				"%leader% invited you for the party %partyname%.");
		config.set("Partyinvite.leader",
				"You invited %player% for the party %partyname%.");
		config.set("Partykick.leader", "You kicked %member% out of the party.");
		config.set("Partykick.member", "You were kicked out of the party.");
		config.set("Partykick.himself",
				"One simply can't ick themself out of the party.");
		config.set("newpartyleader.wrongparty",
				"New party leader is not in your party.");
		config.set("newpartyleader.notinparty",
				"New party leader is not in a party.");
		config.set("newpartyleader.notfound", "Player not found.");
		config.set("Partyinvite.no-open", "You don't have any open invites.");
		config.set("Partydisband", "The party leader has disbanded the party.");
		config.set("Partyleave.member", "You have left the party.");
		config.set(
				"Partyleave.leader",
				"You can't leave the party as a party leader. Disband the party or assign a new leader.");
	}

	public void saveall() {
		savequests();
		try {
			for (Block in : FurnaceStorage.GetAll()) {
				savefurnace(FurnaceStorage.GetStorage(in));
			}
		} catch (NoClassDefFoundError ex) {
			return;
		}
		saveplayers();
	}

	public void loadall() {
		for (File in : furnaces.listFiles()) {
			loadfurnace(in);
		}

	}

	public void savefurnace(FurnaceStorage storage) {
		if (storage.getFurnace().getType() != Material.FURNACE
				&& storage.getFurnace().getType() != Material.BURNING_FURNACE) {
			FurnaceStorage.RemoveStorage(storage.getFurnace());
			return;
		}
		File stor = new File(furnaces, storage.toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(stor);
		config.set("Block.X", storage.getFurnace().getLocation().getX());
		config.set("Block.Y", storage.getFurnace().getLocation().getY());
		config.set("Block.Z", storage.getFurnace().getLocation().getZ());
		config.set("Block.W", storage.getFurnace().getLocation().getWorld()
				.getName());
		config.set("ToBurn", storage.getToBurn());
		config.set("Failures", storage.getFailures());

		for (UUID keys : storage.getPlayers().keySet()) {
			config.set("Players." + keys.toString(),
					storage.getPlayers().get(keys));
		}

		try {
			config.save(stor);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadfurnace(File file) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		Location l = new Location(Bukkit.getWorld(config.getString("Block.W")),
				config.getDouble("Block.X"), config.getDouble("Block.Y"),
				config.getDouble("Block.Z"));
		if (l.getBlock().getType() != Material.FURNACE
				&& l.getBlock().getType() != Material.BURNING_FURNACE) {
			file.delete();
			return;
		}
		FurnaceStorage storage = new FurnaceStorage(l.getBlock(), plugin);
		ItemStack tob = config.getItemStack("ToBurn");
		ItemStack failure = config.getItemStack("Failures");

		HashMap<UUID, Integer> pl = new HashMap<UUID, Integer>();
		for (String in : config.getConfigurationSection("Players").getKeys(
				false)) {
			pl.put(UUID.fromString(in), config.getInt("Players." + in));
		}

		storage.loadall(pl, tob, failure);
	}

	public void removefile(FurnaceStorage storage) {
		for (File file : furnaces.listFiles()) {
			if (file.getName().replace(".yml", "").equals(storage.toString())) {
				file.delete();
				break;
			}
		}
	}

	// default files
	public void defaultboatlogout(File file) {
		ArrayList<String> message = new ArrayList<String>();
		message.add("You jumped of the boat yelling something about a mermaid, luckily you were saved by a fisherman that brought you back.");
		message.add("You jumped naked in the ocean screaming you are the lizard queen. You washed back a shore and a stranger clothed you again.");
		message.add("Have you been drinking? Look where you turned up!");
		message.add("Don't drink salt water!");
		message.add("You've been sleeping the entire journey and ended up back where you started!");
		message.add("A stranger knocked you unconscious. You ended up back where you started.");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : message) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultcartlogout(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("You fell a sleep and ended back at the beginning of your journey.");
		messages.add("While sleeping you fell of the cart. A friendly pig farmer brought you back.");
		messages.add("While traveling you were hit by a low hanging branch and knocked unconscious. You ended up back where you started.");
		messages.add("You ended up back at the beginning after you jumped of the cart screaming the cows were trying to kill you.");
		messages.add("To much alcohol? You got stranded back at the begin.");
		messages.add("A stranger tried robbing you, you jumped of the cart to follow him but lost him here.");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultdragonlogout(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("You fell a sleep and the dragon flew you back.");
		messages.add("You fell out of the coach. Luckily a tree saved your life and you crawled back to the start.");
		messages.add("Your coach has crashed. A strange hairy creature brought you back to this city.");
		messages.add("I don't know what happened but somehow you turned up back here!");
		messages.add("A major dragon fight caused you to end up here again.");
		messages.add("Due to the stormy weather your dragon decided to return back here.");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultdragonleaving(File file) {
		ArrayList<String> message = new ArrayList<String>();
		message.add("With one giant pull you're in the air and on your way.");
		message.add("The Dragon roars as he begins moving the coach.");
		message.add("You begin climbing so high that the air is thinner than you immagined.");
		message.add("You begin sitting confortly while the dragon moves to his take off place.");
		message.add("Pray that the dragon won't fly upside down.");
		message.add("While climbing you look down. You see everything becomes smaller and smaller.");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : message) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defautlcartleaving(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("The oxes seem to be happy to depart with you onboard.");
		messages.add("Look at Old Bessy pull the cart!");
		messages.add("With a cracking sound the cart starts to move.");
		messages.add("Is that smell you or is is one of the oxes?");
		messages.add("The entire cart starts to shake as it departs.");
		messages.add("Pray that this journey ends well!");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultboatleavin(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Ah the smell of salt in the air!");
		messages.add("A cold sea breeze hits you in the face as the ship moves.");
		messages.add("You feel the ship moving as it leaves port.");
		messages.add("Slowly the ship is moving towards the open water.");
		messages.add("And we are on the move!");
		messages.add("The harbor is getting smaller and smaller at the horizon.");
		messages.add("Our voyage has begun!");
		messages.add("I hope nothing bad happens to the ship while sailing!");
		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultcartmassmiss(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("If they didn't break a wheel they'll be here in ");
		messages.add("Unless they get stuck in the mud again they'll be here in ");
		messages.add("I think they are about halfway now. They'll be here in ");
		messages.add("Let's pray they didn't get jumped by a pack of wolfs so we will see them again in ");
		messages.add("Old Bessy is pulling the cart today and boy, does she know how to pull it! They''ll be here in ");
		messages.add("If they are on time they should be here in ");
		messages.add("Guess they are about half way now, still ");
		messages.add("They have to pass an inn so there's no knowing if they will be on time. Normally they are back in ");
		messages.add("Did those bastards left to early again? You'll have to wait ");
		messages.add("Bad luck there, they have just left. You can wait ");
		messages.add("They left in a hurry yelling that some smelly player was coming. They'll be back in ");
		messages.add("Too late! I expect them back in ");

		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultcartalmostthere(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("I can smell the oxes! They must be close.");
		messages.add("Unless that was another cow I heard moo'ing they must be arriving pretty soon.");
		messages.add("They should be here any minute now!");
		messages.add("I'm surprised they haven't arrived yet.");

		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void defaultboatmiss(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("The boat is sailing the oceans, I'll expect it to arrive in");
		messages.add("Unless they stopped to raid a town they will arrive back in port in ");
		messages.add("I think they are about halfway now. They'll be here in ");
		messages.add("I heard they are battling a sea snake! If they win the fight they'll be here in ");
		messages.add("If the ship didn't sink it will be here in ");
		messages.add("They sailed right trough a storm but still I expect them to be here in ");
		messages.add("If the oarsman are as strong as you look I'm sure they'll be here in ");
		messages.add("Pray to the gods the captain knows what he is doing so he can be here in ");
		messages.add("They have passed the point of no return and will be here in ");
		messages.add("If the captain didn't fall for that mermaid again they will be here in ");

		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void defaultboatalmostthere(File file) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("I can hear the captain yell, they must be close!");
		messages.add("I see a dot at the horizon, that must be them!");
		messages.add("By my estimates they must be arriving any minute now.");
		messages.add("I'm surprised they aren't here already!");
		messages.add("Wait! They haven't arrived?");

		try {
			PrintWriter fw = new PrintWriter(file);
			for (String in : messages) {
				fw.println(in);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void defaultdummymessage(File dummymessage) {
		ArrayList<String> message = new ArrayList<String>();
		message.add("Hi.");
		message.add("How are you?");
		message.add("Don't hurt me please!");
		message.add("The more quests you do the richer you become!");
		message.add("I heared that Runes are very valuable here.");
		message.add("Watch out for that crazy wizard around here, he turned himself to a cow already!");
		message.add("May the Gods have mercy on us.");
		message.add("Hope the king doens't show up for more tax money, I gave him everything already.");
		message.add("I heared that the dragon coach had to fight a lvl 50 wither the other day. They barely survived it!");
		message.add("Pay attention to your lvl's, it can and will save you if you know what you can use.");
		message.add("I hope that the new load of slaves are better than the previous ones.");
		message.add("Ever went with that giant dragon boat? I wish I could go, but I am to afraid to meet any pirates.");

		try {
			PrintWriter fw = new PrintWriter(dummymessage);
			for (String in : message) {
				fw.println(in);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// return of the arrays
	public ArrayList<String> TravelDragonLogout() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelCartlogout);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelCartLogout() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelCartlogout);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelBoatLogout() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelBoatlogout);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelDragonLeaving() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelDragonleaving);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelCartLeaving() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelCartleaving);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelBoatLeaving() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelBoatleaving);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelCartmis() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelCartmis);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> TravelBoatmiss() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelBoatmis);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> Travelcartnearmiss() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelCartnearmis);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public ArrayList<String> Travelboatnearmiss() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(TravelBoatnearmis);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return message;
	}

	public ArrayList<String> dummymessage() {
		ArrayList<String> message = new ArrayList<String>();
		try {
			Scanner in = new Scanner(dummymessages);
			while (in.hasNextLine()) {
				message.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public Filewriters(MoY instance) {
		this.plugin = instance;
		this.quest = instance.qserver;
		this.playerinfo = instance.playerinfo;
		filecheck();
		loadall();
		loadquests();
		loadplayers();

	}

	public void loadquests() {
		for (File f : harvest.listFiles()) {
			LoadHarvest(f);
		}
		for (File f : kill.listFiles()) {
			LoadKill(f);
		}
		for (File f : talkto.listFiles()) {
			LoadTalk(f);
		}
		for (File f : event.listFiles()) {
			LoadEvent(f);
		}
	}

	public void savequests() {
		for (Integer i : quest.AllHarvestId()) {
			SaveHarvest(quest.returnharvest(i));
		}
		for (Integer i : quest.AllKillId()) {
			SaveKill(quest.returnkill(i));
		}
		for (Integer i : quest.AllTalkToId()) {
			SaveTalk(quest.returntalkto(i));
		}
		for (Integer i : quest.AllEvents()) {
			SaveEvent(quest.returneventquest(i));
		}
	}

	public void SaveHarvest(QuestHarvest h) {
		File f = new File(harvest, h.getQuestnumber() + ".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		config.set("ID", h.getQuestnumber());
		config.set("Name", h.getName());
		config.set("Item", h.getItemId());
		config.set("MinLvl", h.getMinlvl());
		config.set("Reward", h.getReward());
		config.set("Count", h.getCount());
		config.set("Message", h.getMessage());
		config.set("Prereq", h.getPrereq());
		config.set("Delay", h.getDelay());
		config.set("State", h.getState());
		if(h.getNodes().size() > 0){
			for(int n : h.getNodes()){
				SpeechNode node = SpeechNode.getNode(n);
				config.set("SpeechNode." + n + ".Response", node.getResponse());
				config.set("SpeechNode." + n + ".ID", node.getId());
				for(int t : node.getTraits()){
					SpeechTrait trait = SpeechTrait.getSpeech(t);
					String path = "SpeechNode." + n + ".SpeechTrait." + t;
					config.set(path + ".Message", trait.getMessage());
					config.set(path + ".Depth", trait.getDepth());
				}
			}
		}
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadHarvest(File f) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		int id = config.getInt("ID");
		List<String> reward = config.getStringList("Reward");
		String name = config.getString("Name");
		String delay = config.getString("Delay");
		String prereq = config.getString("Prereq");
		int minlvl = config.getInt("MinLvl");
		int count = config.getInt("Count");
		String itemid = config.getString("Item");
		String message = config.getString("Message");
		String state = config.getString("State");
		if (state == null) {
			state = "disabled";
		}
		quest.loadharvest(id, name, reward, delay, minlvl, message, prereq,
				count, itemid, state);
		QuestHarvest h = quest.returnharvest(id);
		try{
			for (String in : config.getConfigurationSection("SpeechNode").getKeys(false)) {
				int node = Integer.parseInt(in);
				h.AddNode(node);
				SpeechNode n = new SpeechNode(node, plugin);
				n.setResponse(config.getString("SpeechNode." +in + ".Response"));
				for (String traits : config.getConfigurationSection("SpeechNode." +
						in + ".SpeechTrait").getKeys(false)) {
					int trait = Integer.parseInt(traits);
					SpeechTrait t = new SpeechTrait(id, 2, trait);
					t.setDepth(config.getInt("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Depth"));
					t.setMessage(config.getString("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Message"));
					n.addTrait(t);
				}
			}
		}catch(NullPointerException ex){
			return;
		}
	}

	public void SaveKill(QuestKill h) {
		File f = new File(kill, h.getQuestnumber() + ".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		config.set("ID", h.getQuestnumber());
		config.set("Name", h.getName());
		config.set("Monster", h.getMonster());
		config.set("MinLvl", h.getMinlvl());
		config.set("Reward", h.getReward());
		config.set("Count", h.getCount());
		config.set("Message", h.getMessage());
		config.set("Prereq", h.getPrereq());
		config.set("Delay", h.getDelay());
		config.set("State", h.getState());
		if(h.getNodes().size() > 0){
			for(int n : h.getNodes()){
				SpeechNode node = SpeechNode.getNode(n);
				config.set("SpeechNode." + n + ".Response", node.getResponse());
				config.set("SpeechNode." + n + ".ID", node.getId());
				for(int t : node.getTraits()){
					SpeechTrait trait = SpeechTrait.getSpeech(t);
					String path = "SpeechNode." + n + ".SpeechTrait." + t;
					config.set(path + ".Message", trait.getMessage());
					config.set(path + ".Depth", trait.getDepth());
				}
			}
		}
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadKill(File f) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		int id = config.getInt("ID");
		List<String> reward = config.getStringList("Reward");
		String name = config.getString("Name");
		String delay = config.getString("Delay");
		String prereq = config.getString("Prereq");
		int minlvl = config.getInt("MinLvl");
		int count = config.getInt("Count");
		String monster = config.getString("Monster");
		String message = config.getString("Message");
		String state = config.getString("State");
		if (state == null) {
			state = "disabled";
		}
		quest.loadkill(id, name, reward, delay, minlvl, message, prereq, count,
				monster, state);
		QuestKill h = quest.returnkill(id);
		try{
			for (String in : config.getConfigurationSection("SpeechNode").getKeys(false)) {
				int node = Integer.parseInt(in);
				h.AddNode(node);
				SpeechNode n = new SpeechNode(node, plugin);
				n.setResponse(config.getString("SpeechNode." +in + ".Response"));
				for (String traits : config.getConfigurationSection("SpeechNode." +
						in + ".SpeechTrait").getKeys(false)) {
					int trait = Integer.parseInt(traits);
					SpeechTrait t = new SpeechTrait(id, 1, trait);
					t.setDepth(config.getInt("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Depth"));
					t.setMessage(config.getString("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Message"));
					n.addTrait(t);
				}
			}
		}catch(NullPointerException ex){
			return;
		}
	}

	public void deletequest(String type, int id) {
		switch (type) {
		case "kill":
			for (File f : kill.listFiles()) {
				if (f.getName().replace(".yml", "")
						.equals(Integer.toString(id))) {
					f.delete();
					break;
				}
			}
			break;
		case "harvest":
			for (File f : harvest.listFiles()) {
				if (f.getName().replace(".yml", "")
						.equals(Integer.toString(id))) {
					f.delete();
					break;
				}
			}
			break;
		case "talkto":
			for (File f : talkto.listFiles()) {
				if (f.getName().replace(".yml", "")
						.equals(Integer.toString(id))) {
					f.delete();
					break;
				}
			}
			break;
		case "event":
			for (File f : event.listFiles()) {
				if (f.getName().replace(".yml", "")
						.equals(Integer.toString(id))) {
					f.delete();
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	public void SaveTalk(QuestTalkto h) {
		File f = new File(talkto, h.getQuestnumber() + ".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		config.set("ID", h.getQuestnumber());
		config.set("Name", h.getName());
		config.set("Person", h.getNpcid());
		config.set("MinLvl", h.getMinlvl());
		config.set("Reward", h.getReward());
		config.set("Message", h.getMessage());
		config.set("Prereq", h.getPrereq());
		config.set("Delay", h.getDelay());
		config.set("State", h.getState());
		if(h.getNodes().size() > 0){
			for(int n : h.getNodes()){
				SpeechNode node = SpeechNode.getNode(n);
				config.set("SpeechNode." + n + ".Response", node.getResponse());
				config.set("SpeechNode." + n + ".ID", node.getId());
				for(int t : node.getTraits()){
					SpeechTrait trait = SpeechTrait.getSpeech(t);
					String path = "SpeechNode." + n + ".SpeechTrait." + t;
					config.set(path + ".Message", trait.getMessage());
					config.set(path + ".Depth", trait.getDepth());
				}
			}
		}
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadTalk(File f) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		int id = config.getInt("ID");
		int person = config.getInt("Person");
		List<String> reward = config.getStringList("Reward");
		String name = config.getString("Name");
		String delay = config.getString("Delay");
		String prereq = config.getString("Prereq");
		int minlvl = config.getInt("MinLvl");
		String message = config.getString("Message");
		String state = config.getString("State");
		if (state == null) {
			state = "disabled";
		}
		quest.loadtalkto(id, name, person, message, delay, minlvl, prereq,
				reward, state);
		QuestTalkto h = quest.returntalkto(id);
		try{
			for (String in : config.getConfigurationSection("SpeechNode").getKeys(false)) {
				int node = Integer.parseInt(in);
				h.AddNode(node);
				SpeechNode n = new SpeechNode(node, plugin);
				n.setResponse(config.getString("SpeechNode." +in + ".Response"));
				for (String traits : config.getConfigurationSection("SpeechNode." +
						in + ".SpeechTrait").getKeys(false)) {
					int trait = Integer.parseInt(traits);
					SpeechTrait t = new SpeechTrait(id, 3, trait);
					t.setDepth(config.getInt("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Depth"));
					t.setMessage(config.getString("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Message"));
					n.addTrait(t);
				}
			}
		}catch(NullPointerException ex){
			return;
		}
	}

	public void SaveEvent(QuestEvent e) {
		File f = new File(event, e.getNumber() + ".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		config.set("ID", e.getNumber());
		config.set("Name", e.getTitle());
		config.set("Thing", e.getType());
		config.set("Minlvl", e.getMinlvl());
		config.set("Reward", e.getReward());
		config.set("Repeat", e.getRepeat());
		config.set("Start", e.getStartdate());
		config.set("End", e.getEnddate());
		config.set("Count", e.getCount());
		config.set("Message", e.getMessage());
		config.set("State", e.getState());
		if(e.getNodes().size() > 0){
			for(int n : e.getNodes()){
				SpeechNode node = SpeechNode.getNode(n);
				config.set("SpeechNode." + n + ".Response", node.getResponse());
				config.set("SpeechNode." + n + ".ID", node.getId());
				for(int t : node.getTraits()){
					SpeechTrait trait = SpeechTrait.getSpeech(t);
					String path = "SpeechNode." + n + ".SpeechTrait." + t;
					config.set(path + ".Message", trait.getMessage());
					config.set(path + ".Depth", trait.getDepth());
				}
			}
		}
		
		try {
			config.save(f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void LoadEvent(File f) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		String title = config.getString("Name");
		int id = config.getInt("ID");
		String thing = config.getString("Thing");
		int minlvl = config.getInt("Minlvl");
		List<String> reward = config.getStringList("Reward");
		String repeat = config.getString("Repeat");
		long start = config.getLong("Start");
		long end = config.getLong("End");
		int count = config.getInt("Count");
		String message = config.getString("Message");
		String state = config.getString("State");
		if (state == null) {
			state = "disabled";
		}
		quest.loadevent(id, thing, title, start, end, reward, message, count,
				repeat, minlvl, state);
		QuestEvent h = quest.returneventquest(id);
		try{
			for (String in : config.getConfigurationSection("SpeechNode").getKeys(false)) {
				int node = Integer.parseInt(in);
				h.AddNode(node);
				SpeechNode n = new SpeechNode(node, plugin);
				n.setResponse(config.getString("SpeechNode." +in + ".Response"));
				for (String traits : config.getConfigurationSection("SpeechNode." +
						in + ".SpeechTrait").getKeys(false)) {
					int trait = Integer.parseInt(traits);
					SpeechTrait t = new SpeechTrait(id, 7, trait);
					t.setDepth(config.getInt("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Depth"));
					t.setMessage(config.getString("SpeechNode." +in + ".SpeechTrait." + traits
							+ ".Message"));
					n.addTrait(t);
				}
			}
		}catch(NullPointerException ex){
			return;
		}
	}

	public File getLibrary() {
		return library;
	}

}
