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

    public ElevatorPanel(int x, int y, Color color, String text) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
        setSize(width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void paintElevator(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
        int centerX = x + (int)(width / 2.0);
        int centerY = y + (int)(height / 2.0);
        System.out.printf("x: %d, y: %d, centerX: %d, centerY: %d: ", x, y, centerX, centerY);
        g.drawString(text, centerX, centerY);
        ++y;
    }

}
