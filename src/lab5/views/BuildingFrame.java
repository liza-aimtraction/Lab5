package lab5.views;

import lab5.Building;

import javax.swing.*;
import java.awt.*;

public class BuildingFrame extends JFrame {

    private JPanel buildingPanel;

    public BuildingFrame(Building building) {

        buildingPanel = new BuildingPanel(building);
        add(buildingPanel);

        pack(); // sets JFrame dimension to contain subcomponents
        setResizable(true);
        setTitle("Building Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void moveElevators()
    {
        while(true) {
            this.repaint();
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
