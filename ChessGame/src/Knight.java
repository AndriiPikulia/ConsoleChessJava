public class Knight extends Figure {
    private final char[][] board;

    public Knight(char[][] board) {
        this.board = board;
    }

    protected void move(int presentX, int presentY, int nextX, int nextY) {
        int xDifference = Math.abs(presentX - nextX);
        int yDifference = Math.abs(presentY - nextY);

        boolean isPossibleMove = xDifference == 1 && yDifference == 2
                || xDifference == 2 && yDifference == 1;

        if (isPossibleMove) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
        }

        }
    }
