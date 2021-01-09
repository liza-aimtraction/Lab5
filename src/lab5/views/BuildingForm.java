package lab5.views;

import lab5.Building;
import lab5.Elevator;
import lab5.EventLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BuildingForm {
    private JPanel mainPanel;
    private Building building;

    BuildingForm(MainForm form, Building building){
        this.building = building;
        building.startupBuildingThreads();
        ElevatorFrame elevatorFrame = new ElevatorFrame();
        elevatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elevatorFrame.setVisible(true);

        elevatorFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                //building.waitForAllThread();
                EventLogger.saveLogs();
                System.exit(0);
            }
        });

//        JFrame frame = new JFrame("BuildingForm");
//        mainPanel = new JPanel();
//        frame.setContentPane(mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(700, 400);
//        frame.setVisible(true);

    }


}

