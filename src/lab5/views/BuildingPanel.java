package lab5.views;

import lab5.Building;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingPanel extends JPanel {

    private Building building;
    private List<ElevatorPanel> elevatorPanels;
    private int width;
    private int height;

    private final int maxWidth = 300;
    private final int maxHeight = 300;
    private final int margin = 10;

    BuildingPanel(Building building)
    {
        elevatorPanels = new ArrayList<>();
        for(int i = 0; i < building.getElevatorCount(); ++i) {
            System.out.println("Elevator count: " + building.getElevatorCount());
            elevatorPanels.add(new ElevatorPanel(i * ElevatorPanel.width + margin, i * ElevatorPanel.height + margin, Color.RED, "3"));
            System.out.println(elevatorPanels.get(i).getX());
            //add(elevatorPanels.get(i));
        }
        this.building = building;
        width = 400;
        height = 400;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width + 20, height + 20);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i < building.getFloorCount(); ++i){
            g.drawRect(margin, i * ElevatorPanel.height + margin, width, ElevatorPanel.height);
        }

        for (ElevatorPanel panel: elevatorPanels) {
            panel.paintElevator(g);
        }
    }
}

