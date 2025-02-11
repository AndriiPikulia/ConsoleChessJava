public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    protected int getX() {
        return this.x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected int getY() {
        return this.y;
    }

    protected void setY(int y) {
        this.y = y;
    }
}
