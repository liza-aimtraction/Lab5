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
            initBuilder();
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

    // TODO: handle forms input instead of this
    private void initBuilder() {
        builder.setFloorCount(7);
        builder.setSpawnLimit(100);
        builder.setSpawnRate(1);
        builder.addElevator(new BasicElevatorStrategy(), 300, 200);
        builder.addElevator(new OptimalElevatorStrategy(), 1000, 300);
    }

    public void addNewElevator(int selected, double maxMass, double maxVolume) {
        IElevatorStrategy strategy = (selected == 0) ? new BasicElevatorStrategy() : (IElevatorStrategy) new OptimalElevatorStrategy();
        builder.addElevator(strategy, maxMass, maxVolume);
        elevatorList.append(String.format("Created elevator. Strategy: %s; maxMass: %.2f; maxVolume: %.2f\n", strategy.getName(), maxMass, maxVolume));
    }

    private static void buildAndDisplayUI(){
        new MainForm();
    }

    public static void main(String[] args){
        EventLogger.InitEventLogger("log.txt");

        buildAndDisplayUI();
    }
}
