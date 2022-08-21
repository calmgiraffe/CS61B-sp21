package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */

// Tabulates the amount of time needed to create a AList of various sizes using the slow addLast method
public class TimeAList {
    public static int MAX_POWER = 10;

    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        /**
         * Iterate from 0 to the size of AList Ns, where size is the number of rows expected in the final column
         * Get the ith item of Ns
         * Get the ith item of times and assign it to 'time'
         * Get the ith item of opCounts and assign it to 'opCount'
         * Print one line of the resulting table
         */
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }
    // static: no instance attributes of TimeAList
    // public: available to classes other than TimeAList
    public static double addLastsTime(int length) {
        Stopwatch sw = new Stopwatch();
        AList<Integer> newList = new AList<>();
        for (int i = 0; i < length; i++) {
            newList.addLast(i);
        }
        return sw.elapsedTime();
    }
    public static void timeAListConstruction() {
        /**
         * Make new ALists Ns, times, opCounts that will store results of Stopwatch
         * Starting from N=1000, use Stopwatch to find out how much time it takes
         */
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        for (int x = 1; x <= MAX_POWER; x++) {
            int length = (int) Math.pow(2, x) * 1000;

            Ns.addLast(length);
            opCounts.addLast(length);
            times.addLast(addLastsTime(length));
        }
        printTimingTable(Ns, times, opCounts);
    }
}
