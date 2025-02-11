public class Queen extends Figure {
    public Queen(char[][] board) {
        super.board = board;
    }

    @Override
    protected boolean move(Point present, Point next) {
        boolean isPossibleMove = (Math.abs(next.getX() - present.getX()) == Math.abs(next.getY() - present.getY())
                || (present.getX() - next.getX() == 0 || present.getY() - next.getY() == 0));

        boolean isBlockedByOtherFigures = checkIsFigureBetweenFields(present, next);

        if (isPossibleMove && !isBlockedByOtherFigures) {
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
