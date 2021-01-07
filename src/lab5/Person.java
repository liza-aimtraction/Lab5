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
    private String name;
    private double mass;
    private double area;
    private int destinationFloor;
    private int timeSpentInQueue;
    private int timeSpentInElevator;
    private Random rand;

    // for algorithm
    private Floor currentFloor;
    private ElevatorEntrance selectedEntrance;

    public Person(String name, double mass, double area){
        this.name = name;
        this.mass = mass;
        this.area = area;
        this.rand = new Random();
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
        while (true)
        {
            int time = waitSomeTime();
            timeSpentInQueue += time;

            boolean canEnter = checkIfCanEnterElevator();
            if (canEnter)
            {
                break;
            }
        }

        EventLogger.log(name + " entered elevator");
        // TODO: some method which allows to add people in elevator

        // TODO: another cycle which checks if elevator is at destination

        // TODO: some method which allows to remove people from elevator
        EventLogger.log(name + " left elevator");
    }

    private boolean checkIfCanEnterElevator() {
        EventLogger.log(name + "checked if elevator is open...");
        if (selectedEntrance.isOpen())
        {
            return true;
        }
        return false;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int value) {
        destinationFloor = value;
    }
}
