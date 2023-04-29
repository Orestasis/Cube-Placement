import java.util.*;

public class State {
    private List<Cube> cubes;
    private Cube[][] cubeArr;
    private double cost;
    private State previousState;

    public State(List<Cube> cubes, int cost, State previousState) {
        this.cubes = cubes;
        this.cost = cost;
        this.previousState = previousState;
    }


    public State(Table table, State previousState, double cost){
        this.cubeArr = table.getTableArr();
        this.cubes = table.getCubes();
        this.cost = cost;
        this.previousState = previousState;
    }


    public List<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public double getCost() {
        return cost;
    }

    public double setCost(double cost) {
        this.cost = cost;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public List<State> ucs(Table table) {
        // Define a comparator to order states based on their total cost
        Comparator<State> comparator = Comparator.comparing(State::getCost);

        // Create a priority queue to store the states that need to be expanded
        PriorityQueue<State> queue = new PriorityQueue<>(comparator);
        LinkedList<State> path = new LinkedList<>(); // store the shortest path here
        Set<State> explored = new HashSet<>(); // mark states that are already explored
        // Initialize the priority queue with the starting state
        State startState = new State(table.getCubes(), 0, null);
        queue.add(startState);

        // Loop until the priority queue is empty or the goal state is reached
        while (!queue.isEmpty()) {
            // Remove the state with the lowest cost from the priority queue
            State currentState = queue.poll();

            // Check if the goal state is reached
            if (isGoalState(currentState)) { //isGoalState2(currentState,table)
                // Return the path to the goal state
                return getPathToGoalState(currentState);
            }

            // Generate all possible successor states
            List<State> successorStates = generateSuccessorStates(currentState);

            // Calculate the total cost of each successor state and add it to the priority queue
            for (State successorState : successorStates) {
                double totalCost = currentState.getCost() + successorState.getMoveCost();
                successorState.setCost(totalCost);
                successorState.setPreviousState(currentState);
                queue.add(successorState);
            }
        }

        // If the goal state is not found, return an empty list
        return new ArrayList<>();
    }

    private int getMoveCost() {
        Movement move = new Movement();
    }

    private boolean isGoalState(State currentState) {
        boolean isGoal = true;
        List<Cube> sortedCubes = new ArrayList<>();
        sortCubeList(sortedCubes);
        for (Cube c: currentState.cubes) {
            for (Cube cs: sortedCubes) {
                if (c.getId() != cs.getId()) {
                    isGoal = false;
                    break;
                }
            }
        }
        return isGoal;
    }

    public boolean isCubeFree(State state,int x,int y){
        if(y==state.cubeArr[0].length){
            return true;
        }
        return state.cubeArr[x][y+1].getId()==0; // if the cube above (x,y+1) has id=0 then cube in this current state is free
    }
    private List<State> generateSuccessorStates(State state,Table table){
        Cube[][] tempTable = table.getTableArr();
        List<State> nextStates = new ArrayList<>();
        int rows = table.getTableArr().length; // num of rows
        int cols = table.getTableArr()[0].length; // num of cols
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(tempTable[i][j].getId()!=0 && isCubeFree(state,i,j)){
                    if(j==1 && state.cubeArr[i][0].getId()==0){
                        Cube[][] newCubeArray = new Cube[rows][cols];
                        for(int k=0;k<rows;k++){ //
                            for(int l=0;l<cols;l++){
                                newCubeArray[k][l] = state.cubeArr[k][l];
                            }
                        }
                        newCubeArray[i][0] = state.cubeArr[i][j];
                        newCubeArray[i][j].setId(0);
                        double newCost = state.cost + 0.75;
                        table.setTableArr(newCubeArray);
                        state.setCost(newCost);
                        nextStates.add(new State(table,state,newCost));
                    }
                }

            }
        }

    }
    private boolean isGoalState(State currentState,Table table){
        return currentState.cubes.equals(table.getFinalStateCubeList());
    }

    public void sortCubeList(List<Cube> cubes) {
        Comparator<Cube> compareByYCoordinate = Comparator.comparing(Cube::getYCoordinate);
        Comparator<Cube> compareByXCoordinate = Comparator.comparing(Cube::getXCoordinate);
        Comparator<Cube> compareByBothCoordinates = compareByYCoordinate.thenComparing(compareByXCoordinate);

        cubes.sort(compareByBothCoordinates);
    }
}
