package lab5.views;

import lab5.Building;

import javax.swing.*;
import java.awt.*;

public class BuildingPanel extends JPanel {

    private Building building;
    private ElevatorsPanel elevatorsPanel;
    private final int maxWidth = 300;
    private final int maxHeight = 300;

    private int width;
    private int height;

    BuildingPanel(Building building)
    {
        this.building = building;
        width = 400;
        height = 400;
       // width = building.getElevatorCount() * (ElevatorPanel.width + 10) + 100;
       // height = building.getFloorCount() * (ElevatorPanel.height + 10) + 100;
        //setLayout(null);
//        elevatorsPanel = new ElevatorsPanel();
//        add(elevatorsPanel);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(height, width);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int margin = 10;
        g.drawRect(margin, margin, maxWidth, maxHeight);
    }

    JPanel getPanel()
    {
        return elevatorsPanel;
    }

}

