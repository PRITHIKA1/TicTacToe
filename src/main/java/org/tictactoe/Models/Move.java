package org.tictactoe.Models;

public class Move {
    private Cell cell;
    private Player player; // Though Player is present in cell, we need this, because at that time cell would have not got updated

    public Move(Cell cell, Player player) {
        this.cell = cell;
        this.player = player;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
