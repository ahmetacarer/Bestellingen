import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RouteCalc {

    private final int EPOCHS;
    private final int KANDIDATEN;
    private final int TOTALDEST = 250;

    private int[] destinations;
    private int[] packages;
    private int[][] distances;
    private int epochTeller;

    public RouteCalc(int epochs, int kandidaten) {
        EPOCHS = epochs;
        KANDIDATEN = kandidaten;
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
        int averageDistance = Arrays.stream(distances).flatMapToInt(Arrays::stream).sum() / distances.length;

        int[] route = kandidaatRoute.getRoute();
        //•	Supersupersuper heel belangrijk: de route begint op het magazijn (nummer 1)
        if (route[0] == 1)
            score += 100;

        //•	Belangrijk: de totale afstand die afgelegd is van begin tot eind is zo kort mogelijk
        score += 50 - Arrays.stream(route).sum();

        //•	Een beetje belangrijk: de totale afstand die pakketjes hebben afgelegd (is ongeveer de totale wachttijd) is zo minimaal mogelijk.
        for (int j : route) {
            score += 10 - j;
        }
    }


    public void evalueerEpoch(){}

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
        // todo: genereer een volledige set random kandidaten om het algoritme mee te starten.
        IntStream.of(KANDIDATEN).forEach(k -> System.out.println(k));
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
