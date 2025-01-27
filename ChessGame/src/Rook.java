public class Rook extends Figure {
    private final char[][] board;

    public Rook(char[][] board) {
        this.board = board;
    }

    @Override
    protected void move(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = presentX - nextX;
        int yDifference = presentY - nextY;

        boolean isPossibleMove = xDifference == 0 || yDifference == 0;
        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if (isPossibleMove && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
        }
    }

    protected boolean checkIsFigureInLine(int presentX, int presentY, int nextX, int nextY) {
        return presentX == nextX ? checkIsFigureInVerticalLine(presentY, nextY, presentX)
                : checkIsFigureInHorizontalLine(presentX, nextX, presentY);
    }

    protected boolean checkIsFigureInHorizontalLine(int startX, int endX, int constantY) {
        if (endX - startX > 0) {
            for (int i = startX + 1; i < endX; i++) {
                if (board[constantY][i] != '.') {
                    return true;
                }
            }
        }

        if (endX - startX < 0) {
            for (int i = startX - 1; i > endX; i--) {
                if (board[constantY][i] != '.') {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean checkIsFigureInVerticalLine(int startY, int endY, int constantX) {
        if (endY - startY > 0) {
            for (int i = startY + 1; i < endY; i++) {
                if (board[i][constantX] != '.') {
                    return true;
                }
            }
        }

        if (endY - startY < 0) {
            for (int i = startY - 1; i > endY; i--) {
                if (board[i][constantX] != '.') {
                    return true;
                }
            }
        }

        return false;
    }

}
