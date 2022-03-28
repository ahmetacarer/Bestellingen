import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class RouteCalc {

    private final int EPOCHS;
    private final int KANDIDATEN;
    private final int TOTALDEST = 250;

    private int[] destinations;
    private int[] packages;
    private int[][] distances;
    private int epochTeller;

    // alle kandidaatroutes binnen een epoch
    private ArrayList<KandidaatRoute> kandidaatRoutes;

    public RouteCalc(int epochs, int kandidaten) {
        EPOCHS = epochs;
        KANDIDATEN = kandidaten;
        epochTeller = 0;
        kandidaatRoutes = new ArrayList<>();
    }

    public void readSituation(String file) {
        File situationfile = new File(file);
        Scanner scan = null;
        try {
            scan = new Scanner(situationfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int size = scan.nextInt();
        destinations = new int[size];
        packages = new int[size];
        distances = new int[TOTALDEST][TOTALDEST];

        for (int i = 0; i < size; i++) {
            destinations[i] = scan.nextInt();
        }
        for (int i = 0; i < size; i++) {
            packages[i] = scan.nextInt();
        }
        for (int i = 0; i < TOTALDEST; i++) {
            for (int j = 0; j < TOTALDEST; j++) {
                distances[i][j] = scan.nextInt();
            }
        }
    }

    public void bepaalRoute() {
        KandidaatRoute besteKandidaat = kandidaatRoutes.get(0);
        // naar een for loop veranderen
        for (KandidaatRoute k:
                kandidaatRoutes) {
            if (k.compareTo(besteKandidaat) > 0)
                besteKandidaat = k;
        }

        System.out.println("BESTE ROUTE EN SCORE");

        for (int i = 0; i < destinations.length; i++) {
            System.out.printf("%d ", destinations[besteKandidaat.getRoute()[i]]);
        }
        System.out.printf("%n %d", besteKandidaat.getScore());
    }

    public int aantalDuplicaties(int[] route) {
        return (int) Arrays.stream(route)
                .filter(r -> r >= 0)
                .count();
    }

    public void evalueerKandidaat(KandidaatRoute kandidaatRoute) {
        int score = 0;
        int[] route = kandidaatRoute.getRoute();
        int[] kandidaatDestinations = new int[destinations.length];
        int kandidaatDistance = 0;

        for (int i = 0; i < destinations.length; i++) {
            kandidaatDestinations[i] = destinations[route[i]];
        }

        for (int i = 0; i < kandidaatDestinations.length - 1; i++) {
            kandidaatDistance += distances[kandidaatDestinations[i]][kandidaatDestinations[i + 1]];
        }

        int kandidaatTime = 0;

        for (int i = 0; i < route.length - 1; i++) {
            int distance = distances[kandidaatDestinations[i]][kandidaatDestinations[i + 1]];
            // naar magazijn 1 gaan of dezelfde destination
            if (distance == 0 || packages[route[i + 1]] == 0) {
                score -= 100;
            } else {
                kandidaatTime += distance / packages[route[i + 1]];
            }
        }
        score -= aantalDuplicaties(kandidaatRoute.getRoute()) * 150;

        //•	Supersupersuper heel belangrijk: de route begint op het magazijn (nummer 1)
        if (route[0] == 0)
            score += 1000;

        //•	Belangrijk: de totale afstand die afgelegd is van begin tot eind is zo kort mogelijk
        // score verhoogd op basis van de grootte van de afstand
        score -= kandidaatDistance;

        //•	Een beetje belangrijk: de totale afstand die pakketjes hebben afgelegd (is ongeveer de totale wachttijd) is zo minimaal mogelijk.
        score -= kandidaatTime;

        kandidaatRoute.setScore(score);
    }

    public void evalueerEpoch() {
        kandidaatRoutes.forEach(this::evalueerKandidaat);
    }

    // todo: unique combinations
    public KandidaatRoute randomKandidaat() {
        Random random = new Random();
        int[] route = new int[destinations.length];
        for (int i = 0; i < route.length; i++) {
            route[i] = random.nextInt(destinations.length);
        }
        KandidaatRoute kandidaatRoute = new KandidaatRoute();
        kandidaatRoute.setRoute(route);
        return kandidaatRoute;
    }

    public void startSituatie() {
        IntStream.range(0, KANDIDATEN)
                .forEach(k -> kandidaatRoutes.add(randomKandidaat()));
        while (epochTeller != EPOCHS) {
            System.out.println("-----------EPOCH " + epochTeller + "---------------");
            evalueerEpoch();
            volgendeEpoch();
        }
        bepaalRoute();
    }

    // todo: 1.	Schrijf de methode muteer die een elementaire mutatie uitvoert op een kandidaatoplossing en deze als nieuwe kandidaatoplossing teruggeeft.
    public KandidaatRoute muteer(KandidaatRoute kandidaatRoute) {
        int[] route = kandidaatRoute.getRoute();
        int duplicaties = aantalDuplicaties(route);
        if (route[0] != 0) {
            route[0] = 0;
        } else if (duplicaties > 0) {
            vervangEenDuplicatie(route);
        } else {
            optimalizeerWeg(route);
        }
        return kandidaatRoute;
    }

    private void optimalizeerWeg(int[] route) {
        System.out.println("hello world");
        int[] kandidaatDistances = getKandidaatDistances(getKandidaatDestinations(route));
        int[] indexesWithBiggestDistances = getIndexesOfBiggestDistances(kandidaatDistances);
        route[indexesWithBiggestDistances[0]] = route[indexesWithBiggestDistances[1]];
        route[indexesWithBiggestDistances[1]] = route[indexesWithBiggestDistances[0]];
    }

    private int[] getIndexesOfBiggestDistances(int[] kandidaatDistances)
    {
        int[] biggestDistances = new int[2];
        int[] indexes = new int[2];
        for (int i = 0; i < kandidaatDistances.length; i++) {
            if (kandidaatDistances[i] >= biggestDistances[0]) {
                biggestDistances[0] = kandidaatDistances[i];
                indexes[0] = i + 1;
            }
            else if (kandidaatDistances[i] >= biggestDistances[1]) {
                biggestDistances[1] = kandidaatDistances[i];
                indexes[1] = i + 1;
            }
        }
        return biggestDistances;
    }

    private int[] getKandidaatDestinations(int[] route)
    {
        int[] kandidaatDestinations = new int[route.length];
        for (int i = 0; i < destinations.length; i++) {
            kandidaatDestinations[i] = destinations[route[i]];
        }
        return kandidaatDestinations;
    }

    private int[] getKandidaatDistances(int[] kandidaatDestinations)
    {
        int[] kandidaatDistances = new int[destinations.length - 1];
        for (int i = 0; i < kandidaatDistances.length; i++) {
            int kandidaatDistance = 0;
            for (int j = 0; j < kandidaatDestinations.length - 1; j++) {
                kandidaatDistance += distances[kandidaatDestinations[i]][kandidaatDestinations[i + 1]];
            }
            kandidaatDistances[i] = kandidaatDistance;
        }
        return kandidaatDistances;
    }

    private void vervangEenDuplicatie(int[] route) {
        HashSet<Integer> uniqueNumbers = new HashSet<>();
        HashSet<Integer> duplicates = new HashSet<>();
        ArrayList<Integer> missingNumbers = new ArrayList<>();
        for (int i = 0; i < route.length; i++) {
            int val = route[i];
            if (uniqueNumbers.contains(val)) {
                duplicates.add(val);
            } else {
                uniqueNumbers.add(val);
            }
        }

        IntStream.range(0, route.length)
                .forEach(i -> {
                    if (!uniqueNumbers.contains(i))
                        missingNumbers.add(i);
                });

        for (int i = 0; i < route.length; i++) {
            if (missingNumbers.isEmpty()) {
                break;
            }
            if (duplicates.contains(route[i])) {
                int randomIndex = new Random().nextInt(missingNumbers.size());
                route[i] = missingNumbers.get(randomIndex);
                duplicates.remove(route[i]);
                missingNumbers.remove(randomIndex);
            }
        }
    }


    public void volgendeEpoch() {
        // todo : mutataties en random toevoegingen
        sorteerKandidaten();
        int aantalBesteKandidaten = (kandidaatRoutes.size() * 45) / 100;
        int aantalNieuweKandidaten = kandidaatRoutes.size() / 10;
        // removes the 55% worst candidates
        kandidaatRoutes.subList(0, aantalBesteKandidaten)
                .clear();
        kandidaatRoutes.forEach(k -> k = muteer(k));
        IntStream.range(0, aantalNieuweKandidaten).forEach(i -> kandidaatRoutes.add(randomKandidaat()));
        epochTeller++;
    }

    public void sorteerKandidaten()
    {
        kandidaatRoutes.sort(KandidaatRoute::compareTo);
    }
}
