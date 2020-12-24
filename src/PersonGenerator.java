import sun.awt.Mutex;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 *    Timer for person generator
 *    Used in Building
 *    @author  Oikawa
 * */
public class PersonGenerator extends TimerTask{

    private Building building;
    private EventLogger logger;
    private int limitOfGenerations;
    private int generatedPersons;
    private boolean isEnded;
    private int generatedPersonId = 1;

    /**
     * Mutex for generating persons and waiting for them
     */
    public Mutex mtx;

    public PersonGenerator(Building building, EventLogger logger, int limitOfGenerations){
        this.building = building;
        this.logger = logger;
        this.limitOfGenerations = limitOfGenerations;
        this.isEnded = false;
        this.mtx = new Mutex();
    }

    /**
     *  Manually set when limitOfGenerations reached
     *  When set to true, Building object close timer
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
            Person newPerson = new Person(personName, logger);
            newPerson.start();
            building.addPerson(newPerson);
            logger.log("Generated person with name " + personName);
        }
        else{
            isEnded = true;
        }
        mtx.unlock();
    }
}