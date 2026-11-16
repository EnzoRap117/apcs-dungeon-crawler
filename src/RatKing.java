public class RatKing extends Enemy{

	/*
	public static void main(String[] args){
		drawEncounter();
	}
	*/

	public RatKing(int l){
		super('R', "Rat", 100*l, l, true, false, 2);
		super.setLootPoolAtInd(new BlessedArmor(), 0);
		super.setLootPoolAtInd(new DustyBlade(), 1);
	}


	public RatKing(int l, int r, int c){
		super('K', "The Rat King", 100*l, l, true, false, 2);
		super.setLootPoolAtInd(new BlessedArmor(), 0);
		super.setLootPoolAtInd(new DustyBlade(), 1);
		setPosition(r, c);
	}


	public void drawEncounter(){
		System.out.println();
		System.out.println();
		System.out.println("            ");
		System.out.println("            \\/\\/\\/");
		System.out.println("            |____|");
		System.out.println("             oo   '''//,        _");
		System.out.println("           ,/_;~,        \\,    / '");
		System.out.println("           \"'   \\    (    \\    !");
		System.out.println("                 ',|  \\    |__.'");
		System.out.println("                 '~  '~----''");
	}

	public int attack(Player p){
		//int missChance =
		int damage = super.getLevel() * (int)(Math.random() * 6);
		if(damage == 0){
			System.out.println("\n   "+super.getName()+" sinks its teeth into your calf!");
			inflictPoision(p);
			return 0;
		}else{
			System.out.println("\n   "+super.getName() + " hits you for "+damage+" damage!");
			return damage;
		}

	}

	public void inflictPoision(Player p){
		System.out.println("\t ~Inflicted with lethal posion~ //Not implemented yet");
	}


	//Have to make a way for this to
	// 1) set num of ticks and damage per tick
	// 2) decrement tick number each turn (May be able to de this by just running it when ticks is above 0
	/*
	public void inflictPoison(Player p){

	}
	*/







}