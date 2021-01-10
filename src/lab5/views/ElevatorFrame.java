
package lab5.views;

import javax.swing.*;
import java.awt.*;

public class ElevatorFrame extends JFrame {

    BuildingPanel buildingPanel;

    public ElevatorFrame() {

        buildingPanel = new BuildingPanel(null);
        //buildingPanel.setLayout(null);
        //setContentPane(buildingPanel);

        add(buildingPanel);

        pack(); // sets JFrame dimension to contain subcomponents
        setLayout(null);
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