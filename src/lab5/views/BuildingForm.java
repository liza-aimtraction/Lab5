package lab5.views;

import javax.swing.*;

public class BuildingForm {
    private JPanel mainPanel;

    BuildingForm(){
        JFrame frame = new JFrame("BuildingForm");
        mainPanel = new JPanel();
        frame.setContentPane(new BuildingForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
    }
}
