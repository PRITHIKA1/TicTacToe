package org.tictactoe.Controller;

import org.tictactoe.Exceptions.*;
import org.tictactoe.Models.Game;
import org.tictactoe.Models.Player;
import org.tictactoe.Strategy.WinningStrategy;

import java.util.List;
import java.util.Scanner;

public class GameController {

    public Game startGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws InvalidStrategies, InvalidDimension, InvalidSymbol, InvalidPlayers {
        return Game.getGameBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public void makeMove(Game game) throws InvalidRowColumn {
        game.makeMove();
    }

    public void undoMove(Game game) throws InvalidRowColumn {
        game.undoMove();
    }

    public void checkAndSetWinner(Game game) {
        game.checkAndSetWinner();
    }

    public void printWinner(Game game) {
        game.printWinner();
    }
}
