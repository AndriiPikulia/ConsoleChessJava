import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessController {
    private final ChessModel model;
    private final ChessView view;

    Scanner scanner = new Scanner(System.in);
    private final HashMap<String, Integer> positionHistory = new HashMap<>();

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

    public void inputCoordinates(Scanner scanner, char[][] board, HashMap<Character, Integer> boardPositions) {
        System.out.println("Введіть координати фігури якою зочете зробити хід");
        String presentCellCoordinates = scanner.nextLine();

        if(!inputValidation(presentCellCoordinates, boardPositions) || presentCellCoordinates.length() != 2){
            System.out.println("Неправильно введено початкові координати");
            return;
        }

        System.out.println("Введіть координати куди хочете походити");
        String nextCellCoordinates = scanner.nextLine();

        if(!inputValidation(nextCellCoordinates, boardPositions) || nextCellCoordinates.length() != 2){
            System.out.println("Неправильно введено наступні координати");
            return;
        }

        model.setPresentCellCoordinates(presentCellCoordinates);
        model.setNextCellCoordinates(nextCellCoordinates);

        int nextLetterKeyToNumber = 0;
        int presentLetterKeyToNumber = 0;

        char presentLetterCoordinate = getLetterCoordinate(model.getPresentCellCoordinates());
        int presentNumberCoordinate = (getNumberCoordinate(model.getPresentCellCoordinates())) - 1;

        char nextLetterCoordinate = getLetterCoordinate(model.getNextCellCoordinates());
        int nextNumberCoordinate = (getNumberCoordinate(model.getNextCellCoordinates())) - 1;

        for (Map.Entry<Character, Integer> entry : boardPositions.entrySet()) {
            if (entry.getKey() == presentLetterCoordinate) {
                presentLetterKeyToNumber = entry.getValue() - 1;
            }
        }
        for (Map.Entry<Character, Integer> entry : boardPositions.entrySet()) {
            if (entry.getKey() == nextLetterCoordinate) {
                nextLetterKeyToNumber = entry.getValue() - 1;
            }
        }
        Point present = new Point(presentLetterKeyToNumber, presentNumberCoordinate);
        Point next = new Point(nextLetterKeyToNumber, nextNumberCoordinate);

        char figureSymbol = board[presentNumberCoordinate][presentLetterKeyToNumber];

        if (moveValidation(model.moveCount, board, present)) {
            moveFigure(figureSymbol, present, next);
            model.moveCount++;
        }
    }

    public boolean moveValidation(int moveCount, char[][] board, Point present) {
        if ((moveCount % 2) != 0) {
            if (Character.isLowerCase(board[present.getY()][present.getX()])) {
                System.out.println("Це фігура команди чорних! Хід зробити неможливо");
                return false;
            }
            if (board[present.getY()][present.getX()] == '.') {
                System.out.println("Це пуста клітинка! Хід зробити неможливо");
                return false;
            }
        }
        if ((moveCount % 2) == 0) {
            if (Character.isUpperCase(board[present.getY()][present.getX()])) {
                System.out.println("Це фігура команди білих! Хід зробити неможливо");
                return false;
            }
            if (board[present.getY()][present.getX()] == '.') {
                System.out.println("Це пуста клітинка! Хід зробити неможливо");
                return false;
            }
        }
        return true;

    }

    public char getLetterCoordinate(String presentCellCoordinates) {
        return presentCellCoordinates.charAt(0);
    }
    public int getNumberCoordinate(String presentCellCoordinates) {
        return Integer.parseInt(presentCellCoordinates.substring(1));
    }

    public void moveFigure(char figureSymbol, Point present, Point next) {
        char figureSymbolLowerCase = Character.toLowerCase(figureSymbol);
        Figure figure = model.figures.get(figureSymbolLowerCase);
        boolean checkIsFigureTheSameTeam = figure.checkIsFigureTheSameTeam(present, next);
        if (checkIsFigureTheSameTeam) {
            System.out.println("Ви не можете побити свою ж фігуру");
            model.moveCount--;
            return;
        }
        boolean canBeKingAttackedAfterMove = checkCanBeKingAttackedAfterMove(present, next);

        if (canBeKingAttackedAfterMove) {
            System.out.println("Шах! Неможливий хід");
            model.moveCount--;
            return;
        }

        boolean isMoveSuccessful = figure.move(present, next);

        if (isMoveSuccessful) {
            model.setPreviousMove(present.getX(), present.getY(), next.getX(), next.getY());
            updatePositionHistory();
        }
        else {
            System.out.println("Неможливий хід");
            model.moveCount--;
        }
        if (figureSymbolLowerCase == 'k' && isMoveSuccessful) {
            updateKingCoordinates(figure, next);
        }
        if(figureSymbolLowerCase == 'p') {
            model.pawn.promotion(next, model.getFigures(), scanner);
        }

        if (isThreefoldRepetition()) {
            System.out.println("Гра закінчена нічиєю через трьохразове повторення позиції!");
            System.exit(0);
        }

        if (checkIsGameOver(!figure.checkIsFigureWhite(next))) {
            System.exit(0);
        }
    }

    private void updatePositionHistory() {
        String state = Arrays.deepToString(model.board);
        positionHistory.put(state, positionHistory.getOrDefault(state, 0) + 1);
    }

    public boolean isThreefoldRepetition() {
        return positionHistory.getOrDefault(Arrays.deepToString(model.board), 0) >= 3;
    }

    public boolean checkCanBeKingAttackedAfterMove(Point start, Point end) {
        char startFigure = model.board[start.getY()][start.getX()];
        char endFigure = model.board[end.getY()][end.getX()];
        boolean isStartFigureWhite = Character.isUpperCase(startFigure);
        Point kingPoint = isStartFigureWhite ? model.whiteKingPoint : model.blackKingPoint;
        boolean canBeKingAttackedAfterMove;

        model.board[start.getY()][start.getX()] = '.';
        model.board[end.getY()][end.getX()] = startFigure;
        if (Character.toLowerCase(startFigure) == 'k') {
            kingPoint = new Point(end);
        }

        canBeKingAttackedAfterMove = model.king.checkIsFieldAttacked(isStartFigureWhite, kingPoint);

        model.board[start.getY()][start.getX()] = startFigure;
        model.board[end.getY()][end.getX()] = endFigure;
        return canBeKingAttackedAfterMove;
    }

    protected void updateKingCoordinates(Figure figure, Point next) {
        boolean isKingWhite = figure.checkIsFigureWhite(next);

        if (isKingWhite) {
            model.whiteKingPoint = new Point(next);
        }
        if (!isKingWhite) {
            model.blackKingPoint = new Point(next);
        }
    }

    protected boolean checkIsGameOver(boolean isCheckForWhite) {
        boolean isCheckmate = checkIsCheckmate(isCheckForWhite);
        boolean isStalemate = checkIsStalemate(isCheckForWhite);

        if (isCheckmate) {
            model.isGameOver = true;
            System.out.println("Мат!");
        }

        if (isStalemate) {
            model.isGameOver = true;
            System.out.println("Пат");
        }

         return false;
    }

    protected boolean checkIsCheckmate(boolean isCheckForWhite) {
        Point kingPoint = isCheckForWhite ? model.whiteKingPoint : model.blackKingPoint;

        if (!model.king.checkIsFieldAttacked(isCheckForWhite, kingPoint)) {
            return false;
        }

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                boolean isWhite = Character.isUpperCase(model.board[y][x]);
                if (isWhite != isCheckForWhite || model.board[y][x] == '.') {
                    continue;
                }

                if (checkCanFigureAvoidCheckmate(new Point(x, y), isCheckForWhite, kingPoint)) {
                    return false;
                }
            }
        }

        return true;
    }

    protected boolean checkIsStalemate(boolean isCheckForWhite) {
        Point kingPoint = isCheckForWhite ? model.whiteKingPoint : model.blackKingPoint;

        if (model.king.checkIsFieldAttacked(isCheckForWhite, kingPoint)) {
            return false;
        }

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                boolean isWhite = Character.isUpperCase(model.board[y][x]);
                if (isWhite != isCheckForWhite || model.board[y][x] == '.') {
                    continue;
                }

                if (checkCanFigureAvoidCheckmate(new Point(x, y), isCheckForWhite, kingPoint)) {
                    return false;
                }
            }
        }

        return true;
    }



    protected boolean checkCanFigureAvoidCheckmate(Point start, boolean isWhite, Point kingPoint) {
        Figure figure = model.figures.get(Character.toLowerCase(model.board[start.getY()][start.getX()]));

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                char attackedField = model.board[y][x];

                if (start.getX() == x && start.getY() == y) {
                    continue;
                }
                if (Character.toLowerCase(model.board[start.getY()][start.getX()]) == 'k') {
                    kingPoint.setX(x);
                    kingPoint.setY(y);
                }

                boolean isMoveSuccessful = figure.imitateMove(start, new Point(x, y));
                boolean isKingAttacked = model.king.checkIsFieldAttacked(isWhite, kingPoint);

                if (isMoveSuccessful) {
                    model.board[start.getY()][start.getX()] = model.board[y][x];
                    model.board[y][x] = attackedField;
                }
                if (Character.toLowerCase(model.board[start.getY()][start.getX()]) == 'k') {
                    kingPoint.setX(start.getX());
                    kingPoint.setY(start.getY());
                }
                if (isMoveSuccessful && !isKingAttacked) {
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean inputValidation(String CellCoordinates, HashMap<Character, Integer> boardPositions) {
        if(CellCoordinates.length() < 2){
            return false;
        }

        char letter = CellCoordinates.charAt(0);
        char number = CellCoordinates.charAt(1);

        if(!(Character.isLetter(letter) && Character.isDigit(number))) {
            return false;
        }

        if(boardPositions.containsKey(letter) && boardPositions.containsValue(Character.getNumericValue(number))) {
            return true;
        }

        else {
            return false;
        }
    }
}