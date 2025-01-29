public class King extends Figure{
    public King(char[][] board) {
        super.board = board;
    }

    @Override
    protected void move(int presentX, int presentY, int nextX, int nextY) {
        boolean isPossibleMove = (Math.abs(nextX - presentX) == 1 || Math.abs(nextY - presentY) == 1);
        boolean isPossibleMoveForRogue = (Math.abs(nextX - presentX) == 2 && Math.abs(nextY - presentY) == 0);
        boolean isBlockedByOtherFigures = checkIsFigureInLine(presentX, presentY, nextX, nextY);

        System.out.println(!isBlockedByOtherFigures);

        if ((isPossibleMove || isPossibleMoveForRogue) && !isBlockedByOtherFigures) {
            board[nextY][nextX] = board[presentY][presentX];
            board[presentY][presentX] = '.';

        }
        else{
            System.out.println("Ви не можете зробити хід Королем!");
        }
    }
}