package lab5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorEntrance {
    private Queue<Person> peopleInQueue;
    private Elevator elevator;

    public ElevatorEntrance(Elevator elevator) {
        this.elevator = elevator;
        this.peopleInQueue = new PriorityQueue<>();
    }

    boolean _isOpen;
    boolean isOpen() {
        return _isOpen;
    }

    void callElevator() {
        //Todo: get floor number somehow
        //elevator.call();
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
}
