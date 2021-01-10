package lab5.views;

import lab5.Building;
import lab5.Elevator;
import lab5.EventLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BuildingForm {
    private Building building;
    private ElevatorFrame elevatorFrame;

    BuildingForm(Building building){
        this.building = building;
        building.startupBuildingThreads();
        elevatorFrame = new ElevatorFrame();
        elevatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elevatorFrame.setVisible(true);
        new Test().go();

        // elevatorFrame.moveElevators();

       // elevatorFrame.getPanel().repaint();

        BuildingFormThread drawingThread = new BuildingFormThread();
        drawingThread.start();

        elevatorFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                drawingThread.stop();
                building.killAllThreads();
                EventLogger.saveLogs();
                System.exit(0);
            }
        });


        //moveElevators();


//
//        while(true){
//            elevatorFrame.getPanel().repaint();
//
//            //The program waits a while before rerendering
//            try {
//                Thread.sleep(12);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    public void moveElevators()
    {
        for(int i = 0; i < 200; ++i) {
            System.out.println("Boo");
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

    public class BuildingFormThread extends Thread {

        // pass through constructor all you need
        public BuildingFormThread(){

        }
        @Override
        public void run() {
            while(true){
                //System.out.println("Seems to work good");
            }
        }
    }


}

