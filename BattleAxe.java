import java.util.Scanner;

public class BattleAxe extends Weapon{

	private int level;

	public BattleAxe(){
		super("Battle Axe", "a dwarf's weapon of choice", 3, "BattleAxeWithArm_1.txt");
		level = 3;
	}

	public BattleAxe(int l){
		super("Battle Axe", "a dwarf's weapon of choice", l+2, "BattleAxeWithArm_1.txt");
		level = l+2;
	}

	public void attackOptions(Player player, Enemy enemy){
		//System.out.println("\t > Inside BattleAxe attackOptions"); //debugg
		Scanner input = new Scanner(System.in);
		System.out.println("(1) Overhead Strike - default attack");
		System.out.println("(2) Wild Strikes - berserk attack dealing multiple hits at reduced damage");
		System.out.println("(3) Bash - attempt to bash with the hilt for a chance at 2x damage");
		System.out.print("Input: ");
		int action = input.nextInt();
		System.out.println("\n!------------------------------!\n");
		if(action == 1){
			enemy.hurt(getDamage());
		}else if(action == 2){
			int tot = 0;
			for(int i = 0; i < 3; i++){
				int ran = (int)(getDamage() * (Math.random()* 60)/100);
				tot += ran;
				enemy.hurt(ran);
			}
			System.out.println("  ("+enemy.getName() + " hurt for a total of "+tot+" damage)");
		}else if(action == 3){
			double ran = Math.random();
			if(ran < .33){
				enemy.hurt(getDamage() * 2);
			}else{
				System.out.println("Your clumbsy attack misses the "+enemy.getName()+".");
			}
		}
		if(enemy.getHealth() <= 0) enemy.setBattleActive(false);
	}

	public BattleAxe clone(){
		return new BattleAxe(level);
	}


}