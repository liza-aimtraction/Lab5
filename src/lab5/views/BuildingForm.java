package lab5.views;

import lab5.Building;
import lab5.EventLogger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BuildingForm {
    private Building building;
    private BuildingFrame buildingFrame;
    private ElevatorFrame elevatorFrame;

    BuildingForm(Building building){
        this.building = building;
        building.startupBuildingThreads();

        elevatorFrame = new ElevatorFrame();
        elevatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elevatorFrame.setVisible(true);


        //buildingFrame = new BuildingFrame(building);

        BuildingFormThread drawingThread = new BuildingFormThread();
        drawingThread.start();

        //TODO: change to buildingFrame
        elevatorFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                drawingThread.stop();
                building.killAllThreads();
                EventLogger.saveLogs();
                System.exit(0);
            }
        });
    }

    public class BuildingFormThread extends Thread {

        public BuildingFormThread(){

        }

        @Override
        public void run() {
            moveElevators();
            //buildingFrame.moveElevators();
        }

        public void moveElevators()
        {
            while(true) {
                elevatorFrame.getPanel().repaint();
                try
                {
                    Thread.sleep(30);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




}

