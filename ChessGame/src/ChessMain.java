import java.util.HashMap;
import java.util.Scanner;

public class ChessMain {
    public static void main(String[] args) {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView();
        ChessController controller = new ChessController(model, view);
        Scanner scanner = new Scanner(System.in);
        HashMap <Character, Integer> boardPositions = new HashMap <>();
        controller.fillBoardPositions(boardPositions);


        controller.updateView();
        controller.inputCoordinates(scanner);
        controller.swapFiguresOnBoard(model.board,boardPositions);
        controller.updateView();
        controller.inputCoordinates(scanner);
        controller.swapFiguresOnBoard(model.board,boardPositions);
        controller.updateView();
    }
}