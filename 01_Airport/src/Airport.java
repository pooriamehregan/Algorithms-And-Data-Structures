import java.util.Random;
import java.util.Scanner;

public class Airport {
    private final CircularArrayQueue<Airplane> landing;
    private final CircularArrayQueue<Airplane> takeoff;
    int     simTime;    // number of time units to simulate
    int     rejected, landed, tookOff, airportEmpty;
    int     queueSize;
    int     landWait, takeWait;
    double  P_takeoff, P_landing;    // Probability of a flight coming in or going out


    public Airport(String name, int N) {
        queueSize = N;
        landing = new CircularArrayQueue<>(queueSize);
        takeoff = new CircularArrayQueue<>(queueSize);
        rejected = landed = tookOff = airportEmpty = 0;
        landWait = takeWait = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + name + " airport!");
        System.out.print("Simulation time: ");
        simTime = scanner.nextInt();
        System.out.print("Average incoming flights per time unit (< 1.0): ");
        P_takeoff = scanner.nextDouble();
        System.out.print("Average outgoing flights per time unit (< 1.0): ");
        P_landing = scanner.nextDouble();
        scanner.close();

        simulate();
    }

    public Airport() {
        queueSize = 10;
        simTime = 20;
        P_takeoff = P_landing = 0.9;
        landing = new CircularArrayQueue<>(queueSize);
        takeoff = new CircularArrayQueue<>(queueSize);
        rejected = landed = tookOff = airportEmpty = 0;
        landWait = takeWait = 0;

        System.out.println("\nWelcome to Oslo Airport!");
        System.out.println("Simulation time is: " + simTime);
        System.out.println("Average incoming flights per time unit (< 1.0): " + P_takeoff);
        System.out.println("Average outgoing flights per time unit (< 1.0): " + P_landing + "\n");

        simulate();
    }


    private void simulate() {
        int takeoffN, landingN;
        Airplane a;
        boolean tabbed;
        String tab;
        System.out.println();

        for (int i = 1; i <= simTime; i++){
            tabbed = false;
            tab = "";

            landingN = getPoissonRandom(P_landing);
            System.out.printf("%03d: ", i);

            for (int j = 1; j < landingN; j++) {
                if (landing.size() < queueSize) {
                    a = new Airplane(i);
                    landing.enqueue(a);
                    System.out.printf("%sAirplane %03d is ready to land!%n", tab, a.id());
                }
                else {
                    rejected++;
                    System.out.printf("%sRejected! Landing queue is full!%n", tab);
                }
                if (!tabbed) {
                    tabbed = true;
                    tab = "\t ";
                }
            }

            takeoffN = getPoissonRandom(P_takeoff);
            for (int j = 0; j < takeoffN; j++) {
                if (takeoff.size() < queueSize) {
                    a = new Airplane(i);
                    takeoff.enqueue(a);
                    System.out.printf("%sAirplane %03d is ready to take off!%n", tab, a.id());
                }
                else {
                    rejected++;
                    System.out.printf("%sRejected! Take-off queue is full!%n", tab);
                }
                if (!tabbed) {
                    tabbed = true;
                    tab = "\t ";
                }
            }

            if (!landing.isEmpty()) {
                try {
                    a = landing.dequeue();
                    landWait += a.waitingTime(i);
                    landed++;
                    System.out.printf("%sAirplane %03d has landed!%n", tab, a.id());
                }
                catch (Exception e) {e.printStackTrace();}
            }
            else if (!takeoff.isEmpty()) {
                try {
                    a = takeoff.dequeue();
                    takeWait += a.waitingTime(i);
                    tookOff++;
                    System.out.printf("%sAirplane %03d took off!%n",tab, a.id());
                }
                catch (Exception e) {e.printStackTrace();}
            }
            else {
                airportEmpty++;
                System.out.printf("%sAirport is empty!%n", tab);
            }
        }
        System.out.println();
        printResult();
    }

    private void printResult() {
        double emptyPercent = ((double) airportEmpty * 100) / simTime;
        double avLandWait =  ((double) landWait) / (landed + landing.size());
        double avTakeWait = ((double) takeWait) / (tookOff + takeoff.size());

        System.out.printf("Simulation finished after\t\t: %04d%n", simTime);
        System.out.printf("Total processed Airplanes\t\t: %04d%n", (landed + tookOff) );
        System.out.printf("Successful landings\t\t\t\t: %04d%n", landed);
        System.out.printf("Successful take-offs\t\t\t: %04d%n", tookOff);
        System.out.printf("Rejected airplanes\t\t\t\t: %04d%n", rejected);
        System.out.printf("Ready to land\t\t\t\t\t: %04d%n", landing.size());
        System.out.printf("Ready to take-off\t\t\t\t: %04d%n", takeoff.size());
        System.out.printf("Airport was empty\t\t\t\t: %.2f %% %n", emptyPercent);
        System.out.printf("Average waiting on landing\t\t: %.2f%n", avLandWait);
        System.out.printf("Average waiting on take-off\t\t: %.2f%n", avTakeWait);
    }

    private static int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public static void main(String[] args) {
        /* For user defined input, uncomment and use the below statements */
        // int N = 10; // Landing and Takeoff Queue size
        // Airport airport = new Airport("Oslo", N);

        // The below statement uses a default constructor and runs the simulation with predefined values:
        // Simulation time: 20. Average incoming: 0.9. Average outgoing: 0.9.
        // It is just for testing and requires no user input. For custom user input, uncomment and use above statements.
        Airport airport = new Airport();
    }
}
