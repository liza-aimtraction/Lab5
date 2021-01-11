package lab5;

import sun.awt.Mutex;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Math.abs;

/**
 * @author Oikawa, lexa
 */
public class Elevator extends Thread implements IElevator {

    private Building building;
    private double maxMass;
    private double maxArea;
    private IElevatorStrategy elevatorStrategy;
    private ConcurrentLinkedQueue<Integer> callQueue;
    private ArrayList<Person> peopleInside;
    private Mutex peopleMutex;

    public double getMaxMass() {
        return maxMass;
    }

    public double getMaxArea() {
        return maxArea;
    }

    /**
     * Needs to UI for knowing where to draw elevator
     */
    public enum Direction {
        UP, DOWN, IDLE
    }
    private Direction movingDirection;

    private Floor currentFloor;

    private ElevatorStrategyCommand currentCommand = null;

    public IElevatorStrategy getElevatorStrategy()
    {
        return elevatorStrategy;
    }

    /**
     * Needs to UI for knowing where to draw elevator
     * Range [0, 1]
     * When elevator moving to another floor this range should be updated all along the road
     * 0 - at starting floor
     * 1 - at destination floor
     *
     * In this case destination floor can be only  (starting floor - 1) or (starting floor + 1)
     */
    private float progressTo;

    /**
     * Defines moving speed in floors/second
     */
    public final float MOVE_SPEED = 0.75f;

    public Elevator(String name, Building building, Floor startingFloor, IElevatorStrategy strategy, double maxMass, double maxArea){
        setName(name); // thread name

        this.peopleInside = new ArrayList<Person>();
        this.callQueue = new ConcurrentLinkedQueue<Integer>();
        this.peopleMutex = new Mutex();

        this.building = building;
        this.currentFloor = startingFloor;
        this.elevatorStrategy = strategy;
        this.maxMass = maxMass;
        this.maxArea = maxArea;

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

    private void waitForProgressIncrement(int millis) {
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateMovementToFloor(){
        movingDirection =
                currentCommand.floorToMove > currentFloor.getNumber()
                        ? Direction.UP
                        : Direction.DOWN;

        int iterations = 20;
        float timeToMove = 1000.0f / MOVE_SPEED;
        int millisecondsToWait = (int)(timeToMove / iterations);

        for(int i = 1; i <= iterations; i++) {
            progressTo = (float)i / iterations;
            waitForProgressIncrement(millisecondsToWait);
        }
        if(movingDirection == Direction.DOWN){
            changeFloor(building.getLowerFloor(currentFloor));
        }
        else if(movingDirection == Direction.UP){
            changeFloor(building.getUpperFloor(currentFloor));
        }
    }

    public void addPerson(Person person){
        peopleMutex.lock();
        peopleInside.add(person);
        peopleMutex.unlock();
        EventLogger.log(getName() + " added " + person.getName() + " at floor " + currentFloor.getNumber(), getName());
    }

    public void removePerson(Person person){
        peopleMutex.lock();
        peopleInside.remove(person);
        peopleMutex.unlock();
        EventLogger.log(getName() + " removed " + person.getName() + " at floor " + currentFloor.getNumber(), getName());
    }

    public double getCurrentMass() {
        double currentMass = 0;
        peopleMutex.lock();
        for ( Person p : peopleInside ) {
            currentMass += p.getMass();
        }
        peopleMutex.unlock();
        return currentMass;
    }

    public double getCurrentArea() {
        double currentArea = 0;
        peopleMutex.lock();
        for ( Person p : peopleInside ) {
            currentArea += p.getArea();
        }
        peopleMutex.unlock();
        return currentArea;
    }

    public boolean canFitInside(Person person){
        double currentMass = getCurrentMass();
        double currentVolume = getCurrentArea();
        if(currentMass + person.getMass() < maxMass && currentVolume + person.getArea() < maxArea) {
            return true;
        }
        return false;
    }

    public float getFloorHeight() {
        float progressDependingOnDirection =
                (getMovingDirection() == Direction.UP) ? progressTo :
                        (getMovingDirection() == Direction.DOWN) ? -progressTo :
                                0;

        float floorNumber = getCurrentFloor().getNumber();
        return (float)floorNumber + progressDependingOnDirection;
    }

    @Override
    public ArrayList<Person> getPeopleInsideClonedList(){
        peopleMutex.lock();
        ArrayList<Person> clonedPeople = (ArrayList<Person>)peopleInside.clone();
        peopleMutex.unlock();
        return clonedPeople;
    }

    public int getPeopleInsideCount(){
        peopleMutex.lock();
        int count = peopleInside.size();
        peopleMutex.unlock();
        return count;
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