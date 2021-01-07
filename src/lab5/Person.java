package lab5;

/**
 * @author Oikawa
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
    private double volume;
    private int destinationFloor;
    private Floor currentFloor;
    private int timeSpentInQueue;
    private int timeSpentInElevator;

    public Person(String name){
        this.name = name;
    }

    public void run() {

        EventLogger.log(name + " entered elevator");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventLogger.log(name + " left elevator");

    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
