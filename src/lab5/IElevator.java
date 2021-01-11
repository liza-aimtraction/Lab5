package lab5;
import java.util.ArrayList;
import java.util.List;

public interface IElevator {
    ArrayList<Person> getPeopleInsideClonedList();
    int getNextCall();
    int getCurrentFloorNumber();
    int getBuildingFloorCount();
}
