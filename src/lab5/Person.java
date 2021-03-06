package lab5;

import java.util.Random;

/**
 *
 *  Extends Thread, so hve methods start(), join(), isAlive() ...
 *
 *  In run method should be implemented all logic
 *
 *  Handled by building object
 */
public class Person extends Thread {
    private final double mass;
    private final double area;
    private int destinationFloor;
    private int timeSpentInQueue;
    private int timeSpentInElevator;
    private Random rand;
    private Building building;

    // for algorithm
    private Floor currentFloor;
    private ElevatorEntrance selectedEntrance;
    private Elevator enteredElevator;
    private boolean alreadyCalledElevator;

    public Person(String name, double mass, double area, Floor floorSpawnedOn, int destinationFloor, Building building){
        setName(name); // thread name
        this.mass = mass;
        this.area = area;
        this.currentFloor = floorSpawnedOn;
        this.destinationFloor = destinationFloor;
        this.building = building;

        this.rand = new Random();
        this.timeSpentInQueue = 0;
        this.timeSpentInElevator = 0;

        EventLogger.log("Created person '" + name +
                "'(mass = " + mass + ", area = " + area + ", floors: " + floorSpawnedOn.getNumber() + " -> " + destinationFloor + ")", getName());
    }

    public void run() {
        try{
            personLifeCycle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // wait at least 100ms up to 1s
    int waitSomeTime() throws InterruptedException
    {
        int time = 100 + rand.nextInt(900);
        Thread.sleep(time);
        return time;
    }

    private void personLifeCycle() throws InterruptedException {
        EventLogger.log(getName() + " spawned at floor " + currentFloor.getNumber(), getName());

        selectEntrance();

        alreadyCalledElevator = false;

        while (true)
        {
            if (!alreadyCalledElevator) {
                callElevatorIfCan();
            }

            int time = waitSomeTime();
            timeSpentInQueue += time;

            if(enterElevatorIfCan()){
                // entered elevator
                break;
            }

            // to prevent infinite waiting, we wait for no more than 60 seconds
            if (timeSpentInQueue > 60000) {
                EventLogger.log(getName() + " is tired of waiting, it left the queue :(", getName());
                selectedEntrance.removePersonFromQueue(this);
                return;
            }
        }
        // wait for elevator to reach desired floor
        while (true) {
            int timeInElevator = waitSomeTime();
            timeSpentInElevator += timeInElevator;

            if(isElevatorAtDesiredFloor()){
                leaveElevator();
                break;
            }
        }
    }

    private void selectEntrance()
    {
        selectedEntrance = currentFloor.addPersonToEntranceWithShortestQueue(this);
        EventLogger.log(getName() + " entered elevator entrance with " + selectedEntrance.getElevator().getName(), getName());
    }

    private void callElevator(){
        EventLogger.log(getName() + " called elevator at floor" + currentFloor.getNumber(), getName());
        selectedEntrance.callElevator(this.currentFloor.getNumber());
        alreadyCalledElevator = true;
    }

    private void enterElevator(){
        EventLogger.log(getName() + " entered elevator", getName());
        enteredElevator = selectedEntrance.getElevator();
        selectedEntrance.removePersonFromQueue(this);
        selectedEntrance = null;
        currentFloor = null;
        enteredElevator.addPerson(this);
    }

    private void leaveElevator(){
        currentFloor = enteredElevator.getCurrentFloor();
        enteredElevator.removePerson(this);
        EventLogger.log(getName() + " left elevator at floor " + currentFloor.getNumber() +
                ", spent time in queue  " + timeSpentInQueue + ", spent time in elevator  " + timeSpentInElevator,
                getName());
    }

    /**
     *
     * @return true if called elevator
     */
    private boolean callElevatorIfCan(){
        if(checkIfCanCallElevator()){
            callElevator();
            return true;
        }
        return false;
    }

    /**
     *
     * @return true if entered elevator
     */
    private boolean enterElevatorIfCan(){
        if (selectedEntrance.isOpen())
        {
            if (selectedEntrance.getElevator().canFitInside(this)) {
                enterElevator();
                return true;
            }
            else {
                alreadyCalledElevator = false; // so we can call elevator again
                EventLogger.log(getName() + " is too fat to fit in the elevator!", getName());
            }
        }
        return false;
    }
    private boolean checkIfCanCallElevator(){
        return selectedEntrance.getPersonPositionInQueue(this) == 0;
    }

    private boolean isElevatorAtDesiredFloor(){
        return building.getFloor(destinationFloor).getElevatorEntranceByElevator(enteredElevator).isOpen();
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public double getMass(){
        return mass;
    }

    public double getArea(){
        return area;
    }
}
