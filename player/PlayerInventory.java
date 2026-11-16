package player;

import java.io.File;
import Game.Item;

class PlayerInventory{

	Item[] list = new Item[5];

	public PlayerInventory(){
		list[0] = new Item("_", "");
		list[1] = new Item("_", "");
		list[2] = new Item("_", "");
		list[3] = new Item("_", "");
		list[4] = new Item("_", "");
	}

	public Item[] getNewInventory(){
		return list;
	}


}