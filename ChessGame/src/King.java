import java.util.HashMap;

public class King extends Figure {
    private final Rook rook;
    private final HashMap<Character, Figure> figures;
    private boolean isWhiteKingMoved;
    private boolean isBlackKingMoved;

    public King(char[][] board, Rook rook, HashMap<Character, Figure> figures) {
        super.board = board;
        this.rook = rook;
        this.figures = figures;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isSuccessfulImitate = imitateMove(presentX, presentY, nextX, nextY);
        boolean isCastingSuccessful = castling(presentX, presentY, nextX, nextY);

        if (!isSuccessfulImitate && !isCastingSuccessful) {
            return false;
        }

        if(presentX == 4 && presentY == 0) {
            isWhiteKingMoved = true;
        }
        if(presentX == 4 && presentY == 7) {
            isBlackKingMoved = true;
        }

        return true;
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleUsualMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        if (isPossibleUsualMove) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }

    protected boolean castling(int presentX, int presentY, int nextX, int nextY) {
        boolean isCastling = isPossibleCastling(presentX, presentY, nextX, nextY);

        if (isCastling) {
            int directionOne = nextX - presentX > 0 ? -1 : 1;
            int presentRookX = nextX - presentX > 0 ? 7 : 0;
            char rookSymbol = checkIsFigureWhite(presentX, presentY) ? 'R' : 'r';

            board[presentY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            board[presentY][nextX + directionOne] = rookSymbol;
            board[presentY][presentRookX] = '.';
        }

        return isCastling;
    }

    private boolean isPossibleCastling(int presentX, int presentY, int nextX, int nextY) {
        boolean isKingWhite = checkIsFigureWhite(presentX, presentY);
        boolean isCastlingAllowed = presentX == 4 && nextY - presentY == 0 && Math.abs(nextX - presentX) == 2
                && !checkIsFigureBetweenFields(presentX, presentY, nextX, nextY);
        boolean isPossibleLeftWhiteCastling = isKingWhite && !isWhiteKingMoved && !rook.isLeftWhiteRookMoved() && nextX == 6;
        boolean isPossibleRightWhiteCastling = isKingWhite && !isWhiteKingMoved && !rook.isRightWhiteRookMoved() && nextX == 2;
        boolean isPossibleRightBlackCastling = !isKingWhite && !isBlackKingMoved && !rook.isRightBlackRookMoved() && nextX == 6;
        boolean isPossibleLeftBlackCastling = !isKingWhite && !isBlackKingMoved && !rook.isLeftBlackRookMoved() && nextX == 2;
        boolean isPossibleCastling =  (isCastlingAllowed && (isPossibleLeftWhiteCastling || isPossibleRightWhiteCastling
                || isPossibleRightBlackCastling || isPossibleLeftBlackCastling));

        if (!isPossibleCastling) {
            return false;
        }

        for (int i = presentX; i <= nextX; i++) {
            if (checkIsFieldAttacked(isKingWhite, new int[] {i, presentY})) {
                return false;
            }
        }
        for (int i = presentX; i >= nextX; i--) {
            if (checkIsFieldAttacked(isKingWhite, new int[] {i, presentY})) {
                return false;
            }
        }

        return true;
    }

    protected boolean checkIsFieldAttacked(boolean isFieldWhite, int[] coordinates) {
        boolean isAttacked = false;

        outer:
        for(int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                char figureChar = board[y][x];
                char figureCharLowerCase = Character.toLowerCase(figureChar);
                boolean isFigureWhite = Character.isUpperCase(figureChar);

                if (isFigureWhite == isFieldWhite) {
                    continue;
                }

                if (figureChar != '.') {
                    isAttacked = figures.get(figureCharLowerCase).checkCanAttackField(x, y, coordinates[0], coordinates[1]);
                }

                if(isAttacked) {
                    break outer;
                }
            }
        }

        return isAttacked;
    }
}
