import java.util.HashMap;
import java.util.Scanner;

public class Pawn extends Figure {

    public Pawn(char[][] board) {
        super.board = board;
    }

    protected void move(int presentX, int presentY, int nextX, int nextY) {
        int yLength = Math.abs(nextY - presentY);
        char pawn = board[presentY][presentX];

        boolean isWhitePawnStart = (pawn == 'P' && presentY == 1);
        boolean isBlackPawnStart = (pawn == 'p' && presentY == 6);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        boolean isPossibleMovePawnWhite = (presentX == nextX && yLength == 1 && presentY < nextY && board[nextY][nextX] == '.'
                || isWhitePawnStart && presentX == nextX && yLength == 2 && presentY < nextY && board[nextY][nextX] == '.');

        boolean isPossibleMovePawnBlack = (presentX == nextX && yLength == 1 && presentY > nextY && board[nextY][nextX] == '.'
                || isBlackPawnStart && presentX == nextX && yLength == 2 && presentY > nextY && board[nextY][nextX] == '.');

        boolean whitePawnMove = !isBlockedByOtherFigures && pawn == 'P' && isPossibleMovePawnWhite;
        boolean blackPawnMove = !isBlockedByOtherFigures && pawn == 'p' && isPossibleMovePawnBlack;

        if (whitePawnMove || blackPawnMove) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
        }
    }

    protected void promotion(int presentX, int presentY, HashMap<Character, Figure> figures, Scanner scanner) {
        char pawn = board[presentY][presentX];

        boolean isWhitePawnAtTheEnd = (pawn == 'P' && presentY == 7);
        boolean isBlackPawnAtTheEnd = (pawn == 'p' && presentY == 0);

        if (isWhitePawnAtTheEnd) {
            System.out.println("Введіть фігуру на яку хочете перетворити пішака (N, Q, R, B):");
            char figureSymbol = scanner.nextLine().toLowerCase().charAt(0);

            if (figures.containsKey(figureSymbol)) {
                board[presentY][presentX] = Character.toUpperCase(figureSymbol);
            }
        }
        if (isBlackPawnAtTheEnd) {
            System.out.println("Введіть фігуру на яку хочете перетворити пішака (n, q, r, b):");
            char figureSymbol = scanner.nextLine().toLowerCase().charAt(0);

            if (figures.containsKey(figureSymbol)) {
                board[presentY][presentX] = figureSymbol;
            }
        }
    }
}