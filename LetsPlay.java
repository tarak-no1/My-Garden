import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LetsPlay {
    Random rand = new Random();
    Scanner s = new Scanner(System.in);
    Player[] players;
    int boardSize = 3, numberOfPlayers;
    LetsPlay(){
        System.out.println("We have a garden with size 3X3 square. If you want more size, then enter " +
                "your required size. \nNote: Your garden size should be atleast 3");
        this.boardSize = s.nextInt();
        while(boardSize<3) {
            System.out.println("Enter Valid Garden Size: ");
            this.boardSize = s.nextInt();
        }
        System.out.print("Enter number of players : ");
        this.numberOfPlayers = s.nextInt();
        while (this.numberOfPlayers<2 || this.numberOfPlayers>10) {
            System.out.println("Number of players should be between 2 to 10. \n Enter again :");
            this.numberOfPlayers = s.nextInt();
        }
        initializePlayers();
        System.out.println("Let's see who goes first ...");
    }
    private void initializePlayers() {
        this.players = new Player[this.numberOfPlayers];
        for(int i=0;i<this.numberOfPlayers;i++) {
            System.out.print("Enter player-"+(i+1)+" Name : ");
            String playerName = s.next();
            this.players[i] = new Player(playerName, this.boardSize);
            System.out.println();
        }
    }
    private void playGame() {
        ArrayList<Integer> remainingPlayers = new ArrayList<Integer>();
        for(int i=0;i<this.numberOfPlayers;i++) {
            if(!this.players[i].isDiceRolled) remainingPlayers.add(i);
        }
        if(remainingPlayers.size()!=0) {
            System.out.println("Remaining Player List to roll a dice: \n-------------------------------------");
            for (int i = 0; i < remainingPlayers.size(); i++) {
                System.out.print((i + 1) + "." + this.players[remainingPlayers.get(i)].getName() + " ");
                if (i % 2 == 1) System.out.println();
            }
            System.out.println("\nwho wants to roll the die : ");

            int selectedPlayer = -1;
            boolean isInt = false;
            while (!isInt) {
                try {
                    selectedPlayer = s.nextInt() - 1;
                    isInt = true;
                } catch (Exception e) {
                    isInt = false;
                }
                if(isInt) {
                    isInt = !(selectedPlayer >= remainingPlayers.size() || selectedPlayer < 0);
                }
                if(!isInt) {
                    System.out.println("Please enter proper player : ");
                }
            }
            Dice dice = new Dice();
            int diceValue = dice.rollDice();
            System.out.println(this.players[remainingPlayers.get(selectedPlayer)].getName()+" rolled a "+diceValue);
            this.players[remainingPlayers.get(selectedPlayer)].diceValue = diceValue;
            this.players[remainingPlayers.get(selectedPlayer)].dice = dice;
            this.players[remainingPlayers.get(selectedPlayer)].isDiceRolled = true;
            boolean resetStatus = getResetDiceValueStatus(diceValue, remainingPlayers.get(selectedPlayer));
            if (resetStatus) {
                resetPlayerData();
            }
            playGame();
        }
        else {
            int selectedPlayer = 0;
            for(int i = 1;i < this.numberOfPlayers;i++) {
                if(this.players[i].diceValue> this.players[selectedPlayer].diceValue) {
                    selectedPlayer = i;
                }
            }
            System.out.println(this.players[selectedPlayer].getName()+" goes first");
            System.out.println(this.players[selectedPlayer].getName()+" "+this.players[selectedPlayer].dice.toString());
            int diceValue = this.players[selectedPlayer].diceValue;
            switch (diceValue) {
                case 3: {
                    System.out.println("You must plant a tree (2x2) and a flower (1x1)");
                    this.players[selectedPlayer].showGarden();
                    plantTree(selectedPlayer);
                    this.players[selectedPlayer].showGarden();
                    plantTree(selectedPlayer);
                    this.players[selectedPlayer].showGarden();
                    plantFlower(selectedPlayer);
                    break;
                }
                case 6: {
                    System.out.println("You must plant 2 flowers (2 times 1x1)");
                    this.players[selectedPlayer].showGarden();
                    plantFlower(selectedPlayer);
                    this.players[selectedPlayer].showGarden();
                    plantFlower(selectedPlayer);
                    break;
                }
                case 12: {
                    System.out.println("You must plant 2 trees. (2 times 2x2)");
                    this.players[selectedPlayer].showGarden();
                    plantTree(selectedPlayer);
                    this.players[selectedPlayer].showGarden();
                    plantTree(selectedPlayer);
                    break;
                }
                case 5:
                case 10: {
                    System.out.println("The rabbit that lives in your garden will eat something that you have planted." +
                            "might be a flower or part of a tree(1x1)");
                    this.players[selectedPlayer].showGarden();
                    List<int[]> coordinates = this.players[selectedPlayer].getNonEmptyCoordinates();
                    if(coordinates.size()==0) {
                        System.out.println(this.players[selectedPlayer].getName() + ", you have nothing in your garden. So rabbit didn't eat anything.");
                    }
                    else {
                        int[] randomCoordinate = coordinates.get(rand.nextInt(coordinates.size()));
                        this.players[selectedPlayer].eatHere(randomCoordinate[0], randomCoordinate[1]);
                        System.out.println("The rabbit ate whatever was planted in location ("+randomCoordinate[0]+", "+randomCoordinate[1]+")");
                    }
                    break;
                }
                case 2:
                case 4:
                case 8: {
                    System.out.println("You must plant a tree(2x2)");
                    this.players[selectedPlayer].showGarden();
                    plantTree(selectedPlayer);
                    break;
                }
                case 7:
                case 9:
                case 11: {
                    System.out.println("You must plant a flower(1x1)");
                    this.players[selectedPlayer].showGarden();
                    plantFlower(selectedPlayer);
                    break;
                }
            }
            this.players[selectedPlayer].showGarden();
            if (!this.players[selectedPlayer].isGardenFull()) {
                resetPlayerData();
                displayOutcomeActions();
                playGame();
            }
            else {
                System.out.println("FINAL RESULTS\n----------------------------------");
                for (int i = 0; i < this.numberOfPlayers; i++) {
                    System.out.println(this.players[i].getName()+"'s garden");
                    this.players[i].showGarden();
                }
                System.out.println("and the winner is ..... "+this.players[selectedPlayer].getName()+"!!!!!");
                System.out.println("What a beautiful garden you have.");
                System.out.println("\nHope you had fun!!!!");
            }
        }
    }
    private void plantTree(int selectedPlayer) {
        int count = this.players[selectedPlayer].howManyTreesPossible();
        System.out.println("and have "+count+" places to do this.");
        if(count==0) {
            System.out.println("** Sorry no room left to plant a tree - you miss a turn");
        }
        else {
            List<int[]> coordinates = this.players[selectedPlayer].possibleTreeCoordinates();
            System.out.print("Enter coordinates as row column : ");
            boolean isCoordinateExists = false;
            int r = this.boardSize,c = this.boardSize;
            while (!isCoordinateExists) {
                try {
                    r = s.nextInt();
                    c = s.nextInt();
                }catch (Exception e){
                    isCoordinateExists = false;
                }
                for (int[] coordinate : coordinates) {
                    if (coordinate[0] == r && coordinate[1] == c) {
                        isCoordinateExists = true;
                        break;
                    }
                }
                if(!isCoordinateExists) {
                    System.out.println("** Sorry either the row or column is not in the range of 0 to "+(boardSize-1)+
                            " or your tree will be off the grid. Try again");
                }
                else if(!this.players[selectedPlayer].whatIsPlanted(r, c).equals("-")) {
                    isCoordinateExists = false;
                    System.out.println("** Sorry that location is already taken up by a "+this.players[selectedPlayer].whatIsPlanted(r,c)+
                            " Please enter a new set of coordinates.");
                }
            }
            this.players[selectedPlayer].plantTreeInGarden(r, c);
        }
    }
    private void plantFlower(int selectedPlayer) {
        int count = this.players[selectedPlayer].howManyFlowersPossible();
        System.out.println("and have "+count+" places to do this.");
        if(count==0) {
            System.out.println("** Sorry no room left to plant a flower - you miss a turn");
        }
        else {
            List<int[]> coordinates = this.players[selectedPlayer].possibleFlowerCoordinates();
            System.out.print("Enter coordinates as row column : ");
            boolean isCoordinateExists = false;
            int r = 0,c = 0;
            while (!isCoordinateExists) {
                try {
                    r = s.nextInt();
                    c = s.nextInt();
                }catch (Exception e){
                    isCoordinateExists = false;
                }
                for (int[] coordinate : coordinates) {
                    if (coordinate[0] == r && coordinate[1] == c) {
                        isCoordinateExists = true;
                        break;
                    }
                }

                if(!isCoordinateExists) {
                    System.out.println("** Sorry either the row or column is not in the range of 0 to "+(boardSize-1)+
                            " or your flower will be off the grid. Try again");
                }
                else if(!this.players[selectedPlayer].whatIsPlanted(r, c).equals("-")) {
                    isCoordinateExists = false;
                    System.out.println("** Sorry that location is already taken up by a "+this.players[selectedPlayer].whatIsPlanted(r,c)+
                            " Please enter a new set of coordinates.");
                }
            }
            this.players[selectedPlayer].plantFlowerInGarden(r, c);
        }
    }
    private void resetPlayerData() {
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.players[i].diceValue = 0;
            this.players[i].dice = null;
            this.players[i].isDiceRolled = false;
        }
    }
    private boolean getResetDiceValueStatus(int diceValue, int skipIndex) {
        boolean resetStatus = false;
        for(int i=0;i<this.numberOfPlayers;i++) {
            if(diceValue==this.players[i].diceValue && skipIndex!=i){
                System.out.println("We will start over as "+diceValue+" was rolled by "+this.players[i].getName()+" as well");
                System.out.println();
                resetStatus = true;
                break;
            }
        }
        return resetStatus;
    }
    private void displayOutcomeActions() {
        System.out.println("If Total roll is "+
            "\n\t3 - Plant a tree and flower"+
            "\n\t6 - Plant a 2 flowers"+
            "\n\t12 - Plant a 2 true"+
            "\n\t5 or 10 - The rabbit will eat something"+
            "\n\t2,4 or 8 - Plant a tree"+
            "\n\t7,9 or 11 - Plant a flower\n"
        );
    }
    public static void main(String[] args) {
        System.out.println("Welcome to the game");
        System.out.println("Rules of the game");
        System.out.println("\t1. Number of players should be 2 to 10\n" +
                "\t2. Garden size should be atleast 3X3 square\n" +
                "\t3. Each player has a garden board which is empty when they start the game." +
                "A player can plant either a flower which takes up one square or a tree" +
                "which takes up 4 squares (2X2)");
        LetsPlay letsPlay = new LetsPlay();
        letsPlay.displayOutcomeActions();
        letsPlay.playGame();
    }
}
