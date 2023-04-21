package Board;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CellIndex {
    public int x;
    public int y;

    // Gets the index value of the x,y position of the cell
    public static int findIndex(int x, int y, int width, int height) {
        int index = (x * height) + y;
        return index;
    }

    // Pushes new value into a Cell Index Array
    public static CellIndex[] increaseIndex(CellIndex entry, CellIndex[] cellIndex) {
        CellIndex[] newCell = Arrays.copyOf(cellIndex, cellIndex.length + 1);
        newCell[newCell.length - 1] = entry;
        return newCell;
    }

    // Getting Adjacent cells of the bomb
    public static CellIndex[] adjacentCells(int x, int y, int width, int height, Bomb[] bombIndex, boolean context) {
        CellIndex[] cellIndex = {};

        // Getting Adjacent cells clicked cell
        if(Bomb.isBomb(x,y,bombIndex) && !context) {
            return cellIndex;
        }
        // NORTH
        if((x-1) >= 0) {
            CellIndex entry = new CellIndex();
            entry.x = x-1;
            entry.y = y;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // SOUTH
        if((x+1) < width) {
            CellIndex entry = new CellIndex();
            entry.x = x+1;
            entry.y = y;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // EAST
        if((y+1) < height) {
            CellIndex entry = new CellIndex();
            entry.x = x;
            entry.y = y+1;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // WEST
        if((y-1) >= 0) {
            CellIndex entry = new CellIndex();
            entry.x = x;
            entry.y = y-1;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // NORTH-EAST
        if((x-1) >= 0 && (y+1) < height) {
            CellIndex entry = new CellIndex();
            entry.x = x-1;
            entry.y = y+1;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // NORTH-WEST
        if((x-1) >= 0 && (y-1) >= 0) {
            CellIndex entry = new CellIndex();
            entry.x = x-1;
            entry.y = y-1;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // SOUTH-EAST
        if((x+1) < width && (y+1) < height) {
            CellIndex entry = new CellIndex();
            entry.x = x+1;
            entry.y = y+1;
            cellIndex = increaseIndex(entry,cellIndex);
        }

        // SOUTH-WEST
        if((x+1) < width && (y-1) >= 0) {
            CellIndex entry = new CellIndex();
            entry.x = x+1;
            entry.y = y-1;
            cellIndex = increaseIndex(entry,cellIndex);
        }


        return cellIndex;
    }

}
