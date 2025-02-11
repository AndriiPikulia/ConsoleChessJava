abstract public class Figure {
    protected char[][] board;

    abstract protected boolean move(Point present, Point next);
    abstract protected boolean imitateMove(Point present, Point next);

    protected boolean checkIsFigureBetweenFields(Point start, Point end) {
        int indexIAddition = end.getX() - start.getX() > 0 ? 1 : -1;
        int indexJAddition = end.getY() - start.getY() > 0 ? 1 : -1;
        int i = start.getX();
        int j = start.getY();

        while (true) {
            i += i == end.getX() ? 0 : indexIAddition;
            j += j == end.getY() ? 0 : indexJAddition;

            if (i == end.getX() && j == end.getY()) {
                break;
            }

            if (board[j][i] != '.') {
                return true;
            }
        }

        return false;
    }

    protected boolean checkIsFigureWhite(Point p) {
        return Character.isUpperCase(board[p.getY()][p.getX()]);
    }

    protected boolean checkIsFigureBlack(Point p) {
        return Character.isLowerCase(board[p.getY()][p.getX()]);
    }

   protected boolean checkIsFigureTheSameTeam(Point present, Point next) {
        boolean checkIsTwoFigureWhite = checkIsFigureWhite(present) == checkIsFigureWhite(next);
        boolean checkIsTwoFigureBlack = checkIsFigureBlack(present) == checkIsFigureBlack(next);
        if(board[present.getY()][present.getX()] == '.' || board[next.getY()][next.getX()] == '.') {
           return false;
       }
        else return checkIsTwoFigureWhite || checkIsTwoFigureBlack;
   }

    protected boolean checkCanAttackField(Point figurePoint, Point fieldPoint) {
        char field = board[fieldPoint.getY()][fieldPoint.getX()];
        char figure = board[figurePoint.getY()][figurePoint.getX()];
        boolean isMoveSuccessful = imitateMove(figurePoint, fieldPoint);

        if(isMoveSuccessful) {
            board[fieldPoint.getY()][fieldPoint.getX()] = field;
            board[figurePoint.getY()][figurePoint.getX()] = figure;
        }

        return isMoveSuccessful;
    }
}
