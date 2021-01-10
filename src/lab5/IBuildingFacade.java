package lab5;

public interface IBuildingFacade {
    int getFloorCount();
    int getElevatorCount();
    int getPeopleCountOutside(int floorNumber, int entranceNumber);
    String getElevatorStrategyName(int elevatorNumber);
    int getPeopleCountInside(int elevatorNumber);
    float getElevatorHeight(int elevatorNumber);
    double getElevatorMass(int elevatorNumber);
    double getElevatorMaxMass(int elevatorNumber);
    double getElevatorArea(int elevatorNumber);
    double getElevatorMaxArea(int elevatorNumber);
    boolean isElevatorOpen(int elevatorNumber);
}
