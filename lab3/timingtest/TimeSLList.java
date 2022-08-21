package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    public static int MAX_POWER = 7;
    public static int OPS = 10000;

    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static double measuregetLastTime(SLList list) {
        Stopwatch sw = new Stopwatch();
        for (int j = 0; j < OPS; j++) {
            list.getLast();
        }
        return sw.elapsedTime();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        for (int i = 0; i <= MAX_POWER; i++) {
            // Make new SLList to test
            SLList<Integer> newList = new SLList<>();

            // For the given SLList, make the list 'length' in length
            int length = (int) Math.pow(2, i) * 1000;
            for (int n = 0; n < length; n++) {
                newList.addFirst(n);
            }
            Ns.addLast(length);
            times.addLast(measuregetLastTime(newList));
            opCounts.addLast(OPS);
        }
        printTimingTable(Ns, times, opCounts);
    }
}
