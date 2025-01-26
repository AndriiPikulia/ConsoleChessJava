public class ChessView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(char[][] board) {
        System.out.print("  + ");
        for(int j = 0; j < 8; j++) {
            System.out.print("- ");
        }
        System.out.println("+");
        for (int i = 0; i <8; i++) {
            System.out.print(ANSI_BLUE + (i+1) + ANSI_RESET + " | ");
            for (int j = 0; j < 8; j++) {
                System.out.printf(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.print("  + ");
        for(int j = 0; j < 8; j++) {
            System.out.print("- ");
        }
        System.out.println("+");
        System.out.println(ANSI_BLUE + "    a b c d e f g h " + ANSI_RESET);
    }

}

