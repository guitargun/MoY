package moy.tollenaar.stephen.Quests;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;








import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import moy.tollenaar.stephen.InventoryUtils.ItemGenerator;
import moy.tollenaar.stephen.MistsOfYsir.MoY;
import moy.tollenaar.stephen.NPC.NPC;
import moy.tollenaar.stephen.NPC.NPCHandler;
import moy.tollenaar.stephen.PlayerInfo.Playerinfo;
import moy.tollenaar.stephen.PlayerInfo.Playerstats;
import moy.tollenaar.stephen.Speech.SpeechNode;
import moy.tollenaar.stephen.Speech.SpeechTrait;

public class Quest {

	protected MoY plugin;
	protected Playerinfo playerinfo;

	public Quest(MoY instance) {
		this.plugin = instance;
		this.playerinfo = instance.playerinfo;
	}

	private static HashMap<Integer, QuestKill> killquests = new HashMap<Integer, QuestKill>();

	private static HashMap<Integer, QuestHarvest> harvestquests = new HashMap<Integer, QuestHarvest>();

	private static HashMap<Integer, QuestTalkto> talktoquests = new HashMap<Integer, QuestTalkto>();

	private static HashMap<Integer, Warps> warplist = new HashMap<Integer, Warps>();

	private static HashMap<Integer, QuestEvent> eventquests = new HashMap<Integer, QuestEvent>();

	private static HashMap<UUID, HashMap<String, HashMap<Integer, Integer>>> progress = new HashMap<UUID, HashMap<String, HashMap<Integer, Integer>>>();

	public int returnProgress(UUID playeruuid, String type, int number) {
		return progress.get(playeruuid).get(type).get(number);
	}
	
	public HashMap<String, HashMap<Integer, Integer>> getProgress(UUID player){
		return progress.get(player);
	}
	
	public void addProgress(UUID player, String type, int quest, int prog){
		if(progress.get(player) != null){
			if(progress.get(player).get(type) != null){
				progress.get(player).get(type).put(quest, prog);
			}else{
				HashMap<Integer, Integer> id = new HashMap<Integer, Integer>();
				id.put(quest, prog);
				progress.get(player).put(type, id);
			}
		}else{
			HashMap<Integer, Integer> id = new HashMap<Integer, Integer>();
			id.put(quest, 0);
			HashMap<String, HashMap<Integer, Integer>> t = new HashMap<String, HashMap<Integer, Integer>>();
			t.put(type, id);
			progress.put(player, t);
		}
	}
	
	public void addProgress(UUID player, String type, int quest){
		addProgress(player, type, quest, 0);
	}

	public void removekill(int number) {
		killquests.remove(number);
		plugin.fw.deletequest("kill", number);
	}

	public void removeharvest(int number) {
		harvestquests.remove(number);
		plugin.fw.deletequest("harvest", number);
	}

	public void removetalkto(int number) {
		talktoquests.remove(number);
		plugin.fw.deletequest("talkto", number);
	}

	public void removewarp(int number) {
		Warps.allwarps.remove(warplist.get(number));
		warplist.remove(number);
		plugin.database.deletewarp(number);
	}

	public void removeevent(int number) {
		eventquests.remove(number);
		plugin.fw.deletequest("event", number);
	}


	public int createnewkill() {
		int i = 0;
		for (; i <= killquests.size(); i++) {
			if (killquests.get(i) == null) {
				break;
			}
		}

		QuestKill kill = new QuestKill(i);

		killquests.put(i, kill);

		return i;
	}

	public int createnewhar() {
		int i = 0;
		for (; i <= harvestquests.size(); i++) {
			if (harvestquests.get(i) == null) {
				break;
			}
		}

		QuestHarvest kill = new QuestHarvest(i);

		harvestquests.put(i, kill);

		return i;
	}

	public int createnewtalk() {
		int i = 0;
		for (; i <= talktoquests.size(); i++) {
			if (talktoquests.get(i) == null) {
				break;
			}
		}

		QuestTalkto talk = new QuestTalkto(i);
		talktoquests.put(i, talk);
		return i;
	}

	public int createnewwarp(Location l) {
		int i = 0;
		for (; i <= warplist.size(); i++) {
			if (warplist.get(i) == null) {
				break;
			}
		}
		Warps warp = new Warps(i, l);

		warplist.put(i, warp);
		return i;
	}

