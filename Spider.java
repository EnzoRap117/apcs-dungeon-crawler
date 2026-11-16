public class Spider extends Enemy{

	private int level;

	public Spider(int l){

		super('S', "Spider", 50*l, l, true, true, 5);
		super.setLootPoolAtInd(new Sword("Dusty-blade", "a longword covered in cobwebs", 3), 0);
		super.setLootPoolAtInd(new Potion("Large Health Potion", "a flask of vitalic liquid", "healing", 50), 1);
		super.setLootPoolAtInd(new Potion(), 2);
		super.setLootPoolAtInd(new Potion(), 3);
		super.setLootPoolAtInd(new Potion(), 4);
		level = l;

	}

	public void drawEncounter(){
		System.out.println();
		System.out.println();
		System.out.println("            / _ \\");
		System.out.println("          \\_\\(_)/_/");
		System.out.println("           _//o\\\\_ ");
		System.out.println("            /   \\");
	}

	public int attack(Player p){
		//int missChance =
		int damage = super.getLevel() * (int)(Math.random() * 4);
		if(damage == 0){
			System.out.println("\n   "+super.getName()+" bites you with posionous fangs!");
			inflictPoision(p);
			return 0;
		}else{
			System.out.println("\n   "+super.getName() + " hit you for "+damage+" damage!");
			return damage;
		}

	}

	public void inflictPoision(Player p){
		System.out.println("\t ~Injected with non-lethal posion~ //Not implemented yet");
	}

	public Spider clone(){
		return new Spider(level);
	}

	//Have to make a way for this to
	// 1) set num of ticks and damage per tick
	// 2) decrement tick number each turn (May be able to de this by just running it when ticks is above 0
	/*
	public void inflictPoison(Player p){

	}
	*/







}