package lab5.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public MainForm() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
        addElevatorButton.addActionListener(e -> {
            AddElevatorForm addForm = new AddElevatorForm(this);
        });
        start.addActionListener(e -> {
            BuildingForm buildingForm = new BuildingForm(this);
        });
    }

    /**
     * method will be deleted in future.
     */
    public static void initBuilding(){
        MainForm.building = new Building();
        building.createFloors(5);
        building.createElevator(new BasicElevatorStrategy(), building.getFloor(0));
        building.createElevator(new BasicElevatorStrategy(), building.getFloor(0));
        building.createElevator(new BasicElevatorStrategy(), building.getFloor(0));
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
