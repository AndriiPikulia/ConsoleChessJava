public class King extends Figure{
    public King(char[][] board) {
        super.board = board;
    }

    private int countKingMoves;

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMove = (Math.abs(nextX - presentX) == 1 || Math.abs(nextY - presentY) == 1);
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            this.countKingMoves++;
            return true;
        }

        else if(isPossibleMoveForRogue && this.countKingMoves == 0) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            this.countKingMoves++;
            return true;
        }

        return false;
    }

    public int getCountKingMoves() {
        return countKingMoves;
    }
}
