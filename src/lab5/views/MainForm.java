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

    public static void main(String[] args){

        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);

        EventLogger.InitEventLogger("log.txt");
        Building building = new Building();
        building.addFloor();
        building.addFloor(); // let's create 3 floors
        building.addFloor();
        building.addElevator("Elevator1", new BasicElevatorStrategy(), 2);
        building.addElevator("Elevator2", new BasicElevatorStrategy(),0);
        building.addEntranceToFloor(0, 0);
        building.addEntranceToFloor(0, 1);
        building.addEntranceToFloor(1, 0);
        building.addEntranceToFloor(1, 1);
        building.addEntranceToFloor(2, 0);
        building.addEntranceToFloor(2, 1);
        building.addPerson(new Person("ManuallyCreatedPerson1", 60, 0.5, building.getFloor(0)));
        building.addPerson(new Person("ManuallyCreatedPerson2", 90, 0.7, building.getFloor(2)));
        building.setupPersonGenerator(300, 10);
        building.startupBuildingThreads();
        building.waitForAllThread();

        EventLogger.saveLogs();
    }
}
