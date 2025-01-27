public class Pawn extends Figure {

    public Pawn(char[][] board) {
        super.board = board;
    }

    protected void move(int presentX, int presentY, int nextX, int nextY){
    int yLength = Math.abs(nextY - presentY);

    boolean isPossibleMovePawn = (presentX == nextX && yLength == 1 && nextY < presentY && board[nextY][nextX] == '.');

    if(isPossibleMovePawn){
        board[nextY][nextX] = board[presentY][presentX];
        board[presentY][presentX] = '.';
    }
}


}
