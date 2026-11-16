public class Bat extends Enemy{

	/*
	public static void main(String[] args){
		drawEncounter();
	}
	*/

	private int level;


	public Bat(int l){
		super('B', "Bat", 25*l, l, false, true, 7);
		super.setLootPoolAtInd(new Potion(), 0);
		super.setLootPoolAtInd(new Potion("Large Health Potion", "a flask of vitalic liquid", "healing", 50), 1);
		super.setLootPoolAtInd(new Potion(), 2);
		super.setLootPoolAtInd(new Potion(), 3);
		super.setLootPoolAtInd(new Potion(), 4);
		super.setLootPoolAtInd(new Potion(), 5);
		super.setLootPoolAtInd(new Potion(), 6);
		level = l;
	}

	public void drawEncounter(){
		System.out.println();
		System.out.println();
		System.out.println("         /\\                 /\\");
		System.out.println("        / \\'._   (\\_/)   _.'/ \\");
		System.out.println("       /_.''._'--('.')--'_.''._\\");
		System.out.println("       | \\_ / `;=/ \" \\=;` \\ _/ |!");
		System.out.println("        \\/ `\\__|`\\___/`|__/`  \\/");
		System.out.println("         '      \\(/|\\)/       `");
		System.out.println("                 \" ` \"");
	}

	public int attack(Player p){
		//int missChance =
		int damage = super.getLevel() * (int)(Math.random() * 4);
		if(damage == 0){
			System.out.println("\n   "+super.getName()+" sinks its teeth into your shoulder!");
			inflictPoison(p);
			return 0;
		}else{
			System.out.println("\n   "+super.getName() + " hits you for "+damage+" damage!");
			return damage;
		}

	}

	public void inflictPoison(Player p){
		System.out.println("\t ~Inflicted with non-lethal posion~ //Not implemented yet");
	}


	//Have to make a way for this to
	// 1) set num of ticks and damage per tick
	// 2) decrement tick number each turn (May be able to de this by just running it when ticks is above 0
	/*
	public void inflictPoison(Player p){

	}
	*/




	public Bat clone(){
		return new Bat(level);
	}


}