package Board;

import javax.swing.*;
import java.awt.*;

public class Layout {
    // Layout for Cells
    public static JPanel cellLayout(int width, int height,int mines, JFrame frame) {
        // Panel layout
        JPanel panel = new JPanel();
        GridLayout grid = new GridLayout(width,height);
        panel.setLayout(grid);

        // Configuring buttons
        JButton[][] buttons = new JButton[width][height];

        // Adding buttons to panel
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {

                // Setting up button properties
                buttons[i][j] = new JButton();
                	
                // Setting up listener for button
                CellCreation.buttonListener(i,j,width,height,mines,buttons,frame, panel);

                // Adding button to panel
                panel.add(buttons[i][j]);

            }
        }

        return panel;
    }

}
