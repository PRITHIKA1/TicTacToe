package org.tictactoe.Strategy;

import org.tictactoe.Models.Move;
import org.tictactoe.Models.Player;

import java.util.HashMap;
import java.util.List;

public class DiagonalWinningStrategy implements WinningStrategy{
    int dimension;
    HashMap<Integer, HashMap<String, Integer>> diagonalPlayerCountMap = new HashMap<Integer, HashMap<String, Integer>>();

    public DiagonalWinningStrategy(int dimension, List<Player> players) {
        this.dimension = dimension;
        for (int i = 0; i < 2; i++) {
            diagonalPlayerCountMap.put(i, new HashMap<>());
            for (Player player: players) {
                diagonalPlayerCountMap.get(i).put(player.getSymbol().getCharacter(), 0);
            }
        }
    }

    @Override
    public Player getWinner(Move move) {
        int row = move.getCell().getRow();
        int column = move.getCell().getCol();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int diagonal;
        if(row == column) {
            diagonal = 0;
            if (checkValidDiagonalMapping(diagonal, symbol))
                return move.getCell().getPlayer();
        }
        if(row + column == dimension - 1) {
            diagonal = 1;
            if (checkValidDiagonalMapping(diagonal, symbol))
                return move.getCell().getPlayer();
        }
        return null;
    }

    @Override
    public void revertPlayerCountFromMap(Move move) {
        int row = move.getCell().getRow();
        int column = move.getCell().getCol();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int diagonal;
        if(row == column) {
            diagonal = 0;
            revertDiagonalCount(diagonal, symbol);
        }
        if(row + column == dimension - 1) {
            diagonal = 1;
            revertDiagonalCount(diagonal, symbol);
        }
    }

    private boolean checkValidDiagonalMapping(int diagonal, String symbol) {
        int symbolsCount = diagonalPlayerCountMap.get(diagonal).get(symbol);
        diagonalPlayerCountMap.get(diagonal).put(symbol, symbolsCount + 1);
        return symbolsCount + 1 == dimension;
    }

    private void revertDiagonalCount(int diagonal, String symbol) {
        int symbolsCount = diagonalPlayerCountMap.get(diagonal).get(symbol);
        diagonalPlayerCountMap.get(diagonal).put(symbol, symbolsCount - 1);
    }
}
