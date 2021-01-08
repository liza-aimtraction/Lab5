package lab5.ElevatorStrategies;

import lab5.ElevatorStrategyCommand;
import lab5.IElevator;
import lab5.IElevatorStrategy;
import lab5.Person;

import java.util.ArrayList;
import java.util.List;

public class BasicElevatorStrategy implements IElevatorStrategy {
    public ElevatorStrategyCommand CalculateNextMove(IElevator elevator){
        ArrayList<Person> peopleInsideElevator = elevator.getPeopleInside();
        if(peopleInsideElevator.size() == 0){
            ArrayList<Integer> callQueue = elevator.getCallQueue();
            if(callQueue.size() == 0){
                return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.NONE, elevator.getCurrentFloor().getNumber());
            }
            else{
                return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.OUTSIDE, callQueue.get(0));
            }
        }
        else{
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.INSIDE, peopleInsideElevator.get(0).getDestinationFloor());
        }
    }
}
