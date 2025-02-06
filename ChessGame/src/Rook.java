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
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isSuccessfulImitate = imitateMove(presentX, presentY, nextX, nextY);

        if (!isSuccessfulImitate) {
            return false;
        }

        if(presentX == 0 && presentY == 0) {
            setRightWhiteRookMoved(true);
        }
        if(presentX == 7 && presentY == 0) {
            setLeftWhiteRookMoved(true);
        }
        if(presentX == 0 && presentY == 7) {
            setLeftBlackRookMoved(true);
        }
        if(presentX == 7 && presentY == 7) {
            setRightBlackRookMoved(true);
        }

        return true;
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = presentX - nextX;
        int yDifference = presentY - nextY;

        boolean isPossibleMove = xDifference == 0 || yDifference == 0;
        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(presentX, presentY, nextX, nextY);

        if (isPossibleMove && !isBlockedByOtherFigures) {
            checkIsFigureTheSameTeam(presentX, presentY, nextX, nextY);
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }

}
