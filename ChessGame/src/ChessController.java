import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessController {
    private final ChessModel model;
    private final ChessView view;

    Scanner scanner = new Scanner(System.in);

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

    public char getLetterCoordinate(String presentCellCoordinates) {
        return presentCellCoordinates.charAt(0);
    }
    public int getNumberCoordinate(String presentCellCoordinates) {
        return Integer.parseInt(presentCellCoordinates.substring(1));
    }

    public char[][] swapFiguresOnBoard(char[][] board, HashMap<Character, Integer> boardPositions) {
        int nextLetterKeyToNumber = 0;
        int presentLetterKeyToNumber = 0;

        char presentLetterCoordinate = getLetterCoordinate(model.getPresentCellCoordinates());
        int presentNumberCoordinate = (getNumberCoordinate(model.getPresentCellCoordinates()))-1;

        char nextLetterCoordinate = getLetterCoordinate(model.getNextCellCoordinates());
        int nextNumberCoordinate = (getNumberCoordinate(model.getNextCellCoordinates()))-1;

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

        char figureSymbol = board[presentNumberCoordinate][presentLetterKeyToNumber];
        moveFigure(figureSymbol, presentLetterKeyToNumber, presentNumberCoordinate, nextLetterKeyToNumber, nextNumberCoordinate);

        return board;
    }

    public void moveFigure(char figureSymbol, int presentX, int presentY, int nextX, int nextY) {
        char figureSymbolLowerCase = Character.toLowerCase(figureSymbol);
        Figure figure = model.figures.get(figureSymbolLowerCase);

        if (figure == null) {
            return;
        }

        boolean canBeKingAttackedAfterMove = checkCanBeKingAttackedAfterMove(presentX, presentY, nextX, nextY);

        if (canBeKingAttackedAfterMove) {
            return;
        }

        boolean isMoveSuccessful = figure.move(presentX, presentY, nextX, nextY);
        if (figureSymbolLowerCase == 'k' && isMoveSuccessful) {
            updateKingCoordinates(figure, nextX, nextY);
        }
        if(figureSymbolLowerCase == 'P' || figureSymbolLowerCase == 'p') {
            model.pawn.promotion(nextX, nextY, model.getFigures(), scanner);
            model.pawn.beat(presentX, presentY, nextX, nextY);
            model.pawn.enPassant(presentX, presentY, nextX, nextY);
        }

        if (checkIsGameOver(!figure.checkIsFigureWhite(nextX, nextY))) {
            System.exit(0);
        }
    }

    public boolean checkCanBeKingAttackedAfterMove(int startX, int startY, int endX, int endY) {
        char startFigure = model.board[startY][startX];
        char endFigure = model.board[endY][endX];
        boolean isStartFigureWhite = Character.isUpperCase(startFigure);
        int[] kingCoordinates = Arrays.copyOf(isStartFigureWhite ? model.whiteKingCoordinates : model.blackKingCoordinates, 2);
        boolean canBeKingAttackedAfterMove;

        model.board[startY][startX] = '.';
        model.board[endY][endX] = startFigure;
        if (Character.toLowerCase(startFigure) == 'k') {
            kingCoordinates = new int[] {endX, endY};
        }

        canBeKingAttackedAfterMove = model.king.checkIsFieldAttacked(isStartFigureWhite, kingCoordinates);

        model.board[startY][startX] = startFigure;
        model.board[endY][endX] = endFigure;
        return canBeKingAttackedAfterMove;
    }

    protected void updateKingCoordinates(Figure figure, int newX, int newY) {
        boolean isKingWhite = figure.checkIsFigureWhite(newX, newY);

        if (isKingWhite) {
            model.whiteKingCoordinates = new int[] {newX, newY};
        }
        if (!isKingWhite) {
            model.blackKingCoordinates = new int[] {newX, newY};
        }
    }

    protected boolean checkIsGameOver(boolean isCheckForWhite) {
        boolean isCheckmate = checkIsCheckmate(isCheckForWhite);
        boolean isStalemate = checkIsStalemate(isCheckForWhite);

        if (isCheckmate) {
            System.out.println("Мат!");
            return true;
        }

        if (isStalemate) {
            System.out.println("Пат");
            return true;
        }

        return false;
    }

    protected boolean checkIsCheckmate(boolean isCheckForWhite) {
        int[] kingCoordinates = isCheckForWhite ? model.whiteKingCoordinates : model.blackKingCoordinates;

        if (!model.king.checkIsFieldAttacked(isCheckForWhite, kingCoordinates)) {
            return false;
        }

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                boolean isWhite = Character.isUpperCase(model.board[y][x]);
                if (isWhite != isCheckForWhite || model.board[y][x] == '.') {
                    continue;
                }

                if (checkCanFigureAvoidCheckmate(x, y, isCheckForWhite, kingCoordinates)) {
                    return false;
                }
            }
        }

        return true;
    }

    protected boolean checkIsStalemate(boolean isCheckForWhite) {
        int[] kingCoordinates = isCheckForWhite ? model.whiteKingCoordinates : model.blackKingCoordinates;

        if (model.king.checkIsFieldAttacked(isCheckForWhite, kingCoordinates)) {
            return false;
        }

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                boolean isWhite = Character.isUpperCase(model.board[y][x]);
                if (isWhite != isCheckForWhite || model.board[y][x] == '.') {
                    continue;
                }

                if (checkCanFigureAvoidCheckmate(x, y, isCheckForWhite, kingCoordinates)) {
                    return false;
                }
            }
        }

        return true;
    }



    protected boolean checkCanFigureAvoidCheckmate(int startX, int startY, boolean isWhite, int[] kingCoordinates) {
        Figure figure = model.figures.get(Character.toLowerCase(model.board[startY][startX]));

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                char attackedField = model.board[y][x];

                if (startX == x && startY == y) {
                    continue;
                }
                if (Character.toLowerCase(model.board[startY][startX]) == 'k') {
                    kingCoordinates[0] = x;
                    kingCoordinates[1] = y;
                }

                boolean isMoveSuccessful = figure.imitateMove(startX, startY, x, y);
                boolean isKingAttacked = model.king.checkIsFieldAttacked(isWhite, kingCoordinates);

                if (isMoveSuccessful) {
                    model.board[startY][startX] = model.board[y][x];
                    model.board[y][x] = attackedField;
                }
                if (Character.toLowerCase(model.board[startY][startX]) == 'k') {
                    kingCoordinates[0] = startX;
                    kingCoordinates[1] = startY;
                }
                if (isMoveSuccessful && !isKingAttacked) {
                    return true;
                }
            }
        }
        return false;
    }
}
