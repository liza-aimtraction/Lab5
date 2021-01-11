package lab5.views;

import javax.swing.*;

import lab5.*;
import lab5.Elevator;
import lab5.ElevatorStrategies.BasicElevatorStrategy;
import lab5.ElevatorStrategies.OptimalElevatorStrategy;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm {

    private JPanel mainPanel;
    private JButton start;
    private JTextField floors;
    private JButton addElevatorButton;
    private JTextArea elevatorList;
    private JTextField spawnRate;

    private BuildingBuilder builder;

    public MainForm() {
        builder = new BuildingBuilder();

        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
        addElevatorButton.addActionListener(e -> {
            new AddElevatorForm(this);
        });

        start.addActionListener(e -> {
            boolean isValid = builder.validate();
            if (isValid) {
                Building building = builder.build();
                BuildingForm buildingForm = new BuildingForm(building);
                frame.setVisible(false);
            }
            else {
                // TODO: message box about invalid input
            }
        });
    }

    public void addNewElevator(int selected, double maxMass, double maxVolume) {
        IElevatorStrategy strategy = (selected == 0) ? new BasicElevatorStrategy() : (IElevatorStrategy) new OptimalElevatorStrategy();
        lab5.Elevator elevator = building.createElevator(strategy, building.getFloor(0), maxMass, maxVolume);
        elevatorList.append(String.format("%s Strategy: %s; maxMass: %.2f; maxVolume: %.2f\n", elevator.getName(), strategy.getName(), maxMass, maxVolume));
    }

    /**
     * method will be deleted in future.
     */
    public static void initBuilding(){
        MainForm.building = new Building();
        building.setupPersonGenerator(500, 50);
    }

    private static void buildAndDisplayUI(){
        new MainForm();
    }

    public static void main(String[] args){
        EventLogger.InitEventLogger("log.txt");

        buildAndDisplayUI();

        initBuilding();
    }
}
