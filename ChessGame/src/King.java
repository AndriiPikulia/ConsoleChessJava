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
    protected boolean move(Point present, Point next) {
        boolean isSuccessfulImitate = imitateMove(present, next);
        boolean isCastingSuccessful = castling(present, next);

        if (!isSuccessfulImitate && !isCastingSuccessful) {
            return false;
        }

        if(present.getX() == 4 && present.getY() == 0) {
            isWhiteKingMoved = true;
        }
        if(present.getX() == 4 && present.getY() == 7) {
            isBlackKingMoved = true;
        }

        return true;
    }

    protected boolean imitateMove(Point present, Point next) {
        boolean isPossibleUsualMove = (Math.abs(next.getX() - present.getX()) <= 1 && Math.abs(next.getY() - present.getY()) <= 1);

        if (isPossibleUsualMove) {
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            return true;
        }

        return false;
    }

    protected boolean castling(Point present, Point next) {
        boolean isCastling = isPossibleCastling(present, next);

        if (isCastling) {
            int directionOne = next.getX() - present.getX() > 0 ? -1 : 1;
            int presentRookX = next.getX() - present.getX() > 0 ? 7 : 0;
            char rookSymbol = checkIsFigureWhite(present) ? 'R' : 'r';

            board[present.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            board[present.getY()][next.getX() + directionOne] = rookSymbol;
            board[present.getY()][presentRookX] = '.';
        }

        return isCastling;
    }

    private boolean isPossibleCastling(Point present, Point next) {
        boolean isKingWhite = checkIsFigureWhite(present);
        boolean isCastlingAllowed = present.getX() == 4 && next.getY() - present.getY() == 0 && Math.abs(next.getX() - present.getX()) == 2
                && !checkIsFigureBetweenFields(present, next);
        boolean isPossibleLeftWhiteCastling = isKingWhite && !isWhiteKingMoved && !rook.isLeftWhiteRookMoved() && next.getX() == 6;
        boolean isPossibleRightWhiteCastling = isKingWhite && !isWhiteKingMoved && !rook.isRightWhiteRookMoved() && next.getX() == 2;
        boolean isPossibleRightBlackCastling = !isKingWhite && !isBlackKingMoved && !rook.isRightBlackRookMoved() && next.getX() == 6;
        boolean isPossibleLeftBlackCastling = !isKingWhite && !isBlackKingMoved && !rook.isLeftBlackRookMoved() && next.getX() == 2;
        boolean isPossibleCastling =  (isCastlingAllowed && (isPossibleLeftWhiteCastling || isPossibleRightWhiteCastling
                || isPossibleRightBlackCastling || isPossibleLeftBlackCastling));

        if (!isPossibleCastling) {
            return false;
        }

        for (int i = present.getX(); i <= next.getX(); i++) {
            if (checkIsFieldAttacked(isKingWhite, new Point(i, present.getY()))) {
                return false;
            }
        }
        for (int i = present.getX(); i >= next.getX(); i--) {
            if (checkIsFieldAttacked(isKingWhite, new Point(i, present.getY()))) {
                return false;
            }
        }

        return true;
    }

    protected boolean checkIsFieldAttacked(boolean isFieldWhite, Point point) {
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
                    isAttacked = figures.get(figureCharLowerCase).checkCanAttackField(new Point(x, y), point);
                }

                if(isAttacked) {
                    break outer;
                }
            }
        }

        return isAttacked;
    }
}
