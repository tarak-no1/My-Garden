import java.util.Random;

public class Dice {
    int die1, die2;
    public Dice() {
        this.die1 = 0;
        this.die2 = 0;
    }
    public int rollDice() {
        Random rand = new Random();
        this.die1 = rand.nextInt(6)+1;
        this.die2 = rand.nextInt(6)+1;

        return this.die1 + this.die2;
    }
    public int getDice1() {
        return this.die1;
    }
    public int getDice2() {
        return this.die2;
    }

    @Override
    public String toString() {
        return "you rolled "+(this.die1+this.die2)+" (Die 1: "+this.die1+" Die2: "+this.die2+")";
    }
}
