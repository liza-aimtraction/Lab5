package lab5.ElevatorStrategies;

import lab5.ElevatorStrategyCommand;
import lab5.IElevator;
import lab5.IElevatorStrategy;
import lab5.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;

public class OptimalElevatorStrategy implements IElevatorStrategy {
    class PossibleTarget {
        int queuePosition;
        int targetFloor;

        public PossibleTarget(int queuePosition, int targetFloor) {
            this.queuePosition = queuePosition;
            this.targetFloor = targetFloor;
        }

        public float getRelativePriority(int currentFloor) {
            if (currentFloor == targetFloor) return 99999f;
            int distance = Math.abs(currentFloor - targetFloor);
            float initialPriority = getInitialPriority();
            return initialPriority / (float)distance;
        }

        public float getInitialPriority() {
            // firstly to avoid division by zero, secondly to add more chance for later people in queue
            int convertedQueuePosition = queuePosition + 4;

            return 100.0f / convertedQueuePosition;
        }
    }

    public ElevatorStrategyCommand CalculateNextMove(IElevator elevator) {
        ArrayList<Person> peopleInsideElevator = elevator.getPeopleInside();
        ArrayList<Integer> callQueue = elevator.getCallQueue();
        int currentFloor = elevator.getCurrentFloor().getNumber();

        ArrayList<PossibleTarget> priorityList = createPriorityList(peopleInsideElevator, currentFloor);

        if(priorityList.size() > 0) {
            PossibleTarget bestTarget = priorityList.get(0);
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.INSIDE, bestTarget.targetFloor);
        }

        if (callQueue.size() > 0)
        {
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.OUTSIDE, callQueue.get(0));
        }

        return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.NONE, currentFloor);
    }

    private ArrayList<PossibleTarget> createPriorityList(ArrayList<Person> peopleInsideElevator, int currentFloor) {
        ArrayList<PossibleTarget> possibleTargets = new ArrayList<>();

        for (int i = 0; i < peopleInsideElevator.size(); ++i)
        {
            Person person = peopleInsideElevator.get(i);
            int queuePosition = i;
            int destinationFloor = person.getDestinationFloor();
            ElevatorStrategyCommand.TriggerSource source = ElevatorStrategyCommand.TriggerSource.INSIDE;
            possibleTargets.add(new PossibleTarget(queuePosition, destinationFloor));
        }

        possibleTargets.sort((target1, target2) -> {
            float priority1 = target1.getRelativePriority(currentFloor);
            float priority2 = target2.getRelativePriority(currentFloor);
            return Float.compare(priority1, priority2);
        });

        return possibleTargets;
    }
}
