import java.util.HashMap;
import java.util.Scanner;

public class Pawn extends Figure {

    int[] previousMove;

    public Pawn(char[][] board, int[] previousMove) {
        super.board = board;
        this.previousMove = previousMove;
    }

    protected boolean move(int presentX, int presentY, int nextX, int nextY){
    return imitateMove(presentX,presentY,nextX,nextY);
}

    protected boolean beat(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleBeatWhite = (Math.abs(nextX - presentX) == 1) && (nextY - presentY == 1)
                && board[nextY][nextX] != '.' && Character.isLowerCase(board[nextY][nextX]);

        boolean isPossibleBeatBlack = (Math.abs(nextX - presentX) == 1) && (nextY - presentY == -1)
                && board[nextY][nextX] != '.' && Character.isUpperCase(board[nextY][nextX]);

        if((isPossibleBeatWhite && Character.isUpperCase(board[presentY][presentX]))
                || (isPossibleBeatBlack && Character.isLowerCase(board[presentY][presentX]))) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }
        return false;
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
                board[presentY][presentX] = Character.toLowerCase(figureSymbol);
            }
        }
    }

    protected boolean enPassantMove(int presentX, int presentY, int nextX, int nextY) {
        if (checkEnPassant(presentX, presentY, nextX, nextY) && checkIsPreviousMoveForEnPassant(nextX, presentY)) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';

            if (nextX - presentX == 1) {
                board[presentY][presentX + 1] = '.';
                return true;
            }
            if (nextX - presentX == -1) {
                board[presentY][presentX - 1] = '.';
                return true;
            }
        }

        return false;
    }

    protected boolean checkEnPassant(int presentX, int presentY, int nextX, int nextY) {
        boolean canWhiteEnPassant = Character.isUpperCase(board[presentY][presentX])
                && presentY == 4
                && nextY == 5
                && Math.abs(nextX - presentX) == 1
                && (board[presentY][presentX + 1] == 'p' || board[presentY][presentX - 1] == 'p');


        boolean canBlackEnPassant = Character.isLowerCase(board[presentY][presentX])
                && presentY == 3
                && nextY == 2
                && Math.abs(nextX - presentX) == 1
                && (board[presentY][presentX + 1] == 'P' || board[presentY][presentX - 1] == 'P');
        return canWhiteEnPassant || canBlackEnPassant;
    }

    @Override
    protected boolean checkCanAttackField(int pawnX, int pawnY, int fieldX, int fieldY) {
        boolean isPawnWhite = checkIsFigureWhite(pawnX, pawnY);

        if(isPawnWhite && pawnY + 1 == fieldY && (pawnX + 1 == fieldX || pawnX - 1 == fieldX)) {
            return true;
        }

        return !isPawnWhite && pawnY - 1 == fieldY && (pawnX + 1 == fieldX || pawnX - 1 == fieldX);
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        int yLength = Math.abs(nextY - presentY);
        char pawn = board[presentY][presentX];

        boolean isWhitePawnStart = (pawn == 'P' && presentY == 1);
        boolean isBlackPawnStart = (pawn == 'p' && presentY == 6);

        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(presentX, presentY, nextX, nextY);

        boolean isPossibleMovePawnWhite = (presentX == nextX && yLength == 1 &&
                presentY < nextY && board[nextY][nextX] == '.'
                || isWhitePawnStart && presentX == nextX && yLength == 2 &&
                presentY < nextY && board[nextY][nextX] == '.');

        boolean isPossibleMovePawnBlack = (presentX == nextX && yLength == 1 &&
                presentY > nextY && board[nextY][nextX] == '.'
                || isBlackPawnStart && presentX == nextX && yLength == 2 &&
                presentY > nextY && board[nextY][nextX] == '.');

        boolean whitePawnMove = !isBlockedByOtherFigures && pawn == 'P' && isPossibleMovePawnWhite;
        boolean blackPawnMove = !isBlockedByOtherFigures && pawn == 'p' && isPossibleMovePawnBlack;

        if (enPassantMove(presentX, presentY, nextX, nextY)) {
            return true;
        }
        if(whitePawnMove || blackPawnMove){
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }
       return beat(presentX, presentY, nextX, nextY);
    }

    boolean checkIsPreviousMoveForEnPassant(int presentX, int presentY) {
        System.out.println();
        return presentX == previousMove[2]
                && presentY == previousMove[3]
                && Math.abs(presentX - previousMove[0]) == 0
                && Math.abs(presentY - previousMove[1]) == 2;
    }
}
