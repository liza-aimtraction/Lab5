package lab5;

public interface IElevatorStrategy {
    ElevatorStrategyCommand  CalculateNextMove(IElevator elevator);
    String getName();
}
