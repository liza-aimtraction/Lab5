package lab5;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private List<ElevatorEntrance> entrances;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.entrances = new ArrayList<>();
    }

    public void addEntrance(ElevatorEntrance entrance) {
        this.entrances.add(entrance);
    }

    public ElevatorEntrance getEntranceWithShortestQueue() {
        ElevatorEntrance shortest = null;
        int shortestQueueSize = 9999;
        for (ElevatorEntrance entrance : entrances)
        {
            int curQueueSize = entrance.getQueueSize();
            if (curQueueSize < shortestQueueSize)
            {
                shortest = entrance;
                shortestQueueSize = curQueueSize;
            }
        }
        return shortest;
    }

    public int getNumber() {
        return floorNumber;
    }
}
