package lab5.ElevatorStrategies;

import lab5.ElevatorStrategyCommand;
import lab5.IElevator;
import lab5.IElevatorStrategy;
import lab5.Person;

import java.util.ArrayList;

public class BasicElevatorStrategy implements IElevatorStrategy {

    private String name = "Basic";

    public String getName() {
        return name;
    }

    public ElevatorStrategyCommand CalculateNextMove(IElevator elevator){
        ArrayList<Person> peopleInsideElevator = elevator.getPeopleInsideClonedList();
        if(peopleInsideElevator.size() == 0){
            int nextCall = elevator.getNextCall();
            if(nextCall == -1) {
                return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.NONE, elevator.getCurrentFloor().getNumber());
            }
            else{
                return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.OUTSIDE, nextCall);
            }
        }
        else{
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.INSIDE, peopleInsideElevator.get(0).getDestinationFloor());
        }
    }
}
