package lab5;

import sun.awt.Mutex;

import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 *    Timer for person generator
 *    Used in lab5.Building
 *    @author  Oikawa
 * */
public class PersonGenerator extends TimerTask {

    private Building building;
    private int limitOfGenerations;
    private int generatedPersons;
    private boolean isEnded;
    private Random random;

    /**
     * Mutex for generating persons and waiting for them
     */
    public Mutex mtx;

    public PersonGenerator(Building building, int limitOfGenerations){
        this.building = building;
        this.limitOfGenerations = limitOfGenerations;
        this.generatedPersons = 0;
        this.isEnded = false;
        this.mtx = new Mutex();
        this.random = new Random();
    }

    /**
     *  Manually set when limitOfGenerations reached
     *  When set to true, lab5.Building object close timer
     */
    public boolean isEnded(){
        return isEnded;
    }

    /**
     * Method that runs on each timer tick
     */
    @Override
    public void run() {
        mtx.lock();
        if(generatedPersons < limitOfGenerations){
            String personName = "GeneratedPerson" + generatedPersons++;
            double mass = 40 + random.nextDouble() * 60.0;
            double area = 0.3 + 0.5 * random.nextDouble(); // in square meters
            int floorCount = building.getFloorCount();
            int startingFloorNumber = random.nextInt(floorCount);
            int destinationFloorNumber = random.nextInt(floorCount);
            while(startingFloorNumber == destinationFloorNumber){
                destinationFloorNumber = random.nextInt(floorCount);
            }
            Floor startingFloor = building.getFloor(startingFloorNumber);
            Person newPerson = new Person(personName, mass, area, startingFloor, destinationFloorNumber, building);
            newPerson.start();
            building.addPerson(newPerson);
        }
        else{
            isEnded = true;
        }
        mtx.unlock();
    }
}