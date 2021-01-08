package lab5;

import java.util.Random;

/**
 * @author Oikawa, lexa
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

    // for algorithm
    private Floor currentFloor;
    private ElevatorEntrance selectedEntrance;

    public Person(String name, double mass, double area, Floor floorSpawnedOn, int destinationFloor){
        setName(name); // thread name
        this.mass = mass;
        this.area = area;
        this.currentFloor = floorSpawnedOn;
        this.destinationFloor = destinationFloor;

        this.rand = new Random();
        this.timeSpentInQueue = 0;
        this.timeSpentInElevator = 0;

        EventLogger.log("Created person '" + name +
                "'(mass = " + mass + ", area = " + area + ", floors: " + floorSpawnedOn.getNumber() + " -> " + destinationFloor + ")");
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
        EventLogger.log(getName() + " spawned at floor " + currentFloor.getNumber());

        selectEntrance();

        while (true)
        {
            int time = waitSomeTime();
            timeSpentInQueue += time;

            boolean canEnter = checkIfCanEnterElevator();
            if (canEnter) {
                break;
            }

            // to prevent infinite waiting, we wait for no more than 15 seconds
            if (timeSpentInQueue > 15000) {
                EventLogger.log(getName() + " is tired of waiting, it left the queue :(");
                return;
            }
        }

        EventLogger.log(getName() + " entered elevator");
        // TODO: some method which allows to add people in elevator

        // TODO: another cycle which checks if elevator is at destination

        // TODO: some method which allows to remove people from elevator
        EventLogger.log(getName() + " left elevator");
    }

    private synchronized void  selectEntrance()
    {
        selectedEntrance = currentFloor.getEntranceWithShortestQueue();
    }

    private boolean checkIfCanEnterElevator() {
        EventLogger.log(getName() + " checked if elevator is open...");
        if (selectedEntrance.isOpen())
        {
            return true;
        }
        return false;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
