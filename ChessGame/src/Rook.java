public class Rook extends Figure {

    public Rook(char[][] board) {
        super.board = board;
    }

    private int countRookMoves;

    public Rook() {

    }

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
            this.countRookMoves++;
            return true;
        }

        return false;
    }

    public int getCountRookMoves() {
        return countRookMoves;
    }

}
