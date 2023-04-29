public class Movement {
    private int moveCost;

    public int moveCubeUp(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getYCoordinate();
        cube.setYCoordinate(newYCoordinate);
        return newYCoordinate - oldYCoordinate;
    }
    public double moveCubeDown(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getYCoordinate();
        cube.setYCoordinate(newYCoordinate);
        return 0.5 * (oldYCoordinate - newYCoordinate);
    }

    public double moveCubeInSameRow(Cube cube, int newXCoordinate) {
        cube.setXCoordinate(newXCoordinate);
        return 0.75;
    }

    public double moveCube(Cube cube, int newXCoordinate, int newYCoordinate) {
        int totalMoveCost = 0;
        if (cube.getXCoordinate() == newXCoordinate && cube.getYCoordinate() < newYCoordinate) {
            totalMoveCost += moveCubeUp(cube, newYCoordinate);
        }
        else if (cube.getXCoordinate() == newXCoordinate && cube.getYCoordinate() > newYCoordinate) {
            totalMoveCost += moveCubeDown(cube, newYCoordinate);
        }
        else if (cube.getXCoordinate() != newXCoordinate && cube.getYCoordinate() > newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
            totalMoveCost += moveCubeDown(cube, newYCoordinate);
        }
        else if (cube.getXCoordinate() != newXCoordinate && cube.getYCoordinate() < newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
            totalMoveCost += moveCubeUp(cube, newYCoordinate);
        }
        else if (cube.getXCoordinate() != newXCoordinate && cube.getYCoordinate() == newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
        }
        else if (cube.getXCoordinate() == newXCoordinate && cube.getYCoordinate() == newYCoordinate) {
            return 0;
        }
        return totalMoveCost;
    }
}
