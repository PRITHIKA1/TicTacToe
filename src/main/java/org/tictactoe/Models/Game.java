package org.tictactoe.Models;

import org.tictactoe.Exceptions.*;
import org.tictactoe.Strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private GameStatus gameStatus;
    private List<Player> players;
    private int nextPlayerIndex;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategies;
    private Player winner;

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.moves = new ArrayList<>();
        this.nextPlayerIndex = 0;
        this.winner = null;
    }

    public void printBoard() {
        this.board.printBoard();
    }

    public Boolean validateRowColumn(int row, int column) {
        return this.board.validateRowColumn(row, column);
    }

    public void undoMove() {
        while (true) {
            if (moves.isEmpty()) {
                System.out.println("No moves left to undo");
                return;
            }
            int movedPlayerIndex = (nextPlayerIndex - 1 + players.size()) % players.size();
            Scanner sc = new Scanner(System.in);
            System.out.println("Do you want to undo " + this.players.get(movedPlayerIndex).getName() + "'s move ? Y / N: ");
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                Move lastMove = moves.removeLast();
                revertPlayerSymbolCount(lastMove);
                lastMove.getCell().clearCell();
                nextPlayerIndex = (nextPlayerIndex - 1 + players.size()) % players.size();
                printBoard();
            }
            else
                return;
        }
    }


    public void makeMove() throws InvalidRowColumn {
        Player player = this.players.get(nextPlayerIndex);
        System.out.println("Hey " + player.getName() + "!!! It's your turn");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Row: ");
        int row = sc.nextInt();
        System.out.println("Enter Column: ");
        int column = sc.nextInt();

        if(!validateRowColumn(row, column))
            return;

        Cell cell = this.board.getCellToMove(row, column);
        cell.makeMove(player);
        moves.add(new Move(cell, player));
        nextPlayerIndex = (nextPlayerIndex + 1 ) % this.players.size();
        checkAndSetWinner();
    }

    public void checkAndSetWinner() {
        for (WinningStrategy winningStrategy : this.winningStrategies) {
            Player winner = winningStrategy.getWinner(moves.get(moves.size() - 1));
            if (winner != null) {
                this.winner = winner;
                this.gameStatus = GameStatus.WIN;
                break;
            }
        }
        int totalCells = this.board.getDimension() * this.board.getDimension();
        if (this.moves.size() == totalCells)
            this.gameStatus = GameStatus.DRAW;
    }

    public void printWinner() {
        if (winner != null) {
            System.out.println("Hurray !!! " + winner.getName() + ", You WON the game. CONGRATS !!! ");
        }
        else {
            System.out.println("The Match is Draw!!! Better Luck Next time");
        }
    }

    public void revertPlayerSymbolCount(Move move) {
        gameStatus = GameStatus.IN_PROGRESS;
        winner = null;
        for (WinningStrategy winningStrategy: this.winningStrategies) {
            winningStrategy.revertPlayerCountFromMap(move);
        }
    }

    public static GameBuilder getGameBuilder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        private GameBuilder() {
            this.dimension = 0;
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
        }

        private void validateDimension(int dimension, List<Player> players) throws InvalidDimension {
            if(dimension != players.size() + 1)
                throw new InvalidDimension("Invalid Dimension");
        }

        private void validateBotCount(List<Player> players) throws InvalidPlayers {
            int botCount = 0;
            for (Player player : players) {
                if (player.getPlayerType() == PlayerType.BOT)
                    botCount++;
            }
            if (botCount > 1)
                throw new InvalidPlayers("Bot count exceeds 1");
        }

        private void validateUniqueSymbols(List<Player> players) throws InvalidSymbol {
            HashSet<String> symbols = new HashSet<String>();
            for (Player p : players) {
                if (symbols.contains(p.getSymbol().getCharacter()))
                    throw new InvalidSymbol("Already Existing symbols / Invalid Symbol");
                symbols.add(p.getSymbol().getCharacter());
            }
        }

        private void validateStrategies(List<WinningStrategy> winningStrategies) throws InvalidStrategies {
            if(winningStrategies.isEmpty()) {
                throw new InvalidStrategies("No winning strategies provided");
            }
        }

        private void validateGameConfiguration(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws InvalidDimension, InvalidPlayers, InvalidSymbol, InvalidStrategies {
            validateDimension(dimension, players);
            validateBotCount(players);
            validateUniqueSymbols(players);
            validateStrategies(winningStrategies);
        }

        public Game build() throws InvalidDimension, InvalidPlayers, InvalidStrategies, InvalidSymbol {
            validateGameConfiguration(dimension, players, winningStrategies);

            return new Game(
                dimension,
                players,
                winningStrategies
            );
        }

        public List<Player> getPlayers() {
            return players;
        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public List<WinningStrategy> getWinningStrategies() {
            return winningStrategies;
        }

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public int getDimension() {
            return dimension;
        }

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
