import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int repeat = 10;

        while (true) {
            int[] choices = getInputs(); // [N, M, T] where N is array size, M is method type, and T is test type.
            if (notValid(choices)) return; // check if inputs are valid
            int[] array = createArray(choices[0]); // create array
            float medianTime = sortArray(array, choices[1], choices[0], repeat); // sort and return timing of the sorting process
            printResults(choices, medianTime, repeat);
        }
    }


    /**
     * Sorts the given array, and returns the sorting time.
     * @param array is the unsorted array
     * @param M is the sorting method
     * @param repeat number of times to repeat the sorting
     * @return return the median time of "repeat" timings.
     */
    private static float sortArray(int[] array, int M, int N, int repeat) {
        long start, end;
        long[] times = new long[repeat]; // time the sort 10 times
        int[] sortedArray = new int[array.length];
        int m = String.valueOf(2*N).length(); // max number of digits in the biggest value

        for (int i = 0; i < repeat; i++){
            sortedArray = Arrays.copyOf(array, array.length);

            start = System.nanoTime();
            switch (M){
                case 1: {
                    Sorts.insertionSort(sortedArray);
                    break;
                }
                case 2: {
                    Sorts.quickSort(sortedArray, 0, sortedArray.length-1);
                    break;
                }
                case 3: {
                    Sorts.mergeSort(sortedArray,0, sortedArray.length-1);
                    break;
                }
                case 4: {
                    Sorts.radixSort(sortedArray, m);
                }
                default: return 0;
            }
            end = System.nanoTime();
            times[i] = end - start;
        }
        array = sortedArray;
        return getMedian(times);
    }

    /**
     * returns the median of an array of running times
     * @param times an array of running times
     * @return median of times
     */
    private static float getMedian(long[] times){
        Arrays.sort(times);
        return times[times.length/2];
    }

    /**
     * Creates an array filled with random integers
     * @param N array size
     * @return array of size N
     */
    private static int[] createArray(int N) {
        int[] A = new int[N];
        Random r = new Random();
        for (int i = 0; i < N; i++)
            A[i] = r.nextInt(2*N);
        return A;
    }

    /**
     * Checks if inputs are valid
     * @param choices array of inputs
     * @return true if not valid, and false otherwise
     */
    private static boolean notValid(int[] choices) {
        return choices == null 
                || choices[0] == 0
                || choices[1] == 0
                || choices[2] == 0;
    }

    private static void printResults(int[] choices, float medianTime, int repeat) {
        String method = null;
        switch (choices[1]){
            case 1: method = "Insert Sort"; break;
            case 2: method = "Quick Sort"; break;
            case 3: method = "Merge Sort"; break;
            case 4: method = "Radix Sort"; break;
        }
        switch (choices[2]){
            case 1: {
                System.out.printf("The median time of %d runs for %s is %.2ff milliseconds!\n", repeat, method, medianTime/1000000);
                break;
            }
            case 2: {
                float c = getConstant(medianTime, choices[1], choices[0]);
                System.out.printf("The constant c for %s of array size %d is %.4f (using nanoseconds as time unit)!\n", method, choices[0], c);
                break;
            }
            default:
                System.out.println("Something went wrong! Try again!");
        }
    }


    /**
     * Calculates and returns the constant, c
     * @param medianTime running time of the chosen algorithm
     * @param M type of search algorithm
     * @param N array size
     * @return constant c
     */
    private static float getConstant(float medianTime, int M, int N) {
        switch (M){
            case 1: return ( medianTime / ((float) N * N));
            case 2,3: return medianTime / (N * (float) Math.log(N));
            case 4: {
                System.out.println("J");
                int m = String.valueOf(2 * N).length(); // m is the number of digits in the biggest number of the array
                System.out.println(medianTime);
                System.out.println(N*m);
                return medianTime / ((float) N * m) ;
            }
            default: System.out.println("K");return 0;
        }
    }


    /**
     * Takes inputs from user
     * @return returns all inputs as an array of size 3: [N, M, T], N is array size,
     * M is search algorithm to use, and T is test type
     */
    public static int[] getInputs(){
        int[] inputs = new int[3]; // [N, method, test]
        int in, N, M, T; N = M = T = 0;
        int counter = 1;
        boolean exitProgram = false;
        Scanner scanner = new Scanner(System.in);

        while (!exitProgram){
            System.out.println("\nEnter 0 to exit!");
            switch (counter){
                case 1: {
                    System.out.println("Or Enter array size N");
                    counter++;
                    break;
                }
                case 2: {
                    System.out.println("""
                                        Or 1 for Insert Sort
                                           2 for Quick  Sort
                                           3 for Merge  Sort
                                           4 for Radix  Sort""");
                    counter++;
                    break;
                }
                case 3: {
                    System.out.println("""
                                        Or 1 for Timing
                                           2 for Constant C""");
                    counter++;
                    break;
                }
            }
            System.out.print(">>>: ");
            in = scanner.nextInt();
            if (in == 0) exitProgram = true;
            else {
                switch (counter){
                    case 2 : {
                        N = in;
                        break;
                    }
                    case 3 : {
                        M = in;
                        break;
                    }
                    case 4 : {
                        T = in;
                        inputs[0] = N;
                        inputs[1] = M;
                        inputs[2] = T;
                        return inputs;
                    }
                }
            }
        }
        return null;
    }
}
