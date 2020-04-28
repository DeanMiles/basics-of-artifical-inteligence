public class CityLocation {
    public int x;
    public int y;

    CityLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString() {
        return " X: " + x + ", Y: " + y;
    }
}
