public class Rook extends Figure {

    public Rook(char[][] board) {
        super.board = board;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = presentX - nextX;
        int yDifference = presentY - nextY;

        boolean isPossibleMove = xDifference == 0 || yDifference == 0;
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
