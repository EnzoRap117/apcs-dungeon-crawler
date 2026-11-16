import java.util.Scanner;

public class Greatsword extends Weapon{ //unfinished, still a battleAxe

	//private boolean guarded;

	int level;

	public Greatsword(){
		super("Greatsword", "a massive hunk of iron sharpened at both ends", 6, "Greatsword.txt");
	}

	public Greatsword(int l){
		super("Greatsword", "a massive hunk of iron sharpened at both ends", l+5, "Greatsword.txt");
	}

	public void attackOptions(Player player, Enemy enemy){
		//System.out.println("\t > Inside Greatsword attackOptions"); //debugg
		Scanner input = new Scanner(System.in);
		System.out.println("(1) Unwieldy Swing - default attack");
		System.out.println("(2) Lions Claw - Use the blade to propel yourself into the air and attempt strike the "+enemy.getName()+" from above");
		System.out.println("(3) Guard - Use the blade to guard against the next attack");
		System.out.print("Input: ");
		int action = input.nextInt();
		System.out.println("\n!------------------------------!\n");
		if(action == 1){
			if(Math.random() <= .77) enemy.hurt(getDamage());
			else System.out.println("\tYour sword strikes the ground next to the" + enemy.getName());
		}else if(action == 2){ //not finished below here
			double ran = Math.random();
			if(ran <= .40){
				enemy.hurt(getDamage() * 3);
			}else{
				player.hurt(3);
				System.out.println("\tYour acrobatic attack misses the "+enemy.getName()+". You do not stick the landing...(-3HP)");
			}
		}else if(action == 3){
			player.setGuard(true);
			System.out.println("\tYou raise the blade into a defensive stance");
		}
		if(enemy.getHealth() <= 0) enemy.setBattleActive(false);
	}

	public Greatsword clone(){
		return new Greatsword(level);
	}

}