public class Rook extends Figure {
    protected boolean isLeftWhiteRookMoved() {
        return isLeftWhiteRookMoved;
    }

    protected void setLeftWhiteRookMoved(boolean leftWhiteRookMoved) {
        isLeftWhiteRookMoved = leftWhiteRookMoved;
    }

    protected boolean isLeftBlackRookMoved() {
        return isLeftBlackRookMoved;
    }

    protected void setLeftBlackRookMoved(boolean leftBlackRookMoved) {
        isLeftBlackRookMoved = leftBlackRookMoved;
    }

    protected boolean isRightWhiteRookMoved() {
        return isRightWhiteRookMoved;
    }

    protected void setRightWhiteRookMoved(boolean rightWhiteRookMoved) {
        isRightWhiteRookMoved = rightWhiteRookMoved;
    }

    protected boolean isRightBlackRookMoved() {
        return isRightBlackRookMoved;
    }

    protected void setRightBlackRookMoved(boolean rightBlackRookMoved) {
        isRightBlackRookMoved = rightBlackRookMoved;
    }

    private boolean isLeftWhiteRookMoved;
    private boolean isLeftBlackRookMoved;
    private boolean isRightWhiteRookMoved;
    private boolean isRightBlackRookMoved;

    public Rook(char[][] board) {
        super.board = board;
    }

    @Override
    protected boolean move(Point present, Point next) {
        boolean isSuccessfulImitate = imitateMove(present, next);

        if (!isSuccessfulImitate) {
            return false;
        }

        if(present.getX() == 0 && present.getY() == 0) {
            setRightWhiteRookMoved(true);
        }
        if(present.getX() == 7 && present.getY() == 0) {
            setLeftWhiteRookMoved(true);
        }
        if(present.getX() == 0 && present.getY() == 7) {
            setLeftBlackRookMoved(true);
        }
        if(present.getX() == 7 && present.getY() == 7) {
            setRightBlackRookMoved(true);
        }

        return true;
    }

    protected boolean imitateMove(Point present, Point next) {
        int xDifference = present.getX() - next.getX();
        int yDifference = present.getY() - next.getY();

        boolean isPossibleMove = xDifference == 0 || yDifference == 0;
        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(present, next);

        if (isPossibleMove && !isBlockedByOtherFigures) {
            checkIsFigureTheSameTeam(present, next);
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            return true;
        }

        return false;
    }

}
