package lab5;

public class ElevatorStrategyCommand {
    public enum TriggerSource{
        OUTSIDE, INSIDE, NONE, CUSTOM
    }

    public int floorToMove;
    public TriggerSource triggerSource;

    public ElevatorStrategyCommand(TriggerSource source, int floor){
        floorToMove = floor;
        triggerSource = source;
    }
}
