package lab5;

import java.util.ArrayList;
import java.util.Timer;

/**
 * @author Oikawa
 *
 * Contains all persons and elevator threads
 *
 * Generates specified number of persons with some interval
 *
 *
 */
public class Building {
    private ArrayList<Floor> floors;
    private ArrayList<Elevator> elevators;
    private ArrayList<Person> persons;
    private Timer personGeneratorTimer;
    private PersonGenerator timerTask;

    public Building(){
        floors = new ArrayList<Floor>();
        elevators = new ArrayList<Elevator>();
        persons = new ArrayList<Person>();
    }

    /**
     * Takes copy with mutex lock, so persons ArrayList will not change when taking copy
     * Starts persons threads that added from MainForm(fyi: persons that generated by timer task start to executing in timer)
     * But for now in persons can also be objects generated by timer task, that already running,
     *      so we check if thread already running or not
     */
    public void startupBuildingThreads(){
        timerTask.mtx.lock();
        ArrayList<Person> personsCopy = (ArrayList<Person>)persons.clone();
        timerTask.mtx.unlock();
        for (Person person : personsCopy) {
            if (person.isAlive() == false) {
                person.start();
            }
        }

        for (Elevator elevator : elevators) {
            elevator.start();
        }
    }

    /**
     *
     *  @param generatePersonInterval
     *  @param limitOfGenerations represents how many persons to generate
     */
    public void setupPersonGenerator(int generatePersonInterval, int limitOfGenerations){
        timerTask = new PersonGenerator(this, limitOfGenerations);
        personGeneratorTimer = new Timer();
        personGeneratorTimer.schedule(timerTask, 0, generatePersonInterval);
    }


    /**
     * Waits for all persons and elevators threads
     */
    public void waitForAllThread(){
        while(!timerTask.isEnded()){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        personGeneratorTimer.cancel();

        for (Person person : persons) {
            try {
                person.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // no more work for elevators
        for (Elevator elevator : elevators) {
            elevator.stop();
        }
    }

    public void createFloors(int numberOfFloors){
        for(int i = 0; i < numberOfFloors; ++i){
            floors.add(new Floor(i));
        }
    }

    public void createElevator(IElevatorStrategy elevatorStrategy, Floor startingFloor){
        if(startingFloor.getNumber() < 0 || startingFloor.getNumber() >= floors.size()){
            throw new Error("createElevator: Invalid floor passed");
        }
        else{
            elevators.add(new Elevator("Elevator" + elevators.size(), elevatorStrategy, startingFloor, this));
        }

    }

    public void createEntrances()
    {
        for(int floonNumber = 0; floonNumber < floors.size(); ++floonNumber){
            for(int elevatorNumber = 0; elevatorNumber < elevators.size(); ++elevatorNumber){
                Floor floor = getFloor(floonNumber);
                Elevator elevator = getElevator(elevatorNumber);
                ElevatorEntrance entrance = new ElevatorEntrance(elevator);
                floor.addEntrance(entrance);
            }
        }
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public int getFloorCount() {
        return floors.size();
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public Elevator getElevator(int elevatorNumber) {
        return elevators.get(elevatorNumber);
    }

    public Floor getUpperFloor(Floor floor){
        if(floor.getNumber() == getFloorCount() - 2){
            throw new Error("There is no upper floor");
        }
        return floors.get(floor.getNumber() + 1);
    }

    public Floor getLowerFloor(Floor floor){
        if(floor.getNumber() == 0){
            throw new Error("There is no lower floor");
        }
        return floors.get(floor.getNumber() - 1);
    }
}
