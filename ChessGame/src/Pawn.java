import java.util.HashMap;
import java.util.Scanner;

public class Pawn extends Figure {

    private final int[] previousMove;

    public Pawn(char[][] board, int[] previousMove) {
        super.board = board;
        this.previousMove = previousMove;
    }

    protected boolean move(Point present, Point next){
        return imitateMove(present, next);
    }

    protected boolean beat(Point present, Point next) {
        boolean isPossibleBeatWhite = (Math.abs(next.getX() - present.getX()) == 1) && (next.getY() - present.getY() == 1)
                && board[next.getY()][next.getX()] != '.' && Character.isLowerCase(board[next.getY()][next.getX()]);

        boolean isPossibleBeatBlack = (Math.abs(next.getX() - present.getX()) == 1) && (next.getY() - present.getY() == -1)
                && board[next.getY()][next.getX()] != '.' && Character.isUpperCase(board[next.getY()][next.getX()]);

        if((isPossibleBeatWhite && Character.isUpperCase(board[present.getY()][present.getX()]))
                || (isPossibleBeatBlack && Character.isLowerCase(board[present.getY()][present.getX()]))) {
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            return true;
        }
        return false;
    }

    protected void promotion(Point present, HashMap<Character, Figure> figures, Scanner scanner) {
        char pawn = board[present.getY()][present.getX()];

        boolean isWhitePawnAtTheEnd = (pawn == 'P' && present.getY() == 7);
        boolean isBlackPawnAtTheEnd = (pawn == 'p' && present.getY() == 0);

        if (isWhitePawnAtTheEnd) {
            System.out.println("Введіть фігуру на яку хочете перетворити пішака (N, Q, R, B):");
            char figureSymbol = scanner.nextLine().toLowerCase().charAt(0);

            if (figures.containsKey(figureSymbol)) {
                board[present.getY()][present.getX()] = Character.toUpperCase(figureSymbol);
            }
        }
        if (isBlackPawnAtTheEnd) {
            System.out.println("Введіть фігуру на яку хочете перетворити пішака (n, q, r, b):");
            char figureSymbol = scanner.nextLine().toLowerCase().charAt(0);

            if (figures.containsKey(figureSymbol)) {
                board[present.getY()][present.getX()] = Character.toLowerCase(figureSymbol);
            }
        }
    }

    protected boolean enPassantMove(Point present, Point next) {
        if (checkEnPassant(present, next) && checkIsPreviousMoveForEnPassant(next.getX(), present.getY())) {
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';

            if (next.getX() - present.getX() == 1) {
                board[present.getY()][present.getX() + 1] = '.';
                return true;
            }
            if (next.getX() - present.getX() == -1) {
                board[present.getY()][present.getX() - 1] = '.';
                return true;
            }
        }

        return false;
    }

    protected boolean checkEnPassant(Point present, Point next) {
        boolean isWhite = Character.isUpperCase(board[present.getY()][present.getX()]);
        boolean isBlack = Character.isLowerCase(board[present.getY()][present.getX()]);

        boolean rightPawn = (present.getX() + 1 < 8) && (isWhite && board[present.getY()][present.getX() + 1] == 'p'
                || isBlack && board[present.getY()][present.getX() + 1] == 'P');
        boolean leftPawn = (present.getX() - 1 >= 0) && (isWhite && board[present.getY()][present.getX() - 1] == 'p'
                || isBlack && board[present.getY()][present.getX() - 1] == 'P');

        boolean canWhiteEnPassant = isWhite && present.getY() == 4 && next.getY() == 5
                && Math.abs(next.getX() - present.getX()) == 1 && (rightPawn || leftPawn);
        boolean canBlackEnPassant = isBlack && present.getY() == 3 && next.getY() == 2
                && Math.abs(next.getX() - present.getX()) == 1 && (rightPawn || leftPawn);

        return canWhiteEnPassant || canBlackEnPassant;
    }


    @Override
    protected boolean checkCanAttackField(Point pawn, Point field) {
        boolean isPawnWhite = checkIsFigureWhite(pawn);

        if(isPawnWhite && pawn.getY() + 1 == field.getY() && (pawn.getX() + 1 == field.getX() || pawn.getX() - 1 == field.getX())) {
            return true;
        }

        return !isPawnWhite && pawn.getY() - 1 == field.getY() && (pawn.getX() + 1 == field.getX() || pawn.getX() - 1 == field.getX());
    }

    protected boolean checkIsPossibleMovePawnWhite(Point present, Point next) {
        int yLength = Math.abs(next.getY() - present.getY());
        char pawn = board[present.getY()][present.getX()];

        boolean isWhitePawnStart = (pawn == 'P' && present.getY() == 1);
        if(present.getX() == next.getX() && yLength == 1
                && present.getY() < next.getY() && board[next.getY()][next.getX()] == '.'
                || isWhitePawnStart && present.getX() == next.getX()
                && yLength == 2 && present.getY() < next.getY() && board[next.getY()][next.getX()] == '.'){
            return true;
        }
        return false;
    }

    protected boolean checkIsPossibleMovePawnBlack(Point present, Point next) {
        int yLength = Math.abs(next.getY() - present.getY());
        char pawn = board[present.getY()][present.getX()];
        boolean isBlackPawnStart = (pawn == 'p' && present.getY() == 6);
        if(present.getX() == next.getX() && yLength == 1 &&
                present.getY() > next.getY() && board[next.getY()][next.getX()] == '.'
                || isBlackPawnStart && present.getX() == next.getX() && yLength == 2 &&
                present.getY() > next.getY() && board[next.getY()][next.getX()] == '.'){
            return true;
        }
        return false;
    }

    protected boolean imitateMove(Point present, Point next) {
        char pawn = board[present.getY()][present.getX()];

        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(present, next);

        boolean whitePawnMove = !isBlockedByOtherFigures && pawn == 'P' && checkIsPossibleMovePawnWhite(present, next);
        boolean blackPawnMove = !isBlockedByOtherFigures && pawn == 'p' && checkIsPossibleMovePawnBlack(present, next);

        if (enPassantMove(present, next)) {
            return true;
        }
        if(whitePawnMove || blackPawnMove){
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            return true;
        }
       return beat(present, next);
    }

    boolean checkIsPreviousMoveForEnPassant(int presentX, int presentY) {
        System.out.println();
        return presentX == previousMove[2]
                && presentY == previousMove[3]
                && Math.abs(presentX - previousMove[0]) == 0
                && Math.abs(presentY - previousMove[1]) == 2;
    }
}
