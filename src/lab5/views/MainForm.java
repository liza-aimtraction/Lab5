package lab5.views;

import lab5.Building;
import lab5.EventLogger;
import lab5.Person;

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

        EventLogger logger = new EventLogger("log.txt");
        Building building = new Building(logger, 300, 10);
        building.addPerson(new Person("ManuallyCreatedPerson1", logger));
        building.addPerson(new Person("ManuallyCreatedPerson2", logger));


        building.startupBuildingThreads();
        building.waitForAllThread();

        logger.saveLogs();
    }
}
