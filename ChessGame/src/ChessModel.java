import java.util.HashMap;

public class ChessModel {
    String presentCellCoordinates;
    String nextCellCoordinates;
    HashMap<Character, Figure> figures;

    Knight knight;
    Pawn pawn;
    Rook rook;
    Bishop bishop;
    HashMap<Character, Figure> figures;
    Knight knight;
    Pawn pawn;
    Rook rook;

    public ChessModel() {
        this.knight = new Knight(board);
        this.pawn = new Pawn(board);
        this.rook = new Rook(board);
        this.bishop = new Bishop(board);

        this.figures = new HashMap<>();

        figures.put('n', knight);
        figures.put('p', pawn);
        figures.put('r', rook);
        figures.put('b', bishop);
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
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'p','p','p','p','p','p','p','p'},
            {'r','n','b','q','k','b','n','r'}};
}
