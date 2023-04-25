import Board.CellCreation;
import Board.Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;

public class MineSweeper {

    static int width = 12; // Width of play field
    static int height = 12; // Height of play field
    static int mines = 40; // Number of mines present

    // Setting up JFrame
    public static void frameStart(JFrame frame, int w, int h, int mine) {
        frame.setTitle("MineSweeper");
        // JFrame Setup
        width = w;
        height = h;
        mines = mine;
        frame.setSize(800,600);

        // Top and side UI setup
        frame.add(UI.uiTop(mines, frame, width, height), BorderLayout.NORTH);
        frame.add(UI.uiSide(mines, width, height, frame), BorderLayout.EAST);

        // Add grid layout
        frame.add(Layout.cellLayout(width,height,mines,frame));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Timer to check if the game is over
        ActionListener timeIncrement = new ActionListener() {
            boolean gameEnded = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                //If game has ended - stop timer
                if(CellCreation.bombPressed || CellCreation.cellsPressed == ((width * height) - mines)) {
                    UI.timeCounter.stop();
                    if (!gameEnded){
                        gameEnded = true;
                        if (CellCreation.bombPressed){
                            // Pop up the message box indicating the player has lost the game
                            JOptionPane optionPane = new JOptionPane("You lost!", JOptionPane.INFORMATION_MESSAGE, 
                                                                    JOptionPane.DEFAULT_OPTION, null, new Object[] {"Close"});
                            JDialog dialog = optionPane.createDialog("Game Over");
                            dialog.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    frame.dispose();
                                }
                            });
                            dialog.setVisible(true);
                        }
                        else {
                            // Pop up the message box indicating the player has won the game
                            JOptionPane optionPane = new JOptionPane("You won!", JOptionPane.INFORMATION_MESSAGE, 
                                                                    JOptionPane.DEFAULT_OPTION, null, new Object[] {"Close"});
                            JDialog dialog = optionPane.createDialog("Game Over");
                            dialog.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    frame.dispose();
                                }
                            });
                            dialog.setVisible(true);
                        }
                    } 
                    else {
                        frame.dispose();
                    }
                }
            }
        };
        Timer gameTimer = new Timer(100, timeIncrement);
        gameTimer.start();

    }

    public static void main(String args[]) {
        // Send layout of the board
        JFrame frame = new JFrame();
        frameStart(frame,width,height,mines);

    }

}
