import java.util.HashMap;

public class ChessModel {
    String presentCellCoordinates;
    String nextCellCoordinates;
    int[] whiteKingCoordintates;
    int[] blackKingCoordintates;
    HashMap<Character, Figure> figures;
    Knight knight;
    Pawn pawn;
    Rook rook;
    Bishop bishop;
    Queen queen;
    King king;

    public ChessModel() {
        this.figures = new HashMap<>();

        this.knight = new Knight(board);
        this.pawn = new Pawn(board);
        this.rook = new Rook(board);
        this.figures = new HashMap<>();
        this.bishop = new Bishop(board);
        this.queen = new Queen(board);
        this.king = new King(board);

        whiteKingCoordintates = new int[] {4, 0};
        blackKingCoordintates = new int[] {4, 7};

        figures.put('n', knight);
        figures.put('p', pawn);
        figures.put('r', rook);
        figures.put('b', bishop);
        figures.put('q', queen);
        figures.put('k', king);
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
