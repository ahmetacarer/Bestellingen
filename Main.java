import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void genereer(){
        Random rand = new Random();
        int[][] temp = new int[250][250];
        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
                if (i==j){
                    temp[i][j]=0;
                }
                else if (i>j){
                    temp[i][j] = temp[j][i];
                }
                else{
                    temp[i][j] = rand.nextInt(100)+1;
                }
            }
        }

        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
                    System.out.printf("%3d ",temp[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int epochs = 150;
        int kandidaten = 500;
        RouteCalc alg = new RouteCalc(epochs, kandidaten);
        alg.readSituation("invoerbestanden/1.txt");
        alg.startSituatie();
    }
}
