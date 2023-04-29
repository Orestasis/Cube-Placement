public class Cube {
    private int id, xCoordinate, yCoordinate;
    private boolean isFree;

    public Cube(int id, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public boolean isFree() {
        return isFree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setCoordinates(int x,int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

}