	public int createnewevent() {
		int i = 0;
		for (; i < eventquests.size(); i++) {
			if (eventquests.get(i) == null)
				break;
		}
		QuestEvent event = new QuestEvent(i);
		eventquests.put(i, event);

		return i;
	}

	public QuestKill returnkill(int questnumber) {
		if (killquests.get(questnumber) != null) {
			return killquests.get(questnumber);
		} else {
			return null;
		}
	}

	public QuestHarvest returnharvest(int questnumber) {
		if (harvestquests.get(questnumber) != null) {
			return harvestquests.get(questnumber);
		} else {
			return null;
		}
	}

	public QuestTalkto returntalkto(int questnumber) {
		if (talktoquests.get(questnumber) != null) {
			return talktoquests.get(questnumber);
		} else {
			return null;
		}
	}

	public Warps returnwarp(int number) {
		if (warplist.get(number) != null) {
			return warplist.get(number);
		} else {
			return null;
		}
	}

	public QuestEvent returneventquest(int number) {
		return eventquests.get(number);
	}

	public void loadkill(int number, String name, List<String> reward,
			String delay, int minlvl, String message, String prereq, int count,
			String monster, String state) {
		QuestKill kill = new QuestKill(number);

		kill.setName(name);
		kill.setReward(reward);
		kill.setDelay(delay);
		kill.setMinlvl(minlvl);
		kill.setPrereq(prereq);
		kill.setCount(count);
		kill.setMonster(monster);
		kill.setMessage(message);
		kill.setState(state);

		killquests.put(number, kill);
	}

	public void loadharvest(int number, String name, List<String> reward,
			String delay, int minlvl, String message, String prereq, int count,
			String itemid, String state) {
		QuestHarvest kill = new QuestHarvest(number);

		kill.setName(name);
		kill.setReward(reward);
		kill.setDelay(delay);
		kill.setMinlvl(minlvl);
		kill.setPrereq(prereq);
		kill.setCount(count);
		kill.setMessage(message);
		kill.setItem(itemid);
		kill.setState(state);
		harvestquests.put(number, kill);
	}

	public void loadtalkto(int number, String name, int id, String message,
			String delay, int minlvl, String prereq, List<String> reward, String state) {
		QuestTalkto temp = new QuestTalkto(number);
		temp.setName(name);
		temp.setNpcid(id);
		temp.setDelay(delay);
		temp.setMessage(message);
		temp.setMinlvl(minlvl);
		temp.setReward(reward);
		temp.setPrereq(prereq);
		temp.setState(state);
		talktoquests.put(number, temp);
	}

	public void loadwarps(int number, String name, Location start,
			double costs, String type, String state, String overrideTime, boolean overrideOnly, Set<Integer> overrideID) {
		Warps warp = new Warps(number, start);
		warp.setCosts(costs);
		warp.setName(name);
		String[] splitted = type.split("_");
		warp.SetLine(0, splitted[0].trim());
		for(int i = 1; i < splitted.length; i++){
			warp.AddLine(splitted[i].trim());
		}

		warp.setState(state);
		for(int in : overrideID){
			warp.addByPassID(in);
		}
		warp.setBypassedTime(overrideTime);
		warp.setOverrideOnly(overrideOnly);
		warplist.put(number, warp);
	}

	public void loadevent(int id, String type, String title, long start,
			long end, List<String> reward, String message, int count,
			String repeat, int minlvl, String state) {
		QuestEvent e = new QuestEvent(id);
		e.setCount(count);
		e.setEnddate(end);
		e.setMessage(message);
		e.setMinlvl(minlvl);
		e.setTitle(title);
		e.setType(type);
		e.setRepeat(repeat);
		e.setStartdate(start);
		e.setReward(reward);
		e.setState(state);
		RescheduleEvent(e);
		eventquests.put(id, e);
	}

