abstract public class Figure {
    protected char[][] board;

    abstract protected boolean move(int presentX, int presentY, int nextX, int nextY);
    abstract protected boolean imitateMove(int presentX, int presentY, int nextX, int nextY);

    protected boolean checkIsFigureBetweenFields(int startX, int startY, int endX, int endY) {
        int indexIAddition = endX - startX > 0 ? 1 : -1;
        int indexJAddition = endY - startY > 0 ? 1 : -1;
        int i = startX;
        int j = startY;

        while (true) {
            i += i == endX ? 0 : indexIAddition;
            j += j == endY ? 0 : indexJAddition;

            if (i == endX && j == endY) {
                break;
            }

            if (board[j][i] != '.') {
                return true;
            }
        }

        return false;
    }

    protected boolean checkIsFigureWhite(int x, int y) {
        return Character.isUpperCase(board[y][x]);
    }

    protected boolean checkIsFigureBlack(int x, int y) {
        return Character.isLowerCase(board[y][x]);
    }

   protected boolean checkIsFigureTheSameTeam(int presentX, int presentY, int nextX, int nextY) {
        boolean checkIsTwoFigureWhite = checkIsFigureWhite(presentX, presentY) == checkIsFigureWhite(nextX, nextY);
        boolean checkIsTwoFigureBlack = checkIsFigureBlack(presentX, presentY) == checkIsFigureBlack(nextX, nextY);
        if(board[presentY][presentX] == '.' || board[nextY][nextX] == '.') {
           return false;
       }
        else if(checkIsTwoFigureWhite || checkIsTwoFigureBlack) {
            return true;
        }
        return false;
    }

    protected boolean checkCanAttackField(int figureX, int figureY, int fieldX, int fieldY) {
        char field = board[fieldY][fieldX];
        char figure = board[figureY][figureX];
        boolean isMoveSuccessful = imitateMove(figureX, figureY, fieldX, fieldY);

        if(isMoveSuccessful) {
            board[fieldY][fieldX] = field;
            board[figureY][figureX] = figure;
        }

        return isMoveSuccessful;
    }
}
