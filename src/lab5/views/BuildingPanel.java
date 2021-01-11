package lab5.views;

import lab5.Building;
import lab5.Elevator;
import lab5.IBuildingFacade;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildingPanel extends JPanel {

    private IBuildingFacade building;
    private List<ElevatorPanel> elevatorPanels;
    private int width;
    private int height;

    private final int margin = 10;
    private final int waitingPlaceWidth = 50;


    BuildingPanel(IBuildingFacade building)
    {
        this.building = building;
        width = building.getElevatorCount() * (ElevatorPanel.width + waitingPlaceWidth);
        height = building.getFloorCount() * ElevatorPanel.height;

        elevatorPanels = new ArrayList<>();
        for(int i = 0; i < building.getElevatorCount(); ++i) {
            elevatorPanels.add(new ElevatorPanel(i  * (ElevatorPanel.width + waitingPlaceWidth) + margin, height - ElevatorPanel.height, getStrategyColor(building.getElevatorStrategyName(i)),
                    String.format("%d", building.getPeopleCountInside(i) ),
                    String.format("M: %d/%d", (int)building.getElevatorMass(i), (int)building.getElevatorMaxMass(i)),
                    String.format("S: %d/%d", (int)building.getElevatorArea(i), (int)building.getElevatorMaxArea(i)),
                    building.isElevatorOpen(i)));
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public Color getStrategyColor(String strategyName) {
        switch (strategyName)
        {
            case("Basic"): {
                return Color.BLUE;
            }
            case("Prioritized"): {
                return Color.YELLOW;
            }
            case("Round-Robin"): {
                return Color.GREEN;
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
            paintFloor(g, i);

            for(int j = 0; j < elevatorPanels.size(); ++j) {
                paintPeopleOutside(g, i, j);
            }
        }

        for (int i = 0; i < elevatorPanels.size(); ++i) {

            paintWaitingLine(g, i);

            float elevatorHeight = building.getFloorCount() -  1 - building.getElevatorHeight(i);
            int y = (int)(elevatorHeight * ElevatorPanel.height) + margin;
            elevatorPanels.get(i).update(y, building.isElevatorOpen(i), String.format("%d", building.getPeopleCountInside(i) ),
                    String.format("M: %d/%d", (int)building.getElevatorMass(i), (int)building.getElevatorMaxMass(i)),
                    String.format("S: %d/%d", (int)building.getElevatorArea(i), (int)building.getElevatorMaxArea(i)));
            elevatorPanels.get(i).paintElevator(g);
        }
    }

    void paintFloor( Graphics g, int floorNumber) {
        g.drawRect(margin, floorNumber * ElevatorPanel.height + margin, width, ElevatorPanel.height);
    }

    void paintPeopleOutside(Graphics g, int floorNumber, int elevatorNumber) {
        g.drawString(String.valueOf(building.getPeopleCountOutside(floorNumber, elevatorNumber)),
                (elevatorNumber + 1) * ElevatorPanel.width + (elevatorNumber * waitingPlaceWidth) + waitingPlaceWidth / 2,
                (building.getFloorCount() - 1 - floorNumber) * ElevatorPanel.height + ElevatorPanel.height / 2 + margin);
    }

    void paintWaitingLine(Graphics g, int elevatorNumber){
        g.drawLine((elevatorNumber + 1) * (ElevatorPanel.width + waitingPlaceWidth) + margin, margin,
                (elevatorNumber + 1) * (ElevatorPanel.width + waitingPlaceWidth) + margin, height + margin * 2 - margin) ;
    }
}

