public class Journey implements Comparable<Journey>{
    public double distance;
    public String trace;
    public double funValue;

    Journey(double distance, String trace){
        this.distance = distance;
        this.trace = trace;
    }

    Journey(double distance, String trace, double funValue){
        this.distance = distance;
        this.trace = trace;
        this.funValue = funValue;
    }

    public double getDistance(){
        return distance;
    }

    public String getTrace(){
        return trace;
    }

    public double getFunValue(){
        return funValue;
    }

    @Override
    public int compareTo(Journey journey) {
        if (funValue > journey.getFunValue()) {
            return 1;
        } else if (funValue < journey.getFunValue()) {
            return -1;
        } else {
            return 0;
        }
    }
}
