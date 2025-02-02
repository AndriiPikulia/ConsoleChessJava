public class King extends Figure{
    private Rook rook;
    private int countKingMoves;
    public King(char[][] board, Rook rook) {
        super.board = board;
        this.rook = rook;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isPossibleMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);
        int countRookMoves = rook.getCountRookMoves();

        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            this.countKingMoves++;
            return true;
        }

        else if(isPossibleMoveForRogue && this.countKingMoves == 0 && !isBlockedByOtherFigures && countRookMoves == 0) {
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

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isPossibleMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);
        int countRookMoves = rook.getCountRookMoves();

        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        else if(isPossibleMoveForRogue && this.countKingMoves == 0 && !isBlockedByOtherFigures && countRookMoves == 0) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }
}
