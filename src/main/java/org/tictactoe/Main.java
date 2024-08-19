package org.tictactoe;

import org.tictactoe.Controller.GameController;
import org.tictactoe.Exceptions.*;
import org.tictactoe.Models.*;
import org.tictactoe.Strategy.ColumnWinningStrategy;
import org.tictactoe.Strategy.DiagonalWinningStrategy;
import org.tictactoe.Strategy.RowWinningStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidStrategies, InvalidDimension, InvalidSymbol, InvalidPlayers, InvalidRowColumn {
        GameController gameController = new GameController();
        int dimension = 3;
        List<Player> players = List.of(
                new Player("Prithika", PlayerType.HUMAN, new Symbol("X")),
                new Player("Ramanan", PlayerType.BOT, new Symbol("O"))
        );
        Game game = gameController.startGame(
                dimension,
                players,
                List.of(
                        new RowWinningStrategy(dimension, players),
                        new ColumnWinningStrategy(dimension, players),
                        new DiagonalWinningStrategy(dimension, players)
                )
        );

        System.out.println("Game Started......Below is the board to start");
        gameController.printBoard(game);

        while (game.getGameStatus() == GameStatus.IN_PROGRESS) {
            gameController.makeMove(game);
            gameController.printBoard(game);
            gameController.undoMove(game);
        }

        gameController.printWinner(game);
    }
}