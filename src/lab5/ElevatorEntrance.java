package lab5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorEntrance {
    private ArrayList<Person> peopleInQueue;
    private Elevator elevator;

    public ElevatorEntrance(Elevator elevator) {
        this.elevator = elevator;
        this.peopleInQueue = new ArrayList<Person>();
    }

    boolean _isOpen;
    boolean isOpen() {
        return _isOpen;
    }

    void callElevator(int toFloor) {
        elevator.call(toFloor);
    }

    void open() {
        _isOpen = true;
    }

    void close() {
        _isOpen = false;
    }

    int getQueueSize() {
        return peopleInQueue.size();
    }

    int getPersonPositionInQueue(Person person) {
        return peopleInQueue.indexOf(person);
    }

    void addPersonToQueue(Person person){
        peopleInQueue.add(person);
    }

    void removePersonFromQueue(Person person){
        peopleInQueue.remove(person);
    }

    public Elevator getElevator(){
        return elevator;
    }
}
