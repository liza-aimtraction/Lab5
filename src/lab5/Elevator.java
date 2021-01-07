package lab5;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * @author Oikawa, lexa
 */
public class Elevator extends Thread implements IElevator {
    /**
     * How many floors in building
     */
    private int floorsCount;

    private double maxMass;

    private double maxVolume;

    private IElevatorStrategy elevatorStrategy;

    private ArrayList<Integer> callQueue;

    private ArrayList<Person> peopleInside;

    /**
     * Needs to UI for knowing where to draw elevator
     */
    public enum Direction {
        UP, DOWN
    }
    private Direction movingDirection;

    private int currentFloor;

    private ElevatorStrategyCommand currentCommand = null;

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

    private String logName;

    public Elevator(String logName, IElevatorStrategy strategy){
        this.elevatorStrategy = strategy;
    }

    public void setMovingDirection(Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    public Direction getMovingDirection(){
        return movingDirection;
    }

    public void changeFloor(int floorToChange){
        if(floorToChange == currentFloor - 1 || floorToChange == currentFloor + 1){
            currentFloor = floorToChange;
            progressTo = 0;
        }
        else{
            throw  new Error("ERROR: changeFloor : " + currentFloor + " --> " + floorToChange + ". Diff is not 1!");
        }
    }

    /**
     * gets next move(to witch floor to move) from IElevatorStrategy
     */
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean newCommand = false;
            if(currentCommand == null){
                newCommand = true;
                currentCommand = elevatorStrategy.CalculateNextMove(this);
            }

            if(currentCommand.floorToMove == currentFloor && !newCommand) {
                EventLogger.log(logName + " opened doors at floor " + currentFloor);
                // call open in elevatorEntrance
                currentCommand = null;
            }
            else if(currentCommand.floorToMove != currentFloor){
                movingDirection =
                        currentCommand.floorToMove > currentFloor
                        ? Direction.DOWN
                        : Direction.UP;

                simulateMovementToFloor();
            }


        }
    }

    private void simulateMovementToFloor(){
        int iterations = 10;
        for(int i = 0; i < iterations; i++){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTo += 1.0 / (double)iterations;
        }
        int floorDelta = movingDirection == Direction.DOWN ? -1 : 1;
        changeFloor(currentFloor + floorDelta);
    }

    public void addPerson(Person person){
        peopleInside.add(person);
    }

    public void removePerson(Person person){
        peopleInside.remove(person);
    }

    @Override
    public ArrayList<Person> getPeopleInside(){
        return peopleInside;
    }

    @Override
    public ArrayList<Integer> getCallQueue(){
        return callQueue;
    }

    // For Strategy
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }
}