package moy.tollenaar.stephen.Quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;








import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import moy.tollenaar.stephen.InventoryUtils.ItemGenerator;
import moy.tollenaar.stephen.MistsOfYsir.MoY;

public class QuestTalkto {
	private int questnumber;
	
	private String name;
	
	private int npcid;
	
	private List<String> reward;
	
	private String delay;
	private String message;
	
	private int minlvl;
	private Set<Integer> nodes;
	private String prereq;
	private String state;
	public QuestTalkto(int number){
		this.questnumber = number;
		this.name = "title";
		this.npcid = 0;
		this.reward = new ArrayList<String>(Arrays.asList("unknown"));
		this.delay = "0s";
		this.message = "message";
		this.minlvl = 0;
		this.prereq = "none=0";
		this.state = "disbled";
		this.nodes = new HashSet<>();
	}
	
	public void npcsettingstalkto(UUID npcuuid, Player player, MoY plugin) {

		Inventory talktoques = Bukkit.createInventory(null, 18, "TalktoQuest");

		// title
		ItemStack title = ItemGenerator.InfoQuest(name, questnumber, 3, npcuuid.toString());
		// person
		
		ItemStack person = ItemGenerator.PersonQuest(npcid, plugin.getNPCHandler().getNPCByUUID(plugin.qserver.uniquenpcid.get(npcid)).getName());
		// reward
		ItemStack rewardi =ItemGenerator.RewardQuest(reward);
		// min lvl
		ItemStack lvl = ItemGenerator.MinLvlQuest(minlvl);
		// repeat delay
		ItemStack repeat = ItemGenerator.RepeatQuest(delay);
		// message
		ItemStack messagei =ItemGenerator.MessageQuest(message);
		// prerequisite
		ItemStack prereqi =ItemGenerator.PrereqQuest(prereq);
		//queststate
		ItemStack qstate=  ItemGenerator.ActiveQuest(state);
		//speech
				ItemStack speech = ItemGenerator.SpeechTrait(3, questnumber);
		// delete
		ItemStack delete = new ItemStack(new ItemStack(Material.WOOL, 1, (short)5));
		{
			ItemMeta temp = delete.getItemMeta();
			temp.setDisplayName("Delete Quest");
			delete.setItemMeta(temp);
		}

		// tomain
		ItemStack main = new ItemStack(Material.NETHER_STAR);
		{
			List<String> temp = new ArrayList<String>();
			ItemMeta tem = main.getItemMeta();
			tem.setDisplayName("To Main");
			tem.setLore(temp);
			main.setItemMeta(tem);
		}

		talktoques.addItem(title);
		talktoques.addItem(person);
		talktoques.addItem(rewardi);
		talktoques.addItem(lvl);
		talktoques.addItem(repeat);
		talktoques.addItem(messagei);
		talktoques.addItem(prereqi);
		talktoques.addItem(speech);
		talktoques.addItem(qstate);
		talktoques.setItem(talktoques.getSize() - 2, delete);
		talktoques.setItem(talktoques.getSize() - 1, main);
		player.openInventory(talktoques);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNpcid() {
		return npcid;
	}

	public void setNpcid(int npcid) {
		this.npcid = npcid;
	}

	public List<String> getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward.add(reward);
	}
	public void setReward(List<String> reward){
		this.reward = reward;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMinlvl() {
		return minlvl;
	}

	public void setMinlvl(int minlvl) {
		this.minlvl = minlvl;
	}

	public String getPrereq() {
		return prereq;
	}

	public void setPrereq(String prereq) {
		this.prereq = prereq;
	}

	public int getQuestnumber() {
		return questnumber;
	}

	public Set<Integer> getNodes() {
		return nodes;
	}
	public void removeReward(int l){
		reward.remove(l);
	}
	public void AddNode(int node) {
		this.nodes.add(node);
	}
	
	
}
