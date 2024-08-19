package org.tictactoe.Models;

public class Cell {
    private int row;
    private int col;
    private Player player;
    private CellStatus cellStatus;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellStatus = CellStatus.EMPTY;
    }

    public void makeMove(Player p) {
        this.player = p;
        this.cellStatus = CellStatus.FILLED;
    }

    public void printCell() {
        if (cellStatus == CellStatus.EMPTY) {
            System.out.print(" - ");
        } else if (cellStatus == CellStatus.FILLED) {
            System.out.print(" " + player.getSymbol().getCharacter() + " ");
        }
    }

    public void clearCell() {
        this.player = null;
        this.cellStatus = CellStatus.EMPTY;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
