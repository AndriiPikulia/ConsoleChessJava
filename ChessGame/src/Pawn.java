public class Pawn extends Figure {

    public Pawn(char[][] board) {
        super.board = board;
    }

    protected boolean move(int presentX, int presentY, int nextX, int nextY){
    int yLength = Math.abs(nextY - presentY);
    char pawn = board[presentY][presentX];

    boolean isWhitePawnStart = (pawn == 'P' && presentY == 1);
    boolean isBlackPawnStart = (pawn == 'p' && presentY == 6);

    boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

    boolean isPossibleMovePawnWhite = (presentX == nextX && yLength == 1 && presentY < nextY && board[nextY][nextX] == '.'
            || isWhitePawnStart && presentX == nextX && yLength == 2 &&
            presentY < nextY && board[nextY][nextX] == '.');

    boolean isPossibleMovePawnBlack = (presentX == nextX && yLength == 1 &&
            presentY > nextY && board[nextY][nextX] == '.'
            || isBlackPawnStart && presentX == nextX && yLength == 2 &&
            presentY > nextY && board[nextY][nextX] == '.');

    boolean whitePawnMove = !isBlockedByOtherFigures && pawn == 'P' && isPossibleMovePawnWhite;
    boolean blackPawnMove = !isBlockedByOtherFigures && pawn == 'p' && isPossibleMovePawnBlack;

    if(whitePawnMove || blackPawnMove){
        board[nextY][nextX] = board[presentY][presentX];
        board[presentY][presentX] = '.';
        return true;
    }

    return false;
}

    protected boolean checkCanAttackField(int pawnX, int pawnY, int fieldX, int fieldY) {
        boolean isPawnWhite = checkIsFigureWhite(pawnX, pawnY);

        if(isPawnWhite && pawnY + 1 == fieldY && (pawnX + 1 == fieldX || pawnX - 1 == fieldX)) {
            return true;
        }

        return !isPawnWhite && pawnY - 1 == fieldY && (pawnX + 1 == fieldX || pawnX - 1 == fieldX);
    }
}
