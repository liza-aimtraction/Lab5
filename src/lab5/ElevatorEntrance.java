package lab5;

import java.util.Queue;

public class ElevatorEntrance {
    private Queue<Person> peopleInQueue;
    private Elevator elevator;

    boolean _isOpen;
    boolean isOpen() {
        return _isOpen;
    }

    void open() {
        _isOpen = true;
    }

    void close() {
        _isOpen = false;
    }

    void goNextPersonInside() {
    }

    int getQueueSize() {

    }
}
