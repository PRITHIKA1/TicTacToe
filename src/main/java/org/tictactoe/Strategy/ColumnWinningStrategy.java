package org.tictactoe.Strategy;

import org.tictactoe.Models.Move;
import org.tictactoe.Models.Player;

import java.util.HashMap;
import java.util.List;

public class ColumnWinningStrategy implements WinningStrategy {
    int dimension;
    HashMap<Integer, HashMap<String, Integer>> columnPlayerCountMap = new HashMap<Integer, HashMap<String, Integer>>();

    public ColumnWinningStrategy(int dimension, List<Player> players) {
        this.dimension = dimension;
        for (int i = 0; i < dimension; i++) {
            columnPlayerCountMap.put(i, new HashMap<>());
            for (Player player: players) {
                columnPlayerCountMap.get(i).put(player.getSymbol().getCharacter(), 0);
            }
        }
    }

    @Override
    public Player getWinner(Move move) {
        int column = move.getCell().getCol();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int symbolsCount = columnPlayerCountMap.get(column).get(symbol);
        columnPlayerCountMap.get(column).put(symbol, symbolsCount + 1);
        if(symbolsCount + 1 == dimension)
            return move.getCell().getPlayer();
        return null;
    }

    @Override
    public void revertPlayerCountFromMap(Move move) {
        int column = move.getCell().getCol();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int symbolsCount = columnPlayerCountMap.get(column).get(symbol);
        columnPlayerCountMap.get(column).put(symbol, symbolsCount - 1);
    }
}
