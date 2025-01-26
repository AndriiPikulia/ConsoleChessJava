public class ChessModel {
    String presentCellCoordinates;
    String nextCellCoordinates;
    Knight knight;
    Pawn pawn;

    public ChessModel() {
        this.knight = new Knight(board);
        this.pawn = new Pawn(board);
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
