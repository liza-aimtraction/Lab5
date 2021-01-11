package lab5.views;

import javax.swing.*;

import lab5.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

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
            boolean builderInitialized = initBuilder();
            if (!builderInitialized) {
                return;
            }

            boolean isValid = builder.validate();
            if (!isValid) {
                showError("Some input is out of bounds");
                return;
            }

            Building building = builder.build();
            BuildingForm buildingForm = new BuildingForm(building);
            frame.setVisible(false);
        });
    }

    private boolean initBuilder() {
        try {
            builder.setFloorCount(Integer.parseInt(floors.getText()));
        }
        catch (NumberFormatException e) {
            showError("Invalid floor count");
            return false;
        }

        try {
            builder.setSpawnRate(Float.parseFloat(spawnRate.getText()));
        }
        catch (NumberFormatException e) {
            showError("Invalid spawn rate");
            return false;
        }

        builder.setSpawnLimit(100);
        return true;
    }

    private void showError(String message) {
        showMessageDialog(null, message, "Wrong data", ERROR_MESSAGE);
    }

    public void addNewElevator(IElevatorStrategy strategy, double maxMass, double maxVolume) {
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
