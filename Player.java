import java.util.List;

public class Player {
    private String name;
    private Garden garden;
    boolean isDiceRolled = false;
    Dice dice;
    int diceValue=0;
    Player(String name, int boardSize) {
        this.name = name;
        garden = new Garden(boardSize);
    }
    public String getName() {
        return this.name;
    }
    public int howManyFlowersPossible() {
        return this.garden.countPossibleFlowers();
    }
    public int howManyTreesPossible() {
        return this.garden.countPossibleTrees();
    }
    public List<int[]> possibleTreeCoordinates(){
        return this.garden.getPossibleTreeCoordinates();
    }
    public List<int[]> possibleFlowerCoordinates(){
        return this.garden.getPossibleFlowerCoordinates();
    }
    public String whatIsPlanted(int r, int c){
        return this.garden.getInLocation(r, c);
    }
    public void plantTreeInGarden(int r, int c){
        this.garden.plantTree(r, c);
    }
    public void plantFlowerInGarden(int r, int c) {
        this.garden.plantFlower(r, c);
    }
    public void eatHere(int r, int c) {
        this.garden.removeItem(r, c);
    }
    public List<int[]> getNonEmptyCoordinates(){
        return this.garden.getNonEmptyCoordinates();
    }
    public boolean isGardenFull() {
        return this.garden.gardenFull();
    }
    public void showGarden() {
        System.out.println(this.garden.toString());
    }
}
