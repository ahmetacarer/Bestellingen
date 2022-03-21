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
    private KandidaatRoute[] kandidaatRoutes;
    private int[] scores;

    public RouteCalc(int epochs, int kandidaten) {
        EPOCHS = epochs;
        KANDIDATEN = kandidaten;
        epochTeller = 0;
        scores = new int[EPOCHS];
        kandidaatRoutes = new KandidaatRoute[KANDIDATEN];
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

    //todo: optimale route bepalen
    public void bepaalRoute() {

    }

    // todo: testen
    public void evalueerKandidaat(KandidaatRoute kandidaatRoute) {
        int score = 0;
        int totaldistance = Arrays.stream(distances)
                                        .flatMapToInt(Arrays::stream)
                                        .sum();
        int totalPackages = Arrays.stream(packages)
                                    .sum();
        int[] route = kandidaatRoute.getRoute();


        int[] kandidaatDestinations = new int[destinations.length];
        int kandidaatDistance = 0;
        for (int i = 0; i < destinations.length; i++) {
            kandidaatDestinations[i] = destinations[route[i]];
        }
        for (int i = 0; i < kandidaatDestinations.length - 1; i++) {
            kandidaatDistance += kandidaatDestinations[i] + kandidaatDestinations[i + 1];
        }

        int kandidaatAverageTimeForAPackage =  kandidaatDistance / Arrays.stream(route)
                                                                         .map(r -> packages[r])
                                                                         .sum();

        //•	Supersupersuper heel belangrijk: de route begint op het magazijn (nummer 1)
        if (route[0] == 1)
            score += totaldistance * 3;

        //•	Belangrijk: de totale afstand die afgelegd is van begin tot eind is zo kort mogelijk
        // score verhoogd op basis van de grootte van de afstand
        score += totaldistance - Arrays.stream(route).sum();

        //•	Een beetje belangrijk: de totale afstand die pakketjes hebben afgelegd (is ongeveer de totale wachttijd) is zo minimaal mogelijk.
        // score increases as kandidaatAverageTimeForAPackage decreases
        score -= totaldistance / totalPackages - kandidaatAverageTimeForAPackage;
        kandidaatRoute.setScore(score);
    }


    public void evalueerEpoch(){
        Arrays.stream(kandidaatRoutes).forEach(k -> {
            evalueerKandidaat(k);
            // score-systeem om de vooruitgang te zien per epoch
            scores[epochTeller] += k.getScore();
        });
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
        while (epochTeller != EPOCHS)
        {
            IntStream.range(0, KANDIDATEN)
                    .forEach(k -> kandidaatRoutes[k] = randomKandidaat());
            evalueerEpoch();
            System.out.println(scores[epochTeller]);
            volgendeEpoch();
        }
    }

    // todo: pas een mutatie toe op een kandidaatroute en geef de gemuteerd kandidaatroute terug.
    public KandidaatRoute muteer(KandidaatRoute kandidaatRoute) {
        return kandidaatRoute;
    }

    public void volgendeEpoch() {
        // todo : mutataties en random toevoegingen
        epochTeller++;
    }
}
