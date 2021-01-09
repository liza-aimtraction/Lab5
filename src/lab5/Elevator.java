package lab5;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Math.abs;
import static java.lang.Math.log;

/**
 * @author Oikawa, lexa
 */
public class Elevator extends Thread implements IElevator {

    private Building building;

    private double maxMass;

    private double maxVolume;

    private IElevatorStrategy elevatorStrategy;

    private ConcurrentLinkedQueue<Integer> callQueue;

    private ArrayList<Person> peopleInside;

    /**
     * Needs to UI for knowing where to draw elevator
     */
    public enum Direction {
        UP, DOWN, IDLE
    }
    private Direction movingDirection;

    private Floor currentFloor;

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

    public Elevator(String logName, IElevatorStrategy strategy, Floor startingFloor, Building building, double maxMass, double maxVolume){
        setName(logName); // thread name
        this.elevatorStrategy = strategy;
        this.peopleInside = new ArrayList<Person>();
        this.callQueue = new ConcurrentLinkedQueue<Integer>();
        this.currentFloor = startingFloor;
        this.building = building;
        this.maxMass = maxMass;
        this.maxVolume = maxVolume;
        EventLogger.log(getName() + " created ", getName());
    }

    public void call(int toFloor){
        if(!callQueue.contains(toFloor)){
            EventLogger.log(getName() +" called at floor: " + toFloor, getName());
            callQueue.add(toFloor);
        }
    }

    public void setMovingDirection(Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    public Direction getMovingDirection(){
        return movingDirection;
    }

    public void changeFloor(Floor floorToChange){
        if(floorToChange.getNumber() == currentFloor.getNumber() - 1 || floorToChange.getNumber() == currentFloor.getNumber() + 1){
            currentFloor = floorToChange;
            progressTo = 0;
            movingDirection = Direction.IDLE;
            EventLogger.log(getName() + " changed floor to: " + currentFloor.getNumber(), getName());
        }
        else{
            throw new Error("ERROR: changeFloor : " + currentFloor + " --> " + floorToChange + ". Diff is not 1!");
        }
    }

    /**
     * gets next move(to witch floor to move) from IElevatorStrategy
     */
    @Override
    public void run() {
        try{
            elevatorLifeCycle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void elevatorLifeCycle() throws InterruptedException {
        EventLogger.log(getName() + " spawned at floor " + currentFloor.getNumber(), getName());
        while(true){

            Thread.sleep(300);

            if(currentCommand == null || currentCommand.triggerSource == ElevatorStrategyCommand.TriggerSource.NONE){
                currentCommand = elevatorStrategy.CalculateNextMove(this);
                EventLogger.log(getName() + " next command: " + currentCommand.floorToMove + " from " + currentCommand.triggerSource
                        , getName());
            }


            if(arrivedAtCommandFloor()){
                OpenDoors();
                Thread.sleep(2000);
                CloseDoors();
            }
            else if(notAtCommandFloor()){
                simulateMovementToFloor();
            }
            else{
                // nothing to to
            }
        }
    }

    private void OpenDoors(){
        EventLogger.log(getName() + " opened doors at floor " + currentFloor.getNumber(), getName());
        currentFloor.getElevatorEntranceByElevator(this).open();

        if(currentCommand.triggerSource == ElevatorStrategyCommand.TriggerSource.OUTSIDE){
            if(callQueue.peek() != currentFloor.getNumber()) {
                throw new Error("Incorrect logic in Elevator Call Queue");
            }
            callQueue.remove(0);
        }
        currentCommand = null;
    }

    private void CloseDoors(){
        EventLogger.log(getName() + " closed doors at floor " + currentFloor.getNumber(), getName());
        currentFloor.getElevatorEntranceByElevator(this).close();
        if(!callQueue.isEmpty()){
            if(callQueue.peek() == currentFloor.getNumber()){
                callQueue.remove();
            }
        }
    }

    private boolean arrivedAtCommandFloor(){
        if(currentCommand.floorToMove == currentFloor.getNumber()) {
            if (currentCommand.triggerSource != ElevatorStrategyCommand.TriggerSource.NONE) {
                return true;
            }
        }
        return false;
    }

    private boolean notAtCommandFloor(){
        return currentCommand.floorToMove != currentFloor.getNumber();
    }

    private void simulateMovementToFloor(){
        movingDirection =
                currentCommand.floorToMove > currentFloor.getNumber()
                        ? Direction.UP
                        : Direction.DOWN;

        int iterations = 10;
        for(int i = 0; i < iterations; i++){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTo += 1.0 / (double)iterations;
        }
        if(movingDirection == Direction.DOWN){
            changeFloor(building.getLowerFloor(currentFloor));
        }
        else if(movingDirection == Direction.UP){
            changeFloor(building.getUpperFloor(currentFloor));
        }
    }

    public void addPerson(Person person){
        EventLogger.log(getName() + " added " + person.getName() + " at floor " + currentFloor.getNumber(), getName());
        peopleInside.add(person);
    }

    public void removePerson(Person person){
        EventLogger.log(getName() + " removed " + person.getName() + " at floor " + currentFloor.getNumber(), getName());
        peopleInside.remove(person);
    }

    public boolean isAbleToAdd(Person person){
        double currentMass = 0;
        double currentVolume = 0;
        for ( Person p : peopleInside ) {
            currentMass += p.getMass();
            currentVolume += p.getArea();
        }
        if(currentMass + person.getMass() < maxMass && currentVolume + person.getArea() < maxVolume){
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Person> getPeopleInside(){
        return peopleInside;
    }

    @Override
    public int getNextCall(){
        return (callQueue.size() > 0) ? callQueue.peek() : -1;
    }

    // For Strategy
    @Override
    public Floor getCurrentFloor() {
        return currentFloor;
    }
}