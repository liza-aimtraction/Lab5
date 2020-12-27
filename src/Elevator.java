public class Elevator extends Thread {
    /**
     * How many floors in building
     */
    private int floorsCount;

    /**
     * Needs to UI for knowing where to draw elevator
     *      set 0 if not moving
     *      set 1 if moving ud
     *      set -1 if moving down
     */
    private int movingDirection;

    private int currentFloor;

    /**
     * Needs to UI for knowing where to draw elevator
     * Range [0, 1]
     * When elevator moving to another floor this range should be updated all along the road
     * 0 - at starting floor
     * 1 - at destination floor
     *
     * In this case destination floor can be only  (starting floor - 1) or (starting floor + 1)
     */
    private int progressTo;

    public void setMovingDirectionValue(int value) {
        if(value == 0 || value == -1 || value == 1){
            movingDirection = value;
        }
        else{
            throw  new Error("ERROR: setMovingDirectionValue : value is " + value + ", but should be 1, -1 or 0");
        }
    }

    public int getMovingValue(){
        return movingDirection;
    }

    // TODO : Inform all other classes
    public void changeFloor(int floorToChange){
        if(floorToChange == currentFloor - 1 || floorToChange == currentFloor + 1){
            currentFloor = floorToChange;
            progressTo = 0;
        }
        else{
            throw  new Error("ERROR: changeFloor : " + currentFloor + " --> " + floorToChange + ". Diff is not 1!");
        }
    }

}