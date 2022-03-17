public class KandidaatRoute implements Comparable {

    private int score;
    private int[] route;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[] getRoute() {
        return route;
    }

    public void setRoute(int[] route) {
        this.route = route;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
