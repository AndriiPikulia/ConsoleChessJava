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

        boolean game = true;
        while (game) {
            System.out.println("Хід номер " + model.moveCount);
            if ((model.moveCount % 2) != 0) {
                System.out.println("Хід білих");
            } else {
                System.out.println("Хід чорних");
            }
            controller.updateView();
            controller.inputCoordinates(scanner, model.board, boardPositions);
            controller.updateView();
            System.out.println("Enter quit if you want to leave: ");
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                game = false;
            }
        }
        System.out.println("Game over!");
    }
}
