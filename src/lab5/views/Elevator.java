package lab5.views;

import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class Elevator extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private String text;

    public Elevator(int x, int y, int width, int height, Color color, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.drawRect(x, y, width, height);
        int centerX = x + (int)(width / 2.0);
        int centerY = y + (int)(height / 2.0);
        System.out.printf("x: %d, y: %d, centerX: %d, centerY: %d: ", x, y, centerX, centerY);
        g.drawString(text, centerX, centerY);
    }
}
