package lab5.views;

import lab5.Building;
import lab5.Elevator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        this.building = building;
        width = building.getElevatorCount() * ElevatorPanel.width;
        height = building.getFloorCount() * ElevatorPanel.height;

        elevatorPanels = new ArrayList<>();
        for(int i = 0; i < building.getElevatorCount(); ++i) {
            elevatorPanels.add(new ElevatorPanel(i * ElevatorPanel.width + margin, height - ElevatorPanel.height, getStrategyColor(building.getElevatorStrategyName(i)),
                    String.valueOf(building.getPeopleCountInside(i)), building.isElevatorOpen(i)));
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public Color getStrategyColor(String strategyName) {
        switch (strategyName)
        {
            case("Basic"): {
                return Color.BLUE;
            }
            case("Optimal"): {
                return  Color.YELLOW;
            }
            default:
                return Color.BLACK;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width + margin * 2, height + margin * 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i < building.getFloorCount(); ++i){
            g.drawRect(margin, i * ElevatorPanel.height + margin, width, ElevatorPanel.height);

        }

        for (int i = 0; i < elevatorPanels.size(); ++i) {
            float elevatorHeight = building.getFloorCount() -  1 - building.getElevatorHeight(i);
            System.out.println("elevatorHeight:" + elevatorHeight);
            int y = (int)(elevatorHeight * ElevatorPanel.height) + margin;
            elevatorPanels.get(i).update(y, building.isElevatorOpen(i));
            elevatorPanels.get(i).paintElevator(g);
        }
    }
}

