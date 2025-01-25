import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessController {
    private final ChessModel model;
    private final ChessView view;

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(){
        view.printBoard(model.board);
    }

    public void fillBoardPositions(HashMap<Character, Integer> boardPositions) {
        boardPositions.put('a',1);
        boardPositions.put('b',2);
        boardPositions.put('c',3);
        boardPositions.put('d',4);
        boardPositions.put('e',5);
        boardPositions.put('f',6);
        boardPositions.put('g',7);
        boardPositions.put('h',8);
    }

    public void inputCoordinates(Scanner scanner) {
        System.out.println("Введіть координати фігури якою зочете зробити хід");
        String presentCellCoordinates = scanner.nextLine();

        System.out.println("Введіть координати куди хочете походити");
        String nextCellCoordinates = scanner.nextLine();

        model.setPresentCellCoordinates(presentCellCoordinates);
        model.setNextCellCoordinates(nextCellCoordinates);
    }

    public char getPresentLetterCoordinate(String presentCellCoordinates) {
        return presentCellCoordinates.charAt(0);
    }
    public int getPresentNumberCoordinate(String presentCellCoordinates) {
        return Integer.parseInt(presentCellCoordinates.substring(1));
    }
    public char getNextLetterCoordinate(String nextCellCoordinates) {
        return nextCellCoordinates.charAt(0);
    }
    public int getNextNumberCoordinate(String nextCellCoordinates) {
        return Integer.parseInt(nextCellCoordinates.substring(1));
    }

    public char[][] swapFiguresOnBoard(char[][] board, HashMap<Character, Integer> boardPositions) {
        int nextLetterKeyToNumber = 0;
        int presentLetterKeyToNumber = 0;

        char presentLetterCoordinate = getPresentLetterCoordinate(model.getPresentCellCoordinates());
        int presentNumberCoordinate = (getPresentNumberCoordinate(model.getPresentCellCoordinates()))-1;

        char nextLetterCoordinate = getNextLetterCoordinate(model.getNextCellCoordinates());
        int nextNumberCoordinate = (getNextNumberCoordinate(model.getNextCellCoordinates()))-1;

        for(Map.Entry<Character, Integer> entry : boardPositions.entrySet()) {
            if(entry.getKey() == presentLetterCoordinate) {
                presentLetterKeyToNumber = entry.getValue()-1;
            }
        }
        for(Map.Entry<Character, Integer> entry : boardPositions.entrySet()) {
            if(entry.getKey() == nextLetterCoordinate) {
                nextLetterKeyToNumber = entry.getValue()-1;
            }
        }

        char temp = board[presentLetterKeyToNumber][presentNumberCoordinate];
        board[presentLetterKeyToNumber][presentNumberCoordinate] = board[nextLetterKeyToNumber][nextNumberCoordinate];
        board[nextLetterKeyToNumber][nextNumberCoordinate] = temp;
        return board;
    }

}
