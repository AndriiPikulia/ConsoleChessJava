import java.util.HashMap;
import java.util.Scanner;

public class ChessMain {
    public static void main(String[] args) {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView();
        ChessController controller = new ChessController(model, view);
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, Integer> boardPositions = new HashMap<>();
        controller.fillBoardPositions(boardPositions);

        while (!model.isGameOver()) {
            System.out.println("Хід номер " + model.getMoveCount());
            if ((model.getMoveCount() % 2) != 0) {
                System.out.println("Хід білих");
            } else {
                System.out.println("Хід чорних");
            }
            controller.updateView();
            controller.inputCoordinates(scanner, model.getBoard(), boardPositions);
        }
        System.out.println("Game over!");
        controller.updateView();
        System.exit(0);
    }
}
