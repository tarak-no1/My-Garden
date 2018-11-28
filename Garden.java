import java.util.ArrayList;
import java.util.List;

public class Garden {
    private String garden[][];
    private int size;
    Garden(int size) {
        this.garden = new String[size][size];
        this.size = size;
        initializeGarden();
    }
    private void initializeGarden(){
        for(int i=0;i<this.size;i++) for (int j = 0; j < this.size; j++) this.garden[i][j] = "-";
    }
    public String getInLocation(int r, int c) {
        return this.garden[r][c];
    }
    public void plantFlower(int r, int c) {
        this.garden[r][c] = "f";
    }
    public void plantTree(int r, int c) {
        this.garden[r][c] = "t";
        this.garden[r+1][c] = "t";
        this.garden[r][c+1] = "t";
        this.garden[r+1][c+1] = "t";
    }
    public void removeItem(int r, int c) {
        this.garden[r][c] = "-";
    }
    public int countPossibleTrees() {
        int count = 0;
        for(int i=0;i<this.size-1;i++) {
            for (int j = 0; j < this.size-1; j++) {
                if(this.garden[i][j].equals("-") && this.garden[i+1][j].equals("-") && this.garden[i][j+1].equals("-") && this.garden[i+1][j+1].equals("-")){
                    count += 1;
                }
            }
        }
        return count;
    }
    public List<int[]> getPossibleTreeCoordinates() {
        List<int[]> coordinates = new ArrayList<int[]>();
        for(int i=0;i<this.size-1;i++) {
            for (int j = 0; j < this.size-1; j++) {
                if(this.garden[i][j].equals("-") && this.garden[i+1][j].equals("-") && this.garden[i][j+1].equals("-") && this.garden[i+1][j+1].equals("-")){
                    coordinates.add(new int[]{i, j});
                }
            }
        };
        return coordinates;
    }

    public int countPossibleFlowers() {
        int count = 0;
        for(int i=0;i<this.size;i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.garden[i][j].equals("-")){
                    count += 1;
                }
            }
        }
        return count;
    }

    public List<int[]> getPossibleFlowerCoordinates() {
        List<int[]> coordinates = new ArrayList<int[]>();
        for(int i=0;i<this.size;i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.garden[i][j].equals("-")){
                    coordinates.add(new int[]{i, j});
                }
            }
        }
        return coordinates;
    }

    public boolean gardenFull() {
        for(int i=0;i<this.size;i++) {
            for (int j = 0; j < this.size; j++) {
                if(this.garden[i][j].equals("-")){
                    return false;
                }
            }
        }
        return true;
    }
    public List<int[]> getNonEmptyCoordinates() {
        List<int[]> coordinates = new ArrayList<int[]>();
        for(int i=0;i<this.size;i++) {
            for (int j = 0; j < this.size; j++) {
                if(!this.garden[i][j].equals("-")){
                    coordinates.add(new int[]{i, j});
                }
            }
        }
        return coordinates;
    }

    @Override
    public String toString() {
        StringBuilder gardenData = new StringBuilder("\t|\t");
        for(int i=0;i<this.size;i++) gardenData.append(i).append("\t");
        gardenData.append("\n");
        for(int i=0;i<this.size;i++) {
            gardenData.append(i).append("\t|\t");
            for (int j = 0; j < this.size; j++) {
                gardenData.append(this.garden[i][j]).append("\t");
            }
            gardenData.append("\n");
        }
        return gardenData.toString();
    }
}
