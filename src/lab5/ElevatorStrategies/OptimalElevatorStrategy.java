package lab5.ElevatorStrategies;

import lab5.ElevatorStrategyCommand;
import lab5.IElevator;
import lab5.IElevatorStrategy;
import lab5.Person;

import java.util.ArrayList;

public class OptimalElevatorStrategy implements IElevatorStrategy {
    class PossibleTarget {
        int queuePosition;
        int targetFloor;
        ElevatorStrategyCommand.TriggerSource source;

        public PossibleTarget(int queuePosition, int targetFloor, ElevatorStrategyCommand.TriggerSource source) {
            this.queuePosition = queuePosition;
            this.targetFloor = targetFloor;
            this.source = source;
        }

        public float getRelativePriority(int currentFloor) {
            int distance = Math.abs(currentFloor - targetFloor);
            float initialPriority = getInitialPriority();
            return initialPriority / (float)distance;
        }

        public float getInitialPriority() {
            float sourceMultiplier;

            // people inside elevator are more valuable
            switch (source)
            {
                case INSIDE:
                    sourceMultiplier = 100;
                    break;
                case OUTSIDE:
                    sourceMultiplier = 40;
                    break;
                case NONE:
                default:
                    sourceMultiplier = 0;
                    break;
            }

            return sourceMultiplier / queuePosition;
        }
    }

    public ElevatorStrategyCommand CalculateNextMove(IElevator elevator) {
        ArrayList<Person> peopleInsideElevator = elevator.getPeopleInside();
        ArrayList<Integer> callQueue = elevator.getCallQueue();
        int currentFloor = elevator.getCurrentFloor().getNumber();

        ArrayList<PossibleTarget> priorityList = createPriorityList(peopleInsideElevator, callQueue, currentFloor);

        if(priorityList.size() == 0) {
            return new ElevatorStrategyCommand(ElevatorStrategyCommand.TriggerSource.NONE, currentFloor);
        }

        PossibleTarget bestTarget = priorityList.get(0);

        return new ElevatorStrategyCommand(bestTarget.source, bestTarget.targetFloor);
    }

    private ArrayList<PossibleTarget> createPriorityList(ArrayList<Person> peopleInsideElevator, ArrayList<Integer> callQueue, int currentFloor) {
        ArrayList<PossibleTarget> possibleTargets = new ArrayList<>();

        for (int i = 0; i < peopleInsideElevator.size(); ++i)
        {
            Person person = peopleInsideElevator.get(i);
            int queuePosition = i;
            int destinationFloor = person.getDestinationFloor();
            ElevatorStrategyCommand.TriggerSource source = ElevatorStrategyCommand.TriggerSource.INSIDE;
            possibleTargets.add(new PossibleTarget(queuePosition, destinationFloor, source));
        }

        for (int i = 0; i < callQueue.size(); ++i)
        {
            int queuePosition = i;
            int calledFloor = callQueue.get(i);
            ElevatorStrategyCommand.TriggerSource source = ElevatorStrategyCommand.TriggerSource.OUTSIDE;
            possibleTargets.add(new PossibleTarget(queuePosition, calledFloor, source));
        }

        possibleTargets.sort((target1, target2) -> {
            float priority1 = target1.getRelativePriority(currentFloor);
            float priority2 = target2.getRelativePriority(currentFloor);
            return Float.compare(priority1, priority2);
        });

        return possibleTargets;
    }

    private String name = "Optimal";

    public String getName() {
        return name;
    }
}
