import java.util.HashMap;

public class ChessModel {
    String presentCellCoordinates;
    String nextCellCoordinates;

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

    HashMap<String, String> boardFields = new HashMap<>();

    public HashMap <String, String> getBoardFields() {
        return boardFields;
    }

    public ChessModel() {
        fillStartBoard();
    }

    public void fillStartBoard() {
        boardFields.put("a2", "P");
        boardFields.put("b2", "P");
        boardFields.put("c2", "P");
        boardFields.put("d2", "P");
        boardFields.put("e2", "P");
        boardFields.put("f2", "P");
        boardFields.put("g2", "P");
        boardFields.put("h2", "P");

        boardFields.put("a1", "R");
        boardFields.put("b1", "N");
        boardFields.put("c1", "B");
        boardFields.put("d1", "Q");
        boardFields.put("e1", "K");
        boardFields.put("f1", "B");
        boardFields.put("g1", "N");
        boardFields.put("h1", "R");

        boardFields.put("a7", "p");
        boardFields.put("b7", "p");
        boardFields.put("c7", "p");
        boardFields.put("d7", "p");
        boardFields.put("e7", "p");
        boardFields.put("f7", "p");
        boardFields.put("g7", "p");
        boardFields.put("h7", "p");

        boardFields.put("a8", "R");
        boardFields.put("b8", "N");
        boardFields.put("c8", "B");
        boardFields.put("d8", "Q");
        boardFields.put("e8", "K");
        boardFields.put("f8", "B");
        boardFields.put("g8", "N");
        boardFields.put("h8", "R");

    }
}
