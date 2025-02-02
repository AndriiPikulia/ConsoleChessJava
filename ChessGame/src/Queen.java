public class Queen extends Figure {
    public Queen(char[][] board) {
        super.board = board;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMove = (Math.abs(nextX - presentX) == Math.abs(nextY - presentY)
                || (presentX - nextX == 0 || presentY - nextY == 0));

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if (isPossibleMove && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        return move(presentX, presentY, nextX, nextY);
    }
}