	public void allkill(Player player, HashSet<Integer> quests, UUID npcuuid) {
		int rowcount = 0;
		if (quests != null) {
			rowcount = quests.size();
			if (rowcount % 9 == 0) {
				rowcount++;
			}
			while (rowcount % 9 != 0) {
				rowcount++;
			}
		}
		if (rowcount == 0) {
			rowcount = 9;
		}

		Inventory inv = Bukkit.createInventory(null, rowcount, "AllKill");
		if (quests != null) {
			for (Integer i : quests) {
				if (killquests.get(i) != null) {
					QuestKill kill = killquests.get(i);
					ItemStack title = ItemGenerator.InfoQuest(kill.getName(),
							kill.getQuestnumber(), 1, npcuuid.toString());
					inv.addItem(title);
				}
			}
		}

		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create New");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(npcuuid.toString());
			meta.setLore(lore);
			create.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 1, create);
		player.openInventory(inv);

	}

	public void allharvest(Player player, HashSet<Integer> quests, UUID npcuuid) {
		int rowcount = 0;
		if (quests != null) {
			rowcount = quests.size();
			if (rowcount % 9 == 0) {
				rowcount++;
			}
			while (rowcount % 9 != 0) {
				rowcount++;
			}
		}
		if (rowcount == 0) {
			rowcount = 9;
		}

		Inventory inv = Bukkit.createInventory(null, rowcount, "AllHarvest");
		if (quests != null) {
			for (Integer i : quests) {
				if (harvestquests.get(i) != null) {
					QuestHarvest kill = harvestquests.get(i);
					ItemStack title = ItemGenerator.InfoQuest(kill.getName(),
							kill.getQuestnumber(), 2, npcuuid.toString());
					inv.addItem(title);
				}
			}
		}

		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create New");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(npcuuid.toString());
			meta.setLore(lore);
			create.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 1, create);
		player.openInventory(inv);

	}

	public void alltalkto(Player player, HashSet<Integer> quests, UUID npcuuid) {
		int rowcount = 0;
		if (quests != null) {
			rowcount = quests.size();
			if (rowcount % 9 == 0) {
				rowcount++;
			}
			while (rowcount % 9 != 0) {
				rowcount++;
			}
		}
		if (rowcount == 0) {
			rowcount = 9;
		}

		Inventory inv = Bukkit.createInventory(null, rowcount, "AllTalkTo");
		if (quests != null) {
			for (Integer i : quests) {
				if (talktoquests.get(i) != null) {
					QuestTalkto kill = talktoquests.get(i);
					ItemStack title = ItemGenerator.InfoQuest(kill.getName(),
							kill.getQuestnumber(), 3, npcuuid.toString());
					inv.addItem(title);
				}
			}
		}

		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create New");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(npcuuid.toString());
			meta.setLore(lore);
			create.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 1, create);
		player.openInventory(inv);

	}

	public void allwarps(Player player, Integer quests, UUID npcuuid) {
		int rowcount = 9;
		Inventory inv = Bukkit.createInventory(null, rowcount, "AllWarps");
		if (quests != -1) {

			Warps kill = warplist.get(quests);
			if (kill != null) {
				ItemStack title = ItemGenerator.InfoQuest(kill.getName(),
						kill.getWarpid(), 4, npcuuid.toString());
				inv.addItem(title);
			}
		}
		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create New");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(npcuuid.toString());
			meta.setLore(lore);
			create.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 1, create);
		player.openInventory(inv);

	}

	public void AllEvents(Player player, HashSet<Integer> quests, UUID npcuuid) {
		int rowcount = 0;
		if (quests != null) {
			rowcount = quests.size();
			if (rowcount % 9 == 0) {
				rowcount++;
			}
			while (rowcount % 9 != 0) {
				rowcount++;
			}
		}
		if (rowcount == 0) {
			rowcount = 9;
		}
		Inventory inv = Bukkit.createInventory(null, rowcount, "AllEvents");
		if (quests != null) {
			for (int nm : quests) {
				if (eventquests.get(nm) != null) {
					QuestEvent event = eventquests.get(nm);
					ItemStack info = ItemGenerator.InfoQuest(event.getTitle(),
							event.getNumber(), 7, npcuuid.toString());
					inv.addItem(info);
				}
			}
		}
		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create New");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(npcuuid.toString());
			meta.setLore(lore);
			create.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 1, create);

		player.openInventory(inv);
	}

	public void AddActiveQuest(Player player, int number, String quetstype) {
		Playerstats p = playerinfo.getplayer(player.getUniqueId());
		p.addactive(quetstype, number);
		playerinfo.saveplayerdata(p);
	}

	public void AddCompletedQuest(Player player, int number, String questtype,
			String npcname) {
		Playerstats p = playerinfo.getplayer(player.getUniqueId());
		p.deleteactive(questtype, number);
		if (questtype.equals("kill") || questtype.equals("harvest")
				|| questtype.contains("event")) {
			progress.get(player.getUniqueId()).get(questtype).remove(number);
		}

		p.addcompleted(questtype, number);
		String name = returnname(questtype, number);
		String message = plugin.fw.GetUtilityLine("QuestComplete")
				.replace("%questname%", name).replace("%npc%", npcname);
		player.sendMessage(ChatColor.BLUE + "[" + ChatColor.BLUE + "YQuest"
				+ ChatColor.BLUE + "] " + ChatColor.GRAY + message);
		playerinfo.saveplayerdata(p);
	}
	


	@SuppressWarnings("deprecation")
	public void ActiveQuest(Player player) {
		Playerstats p = playerinfo.getplayer(player.getUniqueId());
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (p.getactivetype() != null) {
			for (String type : p.getactivetype()) {
				for (int number : p.getactives(type)) {
					switch (type) {
					case "kill":
						QuestKill kill = returnkill(number);
						if (kill != null) {
							ItemStack kil = new ItemStack(Material.STONE_SWORD);
							{
								ItemMeta meta = kil.getItemMeta();
								meta.setDisplayName(kill.getName());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Kill: " + kill.getMonster());
								lore.add("Progress: "
										+ progress.get(player.getUniqueId())
												.get(type).get(number) + "/"
										+ kill.getCount());
								meta.setLore(lore);
								kil.setItemMeta(meta);
								items.add(kil);
							}
						} else {
							p.deleteactive(type, number);
							removekill(number);
						}
						break;
					case "harvest":
						QuestHarvest harvest = returnharvest(number);
						if (harvest != null) {
							ItemStack har = new ItemStack(
									Material.STONE_PICKAXE);
							{
								ItemMeta meta = har.getItemMeta();
								meta.setDisplayName(harvest.getName());
								ArrayList<String> lore = new ArrayList<String>();
								String[] itemid = harvest.getItemId()
										.split(":");
								ItemStack item;
								if (itemid.length == 2) {
									item = new ItemStack(
											Material.getMaterial(Integer
													.parseInt(itemid[0].trim())),
											1, Short.parseShort(itemid[1]
													.trim()));
								} else {
									item = new ItemStack(
											Material.getMaterial(Integer
													.parseInt(itemid[0].trim())));
								}

								lore.add("get: " + GetItemName(item));
								lore.add("Progress: "
										+ progress.get(player.getUniqueId())
												.get(type).get(number) + "/"
										+ harvest.getCount());
								meta.setLore(lore);
								har.setItemMeta(meta);
								items.add(har);
							}
						} else {
							p.deleteactive(type, number);
							removeharvest(number);
						}
						break;
					case "talkto":
						QuestTalkto talk = returntalkto(number);
						if (talk != null) {
							ItemStack tal = new ItemStack(Material.FEATHER);
							{
								ItemMeta meta = tal.getItemMeta();
								meta.setDisplayName(talk.getName());
								ArrayList<String> lore = new ArrayList<String>();
								NPCHandler handler=  plugin.getNPCHandler();
								NPC npc = handler.getNPCByUUID(plugin.qserver.uniquenpcid
												.get(talk.getNpcid()));
								lore.add("Talk to: " + npc.getName());
								meta.setLore(lore);
								tal.setItemMeta(meta);
								items.add(tal);
							}
						} else {
							p.deleteactive(type, number);
							removetalkto(number);
						}
						break;

					case "eventkill":
						QuestEvent event = returneventquest(number);
						if (event != null) {
							ItemStack ki = new ItemStack(Material.STONE_SWORD);
							{
								ItemMeta meta = ki.getItemMeta();
								meta.setDisplayName(event.getTitle());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Kill: " + event.getType());
								lore.add("Progress: "
										+ progress.get(player.getUniqueId())
												.get(type).get(number) + "/"
										+ event.getCount());
								meta.setLore(lore);
								ki.setItemMeta(meta);
								items.add(ki);
							}
						} else {
							p.deleteactive(type, number);
							removeevent(number);
						}
						break;
					case "eventharvest":
						QuestEvent e = returneventquest(number);
						if (e != null) {
							ItemStack ie = new ItemStack(Material.STONE_PICKAXE);
							{
								ItemMeta meta = ie.getItemMeta();
								meta.setDisplayName(e.getTitle());
								ArrayList<String> lore = new ArrayList<String>();
								String[] itemid = e.getType().split(":");
								ItemStack item;
								if (itemid.length == 2) {
									item = new ItemStack(
											Material.getMaterial(Integer
													.parseInt(itemid[0].trim())),
											1, Short.parseShort(itemid[1]
													.trim()));
								} else {
									item = new ItemStack(
											Material.getMaterial(Integer
													.parseInt(itemid[0].trim())));
								}

								lore.add("get: " + GetItemName(item));
								lore.add("Progress: "
										+ progress.get(player.getUniqueId())
												.get(type).get(number) + "/"
										+ e.getCount());
								meta.setLore(lore);
								ie.setItemMeta(meta);
								items.add(ie);
							}
						} else {
							p.deleteactive(type, number);
							removeevent(number);
						}
						break;
					}
				}
			}
		}
		int rowcount = items.size();
		if (rowcount % 9 == 0) {
			rowcount++;
		}
		while (rowcount % 9 != 0) {
			rowcount++;
		}
		if (rowcount == 0) {
			rowcount = 9;
		}
		Inventory inv = Bukkit.createInventory(null, rowcount, "Active Quests");
		for (ItemStack item : items) {
			inv.addItem(item);
		}
		player.openInventory(inv);
	}

	public void CompletedQuest(Player player) {
		Playerstats p = playerinfo.getplayer(player.getUniqueId());
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (p.getcompletedtype() != null) {
			for (String type : p.getcompletedtype()) {
				for (int number : p.getcompleted(type)) {
					switch (type) {
					case "kill":
						QuestKill kill = returnkill(number);
						if (kill == null) {
							p.deletecompleted(type, number);
							removekill(number);
							continue;
						}
						ItemStack kil = new ItemStack(Material.IRON_SWORD);
						{
							ItemMeta meta = kil.getItemMeta();
							meta.setDisplayName(kill.getName());
							kil.setItemMeta(meta);
							items.add(kil);
						}
						break;
					case "harvest":
						QuestHarvest harvest = returnharvest(number);
						if (harvest == null) {
							p.deletecompleted(type, number);
							removeharvest(number);
							continue;
						}
						ItemStack har = new ItemStack(Material.IRON_PICKAXE);
						{
							ItemMeta meta = har.getItemMeta();
							meta.setDisplayName(harvest.getName());
							har.setItemMeta(meta);
							items.add(har);
						}
						break;
					case "talkto":
						QuestTalkto talk = returntalkto(number);
						if (talk == null) {
							p.deletecompleted(type, number);
							removetalkto(number);
							continue;
						}
						ItemStack tal = new ItemStack(Material.FEATHER);
						{
							ItemMeta meta = tal.getItemMeta();
							meta.setDisplayName(talk.getName());
							tal.setItemMeta(meta);
							items.add(tal);
						}
						break;
					case "eventharvest":
						QuestEvent event = returneventquest(number);
						if (event == null) {
							p.deletecompleted(type, number);
							removeevent(number);
							continue;
						}
						ItemStack ha = new ItemStack(Material.IRON_PICKAXE);
						{
							ItemMeta meta = ha.getItemMeta();
							meta.setDisplayName(event.getTitle());
							ha.setItemMeta(meta);
							items.add(ha);
						}
						break;
					case "eventkill":
						QuestEvent e = returneventquest(number);
						if (e == null) {
							p.deletecompleted(type, number);
							removeevent(number);
							continue;
						}
						ItemStack ki = new ItemStack(Material.IRON_SWORD);
						{
							ItemMeta meta = ki.getItemMeta();
							meta.setDisplayName(e.getTitle());
							ki.setItemMeta(meta);
							items.add(ki);
						}
						break;
					}
				}
			}
		}
		int rowcount = items.size();
		if (rowcount % 9 == 0) {
			rowcount++;
		}
		while (rowcount % 9 != 0) {
			rowcount++;
		}
		if (rowcount == 0) {
			rowcount = 9;
		}
		Inventory inv = Bukkit.createInventory(null, rowcount,
				"Completed Quests");
		for (ItemStack item : items) {
			inv.addItem(item);
		}
		player.openInventory(inv);
	}

	public void RewardedQuest(Player player) {
		Playerstats p = playerinfo.getplayer(player.getUniqueId());
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (p.getrewardedtype() != null) {
			for (String type : p.getrewardedtype()) {
				for (int number : p.getrewardednumber(type)) {
					long dated = p.getrewardedtime(type, number);
					switch (type) {
					case "kill":
						QuestKill kill = returnkill(number);
						if (kill != null) {
							ItemStack kil = new ItemStack(Material.GOLD_SWORD);
							{
								ItemMeta meta = kil.getItemMeta();
								meta.setDisplayName(kill.getName());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Rewarded on " + new Timestamp(dated));
								meta.setLore(lore);
								kil.setItemMeta(meta);
								items.add(kil);
							}
						} else {
							p.deleterewarded(type, number);
							removekill(number);
						}
						break;
					case "harvest":
						QuestHarvest harvest = returnharvest(number);
						if (harvest != null) {
							ItemStack har = new ItemStack(Material.GOLD_PICKAXE);
							{
								ItemMeta meta = har.getItemMeta();
								meta.setDisplayName(harvest.getName());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Rewarded on " + new Timestamp(dated));
								meta.setLore(lore);
								har.setItemMeta(meta);
								items.add(har);
							}
						} else {
							p.deleterewarded(type, number);
							removeharvest(number);
						}
						break;
					case "talkto":
						QuestTalkto talk = returntalkto(number);
						if (talk != null) {
							ItemStack tal = new ItemStack(Material.FEATHER);
							{
								ItemMeta meta = tal.getItemMeta();
								meta.setDisplayName(talk.getName());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Rewarded on " + new Timestamp(dated));
								meta.setLore(lore);
								tal.setItemMeta(meta);
								items.add(tal);
							}
						} else {
							p.deleterewarded(type, number);
							removetalkto(number);

						}
						break;
					case "eventkill":
						QuestEvent event = returneventquest(number);
						if (event != null) {
							ItemStack kil = new ItemStack(Material.GOLD_SWORD);
							{
								ItemMeta meta = kil.getItemMeta();
								meta.setDisplayName(event.getTitle());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Rewarded on " + new Timestamp(dated));
								meta.setLore(lore);
								kil.setItemMeta(meta);
								items.add(kil);
							}
						} else {
							p.deleterewarded(type, number);
							removeevent(number);
						}
						break;
					case "eventharvest":
						QuestEvent e = returneventquest(number);
						if (e != null) {
							ItemStack har = new ItemStack(Material.GOLD_PICKAXE);
							{
								ItemMeta meta = har.getItemMeta();
								meta.setDisplayName(e.getTitle());
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("Rewarded on " + new Timestamp(dated));
								meta.setLore(lore);
								har.setItemMeta(meta);
								items.add(har);
							}
						} else {
							p.deleterewarded(type, number);
							removeevent(number);
						}
						break;
					}
				}
			}
		}
		int rowcount = items.size();
		if (rowcount % 9 == 0) {
			rowcount++;
		}
		while (rowcount % 9 != 0) {
			rowcount++;
		}
		if (rowcount == 0) {
			rowcount = 9;
		}
		Inventory inv = Bukkit.createInventory(null, rowcount,
				"Rewarded Quests");
		for (ItemStack item : items) {
			inv.addItem(item);
		}
		player.openInventory(inv);
	}

	public Set<Integer> AllKillId() {
		return killquests.keySet();
	}

	public Set<Integer> AllHarvestId() {
		return harvestquests.keySet();
	}

	public Set<Integer> AllTalkToId() {
		return talktoquests.keySet();
	}

	public Set<Integer> AllWarpId() {
		return warplist.keySet();
	}

	public Set<Integer> AllEvents() {
		return eventquests.keySet();
	}

	private String returnname(String type, int number) {
		switch (type) {
		case "kill":
			return returnkill(number).getName();
		case "harvest":
			return returnharvest(number).getName();
		case "talkto":
			return returntalkto(number).getName();
		default:
			return "null";
		}
	}

	@SuppressWarnings("deprecation")
	public boolean EventCheck(int number) {
		QuestEvent event = returneventquest(number);
		if (event != null) {
			Date current = new Date(System.currentTimeMillis());

			Date start = new Date(event.getStartdate());
			if (start.getMonth() <= current.getMonth()) {
				if (start.getDate() <= current.getDate()) {
					Date end = new Date(event.getEnddate());

					if (current.getMonth() == end.getMonth()) {
						if (current.getDate() <= end.getDate()) {
							return true;
						}
					} else if (current.getMonth() < end.getMonth()) {
						return true;
					} else {
						RescheduleEvent(event);
						return EventCheck(number);
					}
				}
			}
		}
		return false;
	}

	private void RescheduleEvent(QuestEvent event) {
		if (!event.getRepeat().equals("-1")) {
			long s = event.getStartdate();
			for (String in : event.getRepeat().split(" ")) {
				s = parseDateDiff(in, true, s);
			}
			event.setStartdate(s);

			long e = event.getEnddate();
			for (String in : event.getRepeat().split(" ")) {
				e = parseDateDiff(in, true, e);
			}
			event.setEnddate(e);

			plugin.addStorage(event);
		}

	}

	public String GetItemName(ItemStack item) {
		return CraftItemStack.asNMSCopy(item).getName();
	}

	private long parseDateDiff(String time, boolean future, long starttime) {
		Pattern timePattern = Pattern
				.compile(
						"(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?",

						2);
		Matcher m = timePattern.matcher(time);
		int years = 0;
		int months = 0;
		int weeks = 0;
		int days = 0;
		boolean found = false;
		while (m.find()) {
			if ((m.group() != null) && (!m.group().isEmpty())) {
				for (int i = 0; i < m.groupCount(); i++) {
					if ((m.group(i) != null) && (!m.group(i).isEmpty())) {
						found = true;
						break;
					}
				}
				if (found) {
					if ((m.group(1) != null) && (!m.group(1).isEmpty())) {
						years = Integer.parseInt(m.group(1));
					}
					if ((m.group(2) != null) && (!m.group(2).isEmpty())) {
						months = Integer.parseInt(m.group(2));
					}
					if ((m.group(3) != null) && (!m.group(3).isEmpty())) {
						weeks = Integer.parseInt(m.group(3));
					}
					if ((m.group(4) != null) && (!m.group(4).isEmpty())) {
						days = Integer.parseInt(m.group(4));
					}

					break;
				}
			}
		}
		if (!found) {
			return -1L;
		}
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(starttime);
		if (years > 0) {
			c.add(1, years * (future ? 1 : -1));
		}
		if (months > 0) {
			c.add(2, months * (future ? 1 : -1));
		}
		if (weeks > 0) {
			c.add(3, weeks * (future ? 1 : -1));
		}
		if (days > 0) {
			c.add(5, days * (future ? 1 : -1));
		}

		return c.getTimeInMillis();
	}
	
	public void QuestSpeech(Player player, int type, int number) {
		int rows = getQuestNodes(type, number).size();
		if (rows % 9 == 0) {
			rows++;
		}
		while (rows % 9 != 0) {
			rows++;
		}
		if (rows == 0) {
			rows = 9;
		}
		Inventory inv = Bukkit.createInventory(null, rows, "AllNodes Quest");
		for (int ids : getQuestNodes(type, number)) {
			ItemStack item = new ItemStack(Material.BOOK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("SpeechNode");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer
					.toString(ids))));
			item.setItemMeta(meta);
			inv.addItem(item);
		}
		ItemStack create = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = create.getItemMeta();
			meta.setDisplayName("Create Node");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer.toString(type), Integer.toString(number))));
			create.setItemMeta(meta);
			inv.setItem(inv.getSize() - 1, create);
		}
		player.openInventory(inv);
	}

	public void QuestNode(Player player, int node, int type, int number) {
		SpeechNode n = SpeechNode.getNode(node);
		int rows = 1;
		if (rows % 9 == 0) {
			rows++;
		}
		while (rows % 9 != 0) {
			rows++;
		}
		if (rows == 1) {
			rows = 9;
		}
		rows += 9;
		Inventory inv = Bukkit.createInventory(null, rows, "SpeechNodes Quest");

		ItemStack response = new ItemStack(Material.PAPER);
		{
			ItemMeta meta = response.getItemMeta();
			meta.setDisplayName("Quest Response");
			meta.setLore(new ArrayList<String>(Arrays.asList(n.getResponse())));
			response.setItemMeta(meta);
		}
		inv.addItem(response);
		for (int id : n.getTraits()) {
			ItemStack item = new ItemStack(Material.BOOK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("SpeechTrait");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer
					.toString(id))));
			item.setItemMeta(meta);
			inv.addItem(item);
		}
		ItemStack trait = new ItemStack(Material.WOOL, 1, (short)5);
		{
			ItemMeta meta = trait.getItemMeta();
			meta.setDisplayName("Add Trait");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer.toString(type), Integer.toString(number))));
			trait.setItemMeta(meta);
		}
		ItemStack delete = new ItemStack(Material.WOOL, 1, (short)14);
		{
			ItemMeta meta = delete.getItemMeta();
			meta.setDisplayName("Delete");
			meta.setLore(new ArrayList<String>(Arrays.asList(player.getUniqueId().toString(), Integer.toString(node), Integer.toString(type), Integer.toString(number))));
			delete.setItemMeta(meta);
		}

		inv.setItem(inv.getSize() - 8, trait);
		inv.setItem(inv.getSize() - 1, delete);
		player.openInventory(inv);

	}

	public void QuestTrait(Player player, int trait, int node, int type, int number) {
		SpeechTrait t = SpeechTrait.getSpeech(trait);
		Inventory inv = Bukkit.createInventory(null, 9, "SpeechTrait Quest");
		ItemStack info = new ItemStack(Material.BOOK);
		{
			ItemMeta meta = info.getItemMeta();
			meta.setDisplayName("Info");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer
					.toString(trait))));
			info.setItemMeta(meta);
		}
		ItemStack message = new ItemStack(Material.PAPER);
		{
			ItemMeta meta = message.getItemMeta();
			meta.setDisplayName("Message");
			meta.setLore(new ArrayList<String>(Arrays.asList(t.getMessage())));
			message.setItemMeta(meta);
		}
		ItemStack depth = new ItemStack(Material.COMPASS);
		{
			ItemMeta meta = depth.getItemMeta();
			meta.setDisplayName("Depth");
			meta.setLore(new ArrayList<String>(Arrays.asList(Integer.toString(t
					.getDepth()))));
			depth.setItemMeta(meta);
		}
		ItemStack delete = new ItemStack(Material.WOOL, 1, (short)14);
		{
			ItemMeta meta = delete.getItemMeta();
			meta.setDisplayName("Delete");
			meta.setLore(new ArrayList<String>(Arrays.asList(player.getUniqueId().toString(), Integer.toString(node), Integer.toString(trait), Integer.toString(type), Integer.toString(number))));
			delete.setItemMeta(meta);
		}

		inv.addItem(info);
		inv.addItem(message);
		inv.addItem(depth);
		inv.setItem(8, delete);
		player.openInventory(inv);
	}
	
	protected Set<Integer> getQuestNodes(int type, int number){
		switch(type){
		case 1:
			return returnkill(number).getNodes();
		case 2:
			return returnharvest(number).getNodes();
		case 3:
			return returntalkto(number).getNodes();
		case 7:
			return returneventquest(number).getNodes();
		default:
			return null;
		}
	}
	protected SpeechNode getStart(int type, int number){
		switch(type){
		case 1:
			for(int n : returnkill(number).getNodes()){
				return SpeechNode.getNode(n);
			}
		case 2:
			for(int n : returnharvest(number).getNodes()){
				return SpeechNode.getNode(n);
			}
		case 3:
			for(int n : returntalkto(number).getNodes()){
				return SpeechNode.getNode(n);
			}
		case 7:
			for(int n : returneventquest(number).getNodes()){
				return SpeechNode.getNode(n);
			}
		default:
			return null;
		}
	}
	
	protected void deleteSpeechNode(int type, int number, int node){
		switch(type){
		case 1:
			returnkill(number).getNodes().remove(node);
			break;
		case 2:
			returnharvest(number).getNodes().remove(node);
			break;
		case 3:
			returntalkto(number).getNodes().remove(node);
			break;
		case 7:
			returneventquest(number).getNodes().remove(node);
			break;
		}
	}
	
	protected void AddNode(int type, int number, int node){
		switch(type){
		case 1:
			returnkill(number).AddNode(node);
			break;
		case 2:
			returnharvest(number).AddNode(node);
			break;
		case 3:
			returntalkto(number).AddNode(node);
			break;
		case 7:
			returneventquest(number).AddNode(node);
			break;
		}
	}
}
