public class TowerHopper {
    /*
    There is an array that represents heights of towers
    We start from the first left tower and we can jump up to 'current tower height' steps forward.
    If the current tower has the height e.g. equal to 3, we can jump either to 3 next towers.
    The problem is if we can jump over to the right end of the array.
     */

    int[] towers = {4, 2, 0, 0, 2, 0};
    int startIndex = 0;
    Boolean[] isHopable = new Boolean[towers.length];

    public static void main(String[] args) {
        new TowerHopper().doJob();
    }

    void doJob() {
        System.out.println(isHopable(0));
    }

    boolean isHopable(int n) {
        if (n >= towers.length) return true;
        if (isHopable[n] != null) return isHopable[n];
        for (int i = 1; i <= towers[n]; i++) {
            if (isHopable(n + i)) {
                System.out.println(n);
                return true;
            }
        }
        return false;
    }
}
