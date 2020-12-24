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

    private EventLogger logger;

    public Person(String name, EventLogger logger){
        this.name = name;
        this.logger = logger;
    }

    public void run() {

        logger.log(name + " entered elevator");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log(name + " left elevator");

    }

}
