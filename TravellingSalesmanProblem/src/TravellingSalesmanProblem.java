import java.util.*;

public class TravellingSalesmanProblem {
    private static final double MAX_VALUE = Double.MAX_VALUE;
    private static final int NUMBER_OF_MILLISECONDS_IN_SECOND = 1000000;
    private static final int NUMBER_OF_CITIES = 5;
    private static String ALPHABET_STRING = "ABCDEFGHIJKLMNOPRSTU";
    private static SortedSet<Journey> listOfJourneys = new TreeSet<Journey>();
    private static Stack<Journey> stack = new Stack<Journey>();
    private static Queue<Journey> fifoQueue = new LinkedList<Journey>();
    private static Map<Character, CityLocation> mapOfCities = new HashMap<Character, CityLocation>();
    private static Map<CityLocation, List<String>> visitedCitiesRoutes = new HashMap<CityLocation, List<String>>();
    private static String bestTrack = null;
    private static double bestDistance = MAX_VALUE;

    public static void main(String[] args) {
        initCities();
        initMapWithList();

        double distanceTraveled = 0;
        String trace = "A";
        long startTime = System.nanoTime();
        //double score = greedyAlgorithm(distanceTraveled, trace);
        //double score = searchingInDepth(distanceTraveled, trace);
        //double score = searchingInBreadth(distanceTraveled, trace);
        //double score = algorithmAsterisk(distanceTraveled, trace);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in nanoseconds  : " + timeElapsed);

        System.out.println("Execution time in milliseconds : " + timeElapsed / NUMBER_OF_MILLISECONDS_IN_SECOND);
        System.out.println("Best Found Track: " + bestTrack);
        System.out.println("Best Found Distance " + bestDistance);
    }

    private static double algorithmAsterisk(double distanceTraveled, String trace) {
        double theWay = 0;

        while (true) {
            if (trace.length() == NUMBER_OF_CITIES) {
                double lastTrip = costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A'));
                if (distanceTraveled + lastTrip < bestDistance) {
                    bestDistance = distanceTraveled + lastTrip;
                    bestTrack = trace + "A";
                }
                break;
            }
            double maxValueOfHeuristic = MAX_VALUE;
            Character nextStation = null;

            for (int i = 0; i < NUMBER_OF_CITIES; i++) {
                Character checkCity = ALPHABET_STRING.charAt(i);
                if (trace.contains(String.valueOf(checkCity))) {
                    continue;
                }

                String possibleWay = trace + checkCity;
                double droga = distanceTraveled + costOfJourney(mapOfCities.get(possibleWay.charAt(trace.length() - 1)), mapOfCities.get(checkCity));
                double value = droga * (NUMBER_OF_CITIES - trace.length());
                if (value < maxValueOfHeuristic) {
                    nextStation = checkCity;
                    maxValueOfHeuristic = value;
                    theWay = droga;
                }
                Journey jor = new Journey(droga, possibleWay, value);
                listOfJourneys.add(jor);
            }

            if (maxValueOfHeuristic > listOfJourneys.first().getFunValue() && !listOfJourneys.first().getTrace().contains(trace)) {
                trace = listOfJourneys.first().getTrace();
                distanceTraveled = listOfJourneys.first().getDistance();
                Journey j = listOfJourneys.first();
                listOfJourneys.remove(j);
            } else {
                trace = trace + nextStation;
                distanceTraveled = theWay;
            }
        }
        return 1;
    }

    private static double searchingInBreadth(double distanceTraveled, String trace) {
        while (!(trace.length() == NUMBER_OF_CITIES && fifoQueue.isEmpty())) {
            Journey currentTravelPath = null;
            if (!fifoQueue.isEmpty()) {
                currentTravelPath = fifoQueue.poll();
                distanceTraveled = currentTravelPath.getDistance();
                trace = currentTravelPath.getTrace();
            }

            if (trace.length() == NUMBER_OF_CITIES) {
                if (distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A')) < bestDistance) {
                    bestDistance = distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A'));
                    bestTrack = trace + "A";
                }
                if (fifoQueue.size() == 0) {
                    break;
                }
            }

            if (currentTravelPath.getTrace().length() != NUMBER_OF_CITIES) {
                for (int i = 0; i < NUMBER_OF_CITIES; i++) {
                    Character checkCity = ALPHABET_STRING.charAt(i);
                    if (currentTravelPath.getTrace().contains(String.valueOf(checkCity))) {
                        continue;
                    }
                    String possibleWay = currentTravelPath.getTrace() + checkCity;
                    double newDistance = distanceTraveled + costOfJourney(mapOfCities.get(possibleWay.charAt(currentTravelPath.getTrace().length() - 1)), mapOfCities.get(checkCity));
                    Journey journey = new Journey(newDistance, possibleWay);
                    fifoQueue.add(journey);
                }
            }
        }
        return 1;
    }

