package lab5.views;

import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class ElevatorPanel extends JPanel  {

    private int x;
    private int y;
    public static final int width = 70;
    public static final int height = 70;
    private Color color;
    private String peopleText;
    private String massText;
    private String areaText;
    private  boolean isOpen;

    public ElevatorPanel(int x, int y, Color color, String peopleText, String massText, String areaText, boolean isOpen) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.peopleText = peopleText;
        this.massText = massText;
        this.areaText = areaText;
        this.isOpen = isOpen;
        setSize(width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void update(int y, boolean isOpen, String peopleText, String massText, String areaText){
        this.y = y;
        this.isOpen = isOpen;
        this.peopleText = peopleText;
        this.massText = massText;
        this.areaText = areaText;
    }

    public void paintElevator(Graphics g) {
        g.setColor(color);
        if(isOpen)
            g.drawRect(x, y, width, height);
        else
            g.fillRect(x, y, width, height);
        int peopleTextX = x + (int)(width / 2.0);
        int peopleTextY = y + (int)(height / 2.0);
        g.setColor(Color.BLACK);
        g.drawString(peopleText, peopleTextX, peopleTextY);
        g.drawString(massText, peopleTextX - (massText.length()*3), peopleTextY + 15);
        g.drawString(areaText, peopleTextX - (areaText.length()*3), peopleTextY + 30);
    }

}
