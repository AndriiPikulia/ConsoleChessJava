public class Rook extends Figure {

    public Rook(char[][] board) {
        super.board = board;
    }

    private static boolean isLeftBlackRook;
    private static boolean isRightBlackRook;
    private static boolean isLeftWhiteRook;
    private static boolean isRightWhiteRook;

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = nextX - presentX;
        int yDifference = nextY - presentY;

        boolean isPossibleMoveOy = (xDifference >= 0 && yDifference == 0) || (xDifference <= 0 && yDifference == 0);
        boolean isPossibleMoveOx = xDifference == 0;

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if ((isPossibleMoveOy || isPossibleMoveOx) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            checkWhichRookIsMoving(presentX, presentY);
            return true;
        }

        return false;
    }

    protected static void checkWhichRookIsMoving(int presentX, int presentY) {
        if(presentX == 0 && presentY == 0 ) {
            isLeftWhiteRook = true;
        }
        if(presentX == 7 && presentY == 0 ) {
            isRightWhiteRook = true;
        }
        if(presentX == 0 && presentY == 7 ) {
            isLeftBlackRook = true;
        }
        if(presentX == 7 && presentY == 7 ) {
            isRightBlackRook = true;
        }
    }
    protected static boolean getLeftBlackRook() {
        return isLeftBlackRook;
    }
    protected static boolean getRightBlackRook () {
        return isRightBlackRook;
    }
    protected static boolean getLeftWhiteRook() {
        return isLeftWhiteRook;
    }
    protected static boolean getRightWhiteRook() {
        return isRightWhiteRook;
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = nextX - presentX;
        int yDifference = nextY - presentY;

        boolean isPossibleMoveOy = (xDifference >= 0 && yDifference == 0) || (xDifference <= 0 && yDifference == 0);
        boolean isPossibleMoveOx = xDifference == 0;

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if ((isPossibleMoveOy || isPossibleMoveOx) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }
}
