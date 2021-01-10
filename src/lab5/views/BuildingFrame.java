package lab5.views;

import lab5.Building;

import javax.swing.*;
import java.awt.*;

public class BuildingFrame extends JFrame {

    private JPanel buildingPanel;
    private static final int repaintDelay = 50;

    public BuildingFrame(Building building) {

        buildingPanel = new BuildingPanel(building);
        add(buildingPanel);

        pack(); // sets JFrame dimension to contain subcomponents
        setResizable(true);
        setTitle("Building Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void moveElevators()
    {
        while(true) {
            this.repaint();
            try
            {
                Thread.sleep(repaintDelay);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
