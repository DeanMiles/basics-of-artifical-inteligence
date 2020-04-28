import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//Alan Franas
public class Perceptron {

    //"Ax + By + C = 0";
    private static List<Point> listOfTrainingCoordinates = new ArrayList<Point>();
    private static List<Point> listOfTestCoordinates = new ArrayList<Point>();
    private static int MIN_RANGE = -10;
    private static int MAX_RANGE = 10;
    private static int NUMBER_OF_EPOCHS = 15;
    private static int NUMBER_OF_TRAINING_POINTS = 40;
    private static int NUMBER_OF_TEST_POINTS = 80;
    private static double LEARNING_RATE = 0.2;
    private static double CHANGE_LEARNING_RATE = 0.01;
    public static int[] ATTRIBUTES;
    public static double[] score;
    public static double[] weight;

    public static void main(String[] args) {
        chooseArguments();
        prepareCollection(listOfTrainingCoordinates, NUMBER_OF_TRAINING_POINTS);
        prepareCollection(listOfTestCoordinates, NUMBER_OF_TEST_POINTS);
        double localError = 0;
        int output;
        initWeights();

        for (int i = 0; i < NUMBER_OF_EPOCHS; i++) {
            if (LEARNING_RATE > CHANGE_LEARNING_RATE)
                LEARNING_RATE -= CHANGE_LEARNING_RATE;

            for (Point point : listOfTrainingCoordinates) {
                output = calculateOutput(weight, point);
                localError = (point.getIsBehind() ? 1 : 0) - output;
                if (localError != 0) {
                    weight[0] += LEARNING_RATE * localError * point.getX();
                    weight[1] += LEARNING_RATE * localError * point.getY();
                    weight[2] += LEARNING_RATE * localError;
                }
            }

            int counter = 0;
            for (Point point : listOfTestCoordinates) {
                output = calculateOutput(weight, point);
                if (output == (point.getIsBehind() ? 1 : 0)) {
                    counter++;
                }
            }
            score[i] = (counter * 100) / NUMBER_OF_TEST_POINTS;
            System.out.println("Algorithm efficiency for the era " + i + " is: " + score[i] + "%");
        }

        System.out.println(weight[0] + "   " + weight[1] + "    " + weight[2]);
        displayingPointsAfterTeaching();
    }

    /**
     * displays Points after teaching
     */
    private static void displayingPointsAfterTeaching(){
        for (int i = 0; i < score.length; i++) {
            System.out.print(score[i] + ' ');
            if (i % 10 == 0)
                System.out.println('\n');
        }
    }

    /**
     * prepares weights, inits arrays
     */
    private static void initWeights() {
        Random r = new Random();
        weight = new double[3];
        score = new double[NUMBER_OF_EPOCHS];
        weight[0] = r.nextInt() % 2;
        weight[1] = r.nextInt() % 2;
        weight[2] = r.nextInt() % 2;
    }

    /**
     * prepares collection with rand points
     * @param list   List to generate points
     * @param length number of elements in List
     */
    private static void prepareCollection(List<Point> list, int length) {
        Random r = new Random();

        while (list.size() < length) {
            int randomValue = r.nextInt((MAX_RANGE - MIN_RANGE) + 1) + MIN_RANGE;
            int randomValue2 = r.nextInt((MAX_RANGE - MIN_RANGE) + 1) + MIN_RANGE;
            Point point = new Point(randomValue, randomValue2);

            if (!list.contains(point)) {
                list.add(point);
            }
        }
    }

    /**
     * sets arguments linear function
     */
    private static void chooseArguments() {
        System.out.println("Podaj argumenty prostej \"Ax + By + C = 0\" w formacie [A,B,C]");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String[] arrOfStr = myObj.nextLine().split(",", 3);

        ATTRIBUTES = new int[3];
        for (int i = 0; i < arrOfStr.length; i++) {
            ATTRIBUTES[i] = Integer.parseInt(arrOfStr[i]);
            System.out.println(ATTRIBUTES[i]);
        }
    }

    /**
     * returns either 1 or 0 using function
     *
     * @param weights the array of weights
     * @param p       the x and y input value
     * @return 1 or 0
     */
    private static int calculateOutput(double weights[], Point p) {
        double sum = p.getX() * weights[0] + p.getY() * weights[1] + weights[2];
        return sum > 0 ? 1 : 0;
    }
}

