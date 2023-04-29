
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Table {
    private int numRows, numColumns;
    private int N;
    private Cube[][] tableArr;
    private ArrayList<Cube> cubes, finalStateCubeList;
    List<Integer> ids;
    public Table(int numRows,int numColumns,int N){
        this.numRows = numRows;
        this.numColumns = numColumns;
        tableArr = new Cube[numRows][numColumns];
        cubes = new ArrayList<>();
        ids = new ArrayList<>(N);
        setUpFinalState(N,numRows);
        for(int number=1;number<=N;number++){
            ids.add(number);
            cubes.add(new Cube(number,0,0)); /* creating List of cubes with (0,0) coordinates.
                                                                     let user initialize for each cube what the coordinates will be */
        }
        initializeCubeArr(); // array full of zero coordinate cubes
    }

    public ArrayList<Cube> getCubes(){
        return cubes;
    }

    public Cube[][] getTableArr() {
        return tableArr;
    }

    public void setTableArr(Cube[][] newCubeArr){
        this.tableArr = newCubeArr;
    }

    public ArrayList<Cube> getFinalStateCubeList(){
        return finalStateCubeList;
    }
    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public boolean getCubeStatus(Cube cube) {
        return cube.isFree();
    }

    public void setUpFinalState(int N, int numRows){
        int cubeNumber=1;
        int L = 4*N;
        finalStateCubeList = new ArrayList<>(N);
        for(int rows = 1; rows <=numRows; rows++){
            for(int cols = 1; cols <=N/3; cols++){
                finalStateCubeList.add(new Cube(cubeNumber, rows, cols));
                tableArr[rows-1][cols-1] = new Cube(cubeNumber,rows,cols);
                cubeNumber++;
            }
        }
        for(int rows=0; rows<numRows; rows++){
            for(int cols=N/3+1;cols<=L;cols++){
                tableArr[rows][cols-1] = new Cube(0,0,0);
            }
        }
    }

    // Insert the coordinates of the desired cube here
    public boolean isCubeFree(int row, int column) {
        if (column == 1 || column == 0) {
            // A cube exists above the current cube, so it is not free
            return tableArr[row][column+1].getId() == 0;
        }
        // If we reach here, there are no cubes above the current cube, so it is free
        return true;
    }

    /* For a given cube and new coordinates (x',y') this method first checks if coordinates are free and then checks for validity.
     * It is also updating the table.
     * Maybe this function should be in Movement. If so, then a Table object must be available for elaboration in order to have access in tableArr and isValidCondition() */
    public boolean isMovementValid(Cube cube,int nx,int ny){
        int prevX = cube.getXCoordinate();
        int prevY = cube.getYCoordinate();
        // check if new coordinates are free
        if(tableArr[nx-1][ny-1].getXCoordinate()==0 && tableArr[nx-1][ny-1].getYCoordinate()==0) {// if (x,y)==(0,0) then free
            cube.setCoordinates(nx, ny);
            if (isValidCondition()) {
                tableArr[nx-1][ny-1] = cube;
                return true;
            }
            else{
                cube.setCoordinates(prevX,prevY);
                return false;
            }
        }
        return false;
    }
    public boolean isCubeOnAnotherCube(int row, int column) {
        if (column == 1 || column == 2) {
            return tableArr[row][column-1].getId() != 0;
        }
        return false;
    }
    public boolean isCubeOnTable(Cube cube) {
        return cube.getXCoordinate() == 1;
    }

    public void setCubeStatus(Cube cube) {
        cube.setFree(isCubeFree(cube.getXCoordinate(), cube.getYCoordinate()));
    }

    public boolean isValidCondition() {
        for (Cube c: cubes) {
            if (!(isCubeOnTable(c) || isCubeOnAnotherCube(c.getXCoordinate() -1, c.getYCoordinate() -1))) return false;
                // if cube is on 1st row, its x must be between 1 and L, where L = number of table columns
            else if ((c.getYCoordinate() == 1 && c.getXCoordinate() < 1) || c.getYCoordinate() == 1 && c.getXCoordinate() > getNumColumns()) {
                return false;
            }
            // if cube is on 2nd or 3rd row, its y must be between 1 and K, and the table has L positions available for x, L is 4 * K, cubes are 3 * K,
            // eventually cube must be between 1 and 3/4*numColumns
            else if ((c.getYCoordinate() == 2 || c.getYCoordinate() == 3) && c.getXCoordinate() < 1 || c.getXCoordinate() > 0.75 * getNumColumns()) {
                return false;
            }
        }
        return true;
    }

    public void initializeCubeArr() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                for (Cube c: cubes) {
                    tableArr[row][col] = c;
                }
            }
        }
    }

    public void setInitialStateManually() {
        int countInsertedCubes = 0;
        String[] coordinates;
        System.out.print("Initialize Table\n");
        Scanner sc = new Scanner(System.in);
        while (countInsertedCubes < N) {
            for (Cube c : cubes) {
                System.out.println("On the following coordinates insertion enter two numbers separated by comma.");
                System.out.printf("Give coordinates x,y for Cube[%d]: ", c.getId());
                String input = sc.nextLine();
                coordinates = input.split(",");
                int x = Integer.parseInt(coordinates[0].trim());
                int y = Integer.parseInt(coordinates[1].trim());
                c.setCoordinates(x, y);
                cubes.set(c.getId() - 1, c);
                tableArr[x-1][y - 1] = c;
                countInsertedCubes++;
            }
        }

    }

}
