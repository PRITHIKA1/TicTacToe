package org.tictactoe.Strategy;

import org.tictactoe.Models.Board;
import org.tictactoe.Models.Move;
import org.tictactoe.Models.Player;

import java.util.HashMap;
import java.util.List;

public class RowWinningStrategy implements WinningStrategy {
    int dimension;
    HashMap<Integer, HashMap<String, Integer>> rowPlayerCountMap = new HashMap<Integer, HashMap<String, Integer>>();

    public RowWinningStrategy(int dimension, List<Player> players) {
        this.dimension = dimension;
        for (int i = 0; i < dimension; i++) {
            rowPlayerCountMap.put(i, new HashMap<>());
            for (Player player: players) {
                rowPlayerCountMap.get(i).put(player.getSymbol().getCharacter(), 0);
            }
        }
    }

    @Override
    public Player getWinner(Move move) {
        int row = move.getCell().getRow();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int symbolsCount = rowPlayerCountMap.get(row).get(symbol);
        rowPlayerCountMap.get(row).put(symbol, symbolsCount + 1);
        if(symbolsCount + 1 == dimension)
            return move.getCell().getPlayer();
        return null;
    }

    @Override
    public void revertPlayerCountFromMap(Move move) {
        int row = move.getCell().getRow();
        String symbol = move.getCell().getPlayer().getSymbol().getCharacter();
        int symbolsCount = rowPlayerCountMap.get(row).get(symbol);
        rowPlayerCountMap.get(row).put(symbol, symbolsCount - 1);
    }
}