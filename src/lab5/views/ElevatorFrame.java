package lab5.views;

import javax.swing.*;
import java.awt.*;

public class ElevatorFrame extends JFrame {

    private Elevator elevatorPanel;

    public ElevatorFrame() {

        elevatorPanel = new Elevator(0, 0, 50, 50, Color.BLUE, "6");
        add(elevatorPanel);

//        elevatorPanel = new JPanel();
//        elevatorPanel.setLayout(new BoxLayout(elevatorPanel, BoxLayout.X_AXIS));
//
//        elevatorPanel.add(new Elevator(0, 0, 50, 50, Color.BLUE, "5"));
//        elevatorPanel.add(new Elevator(60, 0, 50, 50, Color.RED, "5"));
//        elevatorPanel.add(new Elevator(120, 0, 50, 50, Color.GREEN, "5"));
//
//        add(elevatorPanel);

        pack(); // sets JFrame dimension to contain subcomponents

        setResizable(false);
        setTitle("Graphics Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null); // centers window on screen
    }

    Elevator getPanel()
    {
        return elevatorPanel;
    }

    public void moveElevators()
    {
        for(int i = 0; i < 200; ++i) {
            System.out.println("moveElevators in Elevator Frame");
            //elevatorPanel.revalidate();
            elevatorPanel.repaint();

//            try
//            {
//                Thread.sleep(30);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
