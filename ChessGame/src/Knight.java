public class Knight extends Figure {

    public Knight(char[][] board) {
        super.board = board;
    }

    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = Math.abs(presentX - nextX);
        int yDifference = Math.abs(presentY - nextY);

        boolean isPossibleMove = xDifference == 1 && yDifference == 2
                || xDifference == 2 && yDifference == 1;

        if (isPossibleMove) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }
}
