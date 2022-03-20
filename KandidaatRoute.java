public class KandidaatRoute implements Comparable<KandidaatRoute> {

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
    public int compareTo(KandidaatRoute o) {
        return getScore() - o.getScore();
    }
}
