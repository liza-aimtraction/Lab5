package lab5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oikawa, lexa
 */
public class Elevator extends Thread implements IElevator {
    /**
     * How many floors in building
     */
    private int floorsCount;

    private List<Person> peopleInside;

    /**
     * Needs to UI for knowing where to draw elevator
     */
    public enum Direction {
        UP, DOWN, IDLE
    }
    private Direction movingDirection;

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

    public void setMovingDirection(Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    public Direction getMovingDirection(){
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

    // For Strategy
    @Override
    public List<Integer> getPeopleDestinations() {
        List<Integer> result = new ArrayList<>();
        for (Person person : peopleInside)
        {
            // TODO: add person destination, need to add getter in Person class
        }
        return result;
    }

    // For Strategy
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }
}