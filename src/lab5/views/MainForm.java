package lab5.views;

import lab5.*;
import lab5.ElevatorStrategies.BasicElevatorStrategy;

import javax.swing.*;

public class MainForm {

    private JPanel mainPanel;
    private JButton start;
    private JTextField floors;
    private JButton addElevatorButton;
    private JTextArea elevatorList;
    private JTextField spawnRate;

    private static Building building;

    /**
     * method will be deleted in future.
     */
    public static void initBuilding(){
        MainForm.building = new Building();
        building.createFloors(3);
        building.createElevator(new BasicElevatorStrategy(), 2);
        building.createElevator(new BasicElevatorStrategy(),0);
        building.createEntrances();
        building.addPerson(new Person("ManuallyCreatedPerson1", 60, 0.5, building.getFloor(0), 1));
        building.addPerson(new Person("ManuallyCreatedPerson2", 90, 0.7, building.getFloor(2), 1));
        building.setupPersonGenerator(300, 10);
        building.startupBuildingThreads();
        building.waitForAllThread();
    }

    private static void buildAndDisplayUI(){
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        EventLogger.InitEventLogger("log.txt");

        buildAndDisplayUI();

        initBuilding();

        EventLogger.saveLogs();
    }
}
