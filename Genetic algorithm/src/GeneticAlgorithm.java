import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//ALAN FRANAS

public class GeneticAlgorithm {
    private static Integer POPULATION_NUMBER = 3;
    private static Integer NUMBER_OF_GENERATIONS = 3;
    private static double MUTATION_PROB = 0.4;
    private static double CROSSOVER_PROB = 0.8;
    private static Integer NUMBER_OF_GENES_IN_CHROMOSOME = 7;
    private static double MAXIMUM_FUNCTION = -Double.MAX_VALUE;
    private static double MAX_NUMBER = -Double.MAX_VALUE;
    private static Integer[][] array = new Integer[POPULATION_NUMBER][NUMBER_OF_GENES_IN_CHROMOSOME];

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < POPULATION_NUMBER; i++) {
            initArg(array[i]);
        }

        for (int t = 0; t < NUMBER_OF_GENERATIONS; t++) {
            for (int i = 0; i < POPULATION_NUMBER; i++) {
                array = rulettka(array, POPULATION_NUMBER);

                if (flip(CROSSOVER_PROB)) {
                    int second = r.nextInt(POPULATION_NUMBER);
                    crossover(array[i], array[second], 7);
                }

                if (flip(MUTATION_PROB)) mutation(array[i]);
            }
        }

        System.out.println("Score - final population: ");
        for (int i = 0; i < POPULATION_NUMBER; i++) {
            print(array[i], NUMBER_OF_GENES_IN_CHROMOSOME);
        }
        System.out.println("The highest value: " + MAXIMUM_FUNCTION);
        System.out.println("The highest argument: " + MAX_NUMBER);
    }

    private static Integer[][] rulettka(Integer[][] array, int n) {
        double percent_score[] = new double[n];
        Integer parentPool[][] = new Integer[n][];
        double sum = 0, from = 0;
        Random r = new Random();
        ArrayList<CircleSlice> roulette = new ArrayList<CircleSlice>();

        for (int i = 0; i < n; i++) {
            percent_score = new double[n];
            for (int j = 0; j < n; j++) {
                sum += adaptationFunction(array[j], true);
            }

            for (int j = 0; j < n; j++) {
                percent_score[j] = adaptationFunction(array[j], true) * 100 / sum;
                roulette.add(new CircleSlice(from, from + percent_score[j], array[j]));
                from += percent_score[j];
            }

            for (int j = 0; j < n; j++) {
                parentPool[j] = returnTheValueOfTheWheel(r.nextInt(100) + 1, roulette);
            }

            for (int k = 0; k < array.length; k++) {
                for (int j = 0; j < array[k].length; j++) {
                    array[k][j] = parentPool[k][j];
                }
            }

            parentPool = new Integer[POPULATION_NUMBER][NUMBER_OF_GENES_IN_CHROMOSOME];
            roulette = new ArrayList<CircleSlice>();
            from = 0;
            sum = 0;
        }
        return array;
    }

    private static Integer[] returnTheValueOfTheWheel(int value, List<CircleSlice> list) {
        for (CircleSlice slice : list) {
            if (slice.getFrom() < value && slice.getUpTp() >= value) {
                return slice.getArray();
            }
        }
        return list.get(0).getArray();
    }

    private static void print(Integer array[], Integer n) {
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
        }
        System.out.println(" = " + adaptationFunction(array, false));
    }

    private static boolean flip(double p) {
        Random r = new Random();
        return p < r.nextGaussian();
    }

    private static void mutation(Integer array[]) {
        Random r = new Random();
        int position = r.nextInt(NUMBER_OF_GENES_IN_CHROMOSOME);
        array[position] = 1 - array[position];
    }

    private static double adaptationFunction(Integer array[], Boolean isNotLinear) {
        String number = "";
        for (int i = 0; i < array.length; i++) {
            number += Integer.toString(array[i]);
        }
        Integer x = Integer.parseInt(number, 2);
        //f(x) = 2(x2 + 1)
        double s = 2 * ((x * x) + 1);
        if (isNotLinear) {
            s = Math.pow(s, 2);
        }
        if (s > MAXIMUM_FUNCTION) {
            MAXIMUM_FUNCTION = s;
            MAX_NUMBER = x;
        }
        return s;
    }

    private static void initArg(Integer array[]) {
        Random rand = new Random();
        for (int i = 0; i < NUMBER_OF_GENES_IN_CHROMOSOME; i++) {
            array[i] = rand.nextInt(2);
        }
    }

    private static void crossover(Integer firstArray[], Integer secondArray[], Integer n) {
        Random rand = new Random();
        int position = rand.nextInt(n);
        for (int i = position; i < n; i++) {
            int p = firstArray[i];
            firstArray[i] = secondArray[i];
            secondArray[i] = p;
        }
    }
}
