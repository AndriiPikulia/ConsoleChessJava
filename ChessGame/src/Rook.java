public class Rook extends Figure {

    public Rook(char[][] board) {
        super.board = board;
    }

    private static int countRookMoves;


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
            System.out.println("Походили турою");
            countRookMoves++;
            return true;
        }

        return false;
    }

    public static int getCountRookMoves() {
        return countRookMoves;
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
