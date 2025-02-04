public class King extends Figure{
    private static int countBlackKingMoves;
    private static int countWhiteKingMoves;
    private static int countKingMoves;

    public King(char[][] board, Rook rook) {
        super.board = board;
    }

    @Override
    protected boolean move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isPossibleMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);
        boolean isLeftBlackRookMoves = Rook.getLeftBlackRook();
        boolean isRightBlackRookMoves = Rook.getRightBlackRook();
        boolean isLeftWhiteRookMoves = Rook.getLeftWhiteRook();
        boolean isRightWhiteRookMoves = Rook.getRightWhiteRook();

        boolean isLeftRookGoingToMove = (!isLeftBlackRookMoves && presentY == 7) || (!isLeftWhiteRookMoves && presentY == 0);
        boolean isRightRookGoingToMove = (!isRightBlackRookMoves && presentY == 7) || (!isRightWhiteRookMoves && presentY == 0);

        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            countKingMoves = countWhiteKingMoves + countBlackKingMoves;
            countKingMoves++;
            return true;
        }

        else if(isPossibleMoveForRogue && countKingMoves == 0 && (countBlackKingMoves == 0 || countWhiteKingMoves == 0) && !isBlockedByOtherFigures /* && countLeftBlackRookMoves == 0 || countRightBlackRookMoves == 0 || countLeftWhiteRookMoves == 0 || countRightWhiteRookMoves == 0*/) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            if(!isLeftBlackRookMoves || !isRightBlackRookMoves && presentY == 7) {
                countBlackKingMoves++;
            }
            else if (!isLeftWhiteRookMoves || !isRightWhiteRookMoves && presentY == 0) {
                countWhiteKingMoves++;
            }

            if(!isLeftRookGoingToMove && nextX == presentX - 2 || !isRightRookGoingToMove && nextX == presentX + 2){
            if(presentY == 7) {
                countBlackKingMoves--;
            }
            else if(presentY == 0) {
                countWhiteKingMoves--;
            }
            }
            return true;
        }
        return false;
    }

    protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isPossibleMove = (Math.abs(nextX - presentX) <= 1 && Math.abs(nextY - presentY) <= 1);

        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        if ((isPossibleMove) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        else if(isPossibleMoveForRogue && countKingMoves == 0 && !isBlockedByOtherFigures/* && countLeftBlackRookMoves == 0 || countRightBlackRookMoves == 0 || countLeftWhiteRookMoves == 0 || countRightWhiteRookMoves == 0*/) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';
            return true;
        }

        return false;
    }
}
