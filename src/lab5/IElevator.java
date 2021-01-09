package lab5;
import java.util.ArrayList;
import java.util.List;

public interface IElevator {
    ArrayList<Person> getPeopleInside();
    int getNextCall();
    Floor getCurrentFloor();
}
