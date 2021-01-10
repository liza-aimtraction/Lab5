
package lab5.views;

import javax.swing.*;
import java.awt.*;

public class ElevatorFrame extends JFrame {

    BuildingPanel buildingPanel;

    public ElevatorFrame() {

        buildingPanel = new BuildingPanel(null);
        buildingPanel.setLayout(null);
        setContentPane(buildingPanel);
        add(new ElevatorPanel(0, 0,  Color.BLUE, "5"));
        add(new ElevatorPanel(60, 0, Color.RED, "5"));
        add(new ElevatorPanel(120, 0, Color.GREEN, "5"));

        //add(buildingPanel);

        pack(); // sets JFrame dimension to contain subcomponents

        setResizable(false);
        setTitle("Graphics Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null); // centers window on screen
        setVisible(true);
    }

    JPanel getPanel()
    {
        return buildingPanel.getPanel();
    }
}