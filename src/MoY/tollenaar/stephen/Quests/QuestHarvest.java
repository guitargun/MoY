package MoY.tollenaar.stephen.Quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import MoY.tollenaar.stephen.InventoryUtils.ItemGenerator;

public class QuestHarvest {

	private int questnumber;

	private String name;

	private List<String> reward;

	private int count;
	private int minlvl;

	private String delay;

	private String prereq;

	private String message;

	private String itemid;

	public void qinventory(Player player, UUID npcuuid) {

		Inventory harvestques = Bukkit
				.createInventory(null, 18, "HarvestQuest");

		// title
		ItemStack title = ItemGenerator.InfoQuest(name, questnumber, 2, npcuuid.toString());


		// Item
		ItemStack monster = ItemGenerator.QuestReq(itemid);
		// count items
		ItemStack counta = ItemGenerator.CountQuest(count);
		
		// reward
		ItemStack rewardc = ItemGenerator.RewardQuest(reward);
		// min level
		ItemStack lvl = ItemGenerator.MinLvlQuest(minlvl);
		// repeat
		ItemStack repeat = ItemGenerator.RepeatQuest(delay);
		// message
		ItemStack messagei = ItemGenerator.MessageQuest(message);
		// prerequisite
		ItemStack prereqi = ItemGenerator.PrereqQuest(prereq);
		// delete
		Wool wool = new Wool(DyeColor.RED);
		ItemStack delete = new ItemStack(wool.toItemStack());
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

		harvestques.addItem(title);
		harvestques.addItem(monster);
		harvestques.addItem(counta);
		harvestques.addItem(rewardc);
		harvestques.addItem(lvl);
		harvestques.addItem(repeat);
		harvestques.addItem(messagei);
		harvestques.addItem(prereqi);
		harvestques.setItem(harvestques.getSize() - 2, delete);
		harvestques.setItem(harvestques.getSize() - 1, main);
		player.openInventory(harvestques);

	}

	public QuestHarvest(int number) {

		this.questnumber = number;
		this.name = "title";
		this.count = 0;
		this.minlvl = 0;
		this.delay = "0s";
		this.prereq = "none=0";
		this.itemid = "1";
		this.message = "message";
		this.reward = new ArrayList<String>(Arrays.asList("unknown"));
	}

	public int getQuestnumber() {
		return questnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward.add(reward);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMinlvl() {
		return minlvl;
	}

	public void setMinlvl(int minlvl) {
		this.minlvl = minlvl;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getPrereq() {
		return prereq;
	}

	public void setPrereq(String prereq) {
		this.prereq = prereq;
	}

	public String getItemId() {
		return itemid;
	}

	public void setItem(String itemid) {
		this.itemid = itemid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setReward(List<String> reward){
		this.reward = reward;
	}

}
