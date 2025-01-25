import java.util.HashMap;

public class ChessView {
    public void printBoard(char[][] board) {
        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}

