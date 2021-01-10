package lab5;

public interface IBuildingFacade {
    int getFloorCount();
    int getPeopleCountOutside(int floorNumber, int entranceNumber);
    int getPeopleCountInside(int elevatorNumber);
    float getElevatorHeight(int elevatorNumber);
    boolean isElevatorOpen(int elevatorNumber);
}
