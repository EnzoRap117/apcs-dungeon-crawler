public class Chest extends Tile{

	private Item item;

	public Chest(Item t){
		super('=');
		item = t;
	}

	public Item getItem(){
		return item;
	}

	public boolean isChest(){
		return true;
	}

}