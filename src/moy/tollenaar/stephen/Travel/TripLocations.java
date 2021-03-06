package moy.tollenaar.stephen.Travel;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class TripLocations {
	private static int UNIQUE_ID = 0;
	
	
	public TripLocations(int id){
		this.id = id;
		this.type = "none";
		this.location = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
		if(UNIQUE_ID < id){
			UNIQUE_ID = id;
		}
		this.setActive(false);
	}
	public TripLocations(){
		this(UNIQUE_ID);
		UNIQUE_ID++;
	}
	
	private int id;
	private String type;
	private Location location;
	private boolean active;
	public void information(Player player){
		Inventory inv =  Bukkit.createInventory(null, 9, "Trip Location info");
		
		ItemStack infoid = new ItemStack(Material.BOOK);
		{
			ItemMeta im = infoid.getItemMeta();
			im.setDisplayName("Id");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(Integer.toString(id));
			im.setLore(lore);
			infoid.setItemMeta(im);
			inv.addItem(infoid);
		}
		
		ItemStack triptype = new ItemStack(Material.MINECART);
		{
			ItemMeta im = triptype.getItemMeta();
			im.setDisplayName("Trip Type");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(type);
			im.setLore(lore);
			triptype.setItemMeta(im);
			inv.addItem(triptype);
		}
		
		ItemStack loc = new ItemStack(Material.COMPASS);
		{
			ItemMeta im = loc.getItemMeta();
			im.setDisplayName("Trip Location");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("X: " + location.getX());
			lore.add("Y: " + location.getY());
			lore.add("Z: " + location.getZ());
			lore.add("World: " + location.getWorld().getName());
			im.setLore(lore);
			loc.setItemMeta(im);
			inv.addItem(loc);
		}
		ItemStack delete = new ItemStack(new Wool(DyeColor.RED).toItemStack());
		{
			ItemMeta im = delete.getItemMeta();
			im.setDisplayName("Delete");
			delete.setItemMeta(im);
			inv.setItem(inv.getSize()-1, delete);
		}
		
		
		player.openInventory(inv);
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}




	
	
	
	
}
