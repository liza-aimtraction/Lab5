package lab5.ElevatorStrategies;

import lab5.ElevatorStrategyCommand;
import lab5.IElevator;
import lab5.IElevatorStrategy;
import lab5.Person;

import java.util.ArrayList;

public class RoundRobinElevatorStrategy implements IElevatorStrategy {
    @Override
    public ElevatorStrategyCommand CalculateNextMove(IElevator elevator) {
        ArrayList<Person> peopleInsideElevator = elevator.getPeopleInsideClonedList();
        int currentFloor = elevator.getCurrentFloorNumber();
        int nextCall = elevator.getNextCall();

        int targetFloor;
        ElevatorStrategyCommand.TriggerSource source;

        if (peopleInsideElevator.size() == 0) {
            if (nextCall == -1) {
                targetFloor = currentFloor;
                source = ElevatorStrategyCommand.TriggerSource.NONE;
            } else {
                targetFloor = nextCall;
                source = ElevatorStrategyCommand.TriggerSource.OUTSIDE;
            }
        } else {
            Person firstPerson = peopleInsideElevator.get(0);
            targetFloor = firstPerson.getDestinationFloor();
            source = ElevatorStrategyCommand.TriggerSource.INSIDE;
        }

        int floorDifference = targetFloor - currentFloor;
        if (floorDifference > 1) {
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.CUSTOM, currentFloor + 1);
        }

        if (floorDifference == 1) {
            return new ElevatorStrategyCommand(source, targetFloor);
        }

        if (floorDifference < -1) {
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.CUSTOM, currentFloor - 1);
        }

        if (floorDifference == -1) {
            return new ElevatorStrategyCommand(source, targetFloor);
        }

        return new ElevatorStrategyCommand(source, currentFloor);
    }

    @Override
    public String getName() {
        return "Round-Robin";
    }
}
