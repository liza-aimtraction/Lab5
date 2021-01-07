package lab5;

import sun.awt.Mutex;

import java.util.TimerTask;

/**
 *    Timer for person generator
 *    Used in lab5.Building
 *    @author  Oikawa
 * */
public class PersonGenerator extends TimerTask{

    private Building building;
    private int limitOfGenerations;
    private int generatedPersons;
    private boolean isEnded;
    private int generatedPersonId = 1;

    /**
     * Mutex for generating persons and waiting for them
     */
    public Mutex mtx;

    public PersonGenerator(Building building, int limitOfGenerations){
        this.building = building;
        this.limitOfGenerations = limitOfGenerations;
        this.isEnded = false;
        this.mtx = new Mutex();
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
            generatedPersons++;
            String personName = "GeneratedPerson" + generatedPersonId++;
            Person newPerson = new Person(personName);
            newPerson.start();
            building.addPerson(newPerson);
            EventLogger.log("Generated person with name " + personName);
        }
        else{
            isEnded = true;
        }
        mtx.unlock();
    }
}