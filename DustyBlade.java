import java.util.Scanner;

public class DustyBlade extends Weapon{ //unfinished, still a battleAxe

	private boolean empowered;
	private int level;

	public DustyBlade(){
		super("Dusty Blade", "an old sword, hilt covered in cobwebs. Still holds an edge", 5, "DustyBlade.txt");
		empowered = false;
		level = 5;
	}

	public DustyBlade(int l){
		super("Dusty Blade", "an old, web-covered sword. Still holds a remarkable edge", 5, "DustyBlade.txt");
		empowered = false;
		level = l+4;
	}

	public void attackOptions(Player player, Enemy enemy){
		//System.out.println("\t > Inside DustyBlade attackOptions"); //debugg
		Scanner input = new Scanner(System.in);
		System.out.println("(1) Standard Slash - default attack");
		System.out.println("(2) Precise Strike - carefully attack a weakpoint for a chance at 2x damage");
		System.out.println("(3) Brush off dust - empower your next turn");
		System.out.print("Input: ");
		int action = input.nextInt();
		System.out.println("\n!------------------------------!\n");
		if(action == 1){
			if(!empowered) enemy.hurt(getDamage());
			else enemy.hurt(getDamage()*2);
		}else if(action == 2){
			double ran = Math.random();
			if(ran < .5){
				if(!empowered) enemy.hurt(getDamage() * 2);
				else enemy.hurt(getDamage() * 4);
			}else{
				System.out.println("Your gamble misses the "+enemy.getName()+".");
			}
		}else if(action == 3){
			if(Math.random() < .8){
				System.out.println("You brush off some spider eggs from the blade's edge, next attack empowered");
				empowered = true;
			}else{
				System.out.println("The cobwebs on the blade's edge are too thick to brush off");
			}

		}
		if(enemy.getHealth() <= 0) enemy.setBattleActive(false);
	}

	public DustyBlade clone(){
		return new DustyBlade(level);
	}

}