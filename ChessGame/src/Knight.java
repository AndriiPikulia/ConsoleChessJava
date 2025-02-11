public class Knight extends Figure {

    public Knight(char[][] board) {
        super.board = board;
    }

    protected boolean move(Point present, Point next) {
        int xDifference = Math.abs(present.getX() - next.getX());
        int yDifference = Math.abs(present.getY() - next.getY());

        boolean isPossibleMove = xDifference == 1 && yDifference == 2
                || xDifference == 2 && yDifference == 1;

        if (isPossibleMove && !checkIsFigureTheSameTeam(present, next)) {
            board[next.getY()][next.getX()] = board[present.getY()][present.getX()];
            board[present.getY()][present.getX()] = '.';
            return true;
        }

        return false;
    }

    protected boolean imitateMove(Point present, Point next) {
        return move(present, next);
    }
}
