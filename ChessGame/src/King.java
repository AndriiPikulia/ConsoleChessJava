public class King extends Figure{
    private Rook rook;
    private static int countKingMoves;

    public King(char[][] board, Rook rook) {
        super.board = board;
        this.rook = rook;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isPossibleMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);
        int countRookMoves = Rook.getCountRookMoves();
        System.out.println("countRookMovesKing" + countRookMoves);
        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            countKingMoves++;
            return true;
        }

        else if(isPossibleMoveForRogue && countKingMoves == 0 && !isBlockedByOtherFigures && countRookMoves == 0) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            countKingMoves++;
            return true;
        }

        return false;
    }

    public static int getCountKingMoves() {
        return countKingMoves;
    }

}
