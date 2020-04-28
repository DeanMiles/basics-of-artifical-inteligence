public class CircleSlice {

    public double from;
    public double upTo;
    public Integer tab[];

    public CircleSlice(double from, double upTo, Integer tab[]){
        this.from = from;
        this.upTo = upTo;
        this.tab = tab;
    }

    public double getFrom(){
        return from;
    }

    public double getUpTp(){
        return upTo;
    }

    public Integer[] getArray(){
        return tab;
    }
}
