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
        moveFigure(board, figureSymbol, presentLetterKeyToNumber, presentNumberCoordinate, nextLetterKeyToNumber, nextNumberCoordinate);

        return board;
    }

    public void moveFigure(char [][] board, char figureSymbol, int presentX, int presentY, int nextX, int nextY) {
        char figureSymbolLowerCase = Character.toLowerCase(figureSymbol);
        Figure figure = model.figures.get(figureSymbolLowerCase);
        System.out.println(figureSymbolLowerCase);
        if (figure == null) {
            System.out.println("12");
            return;
        }

        boolean canBeKingAttackedAfterMove = checkCanBeKingAttackedAfterMove(presentX, presentY, nextX, nextY);

        if (canBeKingAttackedAfterMove) {
            System.out.println("123");
            return;
        }

        boolean isMoveSuccessful = figure.move(presentX, presentY, nextX, nextY);

        if(!isMoveSuccessful) {
            System.out.println("1");
            return;
        }

        if(figureSymbolLowerCase != 'k' && figureSymbol != 'K' && figureSymbolLowerCase != 'p' && figureSymbol != 'P') {
            return;
        }

       /* int countKingMoves = King.getCountKingMoves();
        int countRookMoves = Rook.getCountRookMoves();
        System.out.println("countKingMoves" + countKingMoves);
        System.out.println("countRookMoves" + countRookMoves);
*/
        if (figureSymbolLowerCase == 'k' || figureSymbol == 'K' /*&& countKingMoves == 2 && countRookMoves == 0*/) {
            boolean isRogueKingMoveRight = (nextX == presentX + 2);
            boolean isRogueKingMoveLeft = (nextX == presentX - 2);

            boolean countLeftBlackRookMoves = Rook.getLeftBlackRook();
            boolean countRightBlackRookMoves = Rook.getRightBlackRook();
            boolean countLeftWhiteRookMoves = Rook.getLeftWhiteRook();
            boolean countRightWhiteRookMoves = Rook.getRightWhiteRook();

            boolean isLeftRookGoingToMove = (!countLeftBlackRookMoves && presentY == 7) || (!countLeftWhiteRookMoves && presentY == 0);
            boolean isRightRookGoingToMove = (!countRightBlackRookMoves && presentY == 7) || (!countRightWhiteRookMoves && presentY == 0);

            if (isRogueKingMoveLeft && ((!countLeftBlackRookMoves && presentY == 7) || (!countLeftWhiteRookMoves && presentY == 0))) {
                System.out.println("Походили королем вліво");
                board[nextY][nextX+1] = board[presentY][0];
                board[presentY][0] = '.';
                updateKingCoordinates(figure, nextX, nextY);
            }

            else if (isRogueKingMoveRight && ((!countRightBlackRookMoves && presentY == 7) || (!countRightWhiteRookMoves && presentY == 0))) {
                System.out.println("Походили королем вправо");
                board[nextY][nextX-1] = board[presentY][7];
                board[presentY][7] = '.';
                updateKingCoordinates(figure, nextX, nextY);
            }
            else if(!isLeftRookGoingToMove && isRogueKingMoveLeft || !isRightRookGoingToMove && isRogueKingMoveRight) {
                System.out.println("222");
                board[presentY][presentX] = board[nextY][nextX];
                board[nextY][nextX] = '.';
            }
            else {
                System.out.println("Походили королем на 1 клітинку");
                updateKingCoordinates(figure, nextX, nextY);
            }

            if (checkIsCheckmate(!figure.checkIsFigureWhite(nextX, nextY))) {
                System.out.println("Checkmate");
            }
        }

        else if(figureSymbolLowerCase == 'P' || figureSymbolLowerCase == 'p') {
            System.out.println("Походили пішкою");
            model.pawn.promotion(nextX, nextY, model.getFigures(), scanner);
            model.pawn.beat(presentX, presentY, nextX, nextY);
            model.pawn.enPassant(presentX, presentY, nextX, nextY);
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

        canBeKingAttackedAfterMove = checkIsKingAttacked(isStartFigureWhite, kingCoordinates);

        model.board[startY][startX] = startFigure;
        model.board[endY][endX] = endFigure;
        return canBeKingAttackedAfterMove;
    }

    protected boolean checkIsKingAttacked(boolean isKingWhite, int[] kingCoordinates) {
        boolean isAttacked = false;

        outer:
        for(int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                char figureChar = model.board[y][x];
                char figureCharLowerCase = Character.toLowerCase(figureChar);
                boolean isFigureWhite = Character.isUpperCase(figureChar);

                if (isFigureWhite == isKingWhite) {
                    continue;
                }

                if (figureChar != '.') {
                    isAttacked = model.figures.get(figureCharLowerCase).checkCanAttackField(x, y, kingCoordinates[0], kingCoordinates[1]);
                }

                if(isAttacked) {
                    break outer;
                }
            }
        }

        return isAttacked;
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

    protected boolean checkIsCheckmate(boolean isCheckForWhite) {
        int[] kingCoordinates = isCheckForWhite ? model.whiteKingCoordinates : model.blackKingCoordinates;

        for (int x = 0; x < model.board.length; x++) {
            for (int y = 0; y < model.board[0].length; y++) {
                boolean isWhite = Character.isUpperCase(model.board[y][x]);
                if (isWhite != isCheckForWhite || model.board[y][x] == '.') {
                    continue;
                }

                if (checkCanFigureAvoidCheckmate(x, y, isCheckForWhite, kingCoordinates)) {
                    System.out.println("x: " + x + "; y: " + y);
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

                if (Character.toLowerCase(model.board[startY][startX]) == 'k') {
                    kingCoordinates[0] = x;
                    kingCoordinates[1] = y;
                }

                boolean isMoveSuccessful = figure.imitateMove(startX, startY, x, y);
                boolean isKingAttacked = checkIsKingAttacked(isWhite, kingCoordinates);

                if (isMoveSuccessful) {
                    model.board[startY][startX] = model.board[y][x];
                    model.board[y][x] = attackedField;
                }
                if (Character.toLowerCase(model.board[startY][startX]) == 'k') {
                    kingCoordinates[0] = startX;
                    kingCoordinates[1] = startY;
                }
                if (isMoveSuccessful && !isKingAttacked) {
                    System.out.println("x: " + x + "; y: " + y);
                    return true;
                }
            }
        }
        return false;
    }
}
