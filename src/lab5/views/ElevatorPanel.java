package lab5.views;

import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class ElevatorPanel extends JPanel  {

    private int x;
    private int y;
    public static final int width = 50;
    public static final int height = 50;
    private Color color;
    private String text;
    private  boolean isOpen;

    public ElevatorPanel(int x, int y, Color color, String text, boolean isOpen) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
        this.isOpen = isOpen;
        setSize(width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void update(int y, boolean isOpen, String text){
        this.y = y;
        this.isOpen = isOpen;
        this.text = text;

    }

    public void paintElevator(Graphics g) {
        g.setColor(color);
        if(isOpen)
            g.drawRect(x, y, width, height);
        else
            g.fillRect(x, y, width, height);
        int centerX = x + (int)(width / 2.0);
        int centerY = y + (int)(height / 2.0);
        System.out.printf("x: %d, y: %d, centerX: %d, centerY: %d: ", x, y, centerX, centerY);
        g.setColor(Color.BLACK);
        g.drawString(text, centerX, centerY);
        --y;
    }

}
