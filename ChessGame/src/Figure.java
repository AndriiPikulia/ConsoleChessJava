public class Figure {

    protected String checkTeam(char figure) {
        return Character.isLowerCase(figure) ? "black" : "white";
    }

}
