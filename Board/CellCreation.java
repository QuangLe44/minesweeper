package Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Stack;

public class CellCreation {

    public static int cellsPressed = 0; // Number of cells expanded
    public static Bomb[] bombIndex = {}; // Location of bombs
    public static boolean bombPressed = false; // If bomb is pressed
    public static Stack<Object> undoStack = new Stack<>();          // store the previous moves made by the player

    // class to save states
    static class CellState {
        JButton button;
        Color background;
        String text;
        int x, y;
    
        public CellState(JButton button, Color background, String text, int x, int y) {
            this.button = button;
            this.background = background;
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }

    // Button Listener
    public static void buttonListener(int x, int y, int width, int height, int mines, JButton[][] button, JFrame frame, JPanel panel) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener(x,y,width,height,mines,button,panel);

                // Removing Action listener after pressed
                ((AbstractButton) e.getSource()).removeActionListener(this);
            }
        };
        // Binding action listener to button
        button[x][y].addActionListener(action);
    }

    // Logic of what happens when a button is pressed
    public static void listener(int x, int y, int width, int height, int mines, JButton[][] button, JPanel panel) {
        // If first click
        if(cellsPressed == 0 && button[x][y].getText().length() == 0) {
            // Bomb initializer
            bombIndex = Bomb.bombInitializer(x,y,width,height,mines);
        }

        // Getting Adjacent
        CellIndex[] adjacent = CellIndex.adjacentCells(x,y,width,height, bombIndex, false);
        if(!bombPressed && button[x][y].getText().length() == 0) {
            // If bomb is pressed
            if(cellsPressed != 0) {
                // If user has clicked on a bomb
                for(int i = 0; i < bombIndex.length; i++) {
                    if(bombIndex[i].x == x && bombIndex[i].y == y && !bombPressed) {
                        bombPressed = true;
                        button[x][y].setBackground(Color.RED);
                        panel.remove(CellIndex.findIndex(x,y,width,height));
                        panel.add(button[x][y],CellIndex.findIndex(x,y,width,height));
                    }
                }
            }

            // If bomb isn't pressed
            if(!bombPressed) {
                // Pushing previous state onto undo stack
                CellState previousState = new CellState(
                    button[x][y], button[x][y].getBackground(), button[x][y].getText(), x, y);
                undoStack.push(previousState);

                // Changing color of pressed cell
                button[x][y].setBackground(Color.GREEN);
                CellIndex entry = new CellIndex();
                entry.x = x;
                entry.y = y;
                int bombsNear = Bomb.adjacentBombs(bombIndex,entry,width,height);
                button[x][y].setText(Integer.toString(bombsNear));
                panel.remove(CellIndex.findIndex(x,y,width,height));
                panel.add(button[x][y],CellIndex.findIndex(x,y,width,height));

                // Performing adjacency loop
                adjacencyLoop(adjacent,bombIndex,panel,button,width,height,x,y);
                cellsPressed++;
            }
            // If bomb is pressed
            else {
                // For each cell on the grid
                for(int i = 0; i < width; i++) {
                    for(int j = 0; j < height; j++) {
                        // If cell is a bomb - Set cell to red
                        if(Bomb.isBomb(i,j,bombIndex)) {
                            button[i][j].setBackground(Color.RED);
                        }
                        // Otherwise - set color to green and get its' text value
                        else {
                            CellIndex entry = new CellIndex();
                            entry.x = i;
                            entry.y = j;
                            int bombsNear = Bomb.adjacentBombs(bombIndex,entry,width,height);
                            if(button[i][j].getText().length() != 1) {
                                button[i][j].setText(Integer.toString(bombsNear));
                            }
                            button[i][j].setBackground(Color.GREEN);
                        }
                        panel.remove(CellIndex.findIndex(i,j,width,height));
                        panel.add(button[i][j],CellIndex.findIndex(i,j,width,height));
                    }
                }
            }

        }
    }

    // Logic of when two cells are adjacent to each other
    public static void adjacencyLoop(CellIndex[] adjacent, Bomb[] bombIndex, JPanel panel, JButton[][] button, int width, int height, int x, int y) {
        // For each entry in the adjacency list
        for(int i = 0; i < adjacent.length; i++) {
            // If distance hasn't already been calculated
            if(button[x][y].getText().equals("0") && !button[adjacent[i].x][adjacent[i].y].getBackground().toString().equals("java.awt.Color[r=0,g=255,b=0]")) {
                // Calculating the number of bombs that are near
                int bombsNear = Bomb.adjacentBombs(bombIndex,adjacent[i],width,height);
                if(bombsNear != 0) {
                    button[adjacent[i].x][adjacent[i].y].setText(Integer.toString(bombsNear));
                    button[adjacent[i].x][adjacent[i].y].setBackground(Color.GREEN);
                    panel.remove(CellIndex.findIndex(adjacent[i].x,adjacent[i].y,width,height));
                    panel.add(button[adjacent[i].x][adjacent[i].y], CellIndex.findIndex(adjacent[i].x,adjacent[i].y,width,height));
                    cellsPressed++;

                } else { // Else further expansion is needed
                    // Get new adjacency information
                    CellIndex[] newAdj = CellIndex.adjacentCells(adjacent[i].x,adjacent[i].y,width,height, bombIndex, false);
                    listener(adjacent[i].x,adjacent[i].y, width, height, bombIndex.length, button,panel);
                }
            }
        }
    }


    // Reset global variables upon reset button press
    public static void reset() {
        cellsPressed = 0; // Number of cells expanded
        bombIndex = null; // Location of bombs
        bombPressed = false; // If bomb is pressed
    }

    // Undo variables upon undo button press
    public static void undo() {
        if (undoStack.isEmpty()) {          // if there is no moves made
            return;
        }
        CellState previousState = (CellState) undoStack.pop();          // pop the previous move from the stack
        previousState.button.setBackground(previousState.background);
        previousState.button.setText(previousState.text);
    }
}