    private static double searchingInDepth(double distanceTraveled, String trace) {
        while (true) {
            if (trace.length() == NUMBER_OF_CITIES) {
                if (distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A')) < bestDistance) {
                    bestDistance = distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A'));
                    bestTrack = trace + "A";
                }
                if (stack.size() == 1) {
                    break;
                }
                Journey journey = stack.pop();
                trace = journey.getTrace();
                distanceTraveled = journey.getDistance();
            }
            double costOfJourney = 0;
            double newDistance = distanceTraveled;
            for (int i = 0; i < NUMBER_OF_CITIES; i++) {
                Character checkCity = ALPHABET_STRING.charAt(i);
                if (trace.contains(String.valueOf(checkCity))) {
                    continue;
                }

                if (costOfJourney != 0 && checkCity != null) {
                    String possibleWay = trace.substring(0, trace.length() - 1) + checkCity;
                    double newRoadLength = distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 2)), mapOfCities.get(checkCity));
                    Journey journey = new Journey(newRoadLength, possibleWay);
                    stack.push(journey);

                    List<String> newList = visitedCitiesRoutes.get(mapOfCities.get(journey.trace.charAt(journey.trace.length() - 1)));
                    newList.add(journey.trace);
                    visitedCitiesRoutes.put(mapOfCities.get(checkCity), newList);

                }
                if (checkCity != null && costOfJourney == 0) {
                    List<String> newList = visitedCitiesRoutes.get(mapOfCities.get(checkCity));
                    String possibleTrace = trace + checkCity;
                    if (newList.contains(possibleTrace)) {
                        continue;
                    }
                    costOfJourney = costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get(checkCity));
                    newDistance = distanceTraveled + costOfJourney;
                    trace = trace + checkCity;
                    newList.add(trace);
                    visitedCitiesRoutes.put(mapOfCities.get(checkCity), newList);
                }
            }
            distanceTraveled = newDistance;
        }
        return 1;
    }

    private static double greedyAlgorithm(double distanceTraveled, String trace) {
        if (trace.length() == NUMBER_OF_CITIES) {
            bestDistance = distanceTraveled + costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get('A'));
            bestTrack = trace + "A";
            return bestDistance;
        }
        double cheapestWay = MAX_VALUE;
        Character nextStation = null;
        for (int i = 0; i < NUMBER_OF_CITIES; i++) {
            Character checkCity = ALPHABET_STRING.charAt(i);
            if (trace.contains(String.valueOf(checkCity))) {
                continue;
            }

            double costOfJourney = costOfJourney(mapOfCities.get(trace.charAt(trace.length() - 1)), mapOfCities.get(checkCity));
            if (costOfJourney < cheapestWay) {
                nextStation = checkCity;
                cheapestWay = costOfJourney;
            }
        }
        distanceTraveled += cheapestWay;
        trace = trace + nextStation;
        return greedyAlgorithm(distanceTraveled, trace);
    }

    private static double costOfJourney(CityLocation coordinates, CityLocation coordinates2) {
        return Math.sqrt(Math.pow(coordinates2.getX() - coordinates.getX(), 2) + Math.pow(coordinates2.getY() - coordinates.getY(), 2));
    }

    private static int getRandomDoubleBetweenRange(int min, int max) {
        int x = (int) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }

    private static double checkLengthOfRoad(String path) {
        double score = 0;
        for (int i = 0; i < path.length() - 1; i++) {
            score += costOfJourney(mapOfCities.get(path.charAt(i)), mapOfCities.get(path.charAt(i + 1)));
        }
        return score;
    }

    private static void initCities() {
        mapOfCities.put('A', new CityLocation(-4, -4));
        mapOfCities.put('B', new CityLocation(-1, 3));
        mapOfCities.put('C', new CityLocation(1, 2));
        mapOfCities.put('D', new CityLocation(4, 5));
        mapOfCities.put('E', new CityLocation(1, 5));

        mapOfCities.put('F', new CityLocation(3, 2));
        mapOfCities.put('G', new CityLocation(-1, 2));
        mapOfCities.put('H', new CityLocation(-4, 3));
        mapOfCities.put('I', new CityLocation(4, 2));
        mapOfCities.put('J', new CityLocation(1, -3));

        mapOfCities.put('K', new CityLocation(-5, -8));
        mapOfCities.put('L', new CityLocation(-2, 3));
        mapOfCities.put('M', new CityLocation(-8, 1));
        mapOfCities.put('N', new CityLocation(2, 6));
        mapOfCities.put('O', new CityLocation(3, -3));

        mapOfCities.put('P', new CityLocation(-5, -8));
        mapOfCities.put('R', new CityLocation(-2, 3));
        mapOfCities.put('S', new CityLocation(-8, 1));
        mapOfCities.put('T', new CityLocation(2, 6));
        mapOfCities.put('U', new CityLocation(3, -3));
    }

    private static void initMapWithList() {
        List<String> newList = new ArrayList<String>();
        visitedCitiesRoutes.put(mapOfCities.get('A'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('B'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('C'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('D'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('E'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('F'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('G'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('H'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('I'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('J'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('K'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('L'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('M'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('N'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('O'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('P'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('R'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('S'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('T'), newList);
        visitedCitiesRoutes.put(mapOfCities.get('U'), newList);
        stack.push(new Journey(0, "A"));
        fifoQueue.add(new Journey(0, "A"));
    }
}
