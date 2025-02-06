public class Bishop extends Figure{
    public Bishop(char[][] board) {
        super.board = board;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {

        boolean isPossibleMove = Math.abs(nextX - presentX) == Math.abs(nextY - presentY);
        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(presentX, presentY, nextX, nextY);

        if (isPossibleMove && !isBlockedByOtherFigures && !checkIsFigureTheSameTeam(presentX, presentY, nextX, nextY)) {
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
