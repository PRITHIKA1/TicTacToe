package org.tictactoe.Strategy;

import org.tictactoe.Models.Board;
import org.tictactoe.Models.Move;
import org.tictactoe.Models.Player;

public interface WinningStrategy {
    Player getWinner(Move move);
    void revertPlayerCountFromMap(Move move);
}
