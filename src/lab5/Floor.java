package lab5;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.List;

public class Floor {

    private List<ElevatorEntrance> entrances;

    public ElevatorEntrance getEntranceWithShortestQueue() {
        ElevatorEntrance shortest = null;
        int shortestQueueSize = 9999;
        for (ElevatorEntrance entrance : entrances)
        {
            //todo: find shortest queue
        }
        return shortest;
    }
}
