import java.util.HashMap;
import java.util.Map;

public class ChessView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public void printBoard(char[][] board) {
        System.out.print("  + ");
        for(int j = 0; j < 8; j++) {
            System.out.print("- ");
        }
        System.out.println("+");
        for (int i = 0; i <8; i++) {
            System.out.print(ANSI_YELLOW + (i+1) + ANSI_RESET + " | ");
            for (int j = 0; j < 8; j++) {
                if(!Character.isUpperCase(board[i][j]) && board[i][j] != '.') {
                    System.out.printf(ANSI_BLUE + board[i][j] + ANSI_RESET + " ");
                }
                else {
                    System.out.print(board[i][j]+ " ");
                }
            }
            System.out.println("|");
        }
        System.out.print("  + ");
        for(int j = 0; j < 8; j++) {
            System.out.print("- ");
        }
        System.out.println("+");
        System.out.println(ANSI_YELLOW + "    a b c d e f g h " + ANSI_RESET);
    }

}

