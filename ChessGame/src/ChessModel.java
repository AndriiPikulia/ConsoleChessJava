import java.util.HashMap;

public class ChessModel {
    private String presentCellCoordinates;
    private String nextCellCoordinates;

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Point getWhiteKingPoint() {
        return whiteKingPoint;
    }

    public Point getBlackKingPoint() {
        return blackKingPoint;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public King getKing() {
        return king;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public void setWhiteKingPoint(Point whiteKingPoint) {
        this.whiteKingPoint = whiteKingPoint;
    }

    public void setBlackKingPoint(Point blackKingPoint) {
        this.blackKingPoint = blackKingPoint;
    }

    private boolean isGameOver;
    private int moveCount = 1;
    private final int[] previousMove;
    private Point whiteKingPoint;
    private Point blackKingPoint;
    private HashMap<Character, Figure> figures;
    private final Pawn pawn;
    private final King king;

    private final char[][] board = {{'R','N','B','Q','K','B','N','R'},
            {'P','P','P','P','P','P','P','P'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'p','p','p','p','p','p','p','p'},
            {'r','n','b','q','k','b','n','r'}};

    public ChessModel() {
        this.figures = new HashMap<>();
        previousMove = new int[] {0, 0, 0, 0};

        Knight knight = new Knight(board);
        this.pawn = new Pawn(board, previousMove);
        Rook rook = new Rook(board);
        this.figures = new HashMap<>();
        Bishop bishop = new Bishop(board);
        Queen queen = new Queen(board);
        this.king = new King(board, rook, figures);

        whiteKingPoint = new Point(4, 0);
        blackKingPoint = new Point(4, 7);

        figures.put('n', knight);
        figures.put('p', pawn);
        figures.put('r', rook);
        figures.put('b', bishop);
        figures.put('q', queen);
        figures.put('k', king);
    }

    public void setPreviousMove(Point present, Point next) {
        previousMove[0] = present.getX();
        previousMove[1] = present.getY();
        previousMove[2] = next.getX();
        previousMove[3] = next.getY();
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
}

