package lab5.views;

import javax.swing.*;

import lab5.*;
import lab5.ElevatorStrategies.BasicElevatorStrategy;
import lab5.ElevatorStrategies.OptimalElevatorStrategy;

public class MainForm {

    private JPanel mainPanel;
    private JButton start;
    private JTextField floors;
    private JButton addElevatorButton;
    private JTextArea elevatorList;
    private JTextField spawnRate;

    private static Building building;

    public MainForm() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
        addElevatorButton.addActionListener(e -> {
            new AddElevatorForm(this);
        });
    }

    public void addNewElevator(int selected, double maxMass, double maxVolume) {
        IElevatorStrategy strategy =
                selected == 0 ? new BasicElevatorStrategy() : (IElevatorStrategy) new OptimalElevatorStrategy();
        building.createElevator(strategy, building.getFloor(0), maxMass, maxVolume);
    }

    /**
     * method will be deleted in future.
     */
    public static void initBuilding(){
        MainForm.building = new Building();
        building.createFloors(5);
        building.createElevator(new BasicElevatorStrategy(), building.getFloor(0), 500, 500);
        building.createElevator(new OptimalElevatorStrategy(), building.getFloor(0), 500, 500);
        building.createElevator(new BasicElevatorStrategy(), building.getFloor(0), 500 , 500);
        building.createEntrances();
        building.addPerson(new Person("ManuallyCreatedPerson1", 60, 0.5, building.getFloor(0), 1, building));
        building.addPerson(new Person("ManuallyCreatedPerson2", 60, 0.5, building.getFloor(1), 0, building));
        building.addPerson(new Person("ManuallyCreatedPerson3", 60, 0.5, building.getFloor(2), 1, building));
        building.setupPersonGenerator(300, 15);
        building.startupBuildingThreads();
        building.waitForAllThread();
    }

    private static void buildAndDisplayUI(){
        new MainForm();
    }

    public static void main(String[] args){
        EventLogger.InitEventLogger("log.txt");

        buildAndDisplayUI();

        initBuilding();

        EventLogger.saveLogs();
    }
}
