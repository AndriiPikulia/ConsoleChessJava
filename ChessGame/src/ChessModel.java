import java.util.HashMap;

public class ChessModel {
    String presentCellCoordinates;
    String nextCellCoordinates;
    boolean isGameOver;
    int moveCount = 1;
    int[] previousMove;
    int[] whiteKingCoordinates;
    int[] blackKingCoordinates;
    HashMap<Character, Figure> figures;
    Knight knight;
    Pawn pawn;
    Rook rook;
    Bishop bishop;
    Queen queen;
    King king;

    public ChessModel() {
        this.figures = new HashMap<>();
        previousMove = new int[] {0, 0, 0, 0};

        this.knight = new Knight(board);
        this.pawn = new Pawn(board, previousMove);
        this.rook = new Rook(board);
        this.figures = new HashMap<>();
        this.bishop = new Bishop(board);
        this.queen = new Queen(board);
        this.king = new King(board, rook, figures);

        whiteKingCoordinates = new int[] {4, 0};
        blackKingCoordinates = new int[] {4, 7};

        figures.put('n', knight);
        figures.put('p', pawn);
        figures.put('r', rook);
        figures.put('b', bishop);
        figures.put('q', queen);
        figures.put('k', king);
    }

    public void setPreviousMove(int presentX, int presentY, int nextX, int nextY) {
        previousMove[0] = presentX;
        previousMove[1] = presentY;
        previousMove[2] = nextX;
        previousMove[3] = nextY;
    }

    public HashMap<Character,Figure> getFigures() {
        return figures;
    }


    public String getPresentCellCoordinates() {
        return presentCellCoordinates;
    }

    public void setPresentCellCoordinates(String presentCellCoordinates) {
        this.presentCellCoordinates = presentCellCoordinates;
    }

    public String getNextCellCoordinates() {
        return nextCellCoordinates;
    }

    public void setNextCellCoordinates(String nextCellCoordinates) {
        this.nextCellCoordinates = nextCellCoordinates;
    }

    char[][] board = {{'R','N','B','Q','K','B','N','R'},
            {'P','P','P','P','P','P','P','P'},
            {'.','.','R','.','.','R','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','R','.','.','.','.'},
            {'p','p','p','.','.','.','p','p'},
            {'r','.','.','.','k','.','.','r'}};
}
