public class King extends Figure{
    private final Rook rook;
    private static int countBlackKingMoves;
    private static int countWhiteKingMoves;
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
        boolean countLeftBlackRookMoves = Rook.getLeftBlackRook();
        boolean countRightBlackRookMoves = Rook.getRightBlackRook();
        boolean countLeftWhiteRookMoves = Rook.getLeftWhiteRook();
        boolean countRightWhiteRookMoves = Rook.getRightWhiteRook();

        System.out.println("countLeftBlackRookMoves"+ countLeftBlackRookMoves);
        System.out.println("countRightBlackRookMoves"+ countRightBlackRookMoves);
        System.out.println("countLeftWhiteRookMoves"+ countLeftWhiteRookMoves);
        System.out.println("countRightWhiteRookMoves"+ countRightWhiteRookMoves);

        boolean isLeftRookGoingToMove = (!countLeftBlackRookMoves && presentY == 7) || (!countLeftWhiteRookMoves && presentY == 0);
        boolean isRightRookGoingToMove = (!countRightBlackRookMoves && presentY == 7) || (!countRightWhiteRookMoves && presentY == 0);

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
            if(!countLeftBlackRookMoves || !countRightBlackRookMoves && presentY == 7) {
                countBlackKingMoves++;
            }
            else if (!countLeftWhiteRookMoves || !countRightWhiteRookMoves && presentY == 0) {
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

    public static int getCountWhiteKingMoves() {
        return countWhiteKingMoves;
    }

    public static int getCountBlackKingMoves() {
        return countBlackKingMoves;
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
