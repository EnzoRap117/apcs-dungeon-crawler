public class Potion extends Item{

	private int effectAmount;
	private String kindOfPotion;

	public Potion(){
		super("Small Health Potion", "recovers a small amount of health", 3);
		kindOfPotion = "healing";
		effectAmount = 15;
	}


	public Potion(String n, String d, String k, int e){
		super(n, d, 3);
		kindOfPotion = k;
		effectAmount = e;
	}

	public int getEffectAmount(){
		return effectAmount;
	}

	public String getKindOfPotion(){
		return kindOfPotion;
	}

	public Potion clone(){
		return new Potion(getName(), getDescription(), kindOfPotion, effectAmount);
	}
}