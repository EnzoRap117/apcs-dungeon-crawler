public class Sword extends Item{

	private int level;
	private int damage;
	private int durability;
	//type = 1 (weapon)

	public Sword(){
		super("Dull Longsword", "Your trusty blade, enchanted to be unbreakable", 1);
		level = 1;
		damage = 5;
		durability = 999;
	}

	public Sword(String n, String d, int l){
		super(n, d, 1);
		level = l;
		damage = (int)Math.pow(2, level);
		durability = 100;
	}

	public String getInformation(){
		return "(LVL "+level+")" + "(DMG "+damage+")" + "("+durability+"/100) " + super.getInformation();
	}

	public int getDamage(){
		return (int)(damage * (Math.random()* 41 + 80)/100);
	}

	public int getLevel(){
		return level;
	}
}