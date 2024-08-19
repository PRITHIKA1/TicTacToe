package org.tictactoe.Models;

import org.tictactoe.Exceptions.InvalidRowColumn;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> cells;

    public Board(int dimension) {
        this.dimension = dimension;
        this.cells = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            List<Cell> cellsList  = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                Cell cell = new Cell(i, j);
                cellsList.add(cell);
            }
            this.cells.add(cellsList);
        }
    }

    public Boolean validateRowColumn(int row, int column) {
        if (row >= dimension || column >= dimension) {
            System.out.println("Invalid row and column");
            return false;
        }

        if(this.cells.get(row).get(column).getCellStatus() != CellStatus.EMPTY) {
            System.out.println("Cell is already occupied");
            return false;
        }

        return true;
    }

    public Cell getCellToMove(int row, int column) {
        return this.cells.get(row).get(column);
    }

    public void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print("|");
                this.getCells().get(i).get(j).printCell();
            }
            System.out.print("|");
            System.out.println();
            System.out.println("-------------");
        }
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public void setCells(List<List<Cell>> cells) {
        this.cells = cells;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}