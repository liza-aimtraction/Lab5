package lab5;

import sun.awt.Mutex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorEntrance {
    private ArrayList<Person> peopleInQueue;
    private Elevator elevator;
    private Mutex queueMutex;

    public ElevatorEntrance(Elevator elevator) {
        this.elevator = elevator;
        this.peopleInQueue = new ArrayList<Person>();
        this.queueMutex = new Mutex();
    }

    private boolean _isOpen;
    public boolean isOpen() {
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

    public int getPersonPositionInQueue(Person person) {
        queueMutex.lock();
        int index = peopleInQueue.indexOf(person);
        queueMutex.unlock();
        return index;
    }

    public void addPersonToQueue(Person person){
        queueMutex.lock();
        peopleInQueue.add(person);
        queueMutex.unlock();
    }

    public void removePersonFromQueue(Person person){
        queueMutex.lock();
        peopleInQueue.remove(person);
        queueMutex.unlock();
    }

    public Elevator getElevator(){
        return elevator;
    }
}
