package lab5;

import java.util.ArrayList;
import java.util.List;

public class BuildingBuilder {
    private class ElevatorCreationData {
        public String name;
        public IElevatorStrategy strategy;
        public double maxMass;
        public double maxArea;
        public int startingFloorNumber;

        public ElevatorCreationData(String name, IElevatorStrategy strategy, double maxMass, double maxArea, int startingFloorNumber) {
            this.name = name;
            this.strategy = strategy;
            this.maxMass = maxMass;
            this.maxArea = maxArea;
            this.startingFloorNumber = startingFloorNumber;
        }
    }

    private int floorsToBuild;
    private List<ElevatorCreationData> elevatorsToAdd;
    private float spawnRateInSeconds;
    private int spawnLimit;

    public BuildingBuilder() {
        elevatorsToAdd = new ArrayList<>();
        reset();
    }

    public void reset() {
        floorsToBuild = 0;
        elevatorsToAdd.clear();
        spawnRateInSeconds = 0;
        spawnLimit = 0;
    }

    public void setFloorCount(int count) {
        floorsToBuild = count;
    }

    public void setSpawnRate(float seconds) {
        spawnRateInSeconds = seconds;
    }

    public void setSpawnLimit(int count) {
        spawnLimit = count;
    }

    public void addElevator(IElevatorStrategy strategy, double maxMass, double maxArea) {
        String name = "Elevator" + elevatorsToAdd.size();
        int startingFloor = 0;
        ElevatorCreationData data = new ElevatorCreationData(name, strategy, maxMass, maxArea, startingFloor);
        elevatorsToAdd.add(data);
    }

    public boolean validate() {
        if (floorsToBuild <= 0) return false;
        if (spawnRateInSeconds <= 0) return false;
        if (spawnLimit <= 0) return false;
        if (!validateElevators()) return false;

        return true;
    }

    public Building build() {
        Building building = new Building();

        buildFloors(building);
        createElevators(building);
        initPeopleGenerator(building);

        return building;
    }

    private boolean validateElevators() {
        for (ElevatorCreationData data : elevatorsToAdd) {
            if (data.startingFloorNumber < 0 || data.startingFloorNumber >= floorsToBuild) {
                return false;
            }
        }
        return true;
    }

    private void buildFloors(Building building) {
        for (int i = 0; i < floorsToBuild; ++i) {
            Floor floor = building.addFloor();
            createEntrancesOnNewFloorForEveryElevator(building, floor);
        }
    }

    private void createElevators(Building building) {
        for (ElevatorCreationData data : elevatorsToAdd) {
            createElevator(building, data);
        }
    }

    private void initPeopleGenerator(Building building) {
        int spawnRateInMilliseconds = Math.round(spawnRateInSeconds * 1000.0f);
        building.setupPersonGenerator(spawnRateInMilliseconds, spawnLimit);
    }

    private Elevator createElevator(Building building, ElevatorCreationData data){
        if(data.startingFloorNumber < 0 || data.startingFloorNumber >= building.getFloorCount()){
            throw new Error("createElevator: Invalid floor passed");
        }
        else {
            Floor startingFloor = building.getFloor(data.startingFloorNumber);
            Elevator elevator = new Elevator(data.name, building, startingFloor, data.strategy, data.maxMass, data.maxArea);
            building.addElevator(elevator);
            createEntrancesForNewElevatorOnEveryFloor(building, elevator);
            return elevator;
        }
    }

    private void createEntrancesForNewElevatorOnEveryFloor(Building building, Elevator elevator) {
        for (int floorNumber = 0; floorNumber < building.getFloorCount(); ++floorNumber) {
            Floor floor = building.getFloor(floorNumber);

            tryAddEntranceForElevatorOnFloor(floor, elevator);
        }
    }

    private void createEntrancesOnNewFloorForEveryElevator(Building building, Floor floor) {
        for (int elevatorNumber = 0; elevatorNumber < building.getElevatorCount(); ++elevatorNumber) {
            Elevator elevator = building.getElevator(elevatorNumber);

            tryAddEntranceForElevatorOnFloor(floor, elevator);
        }
    }

    private void tryAddEntranceForElevatorOnFloor(Floor floor, Elevator elevator) {
        // is it stupid? Perhaps returning null if entrance doesn't exist instead of throwing error would be better
        try {
            floor.getElevatorEntranceByElevator(elevator);
            EventLogger.log("Tried to create already existing elevator entrance: " + elevator.getName() + " floor = " + floor.getNumber(), elevator.getName());
        }
        catch (Error e) {
            ElevatorEntrance entrance = new ElevatorEntrance(elevator);
            floor.addEntrance(entrance);
        }
    }
}
