abstract public class Figure {

    protected void move(int presentX, int presentY, int nextX, int nextY){

    };

    protected String checkTeam(char figure) {
        return Character.isLowerCase(figure) ? "black" : "white";
    }


}
