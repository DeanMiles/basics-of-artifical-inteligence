public class Point {
//Alan Franas
    private final double x;
    private final double y;
    private boolean isBehind;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.isBehind = isBehind(x,y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getIsBehind() {
        return isBehind;
    }

    public boolean isBehind(double x, double y) {
        double a = Perceptron.ATTRIBUTES[0] * x + Perceptron.ATTRIBUTES[1] * y + Perceptron.ATTRIBUTES[2];
        return a >= 0;
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")" + isBehind);
    }
}