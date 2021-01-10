package lab5.views;

import lab5.Building;
import lab5.EventLogger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BuildingForm {
    private Building building;
    private BuildingFrame buildingFrame;

    BuildingForm(Building building){
        this.building = building;
        building.startupBuildingThreads();

        buildingFrame = new BuildingFrame(building);
        buildingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildingFrame.setVisible(true);


        BuildingFormThread drawingThread = new BuildingFormThread();
        drawingThread.start();

        buildingFrame.addWindowListener(new WindowAdapter(){
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
            buildingFrame.moveElevators();
        }
    }
}

